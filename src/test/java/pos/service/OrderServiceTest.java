package pos.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import pos.pojo.BrandPojo;
import pos.pojo.OrderItemPojo;
import pos.pojo.OrderPojo;
import pos.pojo.ProductPojo;

public class OrderServiceTest extends AbstractUnitTest {

	// declare all pojo beforehand
	@Before
	public void Declaration() throws ApiException {
		declare();
	}
	  //testing addition of order
    @Test
    public void testAdd() throws ApiException {

        OrderItemPojo orderItemPojo = getOrderItemPojo(productPojoList.get(0), 5, 30.5);
        List<OrderItemPojo> orderItemPojoList3 = new ArrayList<>();
        orderItemPojoList3.add(orderItemPojo);
        List<OrderPojo> orderPojoList= orderService.getAllOrders();
        List<OrderItemPojo> orderItemPojoList = orderService.getAll();
        orderService.add(orderItemPojoList3);
        List<OrderPojo> orderPojoList1 = orderService.getAllOrders();
        List<OrderItemPojo> orderItemPojoList1 = orderService.getAll();

        assertEquals(orderPojoList.size() + 1, orderPojoList1.size());
        assertEquals(orderItemPojoList.size() + 1, orderItemPojoList1.size());
        List<OrderItemPojo> orderItemPojoList2 = orderService.getOrderItems(orderItemPojo.getOrderId());
        assertEquals(orderItemPojoList3.size(), orderItemPojoList2.size());
        assertEquals(orderItemPojo.getOrderId(), orderItemPojoList2.get(0).getOrderId());
        assertEquals(orderItemPojo.getProductId(), orderItemPojoList2.get(0).getProductId());
        assertEquals(orderItemPojo.getQuantity(), orderItemPojoList2.get(0).getQuantity());
        assertEquals(orderItemPojo.getSp(), orderItemPojoList2.get(0).getSp(), 0.001);

    }

    // Testing adding of invalid order
    @Test
    public void testAddWrong() {

        OrderItemPojo orderItemPojo = getWrongOrderItemPojo(productPojoList.get(0));
        List<OrderItemPojo> orderItemPojoList = new ArrayList<>();
        orderItemPojoList.add(orderItemPojo);

        try {
            orderService.add(orderItemPojoList);
            fail("ApiException did not occur");
        } catch (ApiException e) {
            assertEquals(e.getMessage(), "Quantity must be positive");
        }

    }
    // Testing updating of order items
    @Test
    public void testUpdate() throws ApiException {

        OrderItemPojo orderItemPojo= getOrderItemPojo(productPojoList.get(0), 7, 50);
        orderService.update(orderItemPojoList.get(0).getId(), orderItemPojo);
        assertEquals(orderItemPojoList.get(0).getProductId(), orderItemPojo.getProductId());
        assertEquals(orderItemPojoList.get(0).getQuantity(), orderItemPojo.getQuantity());
        assertEquals(orderItemPojoList.get(0).getSp(), orderItemPojo.getSp(), 0.001);
    }

    // Testing getting for order items
    @Test
    public void testGet() throws ApiException {

        OrderItemPojo orderItemPojo = orderService.get(orderItemPojoList.get(0).getId());
        assertEquals(orderItemPojoList.get(0).getOrderId(), orderItemPojo.getOrderId());
        assertEquals(orderItemPojoList.get(0).getProductId(), orderItemPojo.getProductId());
        assertEquals(orderItemPojoList.get(0).getQuantity(), orderItemPojo.getQuantity());
        assertEquals(orderItemPojoList.get(0).getSp(), orderItemPojo.getSp(), 0.001);
    }

    @Test
    public void testAddOrderItem() throws ApiException {
    	OrderItemPojo oitempojo=orderItemPojoList.get(0);
    	//OrderPojo opojo=orderPojoList.get(0);
    	orderService.addOrderItem(orderId, oitempojo);
    	
    	 OrderItemPojo orderItemPojo = orderService.get(oitempojo.getId());
         assertEquals(oitempojo.getOrderId(), orderItemPojo.getOrderId());
         
    	
    }
    
	
	  @Test public void testUpdateOrderPojo() throws ApiException { OrderPojo
	  opojo=orderPojoList.get(0); int id=opojo.getId(); orderService.update(id,
	  opojo); OrderPojo opojo1=orderService.getOrder(id);
	  assertEquals(opojo.getId(),opojo1.getId()); }
	 
