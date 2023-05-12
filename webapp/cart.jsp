<%@page import="java.text.DecimalFormat"%>
<%@page import="com.tik.connection.Dbconn"%>
<%@page import="com.tik.dao.ProductDao"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.tik.model.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
User auth = (User) request.getSession().getAttribute("auth");
if (auth != null) {
	request.setAttribute("auth", auth);
}

ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");

List<Cart> cartProduct = null;

if (cart_list != null) {
	ProductDao pdao = new ProductDao(Dbconn.getConnection());
	cartProduct = pdao.getCartProducts(cart_list);
	double total = pdao.getTotalCartPrice(cart_list);
	request.setAttribute("total", total);
	request.setAttribute("cart_list", cart_list);
}

DecimalFormat dcf = new DecimalFormat("#.##");//number then 2 characters
request.setAttribute("dcf", dcf);
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="includes/head.jsp"%>
<title>Mini E-comm</title>
<style type="text/css">
.table tbody td {
	vertical-align: middle;
}

.btn-incre, .btn-decre {
	box-shadow: none;
	font-size: 25px;
}
</style>

</head>
<body>
	<%@include file="includes/navbar.jsp"%>


	<div class="container">
		<div class="d-flex py-3">
			<h3>Total Price: $ ${ (total > 1) ? dcf.format( total) : 0 }</h3>
			<%
			if (cart_list == null || cart_list.isEmpty()) {
			%>
			<a class="mx-3 btn btn-primary disabled" href="#">Checkout</a>
			<%
			} else {
			%>
			<a class="mx-3 btn btn-primary" href="cart-checkout">Checkout</a>
			<%
			}
			%>


		</div>
		<table class="table table-light">
			<thead>
				<tr>
					<th scope="col">Name</th>
					<th scope="col">Category</th>
					<th scope="col">Price</th>
					<th scope="col">Buy Now</th>
					<th scope="col">Cancel</th>
				</tr>
			</thead>
			<tbody>
				<%
				if (cart_list != null) {
					for (Cart c : cartProduct) {
				%>
				<tr>
					<td><%=c.getP_name()%></td>
					<td><%=c.getCategory()%></td>
					<td><%=dcf.format(c.getPrice())%></td>
					<td>
						<form action="order-now" method="post" class="form-inline">
							<input type="hidden" name="id" value="<%=c.getId()%>"
								class="form-input">
							<div class="form-group d-flex justify-content-between w-50">
								<a class="btn bnt-sm btn-incre"
									href="inc-dec?action=inc&id=<%=c.getId()%>"><i
									class="fas fa-plus-square"></i></a> <input type="text"
									name="quantity" class="form-control w-50"
									value="<%=c.getQuantity()%>" readonly> <a
									class="btn btn-sm btn-decre"
									href="inc-dec?action=dec&id=<%=c.getId()%>"><i
									class="fas fa-minus-square"></i></a>
								<button type="submit" class="btn btn-outline-primary">Buy</button>
							</div>

						</form>
					</td>
					<!-- Here get the id from query parameter and get the specific id for the project -->
					<td><a href="remove-from-cart?id=<%=c.getId()%>"
						class="btn btn-sm btn-danger">Remove</a></td>
				</tr>

				<%
				}
				}
				%>
			</tbody>
		</table>
	</div>

	<%@include file="includes/footer.jsp"%>
</body>
</html>