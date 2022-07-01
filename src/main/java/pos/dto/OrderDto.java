package pos.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pos.model.data.OrderItemData;
import pos.model.form.OrderItemForm;
import pos.model.xml.OrderData;
import pos.pojo.InventoryPojo;
import pos.pojo.OrderItemPojo;
import pos.pojo.OrderPojo;
import pos.pojo.ProductPojo;
import pos.service.ApiException;
import pos.service.InventoryService;
import pos.service.OrderService;
import pos.service.ProductService;
import pos.util.DataConversionUtil;

@Service
public class OrderDto {

	@Autowired
	private OrderService orderService;

	@Autowired
	private ProductService productService;

	@Autowired
	private InventoryService inventoryService;

	public void add(OrderItemForm[] orderItemForms) throws ApiException {
		// orderItemForm.setBarcode(orderItemForm.getBarcode().toLowerCase().trim());

		Map<String, ProductPojo> allProductPojoByBarcode = productService.getAllProductPojosByBarcode();

		List<OrderItemPojo> orderItemList = DataConversionUtil.convertOrderItemForms(allProductPojoByBarcode,
				orderItemForms);
		checkQuantityExists(orderItemList);
		orderService.add(orderItemList);
	}

	private void checkQuantityExists(List<OrderItemPojo> orderItemList) throws ApiException {

		List<OrderItemPojo> orderforms = new ArrayList<>();
		List<InventoryPojo> inventoryPojo = inventoryService.getAll();
		List<ProductPojo> productPojo = productService.getAll();
		HashMap<String, Integer> hMapNumbers = new HashMap<String, Integer>();
		int pid = 0;
		String barcode = null;
		for (int i = 0; i < orderItemList.size(); i++) {
			for (int j = 0; j < inventoryPojo.size(); j++) {
				if (orderItemList.get(i).getProductId().equals(inventoryPojo.get(j).getProductId())) {
					if (orderItemList.get(i).getQuantity() > inventoryPojo.get(j).getQuantity()) {
						pid = orderItemList.get(i).getProductId();
						for (ProductPojo productPojo1 : productPojo) {
							if (productPojo1.getId() == pid) {
								barcode = productPojo1.getBarcode();
							}
						}
						hMapNumbers.put(barcode, orderItemList.get(i).getQuantity());
						orderforms.add(orderItemList.get(i));
					}
				}
			}
		}
		if (!hMapNumbers.isEmpty()) {
			throw new ApiException("The inventory is less for the following products: " + hMapNumbers);
		}
	}

	public void addOrderItem(int orderId, OrderItemForm orderItemForm) throws ApiException {
		orderItemForm.setBarcode(orderItemForm.getBarcode().toLowerCase().trim());
		ProductPojo productPojo = productService.getFromBarcode(orderItemForm.getBarcode());
		OrderItemPojo orderItemPojo = DataConversionUtil.convert(productPojo, orderItemForm);
		orderService.addOrderItem(orderId, orderItemPojo);
	}

	public OrderItemData get(int id) throws ApiException {
		OrderItemPojo orderItemPojo = orderService.get(id);
		ProductPojo productPojo = productService.get(orderItemPojo.getProductId());
		return DataConversionUtil.convert(orderItemPojo, productPojo);
	}

	public List<OrderItemData> getAll() throws ApiException {
		List<OrderItemPojo> orderItemPojoList = orderService.getAll();
		List<OrderItemData> orderItemDataList = new ArrayList<>();
		for (OrderItemPojo orderItemPojo : orderItemPojoList) {
			ProductPojo productPojo = productService.get(orderItemPojo.getProductId());
			orderItemDataList.add(DataConversionUtil.convert(orderItemPojo, productPojo));
		}
		return orderItemDataList;
	}

	public List<OrderData> getAllOrders() {
		List<OrderPojo> orderPojoList = orderService.getAllOrders();
		List<OrderData> orderDataList = new ArrayList<>();
		for (OrderPojo orderPojo : orderPojoList) {
			orderDataList.add(DataConversionUtil.convert(orderPojo));
		}
		return orderDataList;
	}

	public OrderData getSingleOrder(int id) throws ApiException {
		OrderPojo orderPojo = orderService.getOrder(id);
		return DataConversionUtil.convert(orderPojo);
	}

	public List<OrderItemData> getOrderItemsByOrderId(int id) throws ApiException {
		List<OrderItemPojo> orderItemPojoList = orderService.getOrderItems(id);
		List<OrderItemData> orderItemDataList = new ArrayList<>();
		for (OrderItemPojo orderItemPojo : orderItemPojoList) {
			ProductPojo productPojo = productService.get(orderItemPojo.getProductId());
			orderItemDataList.add(DataConversionUtil.convert(orderItemPojo, productPojo));
		}
		return orderItemDataList;
	}

	public void update(int id, OrderItemForm orderItemForm) throws ApiException {
		orderItemForm.setBarcode(orderItemForm.getBarcode().toLowerCase().trim());
		ProductPojo productPojo = productService.getFromBarcode(orderItemForm.getBarcode());
		OrderItemPojo orderItemPojo = DataConversionUtil.convert(productPojo, orderItemForm);
		orderService.update(id, orderItemPojo);
	}
}
