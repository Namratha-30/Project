package pos.util;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import pos.model.data.BrandData;
import pos.model.data.InventoryData;
import pos.model.data.OrderItemData;
import pos.model.data.ProductData;
import pos.model.form.BrandForm;
import pos.model.form.InventoryForm;
import pos.model.form.OrderItemForm;
import pos.model.form.ProductForm;
import pos.model.xml.InventoryXmlList;
import pos.model.xml.OrderData;
import pos.model.xml.OrderInvoiceXmlList;
import pos.model.xml.SaleXmlList;
import pos.pojo.BrandPojo;
import pos.pojo.InventoryPojo;
import pos.pojo.OrderItemPojo;
import pos.pojo.OrderPojo;
import pos.pojo.ProductPojo;
import pos.service.AbstractUnitTest;
import pos.service.ApiException;

public class DataConversionUtilTest extends AbstractUnitTest{

    //declare all the pojo beforehand
	
	  @Before public void Declaration() throws ApiException { declare(); }
	 
    // Testing conversion of brand form to pojo
    @Test
    public void testConvertBranFormTopojo() {
    	BrandForm bform=new BrandForm();
    	bform.setBrand("sbf");
    	bform.setCategory("shfiu");
    	
    	BrandPojo brandpojo=DataConversionUtil.convert(bform);
    	
    	assertEquals(bform.getBrand(), brandpojo.getBrand());
    	assertEquals(bform.getCategory(), brandpojo.getCategory());
    }
    
	/*
	 * @Test public void testConvertBrandPojoToData() { BrandPojo brandpojo=new
	 * BrandPojo(); brandpojo.setBrand("dsfb"); brandpojo.setCategory("dshbf");
	 * 
	 * BrandData bdata=DataConversionUtil.convert(brandpojo);
	 * assertEquals(brandpojo.getBrand(),bdata.getBrand());
	 * assertEquals(brandpojo.getCategory(),bdata.getCategory());
	 * 
	 * }
	 */
    
    // Testing conversion of list of brand pojo to data
    @Test
    public void testListBrandPojoToData() throws ApiException {
        List<BrandPojo> brandPojoList = brandService.getAll();
        List<BrandData> brandDataList = DataConversionUtil.convert(brandPojoList);
        assertEquals(brandPojoList.size(), brandDataList.size());
        assertEquals(brandPojoList.get(0).getBrand(), brandDataList.get(0).getBrand());
        assertEquals(brandPojoList.get(0).getCategory(), brandDataList.get(0).getCategory());
    }

    @Test
    public void testConverListBrandPojoToData() {
    	BrandPojo brandpojo=new BrandPojo();
    	brandpojo.setBrand("dsfb");
    	brandpojo.setCategory("dshbf");
    	
    	List<BrandPojo> brandpojolist=new ArrayList<>();
    	brandpojolist.add(brandpojo);
    	
    	List<BrandData> bdatalist=DataConversionUtil.convert(brandpojolist);
   
    	assertEquals(brandpojo.getBrand(),bdatalist.get(0).getBrand());
    	assertEquals(brandpojo.getCategory(),bdatalist.get(0).getCategory());
    	
    }
    
    @Test
    public void testConvertProductFormToPojo() throws ApiException {
    	ProductForm pform =new ProductForm();
    	pform.setBarcode("111");
    	pform.setMrp(22.00);
    	pform.setName("sdbfy");
    	
    	BrandPojo brandpojo=new BrandPojo();
    	brandpojo.setBrand("hgvhgi");
    	brandpojo.setCategory("dsbfy");
    	brandpojo.setId(1);
    	
    	ProductPojo productpojo=DataConversionUtil.convert(pform,brandpojo);
    	assertEquals(pform.getBarcode(),productpojo.getBarcode());
    	assertEquals(pform.getMrp(),productpojo.getMrp());
    	assertEquals(pform.getName(),productpojo.getName());
    }
    
    
    @Test
    public void testConvertProductPojoToData() throws ApiException {
    	ProductPojo productpojo=new ProductPojo();
    	productpojo.setBarcode("126");
    	productpojo.setMrp(27.3);
    	productpojo.setName("hdvf");
    	productpojo.setId(1);
    	
    	BrandPojo brandpojo=new BrandPojo();
    	brandpojo.setBrand("sjbfi");
    	brandpojo.setCategory("sfb");
    	
    	ProductData pdata=DataConversionUtil.convert(productpojo,brandpojo);
        assertEquals(productpojo.getBarcode(),pdata.getBarcode());
        assertEquals(productpojo.getMrp(),pdata.getMrp());
        assertEquals(brandpojo.getBrand(),pdata.getBrand());
        
    	
    }
    
