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
import pos.dto.BrandDto;
import pos.model.data.BrandData;
import pos.model.form.BrandForm;
import pos.service.ApiException;

//Controls the brand page of the application
@Api
@RestController
@RequestMapping(path = "/api/brands")
public class BrandController extends ExceptionHandler {

	@Autowired
	private BrandDto brandDto;

	// Add a brand
	@ApiOperation(value = "Adding a brand")
	@RequestMapping(path = "", method = RequestMethod.POST)
	public void add(@RequestBody BrandForm brandForm) throws ApiException {

		brandDto.add(brandForm);

	}

	// Add brands using TSV
	@ApiOperation(value = "Adds list of brands")
	@RequestMapping(path = "/list", method = RequestMethod.POST)
	public void add(@RequestBody List<BrandForm> brandFormList) throws ApiException {

		brandDto.addList(brandFormList);

	}

	// Retrieve a brand using id
	@ApiOperation(value = "Get a brand by Id")
	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public BrandData get(@PathVariable int id) throws ApiException {
		return brandDto.get(id);
	}

	// Retrieve complete list of all brands
	@ApiOperation(value = "Get list of all brands")
	@RequestMapping(path = "", method = RequestMethod.GET)
	public List<BrandData> getAll() throws ApiException {
		return brandDto.getAll();
	}

	// Updates a brand
	@ApiOperation(value = "Updates a brand")
	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	public void update(@PathVariable int id, @RequestBody BrandForm brandForm) throws ApiException {
		brandDto.update(id, brandForm);

	}

}