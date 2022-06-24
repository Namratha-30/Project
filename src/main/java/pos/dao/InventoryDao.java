package pos.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

//import jdk.internal.org.jline.utils.Log;
import pos.pojo.InventoryPojo;
import pos.service.ApiException;

//Repository for inventory
@Repository
public class InventoryDao extends AbstractDao{

    //Insert into table
    @Transactional
    public void insert(InventoryPojo inventoryPojo){
        em().persist(inventoryPojo);
    }

    //Retrieve an inventory pojo with id
    public InventoryPojo select(int id){
        return em().find(InventoryPojo.class,id);
    }

    //Retrieve list of inventory pojo
    public List<InventoryPojo> selectAll() throws ApiException {
        String select_all = "select p from InventoryPojo p";
        
        try
        {
        	TypedQuery<InventoryPojo> query = getQuery(select_all,  InventoryPojo.class);
        return query.getResultList();
        }
        catch(NoResultException e){
//        	Log.debug("No data found");
        	throw new ApiException(e.getMessage());
        }
    }

    //Update an inventory
    public void update(int id,InventoryPojo inventoryPojo) {

    }

    //get from product Id
    //TODO rename
    public InventoryPojo selectByProductId(int productId){
        String select="select p from InventoryPojo p where productId=:productId";
        TypedQuery<InventoryPojo> query = getQuery(select, InventoryPojo.class);
        query.setParameter("productId", productId);
        List<InventoryPojo> res=query.getResultList();
        if(res.size()>0){
            return res.get(0);
        }
        else
            return null;
    }

}