    @Test
    public void testConvertInventoryFormtoPojo() throws ApiException {
    	InventoryForm invform=new InventoryForm();
    	invform.setBarcode("16387");
    	invform.setQuantity(22);
    	ProductPojo productpojo=new ProductPojo();
    	productpojo.setId(1);
    	
    	InventoryPojo invpojo=DataConversionUtil.convert(invform,productpojo);
    	assertEquals(invform.getQuantity(),invpojo.getQuantity());
    	assertEquals(productpojo.getId(),invpojo.getProductId());
    }
    
    @Test
    public void testConvertInventoryPojoToData() {
    	InventoryPojo invpojo=new InventoryPojo();
    	invpojo.setId(1);
    	invpojo.setQuantity(22);
    	ProductPojo productpojo=new ProductPojo();
    	productpojo.setBarcode("11");
    	
    	InventoryData invdata=DataConversionUtil.convert(invpojo, productpojo);
    	
    	assertEquals(invpojo.getQuantity(),invdata.getQuantity());
    	assertEquals(productpojo.getBarcode(),invdata.getBarcode());
    	
    	
    }
    
    @Test
    public void testConvertOrderItemFormToOrderIemPojo() throws ApiException {
    	OrderItemForm oitemform=new OrderItemForm();
    	oitemform.setBarcode("237");
    	oitemform.setSp(13.3);
    	oitemform.setQuantity(55);
    	
    	ProductPojo productpojo =new ProductPojo();
    	productpojo.setId(1);
    	
    	OrderItemPojo oitempojo=DataConversionUtil.convert(productpojo,oitemform);
    	assertEquals(oitemform.getQuantity(),oitempojo.getQuantity());
    	assertEquals(productpojo.getId(),oitempojo.getProductId());
    	assertEquals(oitemform.getSp(),oitempojo.getSp());
    }
    
    @Test
    public void testConvertOrderItemPojoToData() throws ApiException {
    	OrderItemPojo oitempojo=new OrderItemPojo();
    	oitempojo.setId(1);
    	
    	oitempojo.setSp(13.3);
    	oitempojo.setQuantity(55);
    	oitempojo.setOrderId(1002);
    	
    	
    	ProductPojo productpojo =new ProductPojo();
    	productpojo.setBarcode("111");
    	
    	OrderItemData oitemdata=DataConversionUtil.convert(oitempojo,productpojo);
    	assertEquals(oitempojo.getQuantity(),oitemdata.getQuantity());
    	assertEquals(productpojo.getBarcode(),oitemdata.getBarcode());
    	assertEquals(oitempojo.getSp(),oitemdata.getSp());
    }
    
