package dao;

import db.DbConfig;
import service.ProductServiceImpl;

public class DAOFactory {

	
	public static IProduct createIproduct() {
		return new ProductServiceImpl(DbConfig.getConnection());
	}
}
