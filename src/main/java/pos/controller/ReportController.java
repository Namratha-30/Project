package pos.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import pos.model.form.ReportFilter;
import pos.service.ReportService;

//Controls all the report generation
@Api
@RestController
public class ReportController extends ExceptionHandler{

    @Autowired
    private ReportService reportService;

    //Retrieves the brand report
    @ApiOperation(value = "Gets Brand Report")
    @RequestMapping(path = "/api/report/brands", method = RequestMethod.GET)
    public void get(HttpServletResponse response) throws Exception {
        byte[] bytes = reportService.generatePdfResponse("brand");
        createPdfResponse(bytes, response);
    }

    //Retrieves the inventory report
    @ApiOperation(value = "Gets Inventory Report")
    @RequestMapping(path = "/api/report/inventories", method = RequestMethod.GET)
    public void getInventory(HttpServletResponse response) throws Exception {
        byte[] bytes = reportService.generatePdfResponse("inventory");
        createPdfResponse(bytes, response);
    }

    //Retrieves the sales report
    @ApiOperation(value = "Gets Sales Report")
    @RequestMapping(path = "/api/report/sales", method = RequestMethod.POST)
    public void getSales(@RequestBody ReportFilter reportFilter, HttpServletResponse response) throws Exception {
        reportFilter.setBrand(reportFilter.getBrand().toLowerCase().trim());
        reportFilter.setCategory(reportFilter.getCategory().toLowerCase().trim());
        byte[] bytes = reportService.generatePdfResponse("sales", reportFilter);
        createPdfResponse(bytes, response);
    }

    public void createPdfResponse(byte[] bytes, HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setContentLength(bytes.length);

        response.getOutputStream().write(bytes);
        response.getOutputStream().flush();
    }
}