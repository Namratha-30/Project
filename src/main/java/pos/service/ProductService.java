package pos.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pos.dao.ProductDao;
import pos.model.form.InventoryForm;
import pos.pojo.BrandPojo;
import pos.pojo.InventoryPojo;
import pos.pojo.ProductPojo;
import pos.util.StringUtil;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;
    
    @Autowired
    private InventoryService in;

    //adds a product
    @Transactional(rollbackOn = ApiException.class)
    public void add(ProductPojo productPojo) throws ApiException{
        normalize(productPojo);
        
    	
        check(productPojo);
        ProductPojo productPojo1= productDao.selectIdFromBarcode(productPojo.getBarcode());
        if(productPojo1!=null) {
            throw new ApiException("Product with given barcode: " + productPojo.getBarcode()+" already exists");
        }
        productPojo.setMrp(Math.round(productPojo.getMrp()*100.0)/100.0);
        productDao.insert(productPojo);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void addList(List<ProductPojo> productPojoList) throws ApiException {
        for (ProductPojo productPojo:productPojoList){
            normalize(productPojo);
            checkDuplicates(productPojoList);
            check(productPojo);
        }
        for (ProductPojo productPojo:productPojoList){
            add(productPojo);
        }
    }
    public List<ProductPojo> checkDuplicates(List<ProductPojo> productPojoList) throws ApiException
    {
    	List<ProductPojo> productPojoList1=new ArrayList<>();
    	HashMap<String , String> hMapNumbers = new HashMap<String , String>();
    	for(int i=0;i<productPojoList.size();i++)
    	{
    		for(int j=i+1;j<productPojoList.size();j++)
    		{
    			if((productPojoList.get(i).getBarcode().equals(productPojoList.get(j).getBarcode())))
    			{
    				hMapNumbers.put(productPojoList.get(i).getBarcode(), null);
    				productPojoList1.add(productPojoList.get(i));
    			}
    		}
    	}
    	
    		if(!productPojoList1.isEmpty())
        	{
        		throw new ApiException("Repeated Barcodes exists in File uploaded"+hMapNumbers);
        	}
        	return productPojoList1;
    }
    //gets a product by id
    @Transactional(rollbackOn = ApiException.class)
    public ProductPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    //gets product for barcode
    @Transactional
    public ProductPojo getFromBarcode(String barcode) throws ApiException {
        return checkBarcode(barcode);
    }

    //gets list of all product pojo
    @Transactional
    public List<ProductPojo> getAll() {
        return productDao.selectAll();
    }


    //updates product pojo
    @Transactional(rollbackOn  = ApiException.class)
    public void update(int id, ProductPojo productPojo) throws ApiException {
        check(productPojo);
        normalize(productPojo);
        ProductPojo productPojo1 = getCheck(id);
      // ProductPojo productPojo2=getCheckBarcode(productPojo.getBarcode());
        productPojo1.setBarcode(productPojo.getBarcode());
        productPojo1.setName(productPojo.getName());
        productPojo1.setMrp(Math.round(productPojo.getMrp()*100.0)/100.0);
        productDao.update(id, productPojo1);
    }


	
	/*
	 * private ProductPojo getCheckBarcode(String barcode) throws ApiException {
	 * ProductPojo productPojo = productDao.selectIdFromBarcode(barcode); if
	 * (productPojo!=null && productPojo.getBarcode()!=barcode) { throw new
	 * ApiException("Product with given barcode: " + barcode+" already exist"); }
	 * return productPojo; }
	 */
	 

	//HELPER METHODS
    //checks whether product pojo is valid or not
    public void check(ProductPojo productPojo) throws ApiException {
        if(StringUtil.isEmpty(productPojo.getBarcode())) {
            throw new ApiException("Barcode cannot be empty");
        }
        if(StringUtil.isEmpty(productPojo.getName())) {
            throw new ApiException("Name cannot be empty");
        }
        if(productPojo.getMrp()<=0)
            throw new ApiException("Mrp cannot be negative or zero");

    }

    //checks whether barcode is valid
    @Transactional(rollbackOn = ApiException.class)
    public ProductPojo checkBarcode(String barcode) throws ApiException {
        if(barcode==null)
            throw new ApiException("Barcode cannot be empty");
        ProductPojo productPojo= productDao.selectIdFromBarcode(barcode);
        if(productPojo==null){
            throw new ApiException("Product with given barcode: "+ barcode+" does not exist");
        }
        return productPojo;
    }

    //checks list if all barcodes are present
    public void checkBarcodeExixtsOrNor(List<InventoryForm> inventoryFormList) throws ApiException{
		// TODO Auto-generated method stub
		HashMap<String, Integer> hMapInventory=new HashMap<String,Integer>();
		for(InventoryForm inventoryForm:inventoryFormList)
		{
			ProductPojo productPojo= productDao.selectIdFromBarcode(inventoryForm.getBarcode());
			if(productPojo==null){
				hMapInventory.put(inventoryForm.getBarcode(), inventoryForm.getQuantity());
			}
		}
		if(!hMapInventory.isEmpty())
		{
			throw new ApiException("The following Barcode's does not exist: "+hMapInventory);
		}
	}
    
    //checks whether product with given id exists
    @Transactional
    public ProductPojo getCheck(int id) throws ApiException {
        ProductPojo productPojo = productDao.select(id);
        if (productPojo == null) {
            throw new ApiException("Product with given ID: " + id+" does not exist");
        }
        return productPojo;
    }

    //maps all the product pojo with their barcode
    @Transactional
    public Map<String, ProductPojo> getAllProductPojosByBarcode() {
        List<ProductPojo> productPojoList = getAll();
        Map<String, ProductPojo> barcodeProduct = new HashMap<>();
        for (ProductPojo productPojo : productPojoList) {
            barcodeProduct.put(productPojo.getBarcode(), productPojo);
        }
        return barcodeProduct;
    }

    //normalize product pojo
    @Transactional
    protected static void normalize(ProductPojo productPojo) {
        productPojo.setName(StringUtil.toLowerCase(productPojo.getName()));
        productPojo.setBarcode(StringUtil.toLowerCase(productPojo.getBarcode()));
    }

	

	

}
