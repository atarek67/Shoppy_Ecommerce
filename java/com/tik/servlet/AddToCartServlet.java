package com.tik.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tik.model.Cart;


@WebServlet("/add-to-cart")
public class AddToCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		
		
		try(PrintWriter out = response.getWriter()) {
			
			ArrayList<Cart> cartList = new ArrayList<Cart>();
			
			int id = Integer.parseInt(request.getParameter("id"));
			Cart c = new Cart();
			c.setId(id);
			c.setQuantity(1);
			
			HttpSession session = request.getSession();
			
			ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
			
			if(cart_list == null) {
				cartList.add(c);
				session.setAttribute("cart-list", cartList);
				response.sendRedirect("index.jsp");
			}else {
				cartList = cart_list;				
				boolean exist = false;
				for(Cart c1 : cartList) {
					if(c1.getId() == id) {
						exist = true;
						 out.println("<script>alert('Item already added to the cart.');</script>");
					      out.println("<script>window.location='cart.jsp';</script>");
					}					
				}
				if(!exist) {
						cartList.add(c);
						response.sendRedirect("index.jsp");
						}
			}
		}		
	}
}
