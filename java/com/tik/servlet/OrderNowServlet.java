package com.tik.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import com.tik.connection.Dbconn;
import com.tik.dao.OrderDao;
import com.tik.model.Cart;
import com.tik.model.Order;
import com.tik.model.User;


@WebServlet("/order-now")
public class OrderNowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try(PrintWriter out = response.getWriter() ){
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			User auth = (User) request.getSession().getAttribute("auth");
			if(auth != null) {
				String productId = request.getParameter("id");
				int productQuantity =Integer.parseInt(request.getParameter("quantity"));
				
				//must be a more than zero
				if(productQuantity <= 0) {
					productQuantity = 1;
				}
				
				Order order = new Order();
				order.setId(Integer.parseInt(productId));
				order.setUid(auth.getId());
				order.setQunatity(productQuantity);
				order.setDate(formatter.format(date));
				
				OrderDao odao = new OrderDao(Dbconn.getConnection());
				boolean result = odao.insertOrder(order);
				
				if(result) {
					
					ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
					if(cart_list != null) {
						for(Cart c : cart_list) {
							
							if(c.getId() == Integer.parseInt(productId)) {
								System.out.print(cart_list.indexOf(c));
								//Search for the first occurrence and return its index
								cart_list.remove(cart_list.indexOf(c));
								break;
							}
						}
					}
					
					response.sendRedirect("orders.jsp");
				}else {
					out.print("errrrrrrrrrrrrror");
				}
				
				
			}else {
				//law msh 3amel login wadholy 3ala el login page
				response.sendRedirect("login.jsp");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
