package service;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.IProduct;
import db.DbConfig;
import model.Product;
import exception.DbException;

public class ProductServiceImpl implements IProduct{
	private Connection conn;
	
	public ProductServiceImpl(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public List<Product> getAll() {
		String getAll = "select * from estoque order by id";
		this.conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Product> product = new ArrayList<Product>();
		try {
			conn = DbConfig.getConnection();
			ps = conn.prepareStatement(getAll);
			rs = ps.executeQuery();
			while(rs.next()) {
				Product prod = new Product(rs.getInt(1), rs.getString(2),rs.getDouble(3),rs.getInt(4));
				product.add(prod);
			}
			return product;
		}catch(Exception e) {
			throw new DbException(e.getMessage(),e.getCause());
		}finally {
			DbConfig.closeConnection(conn);
			DbConfig.closePreparedStatement(ps);
			DbConfig.closeResultSet(rs);
		}
	}

	@Override
	public Product saveProduct(Product product) {
		String saveProduct = "insert into estoque (name , price, quantity) values ( ? , ? ,?)";
		this.conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DbConfig.getConnection();
			ps = conn.prepareStatement(saveProduct,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, product.getName());
			ps.setDouble(2, product.getPrice());
			ps.setInt(3, product.getQuantity());
			int affectedRow = ps.executeUpdate();
			
			if(affectedRow > 0) {
				rs = ps.getGeneratedKeys();
				if(rs.next()) {
					Product prod = new Product(rs.getInt(1) , product.getName(), product.getPrice(),
							product.getQuantity());
					return prod;
				}
			}
		}catch(Exception e) {
			throw new DbException(e.getMessage(),e.getCause());
		}finally {
			DbConfig.closeConnection(conn);
			DbConfig.closePreparedStatement(ps);
			DbConfig.closeResultSet(rs);
		}
		return null;
	}

	@Override
	public Product updateProduct(Product product) {
		String update = "update estoque set name = ?, price = ?, quantity = ? where id = ?";
		this.conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DbConfig.getConnection();
			ps = conn.prepareStatement(update,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, product.getName());
			ps.setDouble(2, product.getPrice());
			ps.setInt(3, product.getQuantity());
			ps.setInt(4,product.getId());
			int affectedRow = ps.executeUpdate();
			
			if(affectedRow > 0) {
				return product;
			}
		}catch(Exception e) {
			throw new DbException(e.getMessage(),e.getCause());
		}finally {
			DbConfig.closeConnection(conn);
			DbConfig.closePreparedStatement(ps);
			DbConfig.closeResultSet(rs);
		}
		return null;
	}

	@Override
	public String deleteProduct(Integer id) {
		String delete = "delete from estoque where id = ?";
		this.conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DbConfig.getConnection();
			ps = conn.prepareStatement(delete);
			ps.setInt(1, id);
			int affectedRows = ps.executeUpdate();
			if(affectedRows > 0) {
				return "Product was deleted.";
			}
		}catch(Exception e) {
			throw new DbException(e.getMessage(),e.getCause());
		}finally {
			DbConfig.closePreparedStatement(ps);	
		}
		return null;
		
	}

}