	  @Test
	  public void testUpdateInventory() throws ApiException {
		  OrderItemPojo oitempojo=new OrderItemPojo();
		  oitempojo.setProductId(productPojoList.get(2).getId());
		  oitempojo.setQuantity(2);
		  oitempojo.setSp(1000.0);
		  try {
			  orderService.updateInventory(oitempojo,0);
			  fail("ApiException did not occur");
	        } catch (ApiException e) {
	            assertEquals(e.getMessage(), "Inventory for this item: "+ productService.get(oitempojo.getProductId()).getBarcode()+" does not exist");
	        }
	  }
	  
	  //testing when order quantity is more than inventory quantity
	  @Test
	  public void testLessInventory() throws ApiException {
		  OrderItemPojo oitempojo=new OrderItemPojo();
		  oitempojo.setProductId(productPojoList.get(0).getId());
		  oitempojo.setQuantity(800);
		  oitempojo.setSp(1000.0);
		  int quantInInventory=inventoryService.getFromProductId(oitempojo.getProductId()).getQuantity() + 0;
		  try
		  {
			  orderService.updateInventory(oitempojo,0);
			  fail("ApiException did not occur");
	        } catch (ApiException e) {
	            assertEquals(e.getMessage(), "The current product inventory is: "
                        + quantInInventory+" order cannot be placed more than that");
	        }
	  }
	  
	  @Test
	  public void  testGetBrandFromOrderItem() throws ApiException {
		  OrderItemPojo oitempojo=orderItemPojoList.get(0);
		  BrandPojo bpojo1=brandPojoList.get(0);
		  BrandPojo bpojo=orderService.getBrandFromOrderItem(oitempojo);
		  assertEquals(bpojo1.getBrand(),bpojo.getBrand());
	  }
	  
	  @Test
	  public void testCheckIfExistsOrder() {
		  try {
			  orderService.checkIfExistsOrder(220);
			  fail("ApiException did not occur");
	        } catch (ApiException e) {
	            assertEquals(e.getMessage(), "Order with given ID: " + 220+" does not exist");
	        }
	  }
	/*
	 * //testing id productid of OrderItemPojo exists
	 * 
	 * @Test public void testIfOrderProductIfExists() { OrderItemPojo oitempojo=new
	 * OrderItemPojo(); oitempojo.setProductId(-22); oitempojo.setQuantity(2);
	 * oitempojo.setSp(1000.0); try { orderService.check(oitempojo);
	 * fail("ApiException did not occur"); } catch (ApiException e) {
	 * assertEquals(e.getMessage(), "Product with this id does not exist"); } }
	 */
	  
	  //testing if selling price is valid or not
	  @Test
	  public void testIfOrderSellingPrice() {
		  OrderItemPojo oitempojo=new OrderItemPojo();
		  oitempojo.setProductId(productPojoList.get(0).getId());
		  oitempojo.setQuantity(2);
		  oitempojo.setSp(-1000.0);
		  try {
			  orderService.check(oitempojo);
			  fail("ApiException did not occur");
	        } catch (ApiException e) {
	            assertEquals(e.getMessage(),"Selling price must be positive");
	        }
	  }
	  
	  @Test
	  public void testCheckIfExists() {
		  try {
			  orderService.checkIfExists(-22);
			  fail("ApiException did not occur");
	        } catch (ApiException e) {
	            assertEquals(e.getMessage(),"OrderItem with given ID: " + -22+" does not exist");
	        }
	  }

    //returns an orderItem pojo
    private OrderItemPojo getOrderItemPojo(ProductPojo productPojo, int quantity, double sp) {
        OrderItemPojo orderItemPojo = new OrderItemPojo();
        orderItemPojo.setProductId(productPojo.getId());
        orderItemPojo.setQuantity(quantity);
        orderItemPojo.setSp(sp);
        return orderItemPojo;
    }



    private OrderItemPojo getWrongOrderItemPojo(ProductPojo productPojo) {
        OrderItemPojo orderItemPojo = new OrderItemPojo();
        orderItemPojo.setProductId(productPojo.getId());
        orderItemPojo.setQuantity(-5);
        orderItemPojo.setSp(30.0);
        return orderItemPojo;
    }
    
    
    
}
