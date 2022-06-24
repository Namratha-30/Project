package pos.model.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pos.model.form.InventoryForm;

@EqualsAndHashCode(callSuper = true)
@Data
public class InventoryData extends InventoryForm{
    private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
