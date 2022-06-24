package pos.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import pos.pojo.BrandPojo;

public class BrandServiceTest extends AbstractUnitTest {

	/*
	 * @Before public void Declaration() throws ApiException { declare(); }
	 */

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

	/*
	 * @Test public void testCheck() throws ApiException {
	 * 
	 * 
	 * List<BrandPojo> brandPojoList=new ArrayList<>(); BrandPojo brand1 = new
	 * BrandPojo(); brand1.setBrand("brand"); brand1.setCategory("category1");
	 * brand1.setId(2);
	 * 
	 * brandService.add(brand1); brandPojoList.add(brand1);
	 * 
	 * 
	 * 
	 * try { brandService.add(brand1); fail("ApiException did not occur"); } catch
	 * (ApiException e) { assertEquals(e.getMessage(),
	 * "Brand and Category already exist: "+brand1.getBrand()+" "+brand1.getCategory
	 * ()); } }
	 */

	@Test
	public void testGetCheck() throws ApiException {

		try {
			brandService.getCheck(-2);
			fail("ApiException did not occur");
		} catch (ApiException e) {
			assertEquals(e.getMessage(), "Brand with given ID: " + -2+" does not exist");
		}

	}

	/*
	 * @Test public void testGetBrandPojo() throws ApiException {
	 * 
	 * BrandPojo brandpojo =new BrandPojo(); brandpojo.setBrand(" nams      " );
	 * brandpojo.setCategory("vhgv");
	 * 
	 * brandService.add(brandpojo);
	 * 
	 * BrandPojo brandpojo1 = new BrandPojo(); brandpojo1.setBrand(" qbd      ");
	 * brandpojo1.setCategory("ambsd");
	 * 
	 * try { brandService.getBrandPojo(brandpojo1.getBrand(),
	 * brandpojo1.getCategory()); fail("ApiException did not occur"); } catch
	 * (ApiException e) { assertEquals(e.getMessage(),
	 * "The brand and category combination given does not exist:" +
	 * brandpojo1.getBrand() + " ," + brandpojo1.getCategory()); }
	 * 
	 * }
	 */

}