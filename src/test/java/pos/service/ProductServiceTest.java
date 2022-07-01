package pos.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import pos.model.form.InventoryForm;
import pos.pojo.BrandPojo;
import pos.pojo.ProductPojo;

public class ProductServiceTest extends AbstractUnitTest {

	@Before
	public void Declaration() throws ApiException {
		declare();
	}

	// testing normalize method from ProductService
	@Test
	public void testNormalize() throws ApiException {
		ProductPojo productPojo = new ProductPojo();
		productPojo.setName(" Amuly  ");
		productPojo.setBarcode(" 1132    ");
		productService.normalize(productPojo);
		assertEquals("amuly", productPojo.getName());
		assertEquals("1132", productPojo.getBarcode());

	}

	// testing add method from ProductService
	@Test
	public void testAdd() throws ApiException {
		ProductPojo productPojo = new ProductPojo();
		productPojo.setName(" nams      ");
		productPojo.setBarcode("vhgv");
		productPojo.setMrp(22.44);

		productService.add(productPojo);
		ProductPojo resultProductPojo = productService.get(productPojo.getId());
		assertEquals(productPojo.getName(), resultProductPojo.getName());
		assertEquals(productPojo.getBarcode(), resultProductPojo.getBarcode());
		assertEquals(productPojo.getMrp(), resultProductPojo.getMrp());

	}

	// testing id barcode is empty should throw exception
	@Test
	public void testIfNullBarcodeShouldGetException() throws ApiException {
		ProductPojo productpojo = new ProductPojo();
		productpojo.setName("asbd");
		productpojo.setBarcode("");

		try {
			productService.check(productpojo);
			fail("ApiException did not occur");
		} catch (ApiException e) {
			assertEquals(e.getMessage(), "Barcode cannot be empty");
		}

	}

	// Testing if valid id passed getting valid result
	@Test()
	public void testGetById() throws ApiException {

		ProductPojo productPojo = productService.get(productPojoList.get(0).getId());
		assertEquals(productPojoList.get(0).getBarcode(), productPojo.getBarcode());
		assertEquals(productPojoList.get(0).getBrandCategory(), productPojo.getBrandCategory());
		assertEquals(productPojoList.get(0).getMrp(), productPojo.getMrp(), 0.001);
		assertEquals(productPojoList.get(0).getName(), productPojo.getName());

	}

	// testing if Name is null should throw exception
	@Test
	public void testIfNullNameShouldGetException() throws ApiException {
		ProductPojo productpojo = new ProductPojo();
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
		ProductPojo productpojo = new ProductPojo();
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
		ProductPojo productpojo = new ProductPojo();

		productpojo.setBarcode("-112");

		try {
			productService.checkBarcode(productpojo.getBarcode());
			fail("ApiException did not occur");
		} catch (ApiException e) {
			assertEquals(e.getMessage(), "Product with given barcode: " + productpojo.getBarcode() + " does not exist");
		}

	}

	@Test
	public void testGetCheck() throws ApiException {
		ProductPojo productpojo = new ProductPojo();
		productpojo.setName(" nams      ");
		productpojo.setBarcode("1122");
		productpojo.setId(33);

		try {
			productService.getCheck(productpojo.getId());
			fail("ApiException did not occur");
		} catch (ApiException e) {
			assertEquals(e.getMessage(), "Product with given ID: " + productpojo.getId() + " does not exist");
		}

	}

	// to check if product with given barcode exists or not
	@Test
	public void testAddBarcode() {
		ProductPojo pojo = productPojoList.get(0);
		try {
			productService.add(pojo);
			fail("ApiException did not occur");
		} catch (ApiException e) {
			assertEquals(e.getMessage(), "Product with given barcode: " + pojo.getBarcode() + " already exists");
		}

	}

	// testing addList
	@Test
	public void testAddList() throws ApiException {
		BrandPojo bpojo = brandPojoList.get(0);
		List<ProductPojo> pojolist = new ArrayList<>();
		ProductPojo pojo = new ProductPojo();
		pojo.setBarcode("111");
		pojo.setMrp(55.0);
		pojo.setBrandCategory(bpojo.getId());
		pojo.setName("product-z");
		pojolist.add(pojo);
		productService.addList(pojolist);
		ProductPojo productpojo1 = productService.get(pojolist.get(0).getId());
		assertEquals(pojo.getName(), productpojo1.getName());
		assertEquals(pojo.getBarcode(), productpojo1.getBarcode());
		assertEquals(pojo.getMrp(), productpojo1.getMrp());

	}

	@Test
	public void testCheckDuplicates() {
		ProductPojo pojo = productPojoList.get(0);
		ProductPojo pojo1 = productPojoList.get(0);
		List<ProductPojo> pojolist = new ArrayList<>();
		pojolist.add(pojo);
		pojolist.add(pojo1);
		try {
			productService.checkDuplicates(pojolist);
			fail("ApiException did not occur");
		} catch (ApiException e) {
			assertEquals(e.getMessage(),
					"Repeated Barcodes exists in File uploaded{" + pojo.getBarcode() + "=" + null + "}");
		}
	}

	@Test
	public void testGetFromBarcode() {
		String barcode = null;
		try {
			productService.getFromBarcode(barcode);
			fail("ApiException did not occur");
		} catch (ApiException e) {
			assertEquals(e.getMessage(), "Barcode cannot be empty");
		}
	}

	@Test
	public void testUpdate() throws ApiException {
		ProductPojo pojo = productPojoList.get(0);
		ProductPojo pojo1 = new ProductPojo();
		pojo1.setBarcode("101");
		pojo1.setId(pojo.getId());
		pojo1.setMrp(20.0);
		pojo1.setName("pojo-edit");
		productService.update(pojo.getId(), pojo1);
		ProductPojo productpojo1 = productService.get(pojo.getId());
		assertEquals(pojo1.getName(), productpojo1.getName());
		assertEquals(pojo1.getBarcode(), productpojo1.getBarcode());
		assertEquals(pojo1.getMrp(), productpojo1.getMrp());
	}

	@Test
	public void testCheckBarcodeExistsOrNot() {
		InventoryForm invform = new InventoryForm();
		invform.setBarcode("barcode");
		invform.setQuantity(22);
		List<InventoryForm> invformlist = new ArrayList<>();
		invformlist.add(invform);
		try {
			productService.checkBarcodeExixtsOrNor(invformlist);
			fail("ApiException did not occur");
		} catch (ApiException e) {
			assertEquals(e.getMessage(), "The following Barcode's does not exist: {" + invform.getBarcode() + "="
					+ invform.getQuantity() + "}");
		}
	}

	@Test
	public void testGetAllProductPojosByBarcode() {
		Map<String, ProductPojo> barcodeproduct = productService.getAllProductPojosByBarcode();
		ProductPojo pojo = productPojoList.get(0);
		assertEquals(pojo, barcodeproduct.get(pojo.getBarcode()));
	}

}
