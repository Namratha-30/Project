package pos.model.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pos.model.form.BrandForm;

//Data about brand
@EqualsAndHashCode(callSuper = true)
@Data
public class BrandData extends BrandForm{

    private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