    @Test
    public void testConvertOrderPojoToData() throws ApiException {
        OrderPojo orderPojo=new OrderPojo();
        orderPojo.setId(1);
        orderPojo.setDatetime(LocalDateTime.now());
        orderPojo.setIsInvoiceGenerated(true);
        OrderData orderData = DataConversionUtil.convert(orderPojo);
        assertEquals(orderPojo.getId(), orderData.getId());
        
    }
 // Testing conversion of list of order items to invoice
    @Test
    public void testOrderItemstoInvoice() throws ApiException {
        List<OrderItemPojo> orderItemPojoList = orderService.getAll();
        Map<OrderItemPojo,ProductPojo> productPojoList=orderService.getProductPojos(this.orderItemPojoList);
        OrderInvoiceXmlList orderInvoiceXmlList = DataConversionUtil.convertToInvoiceDataList(orderItemPojoList,productPojoList);
        assertEquals(orderItemPojoList.size(), orderInvoiceXmlList.getOrderInvoiceData().size());
        assertEquals(orderInvoiceXmlList.getOrderInvoiceData().get(0).getName(), productService.get(orderItemPojoList.get(0).getProductId()).getName());
        assertEquals(orderInvoiceXmlList.getOrderInvoiceData().get(0).getQuantity(), orderItemPojoList.get(0).getQuantity());
        assertEquals(orderInvoiceXmlList.getOrderInvoiceData().get(0).getMrp(), orderItemPojoList.get(0).getSp(), 0.001);
    }
    
    // Testing conversion of list of orderItem forms to pojo
    @Test
    public void testOrderItemsFormtoPojo() throws ApiException {
        OrderItemForm[] orderItemForms = new OrderItemForm[1];
        OrderItemForm orderItemForm = new OrderItemForm();
        orderItemForm.setBarcode(productPojoList.get(0).getBarcode());
        orderItemForm.setQuantity(2);
        orderItemForms[0] = orderItemForm;
        Map<String, ProductPojo> barcode_product = new HashMap<>();
        barcode_product.put(productPojoList.get(0).getBarcode(), productPojoList.get(0));
        barcode_product.put(productPojoList.get(1).getBarcode(), productPojoList.get(1));

        List<OrderItemPojo> orderItemPojoList = DataConversionUtil.convertOrderItemForms(barcode_product, orderItemForms);
        assertEquals(1, orderItemPojoList.size());
        assertEquals(orderItemForms[0].getBarcode(), productService.get(orderItemPojoList.get(0).getProductId()).getBarcode());
        assertEquals(orderItemForms[0].getQuantity(), orderItemPojoList.get(0).getQuantity());
    }
    
    // Test conversion to inventory report list
    @Test
    public void testConvertInventoryReportList() {
        Map<BrandPojo, Integer> quantityPerBrandPojo = new HashMap<>();
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand("brand1");
        brandPojo.setCategory("category1");
        BrandPojo brandPojo1 = new BrandPojo();
        brandPojo1.setBrand("brand2");
        brandPojo1.setCategory("category2");
        quantityPerBrandPojo.put(brandPojo, 1);
        quantityPerBrandPojo.put(brandPojo1, 2);
        InventoryXmlList inv_list = DataConversionUtil.convertInventoryReportList(quantityPerBrandPojo);
        assertEquals(2, inv_list.getInventoryReportData().size());
    }
    

    // Test conversion to sales list
	/*
	 * @Test public void testConvertSalesList() { Map<BrandPojo, Integer>
	 * quantityPerBrandPojo = new HashMap<>(); Map<BrandPojo, Double>
	 * revenuePerBrandCategory = new HashMap<>(); BrandPojo brandPojo = new
	 * BrandPojo(); brandPojo.setBrand("brand1");
	 * brandPojo.setCategory("category1"); BrandPojo brandPojo1 = new BrandPojo();
	 * brandPojo1.setBrand("brand2"); brandPojo1.setCategory("category2");
	 * quantityPerBrandPojo.put(brandPojo, 1); quantityPerBrandPojo.put(brandPojo1,
	 * 2); revenuePerBrandCategory.put(brandPojo, 100.00);
	 * revenuePerBrandCategory.put(brandPojo1, 200.00); SaleXmlList saleXmlList =
	 * DataConversionUtil.convertSalesList(quantityPerBrandPojo,
	 * revenuePerBrandCategory); assertEquals(2,
	 * saleXmlList.getSaleReportDataList().size()); }
	 */
    
	/*
	 * @Test public void testConvertOrderItemFormstoList() {
	 * 
	 * }
	 */
    
}
