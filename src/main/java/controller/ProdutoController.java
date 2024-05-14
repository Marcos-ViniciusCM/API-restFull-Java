package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.DAOFactory;
import dao.IProduct;
import db.DbConfig;
import model.Product;

@WebServlet(name = "ProdutoController" , urlPatterns = "/product")
public class ProdutoController extends HttpServlet{

	
	private static final long serialVersionUID = 1L;
	
	Gson gson = new Gson();
	IProduct prodDAO = DAOFactory.createIproduct();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		response.setStatus(200);
		
		Connection conn = DbConfig.getConnection();
		DbConfig.closeConnection(conn);
		
		
		PrintWriter pw = response.getWriter();
		pw.print(gson.toJson(prodDAO.getAll()));//response is a object of type product
		pw.flush();
		
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		response.setStatus(201);//created
		
		StringBuffer sb = new StringBuffer();
		BufferedReader br = request.getReader();
		String attribute = null;
		
		while((attribute = br.readLine()) != null) {
			sb.append(attribute);
		}
		
		Product prod = gson.fromJson(sb.toString(),Product.class);
		PrintWriter pw = response.getWriter();
		pw.print(gson.toJson(prodDAO.saveProduct(prod)));//response is a object of type product
		pw.flush();
		
	}
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		response.setStatus(202);//accepted
		
		StringBuffer sb = new StringBuffer();
		BufferedReader br = request.getReader();
		String attribute = null;
		
		while((attribute = br.readLine()) != null) {
			sb.append(attribute);
		}
		
		Product prod = gson.fromJson(sb.toString(),Product.class);
		Product prodResp = prodDAO.updateProduct(prod);
		PrintWriter pw = response.getWriter();
		if(prodResp == null) {
			response.setStatus(404);//not found
			pw.print("Product was not found ");
			pw.flush();
		}else {
			pw.print(gson.toJson(prodResp));//response is a object of type product
			pw.flush();
		}
		
		
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		response.setStatus(202);//accepted
		
		StringBuffer sb = new StringBuffer();
		BufferedReader br = request.getReader();
		String attribute = null;
		
		while((attribute = br.readLine()) != null) {
			sb.append(attribute);
		}
		
		Product prod = gson.fromJson(sb.toString(),Product.class);
		String prodResp = prodDAO.deleteProduct(prod.getId());
		PrintWriter pw = response.getWriter();
		if(prodResp == null) {
			response.setStatus(404);//not found
			pw.print("Product was not found ");
			pw.flush();
		}else {
			pw.print(gson.toJson(prodResp));
			pw.flush();
		}
		
	}

}
