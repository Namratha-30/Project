package pos.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import pos.pojo.InventoryPojo;
import pos.pojo.ProductPojo;

public class InventoryServiceTest extends AbstractUnitTest{
	
    @Before
    public void Declaration() throws ApiException {
        declare();
    }

	
    //testing addition of an inventory
    @Test
    public void testAdd() throws ApiException {

        InventoryPojo inventoryPojo = getInventoryPojo(productPojoList.get(2));
        List<InventoryPojo> inventoryPojoList = inventoryService.getAll();
        inventoryService.add(inventoryPojo);
        List<InventoryPojo> inventoryPojoList1 = inventoryService.getAll();
        assertEquals(inventoryPojoList.size() + 1, inventoryPojoList1.size());
        assertEquals(inventoryPojo.getProductId(), inventoryService.get(inventoryPojo.getId()).getProductId());
        assertEquals(inventoryPojo.getQuantity(), inventoryService.get(inventoryPojo.getId()).getQuantity());

    }

	
	@Test
	public void testCheck() throws ApiException {
		InventoryPojo pojo=new InventoryPojo();
		
		pojo.setQuantity(-33);
		try {
			inventoryService.check(pojo);
			 fail("ApiException did not occur");
        } catch (ApiException e) {
            assertEquals(e.getMessage(), "Quantity cannot be negative");
        }
		
	}
	

    //testing whether inventory with id exists or not
    @Test()
    public void testCheckIfExistsId() throws ApiException {

        InventoryPojo inventoryPojo = inventoryService.getCheck(inventoryPojoList.get(0).getId());
        assertEquals(inventoryPojoList.get(0).getProductId(), inventoryPojo.getProductId());
        assertEquals(inventoryPojoList.get(0).getQuantity(), inventoryPojo.getQuantity());
    }

    
	
	@Test
	public void testGetCheck() throws ApiException {
		
		 
		  
		  try {
			  inventoryService.getCheck(20);
			  fail("ApiException did not occur");
	        } catch (ApiException e) {
	            assertEquals(e.getMessage(), "Inventory with given ID: " + 20+" does not exist");
	        }
		  
	}
	
	  // Testing validation function
    @Test
    public void testValidate() throws ApiException {
        InventoryPojo inventoryPojo = getInventoryPojo(productPojoList.get(2));
        inventoryService.check(inventoryPojo);
        assertTrue(inventoryPojo.getQuantity() > 0);
    }
	
	 // Testing get by product id
    @Test()
    public void testGetByProductId() throws ApiException {

        InventoryPojo inventoryPojo = inventoryService.getFromProductId(inventoryPojoList.get(0).getProductId());
        assertEquals(inventoryPojoList.get(0).getProductId(), inventoryPojo.getProductId());
        assertEquals(inventoryPojoList.get(0).getQuantity(), inventoryPojo.getQuantity());

    }
	
	  // Testing get for a pojo which does not exist
    @Test()
    public void testGetByIdNotExisting() {

        int id = 5;
        try {
            inventoryService.get(5);
            fail("ApiException did not occur");
        } catch (ApiException e) {
            assertEquals(e.getMessage(), "Inventory with given ID: " + id+" does not exist");
        }

    }
	
    // Testing get by id
    @Test()
    public void testGetById() throws ApiException {

        InventoryPojo inventoryPojo = inventoryService.get(inventoryPojoList.get(0).getId());
        assertEquals(inventoryPojoList.get(0).getProductId(), inventoryPojo.getProductId());
        assertEquals(inventoryPojoList.get(0).getQuantity(), inventoryPojo.getQuantity());

    }
    
	/*
	 * @Test public void testAddList() throws ApiException { InventoryPojo invpojo
	 * =new InventoryPojo(); List<InventoryPojo> inlistpojo=new
	 * ArrayList<InventoryPojo>(); invpojo.setId(1); invpojo.setProductId(2);
	 * invpojo.setQuantity(22); inlistpojo.add(invpojo);
	 * inventoryService.addList(inlistpojo); InventoryPojo
	 * invpojo1=inventoryService.get(invpojo.getId());
	 * assertEquals(invpojo.getQuantity(),invpojo1.getQuantity());
	 * assertEquals(invpojo.getProductId(),invpojo1.getProductId());
	 * 
	 * }
	 */	
	 //returns an inventory
    private InventoryPojo getInventoryPojo(ProductPojo productPojo) {
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setProductId(productPojo.getId());
        inventoryPojo.setQuantity(20);
        return inventoryPojo;
    }
    
  //Testing updation
    @Test()
    public void testUpdate() throws ApiException
    {
    	InventoryPojo inventoryPojo1=inventoryPojoList.get(1);
  		InventoryPojo inventoryPojo=getInventoryPojo(productPojoList.get(1));
  		inventoryService.update(inventoryPojo1.getId(), inventoryPojo);
  		assertEquals(inventoryPojo1.getQuantity(),inventoryPojo.getQuantity());
    }
	
	
	

}
