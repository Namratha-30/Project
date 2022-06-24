package pos.model.data;

import lombok.Data;
import pos.model.form.ProductForm;

@Data
public class ProductData extends ProductForm{

    private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
