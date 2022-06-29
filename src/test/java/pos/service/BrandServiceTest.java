package pos.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import pos.model.form.ProductForm;
import pos.pojo.BrandPojo;

public class BrandServiceTest extends AbstractUnitTest {

	
	  @Before public void Declaration() throws ApiException { declare(); }
	 

	// testing normalize
	@Test
	public void testNormalize() throws ApiException {
		BrandPojo brandpojo = new BrandPojo();
		brandpojo.setBrand(" Amul  ");
		brandpojo.setCategory(" SJND ");
		brandService.normalize(brandpojo);

		System.out.println(brandService.add());
		assertEquals("amul", brandpojo.getBrand());
		assertEquals("sjnd", brandpojo.getCategory());

	}

	@Test
	public void testAddList() throws ApiException {
		List<BrandPojo> bpojolist = new ArrayList<>();
		BrandPojo bpojo = new BrandPojo();
		bpojo.setBrand("sbf");
		bpojo.setCategory("jhf");
		bpojolist.add(bpojo);
		brandService.addList(bpojolist);
		BrandPojo brandpojo1 = brandService.get(bpojolist.get(0).getId());
		assertEquals(bpojo.getBrand(), brandpojo1.getBrand());
		assertEquals(bpojo.getCategory(), brandpojo1.getCategory());

	}

	// testing add
	@Test
	public void testAdd() throws ApiException {
		BrandPojo brandpojo = new BrandPojo();
		brandpojo.setBrand(" nams      ");
		brandpojo.setCategory("vhgv");
		System.out.println(brandpojo.getBrand());
		brandService.add(brandpojo);
		BrandPojo brandpojo1 = brandService.get(brandpojo.getId());
		assertEquals(brandpojo.getBrand(), brandpojo1.getBrand());
		assertEquals(brandpojo.getCategory(), brandpojo1.getCategory());

	}

	@Test
	public void testBrandCheck() throws ApiException {
		BrandPojo pojo = new BrandPojo();
		pojo.setBrand("");
		pojo.setCategory("bajdb");

		try {
			brandService.check(pojo);
			fail("ApiException did not occur");
		} catch (ApiException e) {
			assertEquals(e.getMessage(), "Brand name cannot be empty");
		}

	}

	@Test
	public void testCategoryCheck() throws ApiException {
		BrandPojo pojo = new BrandPojo();
		pojo.setBrand("bajdb");
		pojo.setCategory("");

		try {
			brandService.check(pojo);
			fail("ApiException did not occur");
		} catch (ApiException e) {
			assertEquals(e.getMessage(), "Category name cannot be empty");
		}
	}

	

	@Test
	public void testGetCheck() throws ApiException {

		try {
			
			brandService.getCheck(-2);
			
			fail("ApiException did not occur");
		} catch (ApiException e) {
			assertEquals(e.getMessage(), "Brand with given ID: " + -2+" does not exist");
		}

	}
	
	//if given barcode already exists or not
    @Test
    public void testCheckExistsOrNot() throws ApiException{
    	ProductForm pojo=new ProductForm();
    	pojo.setName("product-z");
    	pojo.setMrp(50.0);
    	pojo.setBarcode("1");
    	pojo.setBrandCategory(22);
    	pojo.setBrand("brand-z");
    	pojo.setCategory("category-z");
        List<ProductForm> productformlist=new ArrayList<>();
        productformlist.add(pojo);
        try {
        	brandService.checkExixtsOrNot(productformlist);
        	fail("ApiException did not occur");
		} catch (ApiException e) {
			assertEquals(e.getMessage(), "The following brand and category combinations does not exists: {"+pojo.getBrand()+"="+pojo.getCategory()+"}");
		}
    	
    	
    }
    
    //testing if brand and category pair exists or not
    @Test
    public void testGetBrandPojo() {
    	String brand="brand-z";
    	String category="category-z";
    	try {
    	brandService.getBrandPojo(brand, category);
    	fail("ApiException did not occur");
		} catch (ApiException e) {
			assertEquals(e.getMessage(), "The brand: " + brand + " and category: "+ category+" combination given does not exist");
		}
    	
    }
    
    @Test
    public void testUpdate() throws ApiException {
    	
    	BrandPojo brandpojo=new BrandPojo();
    	brandpojo.setBrand("brand-0");
    	brandpojo.setCategory("category-0");
    	BrandPojo bpojo=brandPojoList.get(0);
    	//System.out.println(bpojo.getId());
    	brandService.update(bpojo.getId(), brandpojo);
    	BrandPojo brandpojo1 = brandService.get(bpojo.getId());
		assertEquals(brandpojo.getBrand(), brandpojo1.getBrand());
		assertEquals(brandpojo.getCategory(), brandpojo1.getCategory());
    	
    }
   //testing if brand and category already exists in check method
	@Test
	public void testCheckList() {
		BrandPojo bpojo=brandPojoList.get(0);
		try {
			brandService.check(bpojo);
			fail("ApiException did not occur");
		} catch (ApiException e) {
			assertEquals(e.getMessage(), "Brand: "+bpojo.getBrand()+" and Category: " +bpojo.getCategory()+" already exist");
		}
	}
	
	@Test
	public void testCheckDuplicates() {
		BrandPojo bpojo=brandPojoList.get(0);
		BrandPojo bpojo1=brandPojoList.get(0);
		List<BrandPojo> bpojolist=new ArrayList<>();
		bpojolist.add(bpojo);
		bpojolist.add(bpojo1);
		try {
			brandService.checkDuplicates(bpojolist);
			fail("ApiException did not occur");
		} catch (ApiException e) {
			assertEquals(e.getMessage(), "Duplicate Items exists in File uploaded{"+bpojo.getBrand()+"="+bpojo.getCategory()+"}");
		}
	}

}