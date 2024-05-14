package dao;

import java.util.List;
import model.Product;


public interface IProduct {
	
	 List<Product> getAll();
	 Product saveProduct(Product product);
	 Product updateProduct(Product product);
	 String deleteProduct(Integer id);
	

}
