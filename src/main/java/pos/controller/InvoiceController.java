package pos.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import pos.service.ReportService;

//Controls the invoice generation of an order
@Api
@RestController
public class InvoiceController extends ExceptionHandler{

    @Autowired
    private ReportService reportService;

    //Generates the invoice of an order from orderId
    @ApiOperation(value = "Gets Invoice PDF by id")
    @RequestMapping(path = "/api/invoice/{id}", method = RequestMethod.GET)
    public void get(@PathVariable int id, HttpServletResponse response) throws Exception {
        byte[] bytes = reportService.generatePdfResponse("invoice", id);
        createPdfResponse(bytes, response);
    }

    //Creates PDF
    public void createPdfResponse(byte[] bytes, HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setContentLength(bytes.length);

        response.getOutputStream().write(bytes);
        response.getOutputStream().flush();
    }
}
