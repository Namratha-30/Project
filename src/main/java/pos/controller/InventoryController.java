package pos.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pos.model.data.InventoryData;
import pos.model.form.InventoryForm;
import pos.pojo.InventoryPojo;
import pos.pojo.ProductPojo;
import pos.service.ApiException;
import pos.service.InventoryService;
import pos.service.ProductService;
import pos.util.DataConversionUtil;
import pos.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

//Controls the inventory page of the application
@Api
@RestController
@RequestMapping(path="/api/inventories")
public class InventoryController extends ExceptionHandler{

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private ProductService productService;

    //Adds a product to the inventory
    @ApiOperation(value = "Adds a product to inventory")
    @RequestMapping(path = "", method = RequestMethod.POST)
    public void add(@RequestBody InventoryForm inventoryForm) throws ApiException {
    	inventoryForm.setBarcode(inventoryForm.getBarcode().toLowerCase().trim());

    	
        ProductPojo productPojo= productService.getFromBarcode(inventoryForm.getBarcode());
        InventoryPojo inventoryPojo= DataConversionUtil.convert(inventoryForm,productPojo);
        inventoryService.add(inventoryPojo);
    }

    @ApiOperation(value = "Adds list of inventories")
    @RequestMapping(path = "/list", method = RequestMethod.POST)
    public void add(@RequestBody List<InventoryForm> inventoryFormList) throws ApiException {
        List<InventoryPojo> inventoryPojoList=new ArrayList<>();
        if(inventoryFormList.size()==0) {
	    	 throw new ApiException("List size cannot be zero");
	     }
        productService.checkBarcodeExixtsOrNor(inventoryFormList);
        for(InventoryForm inventoryForm :inventoryFormList) {
            ProductPojo productPojo= productService.getFromBarcode(inventoryForm.getBarcode());
            InventoryPojo inventoryPojo= DataConversionUtil.convert(inventoryForm,productPojo);
            inventoryPojoList.add(inventoryPojo);
        }
        inventoryService.addList(inventoryPojoList);
    }
    //Retrieves a product by id
    @ApiOperation(value = "Get inventory of product  by Id")
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public InventoryData get(@PathVariable int id) throws ApiException {
        InventoryPojo inventoryPojo = inventoryService.get(id);
        ProductPojo productPojo=productService.get(inventoryPojo.getProductId());
        return DataConversionUtil.convert(inventoryPojo,productPojo);
    }

    //Retrieves the total list of products in the inventory
    @ApiOperation(value = "Gets list of inventory")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<InventoryData> getAll() throws ApiException {
        List<InventoryPojo> inventoryPojoList = inventoryService.getAll();
        List<InventoryData> inventoryDataList = new ArrayList<>();
        for (InventoryPojo inventoryPojo : inventoryPojoList){
            ProductPojo productPojo=productService.get(inventoryPojo.getProductId());
            inventoryDataList.add(DataConversionUtil.convert(inventoryPojo,productPojo));
        }
        return inventoryDataList;
    }

    //Updates an inventory of a product
    @ApiOperation(value = "Updates an inventory")
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody InventoryForm inventoryForm) throws ApiException {
        inventoryForm.setBarcode(inventoryForm.getBarcode().toLowerCase().trim());
        ProductPojo productPojo= productService.getFromBarcode(inventoryForm.getBarcode());
        InventoryPojo inventoryPojo= DataConversionUtil.convert(inventoryForm,productPojo);
        inventoryService.update(id, inventoryPojo);
    }

}
