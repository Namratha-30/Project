package pos.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pos.model.data.ProductData;
import pos.model.form.ProductForm;
import pos.pojo.BrandPojo;
import pos.pojo.ProductPojo;
import pos.service.ApiException;
import pos.service.BrandService;
import pos.service.ProductService;
import pos.util.DataConversionUtil;

@Service
public class ProductDto {

	@Autowired
	private ProductService productService;

	@Autowired
	private BrandService brandService;

	public void add(ProductForm productForm) throws ApiException {

		productForm.setBrand(productForm.getBrand().toLowerCase().trim());
		productForm.setCategory(productForm.getCategory().toLowerCase().trim());

		BrandPojo brandPojo = brandService.getBrandPojo(productForm.getBrand(), productForm.getCategory());
		productService.add(DataConversionUtil.convert(productForm, brandPojo));
	}

	public void addList(List<ProductForm> productFormList) throws ApiException {
		List<ProductPojo> productPojoList = new ArrayList<>();
		if (productFormList.size() == 0) {
			throw new ApiException("List size cannot be zero");
		}
		brandService.checkExixtsOrNot(productFormList);
		for (ProductForm productForm : productFormList) {
			productForm.setBrand(productForm.getBrand().toLowerCase().trim());
			productForm.setCategory(productForm.getCategory().toLowerCase().trim());
			BrandPojo brandPojo = brandService.getBrandPojo(productForm.getBrand(), productForm.getCategory());
			productPojoList.add(DataConversionUtil.convert(productForm, brandPojo));
		}
		productService.addList(productPojoList);
	}

	public ProductData get(int id) throws ApiException {
		ProductPojo productPojo = productService.get(id);
		BrandPojo brandPojo = brandService.get(productPojo.getBrandCategory());
		return DataConversionUtil.convert(productPojo, brandPojo);
	}

	public List<ProductData> getAll() throws ApiException {

		List<ProductPojo> productPojoList = productService.getAll();
		List<ProductData> productDataList = new ArrayList<>();
		for (ProductPojo productPojo : productPojoList) {
			BrandPojo brandPojo = brandService.get(productPojo.getBrandCategory());
			productDataList.add(DataConversionUtil.convert(productPojo, brandPojo));
		}
		return productDataList;
	}

	public void update(int id, ProductForm productForm) throws ApiException {
		ProductPojo productPojo = productService.get(id);
		productForm.setBrand(brandService.get(productPojo.getBrandCategory()).getBrand());
		productForm.setCategory(brandService.get(productPojo.getBrandCategory()).getCategory());

		BrandPojo brandPojo = brandService.getBrandPojo(productForm.getBrand(), productForm.getCategory());
		productService.update(id, DataConversionUtil.convert(productForm, brandPojo));
	}
}
