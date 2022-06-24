package pos.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pos.model.data.BrandData;
import pos.model.form.BrandForm;
import pos.pojo.BrandPojo;
import pos.service.ApiException;
import pos.service.BrandService;
import pos.util.DataConversionUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//Controls the brand page of the application
@Api
@RestController
@RequestMapping(path="/api/brands")
public class BrandController extends ExceptionHandler{

    @Autowired
    private BrandService brandService;

    //Add a brand
    @ApiOperation(value = "Adding a brand")
    @RequestMapping(path = "", method = RequestMethod.POST)
    public void add(@RequestBody BrandForm brandForm) throws ApiException {
        BrandPojo brandPojo= DataConversionUtil.convert(brandForm);
        brandService.add(brandPojo);
    }
    
    //Add brands using TSV
    @ApiOperation(value = "Adds list of brands")
    @RequestMapping(path = "/list", method = RequestMethod.POST)
    public void add(@RequestBody List<BrandForm> brandForm) throws ApiException {

        List<BrandPojo> brandPojoList=new ArrayList<>();
	     if(brandForm.size()==0) {
	    	 throw new ApiException("List size cannot be zero");
	     }
		 
        for(BrandForm brandForm1:brandForm) {
            BrandPojo brandPojo = DataConversionUtil.convert(brandForm1);
            brandPojoList.add(brandPojo);
        }
        brandService.addList(brandPojoList);
    	
    }

    //Retrieve a brand using id
    @ApiOperation(value = "Get a brand by Id")
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public BrandData get(@PathVariable int id) throws ApiException {
        BrandPojo brandPojo = brandService.get(id);
        return DataConversionUtil.convert(brandPojo);
    }

    //Retrieve complete list of all brands
    @ApiOperation(value = "Get list of all brands")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<BrandData> getAll() throws ApiException{
        List<BrandPojo> brandPojoList = brandService.getAll();
        return DataConversionUtil.convert(brandPojoList);
    }

    //Updates a brand
    @ApiOperation(value = "Updates a brand")
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody BrandForm brandForm) throws ApiException {
        BrandPojo brandPojo = DataConversionUtil.convert(brandForm);
        brandService.update(id, brandPojo);
    }
   
}
