package pos.model.xml;


import pos.model.data.OrderInvoiceData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@XmlRootElement(name = "items")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderInvoiceXmlList {
    @XmlElement(name="order_id")
    private Integer order_id;
    @XmlElement(name="datetime")
    private String datetime;
    @XmlElement(name="total")
    private Double total;
    @XmlElement(name="item")
    private List<OrderInvoiceData> orderInvoiceData;
	public Integer getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public List<OrderInvoiceData> getOrderInvoiceData() {
		return orderInvoiceData;
	}
	public void setOrderInvoiceData(List<OrderInvoiceData> orderInvoiceData) {
		this.orderInvoiceData = orderInvoiceData;
	}
}
