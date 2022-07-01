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
import pos.dto.InventoryDto;
import pos.model.data.InventoryData;
import pos.model.form.InventoryForm;
import pos.service.ApiException;

//Controls the inventory page of the application
@Api
@RestController
@RequestMapping(path="/api/inventories")
public class InventoryController extends ExceptionHandler{

   @Autowired
   private InventoryDto inventoryDto;

    //Adds a product to the inventory
    @ApiOperation(value = "Adds a product to inventory")
    @RequestMapping(path = "", method = RequestMethod.POST)
    public void add(@RequestBody InventoryForm inventoryForm) throws ApiException {
    	
    	inventoryDto.add(inventoryForm);
    	
    }

    @ApiOperation(value = "Adds list of inventories")
    @RequestMapping(path = "/list", method = RequestMethod.POST)
    public void add(@RequestBody List<InventoryForm> inventoryFormList) throws ApiException {
       inventoryDto.add(inventoryFormList);
    }
    //Retrieves a product by id
    @ApiOperation(value = "Get inventory of product  by Id")
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public InventoryData get(@PathVariable int id) throws ApiException {
        return inventoryDto.get(id);
    }

    //Retrieves the total list of products in the inventory
    @ApiOperation(value = "Gets list of inventory")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<InventoryData> getAll() throws ApiException {
        return inventoryDto.getAll();
    }

    //Updates an inventory of a product
    @ApiOperation(value = "Updates an inventory")
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody InventoryForm inventoryForm) throws ApiException {
       inventoryDto.update(id, inventoryForm);
    }

}
