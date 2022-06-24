package pos.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import pos.pojo.ProductPojo;

public class ProductServiceTest extends AbstractUnitTest {
	

    @Before
    public void Declaration() throws ApiException {
        declare();
    }
	
	//testing normalize 
		@Test
		public void testNormalize() throws ApiException {
			ProductPojo productpojo=new ProductPojo();
			productpojo.setName(" Amuly  ");
			productpojo.setBarcode(" 1132    ");
			productService.normalize(productpojo);
			assertEquals("amuly",productpojo.getName());
			assertEquals("1132", productpojo.getBarcode());
			
		}
		
		//testing add 
		  @Test
		  public void testAdd() throws ApiException { 
			  ProductPojo productpojo =new ProductPojo();
			  productpojo.setName(" nams      " );
			  productpojo.setBarcode("vhgv"); 
			  productpojo.setMrp(22.44);
			  
			
		  
		  productService.add(productpojo);
		  ProductPojo productpojo1=productService.get(productpojo.getId());
		  assertEquals(productpojo.getName(),productpojo1.getName());
		  assertEquals(productpojo.getBarcode(),productpojo1.getBarcode());
		  assertEquals(productpojo.getMrp(),productpojo1.getMrp());

		  }
	
		  @Test
			public void testBarcodeCheck() throws ApiException {
				ProductPojo productpojo=new ProductPojo();
				productpojo.setName("asbd");
				productpojo.setBarcode("");
				
				
				try {
					productService.check(productpojo);
					fail("ApiException did not occur");
			        } catch (ApiException e) {
			            assertEquals(e.getMessage(), "Barcode cannot be empty");
			        }
				
				
			}
		  
		   // Testing get by id
		    @Test()
		    public void testGetById() throws ApiException {

		        ProductPojo productPojo = productService.get(productPojoList.get(0).getId());
		        assertEquals(productPojoList.get(0).getBarcode(), productPojo.getBarcode());
		        assertEquals(productPojoList.get(0).getBrandCategory(), productPojo.getBrandCategory());
		        assertEquals(productPojoList.get(0).getMrp(), productPojo.getMrp(), 0.001);
		        assertEquals(productPojoList.get(0).getName(), productPojo.getName());

		    }
			

		  @Test
			public void testNameCheck() throws ApiException {
				ProductPojo productpojo=new ProductPojo();
				productpojo.setName("");
				productpojo.setBarcode("112");
				
				try {
					productService.check(productpojo);
					fail("ApiException did not occur");
			        } catch (ApiException e) {
			            assertEquals(e.getMessage(), "Name cannot be empty");
			        }
				
				
			}
		  
		  @Test
			public void testMrpCheck() throws ApiException {
				ProductPojo productpojo=new ProductPojo();
				productpojo.setName("bwbfdn");
				productpojo.setBarcode("112");
				productpojo.setMrp(-22.3);
				try {
					productService.check(productpojo);
					fail("ApiException did not occur");
			        } catch (ApiException e) {
			            assertEquals(e.getMessage(), "Mrp cannot be negative or zero");
			        }
				
				
			}
		  @Test
			public void testCheckBarcode() throws ApiException {
				ProductPojo productpojo=new ProductPojo();
				
				productpojo.setBarcode("-112");
				
				try {
					productService.checkBarcode(productpojo.getBarcode());
					fail("ApiException did not occur");
			        } catch (ApiException e) {
			            assertEquals(e.getMessage(), "Product with given barcode: "+ productpojo.getBarcode()+" does not exist");
			        }
				
				
			}
		  
		  
			
			@Test
			public void testGetCheck() throws ApiException {
				ProductPojo productpojo =new ProductPojo();
				 productpojo.setName(" nams      " );
				  productpojo.setBarcode("1122");
				  productpojo.setId(33);
				 
				 
				  try {
					  productService.getCheck(productpojo.getId());
					  fail("ApiException did not occur");
				        } catch (ApiException e) {
				            assertEquals(e.getMessage(), "Product with given ID: " + productpojo.getId()+" does not exist");
				        }
				  
			  
				  
			}
	

}
