package pos.dao;

import org.springframework.stereotype.Repository;
import pos.pojo.OrderItemPojo;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class OrderItemDao extends AbstractDao{
	
    //Add orderItem
    @Transactional
    public void insert(OrderItemPojo orderItemPojo){
        em().persist(orderItemPojo);
    }

    //Select orderItem by id
    public OrderItemPojo select(int id){
        return em().find(OrderItemPojo.class, id);
    }

    //Select all orderItems
    public List<OrderItemPojo> selectAll() {
        String select_all = "select p from OrderItemPojo p";
        TypedQuery<OrderItemPojo> query = getQuery(select_all,  OrderItemPojo.class);
        return query.getResultList();
    }

    //Get orderItems for same orderId
    public List<OrderItemPojo> selectByOrderId(int orderId){
        String select_from_orderId = "select p from OrderItemPojo p where orderId=:orderId";
        TypedQuery<OrderItemPojo> query = getQuery(select_from_orderId,  OrderItemPojo.class);
        query.setParameter("orderId", orderId);
        return query.getResultList();
    }

}
