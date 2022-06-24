package pos.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

//import jdk.internal.org.jline.utils.Log;
import pos.pojo.BrandPojo;
import pos.service.ApiException;


//Repository for brand
@Repository
public class BrandDao extends AbstractDao{

	
    private static String deleteById = "delete from BrandPojo p where id=:id";

    //Insert into table
    @Transactional
    public void insert(BrandPojo brandPojo){
        em().persist(brandPojo);
    }

    //Retrieve a brand pojo
    @Transactional
    public BrandPojo select(int id){
        return em().find(BrandPojo.class,id);
    }

    //Retrieve all brand pojo
    @Transactional
    public List<BrandPojo> selectAll() throws ApiException {
        String selectAll = "select p from BrandPojo p";
        try
        {
        TypedQuery<BrandPojo> query = getQuery(selectAll,  BrandPojo.class);
        return query.getResultList();
        }
        catch(NoResultException e){
//        	Log.debug("No data found");
        	throw new ApiException(e.getMessage());
        }

        
    }

    //Update a brand with given brandId
    @Transactional
    public void update(int id,BrandPojo brandPojo) {

    }

    @Transactional
    //Retrieve brand pojo based in brand and category
    public List<BrandPojo> selectByBrandAndCategory(String brand, String category){
        String selectBrandCategory = "select p from BrandPojo p where brand=:brand and category=:category";
        TypedQuery<BrandPojo> query = getQuery(selectBrandCategory, BrandPojo.class);
        query.setParameter("brand",brand);
        query.setParameter("category",category);
        return query.getResultList();
    }
    
}
