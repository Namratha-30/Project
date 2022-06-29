package pos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pos.model.data.BrandData;
import pos.model.data.OrderInvoiceData;
import pos.model.form.ReportFilter;
import pos.model.xml.*;
import pos.pojo.*;
import pos.util.DataConversionUtil;
import pos.util.PdfConversionUtil;

import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private InventoryService inventoryService;

    // generates pdf response for all reports
    public byte[] generatePdfResponse(String type, Object... obj) throws Exception {
        if (type.contentEquals("brand")) {
            BrandXmlList brandXmlList = generateBrandList();
            PdfConversionUtil.generateXml(new File("brand.xml"), brandXmlList, BrandXmlList.class);
            return PdfConversionUtil.generatePDF(new File("brand.xml"), new StreamSource("brand.xsl"));
        } else if (type.contentEquals("inventory")) {
            InventoryXmlList inventoryXmlList = generateInventoryList();
            PdfConversionUtil.generateXml(new File("inventory.xml"), inventoryXmlList, InventoryXmlList.class);
            return PdfConversionUtil.generatePDF(new File("inventory.xml"), new StreamSource("inventory.xsl"));
        }
        else if (type.contentEquals("sales")) {

            SaleXmlList saleXmlList = generateSalesList((ReportFilter) obj[0]);
           // System.out.println((ReportFilter) obj[0]);
            if(saleXmlList.getSaleReportDataList().isEmpty()) {
                throw new ApiException("No sales was done in this date range for this particular brand and category pair");
            }
            PdfConversionUtil.generateXml(new File("sales.xml"), saleXmlList, SaleXmlList.class);
            return PdfConversionUtil.generatePDF(new File("sales.xml"), new StreamSource("sales.xsl"));
        }
        else {
            OrderInvoiceXmlList orderInvoiceXmlList = generateInvoiceList((Integer) obj[0]);
            PdfConversionUtil.generateXml(new File("invoice.xml"), orderInvoiceXmlList, OrderInvoiceXmlList.class);
            return PdfConversionUtil.generatePDF(new File("invoice.xml"), new StreamSource("invoice.xsl"));
        }
    }

    //generates list of brands for brand report
    public BrandXmlList generateBrandList() throws ApiException{
        List<BrandPojo> brandPojoList = brandService.getAll();
        List<BrandData> brandDataList = DataConversionUtil.convert(brandPojoList);
        BrandXmlList brandXmlList = new BrandXmlList();
        brandXmlList.setBrandDataList(brandDataList);
        return brandXmlList;
    }

    // Generate inventory list for inventory report
    public InventoryXmlList generateInventoryList() throws ApiException {
        List<InventoryPojo> inventoryPojoList = inventoryService.getAll();
        Map<BrandPojo, Integer> quantityPerBrandPojo = GroupByBrandCategory(inventoryPojoList);
        return DataConversionUtil.convertInventoryReportList(quantityPerBrandPojo);

    }

    // Gets inventory for each brand
    private Map<BrandPojo, Integer> GroupByBrandCategory(List<InventoryPojo> inventoryPojoList) throws ApiException {
        Map<BrandPojo, Integer> map = new HashMap<>();
        for (InventoryPojo inventoryPojo : inventoryPojoList) {
            map.merge(inventoryService.getBrandFromInventory(inventoryPojo), inventoryPojo.getQuantity(), Integer::sum);
        }
        return map;
    }

    //Generate sales list for sales report
    public SaleXmlList generateSalesList(ReportFilter reportFilter) throws Exception {
        List<OrderItemPojo> orderItemPojoList1 = FilterByDate(reportFilter);
        System.out.println(orderItemPojoList1.size());
        Map<String, Integer> quantityPerBrandCategory = getMapQuantity(reportFilter, orderItemPojoList1);
        Map<String, Double> revenuePerBrandCategory = getMapRevenue(reportFilter, orderItemPojoList1);
       // System.out.println(revenuePerBrandCategory);
        return DataConversionUtil.convertSalesList(quantityPerBrandCategory, revenuePerBrandCategory);
    }

    //Getting order items based on date
    private List<OrderItemPojo> FilterByDate(ReportFilter reportFilter) throws ApiException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime startDate = LocalDate.parse(reportFilter.getStartDate(), formatter).atStartOfDay();
        LocalDateTime endDate = LocalDate.parse(reportFilter.getEndDate(), formatter).atStartOfDay().plusDays(1);
        return orderService.getOrderItemInDate(startDate,endDate);
    }

    // Gets quantity from brand
    private Map<String, Integer> getMapQuantity(ReportFilter reportFilter, List<OrderItemPojo> orderItemPojoList) throws ApiException {
        Map<String, Integer> quantityPerBrandCategory = new HashMap<>();
        
        for (OrderItemPojo orderItemPojo : orderItemPojoList) {
            if (Equals(orderService.getBrandFromOrderItem(orderItemPojo).getBrand(), reportFilter.getBrand())
                    && Equals(orderService.getBrandFromOrderItem(orderItemPojo).getCategory(), reportFilter.getCategory())) {
            	if(!quantityPerBrandCategory.containsKey(orderService.getBrandFromOrderItem(orderItemPojo).getCategory())){
					  quantityPerBrandCategory.put(orderService.getBrandFromOrderItem(orderItemPojo).getCategory(),orderItemPojo.getQuantity());
				  }else {
				  quantityPerBrandCategory.put(orderService.getBrandFromOrderItem(orderItemPojo).getCategory(),quantityPerBrandCategory.get(orderService.getBrandFromOrderItem(orderItemPojo).getCategory())+orderItemPojo.getQuantity());
				  }
              //  quantityPerBrandCategory.merge(orderService.getBrandFromOrderItem(orderItemPojo), orderItemPojo.getQuantity(), Integer::sum);
            }
        }
        return quantityPerBrandCategory;
    }

    //Generate revenue based on brand and category
    private Map<String, Double> getMapRevenue(ReportFilter reportFilter, List<OrderItemPojo> orderItemPojoList) throws ApiException {
        Map<String, Double> revenuePerBrandCategory = new HashMap<>();
        for (OrderItemPojo orderItemPojo : orderItemPojoList) {
            if (Equals(orderService.getBrandFromOrderItem(orderItemPojo).getBrand(), reportFilter.getBrand())
                    && Equals(orderService.getBrandFromOrderItem(orderItemPojo).getCategory(), reportFilter.getCategory())) {
            	if(!revenuePerBrandCategory.containsKey(orderService.getBrandFromOrderItem(orderItemPojo).getCategory())){
          		  revenuePerBrandCategory.put(orderService.getBrandFromOrderItem(orderItemPojo).getCategory(),orderItemPojo.getRevenue());
				  }else {
					  revenuePerBrandCategory.put(orderService.getBrandFromOrderItem(orderItemPojo).getCategory(),revenuePerBrandCategory.get(orderService.getBrandFromOrderItem(orderItemPojo).getCategory())+orderItemPojo.getRevenue());
				  }
            	
            	//System.out.println(orderItemPojo.getRevenue());
               // revenuePerBrandCategory.merge(orderService.getBrandFromOrderItem(orderItemPojo), orderItemPojo.getRevenue(), Double::sum);
            }
        }
        return revenuePerBrandCategory;
    }


    private static Boolean Equals(String a, String b) {
        return (a.contentEquals(b) || b.isEmpty());
    }

    //generates invoice of order
    public OrderInvoiceXmlList generateInvoiceList(int orderId) throws Exception {
        List<OrderItemPojo> orderItemPojoList = orderService.getOrderItems(orderId);
        Map<OrderItemPojo,ProductPojo> productPojoList=orderService.getProductPojos(orderItemPojoList);
        OrderInvoiceXmlList orderInvoiceXmlList = DataConversionUtil.convertToInvoiceDataList(orderItemPojoList,productPojoList);
        orderInvoiceXmlList.setOrder_id(orderItemPojoList.get(0).getOrderId());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        orderInvoiceXmlList.setDatetime(orderService.getOrder(orderItemPojoList.get(0).getOrderId()).getDatetime().format(formatter));
        double total = calculateTotal(orderInvoiceXmlList);
        orderInvoiceXmlList.setTotal(total);
        OrderPojo orderPojo= orderService.getOrder(orderId);
        orderPojo.setIsInvoiceGenerated(true);
        orderService.update(orderId,orderPojo);
        return orderInvoiceXmlList;
    }

    //Calculates total cost of order
    private static double calculateTotal(OrderInvoiceXmlList orderInvoiceXmlList) {
        double total = 0;
        for (OrderInvoiceData orderInvoiceData : orderInvoiceXmlList.getOrderInvoiceData()) {
            total += (orderInvoiceData.getMrp() * orderInvoiceData.getQuantity());
        }
        return total;
    }
}