package pos.dao;

import org.springframework.stereotype.Repository;
import pos.pojo.ProductPojo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

//Repository for product
@Repository
public class ProductDao extends AbstractDao{


    //Add a product
    @Transactional
    public void insert(ProductPojo productPojo){
        em().persist(productPojo);
    }

    //Retrieve a product pojo with id
    public ProductPojo select(int id){
        return em().find(ProductPojo.class,id);
    }

    //Retrieve all products
    public List<ProductPojo> selectAll() {
        String select_all = "select p from ProductPojo p";
        TypedQuery<ProductPojo> query = getQuery(select_all,  ProductPojo.class);
        return query.getResultList();
    }

    //Update a product pojo
    //TODO
    public void update(int id,ProductPojo productPojo) {
    	
    }

    //Retrieve product pojo from barcode
    public ProductPojo selectIdFromBarcode(String barcode){
        String select_id_barcode = "select p from ProductPojo p where barcode=:barcode";
        TypedQuery<ProductPojo> query = getQuery(select_id_barcode, ProductPojo.class);
        query.setParameter("barcode", barcode);
        List<ProductPojo> res=query.getResultList();
        if(res.size()>0){
            return res.get(0);
        }
        else
            return null;
    }
    
}
