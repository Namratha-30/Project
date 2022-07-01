package pos.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pos.model.data.BrandData;
import pos.model.form.BrandForm;
import pos.pojo.BrandPojo;
import pos.service.ApiException;
import pos.service.BrandService;
import pos.util.DataConversionUtil;

@Service
public class BrandDto {

	@Autowired
	private BrandService brandService;

	public void add(BrandForm brandForm) throws ApiException {
		BrandPojo brandPojo = DataConversionUtil.convert(brandForm);
		brandService.add(brandPojo);
	}

	public void addList(List<BrandForm> brandFormList) throws ApiException {
		List<BrandPojo> brandPojoList = new ArrayList<>();
		if (brandFormList.size() == 0) {
			throw new ApiException("List size cannot be zero");
		}

		for (BrandForm brandForm : brandFormList) {
			BrandPojo brandPojo = DataConversionUtil.convert(brandForm);
			brandPojoList.add(brandPojo);
		}
		brandService.addList(brandPojoList);

	}

	public BrandData get(int id) throws ApiException {
		BrandPojo brandPojo = brandService.get(id);
		return DataConversionUtil.convert(brandPojo);
	}

	public List<BrandData> getAll() throws ApiException {
		List<BrandPojo> brandPojoList = brandService.getAll();
		return DataConversionUtil.convert(brandPojoList);
	}

	public void update(int id, BrandForm brandForm) throws ApiException {
		BrandPojo brandPojo = DataConversionUtil.convert(brandForm);
		brandService.update(id, brandPojo);
	}

}
