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

	@Before
	public void Declaration() throws ApiException {
		declare();
	}

	// testing normalize function to give lower case and trimmed values
	@Test
	public void testNormalize() throws ApiException {
		BrandPojo brandpojo = new BrandPojo();
		brandpojo.setBrand(" Amul  ");
		brandpojo.setCategory(" SJND ");
		BrandService.normalize(brandpojo);

		assertEquals("amul", brandpojo.getBrand());
		assertEquals("sjnd", brandpojo.getCategory());

	}

	// testings addList method from BrandService
	@Test
	public void testAddList() throws ApiException {
		List<BrandPojo> brandPojoList = new ArrayList<>();
		BrandPojo brandPojo = new BrandPojo();
		brandPojo.setBrand("amul");
		brandPojo.setCategory("milk");
		brandPojoList.add(brandPojo);

		BrandPojo secondBrandPojo = new BrandPojo();
		secondBrandPojo.setBrand("dairy");
		secondBrandPojo.setCategory("chocolate");
		brandPojoList.add(secondBrandPojo);

		brandService.addList(brandPojoList);
		BrandPojo resultBrandPojo = brandService.get(brandPojoList.get(0).getId());
		BrandPojo resultOfSecondBrandPojo = brandService.get(brandPojoList.get(1).getId());
		assertEquals(brandPojo.getBrand(), resultBrandPojo.getBrand());
		assertEquals(brandPojo.getCategory(), resultBrandPojo.getCategory());
		assertEquals(secondBrandPojo.getBrand(), resultOfSecondBrandPojo.getBrand());

	}

	// testing add method from BrandService
	@Test
	public void testAdd() throws ApiException {
		BrandPojo brandPojo = new BrandPojo();
		brandPojo.setBrand(" nams      ");
		brandPojo.setCategory("vhgv");
		brandService.add(brandPojo);
		BrandPojo resultBrandPojo = brandService.get(brandPojo.getId());
		assertEquals(brandPojo.getBrand(), resultBrandPojo.getBrand());
		assertEquals(brandPojo.getCategory(), resultBrandPojo.getCategory());

	}

	// testing if null value of brand passed should get exception
	@Test
	public void testIfNullBrandShouldGetException() throws ApiException {
		BrandPojo brandPojo = new BrandPojo();
		brandPojo.setBrand("");
		brandPojo.setCategory("bajdb");

		try {
			brandService.check(brandPojo);
			fail("ApiException did not occur");
		} catch (ApiException e) {
			assertEquals(e.getMessage(), "Brand name cannot be empty");
		}

	}

	// testing if null value of category passed should get exception
	@Test
	public void testIfNullCategoryShouldGetException() throws ApiException {
		BrandPojo brandPojo = new BrandPojo();
		brandPojo.setBrand("bajdb");
		brandPojo.setCategory("");

		try {
			brandService.check(brandPojo);
			fail("ApiException did not occur");
		} catch (ApiException e) {
			assertEquals(e.getMessage(), "Category name cannot be empty");
		}
	}

	// test if invalid id passed should give exception
	@Test
	public void testIfInvalidIdShouldGetException() throws ApiException {

		try {

			brandService.getCheck(-2);

			fail("ApiException did not occur");
		} catch (ApiException e) {
			assertEquals(e.getMessage(), "Brand with given ID: " + -2 + " does not exist");
		}

	}

	// if given barcode already exists or not
	@Test
	public void testBarcodeCheckExistsOrNot() throws ApiException {
		ProductForm productPojo = new ProductForm();
		productPojo.setName("product-z");
		productPojo.setMrp(50.0);
		productPojo.setBarcode("1");
		productPojo.setBrandCategory(22);
		productPojo.setBrand("brand-z");
		productPojo.setCategory("category-z");
		List<ProductForm> productFormList = new ArrayList<>();
		productFormList.add(productPojo);
		try {
			brandService.checkExixtsOrNot(productFormList);
			fail("ApiException did not occur");
		} catch (ApiException e) {
			assertEquals(e.getMessage(), "The following brand and category combinations does not exists: {"
					+ productPojo.getBrand() + "=" + productPojo.getCategory() + "}");
		}

	}

	// testing if brand and category pair exists or not
	@Test
	public void testGetBrandPojo() {
		String brand = "brand-z";
		String category = "category-z";
		try {
			brandService.getBrandPojo(brand, category);
			fail("ApiException did not occur");
		} catch (ApiException e) {
			assertEquals(e.getMessage(),
					"The brand: " + brand + " and category: " + category + " combination given does not exist");
		}

	}

	// testing update method from BrandService
	@Test
	public void testUpdate() throws ApiException {

		BrandPojo brandPojo = new BrandPojo();
		brandPojo.setBrand("brand-0");
		brandPojo.setCategory("category-0");
		BrandPojo existingBrandPojo = brandPojoList.get(0);
		// System.out.println(bpojo.getId());
		brandService.update(existingBrandPojo.getId(), brandPojo);
		BrandPojo resultBrandPojo = brandService.get(existingBrandPojo.getId());
		assertEquals(brandPojo.getBrand(), resultBrandPojo.getBrand());
		assertEquals(brandPojo.getCategory(), resultBrandPojo.getCategory());

	}

	// testing if brand and category already exists
	@Test
	public void testBrandPojoExistsOrNot() {
		BrandPojo brandPojo = brandPojoList.get(0);
		try {
			brandService.check(brandPojo);
			fail("ApiException did not occur");
		} catch (ApiException e) {
			assertEquals(e.getMessage(),
					"Brand: " + brandPojo.getBrand() + " and Category: " + brandPojo.getCategory() + " already exist");
		}
	}

	// checking if duplicates exists in given list
	@Test
	public void testCheckDuplicates() {
		BrandPojo brandPojo = brandPojoList.get(0);
		BrandPojo duplicateBrandPojo = brandPojoList.get(0);
		List<BrandPojo> brandPojoList = new ArrayList<>();
		brandPojoList.add(brandPojo);
		brandPojoList.add(duplicateBrandPojo);
		try {
			brandService.checkDuplicates(brandPojoList);
			fail("ApiException did not occur");
		} catch (ApiException e) {
			assertEquals(e.getMessage(), "Duplicate Items exists in File uploaded{" + brandPojo.getBrand() + "="
					+ brandPojo.getCategory() + "}");
		}
	}

}