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
import pos.dto.OrderDto;
import pos.model.data.OrderItemData;
import pos.model.form.OrderItemForm;
import pos.model.xml.OrderData;
import pos.service.ApiException;

//Controls the order page of the application
@Api
@RestController
public class OrderController extends ExceptionHandler{

	@Autowired
	private OrderDto orderDto;
  
    //Adds an order
    @ApiOperation(value = "Adds Order Items")
    @RequestMapping(path = "/api/orders", method = RequestMethod.POST)
    public void add(@RequestBody OrderItemForm[] orderItemForms) throws ApiException{
        //orderItemForm.setBarcode(orderItemForm.getBarcode().toLowerCase().trim());
        orderDto.add(orderItemForms);
    	
    }

   

	//Adds an OrderItem to an existing order
    @ApiOperation(value = "Adds an OrderItem to an existing order")
    @RequestMapping(path = "/api/order/{orderId}/orderItems", method = RequestMethod.POST)
    public void addOrderItem(@PathVariable int orderId, @RequestBody OrderItemForm orderItemForm) throws ApiException {
        orderDto.addOrderItem(orderId, orderItemForm);
    }

    //Gets a OrderItem details record by id
    @ApiOperation(value = "Gets a OrderItem details record by id")
    @RequestMapping(path = "/api/orderItems/{id}", method = RequestMethod.GET)
    public OrderItemData get(@PathVariable int id) throws ApiException {
        return orderDto.get(id);
    }

    //Gets list of Order Items
    @ApiOperation(value = "Gets list of Order Items")
    @RequestMapping(path = "/api/orderItems", method = RequestMethod.GET)
    public List<OrderItemData> getAll() throws ApiException {
        
        return orderDto.getAll();
    }

    //Gets list of Orders
    @ApiOperation(value = "Gets list of Orders")
    @RequestMapping(path = "/api/orders", method = RequestMethod.GET)
    public List<OrderData> getAllOrders() {
        
        return orderDto.getAllOrders();
    }

    //Get single order
    @ApiOperation(value = "Gets an Order")
    @RequestMapping(path = "/api/singleOrder/{id}", method = RequestMethod.GET)
    public OrderData getSingleOrder(@PathVariable int id) throws ApiException {
        
        return orderDto.getSingleOrder(id);
    }

    //Gets list of Order Items of  particular order
    @ApiOperation(value = "Gets list of Order Items of a particular order")
    @RequestMapping(path = "/api/orders/{id}", method = RequestMethod.GET)
    public List<OrderItemData> getOrderItemsByOrderId(@PathVariable int id) throws ApiException {
        
        return orderDto.getOrderItemsByOrderId(id);
    }

    //Updates a OrderItem record
    @ApiOperation(value = "Updates a OrderItem record")
    @RequestMapping(path = "/api/orderItems/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody OrderItemForm orderItemForm) throws ApiException {
       orderDto.update(id, orderItemForm);
    }
}
