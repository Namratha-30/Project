package pos.model.xml;


import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import pos.model.data.BrandData;


@XmlRootElement(name = "brands")
@XmlAccessorType(XmlAccessType.FIELD)
public class BrandXmlList {

    @XmlElement(name = "brand_item")
    List<BrandData> brandDataList;

	public List<BrandData> getBrandDataList() {
		return brandDataList;
	}

	public void setBrandDataList(List<BrandData> brandDataList) {
		this.brandDataList = brandDataList;
	}
}
