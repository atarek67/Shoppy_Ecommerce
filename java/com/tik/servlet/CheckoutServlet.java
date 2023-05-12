package com.tik.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tik.connection.Dbconn;
import com.tik.dao.OrderDao;
import com.tik.model.Cart;
import com.tik.model.Order;
import com.tik.model.User;

@WebServlet("/cart-checkout")
public class CheckoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try (PrintWriter out = response.getWriter()) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();

			// Retrive all cart products hena insallah
			ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
			// For user authentaction hena
			User auth = (User) request.getSession().getAttribute("auth");

			if (cart_list != null && auth != null) {

				for (Cart c : cart_list) {
					// bagahz el Order model object 3ashan amlaa
					Order order = new Order();
					order.setId(c.getId());
					order.setUid(auth.getId());
					order.setQunatity(c.getQuantity());
					order.setDate(formatter.format(date));

					// ba3ml object mn el DAO we aft7 connection 3ashan adakhl el data ba2a
					OrderDao odao = new OrderDao(Dbconn.getConnection());
					boolean result = odao.insertOrder(order);
					if (!result)
						break; // break the loop
				}
				
				cart_list.clear();
				response.sendRedirect("orders.jsp");

			} else if (auth == null) {
				response.sendRedirect("cart.jsp");
			} else
				response.sendRedirect("login.jsp");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
