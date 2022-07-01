package pos.service;

import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pos.dao.BrandDao;
import pos.model.form.ProductForm;
import pos.pojo.BrandPojo;
import pos.util.StringUtil;

@Service
public class BrandService {

	@Autowired
	private BrandDao brandDao;

	/*
	 * @Autowired private ProductService ps;
	 */

	// Add a brand
	@Transactional(rollbackOn = ApiException.class)
	public void add(BrandPojo brandPojo) throws ApiException {

		normalize(brandPojo);

		check(brandPojo);
		brandDao.insert(brandPojo);
	}

	@Transactional(rollbackOn = ApiException.class)
	public void addList(List<BrandPojo> brandPojoList) throws ApiException {
		checkDuplicates(brandPojoList);

		for (BrandPojo brandPojo : brandPojoList) {
			add(brandPojo);
		}
	}

	// TODO simplify below method
	public void checkDuplicates(List<BrandPojo> brandPojoList) throws ApiException {
		HashMap<String, String> hMapNumbers = new HashMap<String, String>();
		for (int i = 0; i < brandPojoList.size(); i++) {
			for (int j = i + 1; j < brandPojoList.size(); j++) {
				if ((brandPojoList.get(i).getBrand().equals(brandPojoList.get(j).getBrand()))
						&& (brandPojoList.get(i).getCategory().equals(brandPojoList.get(j).getCategory()))) {
					hMapNumbers.put(brandPojoList.get(i).getBrand(), brandPojoList.get(i).getCategory());
				}
			}
		}
		if (hMapNumbers.size() > 0) {
			throw new ApiException("Duplicate Items exists in File uploaded" + hMapNumbers);
		}
	}

	// get a brand by id
	@Transactional(rollbackOn = ApiException.class)
	public BrandPojo get(int id) throws ApiException {
		return getCheck(id);
	}

	// get list of all brands
	@Transactional
	public List<BrandPojo> getAll() throws ApiException {
		return brandDao.selectAll();
	}

	// update a brand
	@Transactional(rollbackOn = ApiException.class)
	public void update(int id, BrandPojo brandPojo) throws ApiException {
		normalize(brandPojo);
		check(brandPojo);
		BrandPojo existingBrandPojo = getCheck(id);
		existingBrandPojo.setBrand(brandPojo.getBrand());
		existingBrandPojo.setCategory(brandPojo.getCategory());
		brandDao.update(id, existingBrandPojo);
	}

	// HELPER METHODS
	// check whether brand with given id exists or not
	@Transactional
	public BrandPojo getCheck(int id) throws ApiException {
		BrandPojo brandPojo = brandDao.select(id);
		if (brandPojo == null) {
			throw new ApiException("Brand with given ID: " + id + " does not exist");
		}
		return brandPojo;
	}

	// checks whether the entered values are null or not
	public void check(BrandPojo brandPojo) throws ApiException {
		if (StringUtil.isEmpty(brandPojo.getBrand())) {
			throw new ApiException("Brand name cannot be empty");
		}
		if (StringUtil.isEmpty(brandPojo.getCategory())) {
			throw new ApiException("Category name cannot be empty");
		}
		List<BrandPojo> brandPojoList = brandDao.selectByBrandAndCategory(brandPojo.getBrand(),
				brandPojo.getCategory());
		if (!brandPojoList.isEmpty()) {
			throw new ApiException(
					"Brand: " + brandPojo.getBrand() + " and Category: " + brandPojo.getCategory() + " already exist");
		}
	}

	// gets a brand by brand and category
	@Transactional()
	public BrandPojo getBrandPojo(String brand, String category) throws ApiException {

		List<BrandPojo> brandPojoList = brandDao.selectByBrandAndCategory(brand, category);
		if (brandPojoList.isEmpty()) {
			throw new ApiException(
					"The brand: " + brand + " and category: " + category + " combination given does not exist");
		}
		return brandPojoList.get(0);
	}

	// normalizes a pojo into lower case and trimmed
	protected static void normalize(BrandPojo brandPojo) {
		brandPojo.setBrand(StringUtil.toLowerCaseAndTrim(brandPojo.getBrand()));
		brandPojo.setCategory(StringUtil.toLowerCaseAndTrim(brandPojo.getCategory()));
	}

	// checks if band and category pair exixts or not
	public void checkExixtsOrNot(List<ProductForm> productFormList) throws ApiException {
		HashMap<String, String> hMapProducts = new HashMap<String, String>();
		for (ProductForm productForm : productFormList) {
			List<BrandPojo> brandPojoList = brandDao.selectByBrandAndCategory(productForm.getBrand(),
					productForm.getCategory());
			if (brandPojoList.isEmpty()) {
				hMapProducts.put(productForm.getBrand(), productForm.getCategory());
			}
		}
		if (!hMapProducts.isEmpty()) {
			throw new ApiException("The following brand and category combinations does not exists: " + hMapProducts);
		}
	}

}