<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.tik.dao.ProductDao"%>
<%@page import="com.tik.connection.Dbconn"%>
<%@page import="com.tik.model.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
User auth = (User) request.getSession().getAttribute("auth");
if (auth != null) {
	request.setAttribute("auth", auth);
}

ProductDao pd = new ProductDao(Dbconn.getConnection());
List<Product> products = pd.getAllProducts();

ArrayList <Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");


if(cart_list != null){
	request.setAttribute("cart_list", cart_list);
}
%>

<!DOCTYPE html>
<html>
<head>
<%@include file="includes/head.jsp"%>
<title>Mini E-commerce</title>

</head>
<body>
	<%@include file="includes/navbar.jsp"%>

	<div class="container">
		<div class="card-header my-3 text-center h2">All Products</div>
		<div class="row">
			<%
			if (!products.isEmpty()) {
				for (Product p : products) {
			%>

			<div class="col-md-3 my-4">
				<div class="card w-100" style="width: 18rem;">
					<img class="card-img-top"
						src="product_images/<%=p.getImage() %>"
						alt="image"
						width="300"
						height="300"
						>
					<div class="card-body">
						<h4 class="card-title"><%=p.getP_name()%></h4>
						<h5 class="price">
							Price: $
							<%=p.getPrice()%>
						</h5>
						<h6 class="category">
							Category:
							<%=p.getCategory()%></h6>
						<div class="mt-3 d-flex justify-content-between">
							<a href="add-to-cart?id=<%=p.getId() %>" class="btn btn-outline-dark">Add to cart</a> <a
								href="order-now?quantity=1&id=<%=p.getId() %>" class="btn btn-outline-primary">Buy NOW</a>
						</div>

					</div>
				</div>
			</div>
			<%
			}
			}
			%>

		</div>
	</div>


	<%@include file="includes/footer.jsp"%>
</body>
</html>