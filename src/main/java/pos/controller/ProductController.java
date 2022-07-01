package pos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import pos.dto.ProductDto;
import pos.model.data.ProductData;
import pos.model.form.ProductForm;
import pos.service.ApiException;

//Controls the products page of the application
@Api
@RestController
@RequestMapping(path = "/api/products")
public class ProductController extends ExceptionHandler {

	@Autowired
	private ProductDto productDto;

	// Adds a product
	@ApiOperation(value = "Adds a product")
	@RequestMapping(path = "", method = RequestMethod.POST)
	public void add(@RequestBody ProductForm productForm) throws ApiException {
		productDto.add(productForm);
	}

	@ApiOperation(value = "Adds list of products")
	@RequestMapping(path = "/list", method = RequestMethod.POST)
	public void add(@RequestBody List<ProductForm> productFormList) throws ApiException {

		productDto.addList(productFormList);

	}

	// Retrieves a product by productId
	@ApiOperation(value = "Get a product by Id")
	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public ProductData get(@PathVariable int id) throws ApiException {
		return productDto.get(id);

	}

	// Retrieves list of all products
	@ApiOperation(value = "Get list of all products")
	@RequestMapping(path = "", method = RequestMethod.GET)
	public List<ProductData> getAll() throws ApiException {

		return productDto.getAll();

	}

	// Updates a product
	@ApiOperation(value = "Updates a product")
	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	public void update(@PathVariable int id, @RequestBody ProductForm productForm) throws ApiException {
		productDto.update(id, productForm);

	}

}
