package pos.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pos.model.data.InventoryData;
import pos.model.form.InventoryForm;
import pos.pojo.InventoryPojo;
import pos.pojo.ProductPojo;
import pos.service.ApiException;
import pos.service.InventoryService;
import pos.service.ProductService;
import pos.util.DataConversionUtil;

@Service
public class InventoryDto {

	@Autowired
	private InventoryService inventoryService;

	@Autowired
	private ProductService productService;

	public void add(InventoryForm inventoryForm) throws ApiException {

		inventoryForm.setBarcode(inventoryForm.getBarcode().toLowerCase().trim());

		ProductPojo productPojo = productService.getFromBarcode(inventoryForm.getBarcode());
		InventoryPojo inventoryPojo = DataConversionUtil.convert(inventoryForm, productPojo);
		inventoryService.add(inventoryPojo);
	}

	public void add(List<InventoryForm> inventoryFormList) throws ApiException {
		List<InventoryPojo> inventoryPojoList = new ArrayList<>();
		if (inventoryFormList.size() == 0) {
			throw new ApiException("List size cannot be zero");
		}
		productService.checkBarcodeExixtsOrNor(inventoryFormList);
		for (InventoryForm inventoryForm : inventoryFormList) {
			ProductPojo productPojo = productService.getFromBarcode(inventoryForm.getBarcode());
			InventoryPojo inventoryPojo = DataConversionUtil.convert(inventoryForm, productPojo);
			inventoryPojoList.add(inventoryPojo);
		}
		inventoryService.addList(inventoryPojoList);
	}

	public InventoryData get(int id) throws ApiException {
		InventoryPojo inventoryPojo = inventoryService.get(id);
		ProductPojo productPojo = productService.get(inventoryPojo.getProductId());
		return DataConversionUtil.convert(inventoryPojo, productPojo);
	}

	public List<InventoryData> getAll() throws ApiException {
		List<InventoryPojo> inventoryPojoList = inventoryService.getAll();
		List<InventoryData> inventoryDataList = new ArrayList<>();
		for (InventoryPojo inventoryPojo : inventoryPojoList) {
			ProductPojo productPojo = productService.get(inventoryPojo.getProductId());
			inventoryDataList.add(DataConversionUtil.convert(inventoryPojo, productPojo));
		}
		return inventoryDataList;
	}

	public void update(int id, InventoryForm inventoryForm) throws ApiException {
		inventoryForm.setBarcode(inventoryForm.getBarcode().toLowerCase().trim());
		ProductPojo productPojo = productService.getFromBarcode(inventoryForm.getBarcode());
		InventoryPojo inventoryPojo = DataConversionUtil.convert(inventoryForm, productPojo);
		inventoryService.update(id, inventoryPojo);
	}

}
