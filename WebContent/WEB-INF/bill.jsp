<%@page import="com.Billing"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Billing</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.4.1.min.js"></script>
<script src="Components/bill.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-6">
				<h1>Billing</h1>

				<form id="formProduct" name="formProduct" method="post" action="bill.jsp">


						Month<input id="Month" name="Month" type="text"
						class="form-control form-control-sm"> 
						
						<br>Customer name<input id="Customer_name" name="Customer_name" type="text"
						class="form-control form-control-sm"> 
						
						<br>Meter reading<input id="Meter_reading" name="Meter_reading" type="text"
						class="form-control form-control-sm"> 
						
						<br>Charge for this month<input id="Charge_for_this_month" name="Charge_for_this_month" type="text"
						class="form-control form-control-sm"> 
						
						<br>Total bill amount<input id="Total_Bill_amount" name="Total_Bill_amount" type="text"
						class="form-control form-control-sm">
						
						<br> <input
						id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-primary"> <input type="hidden"
						id="hidProjectIDSave" name="hidProjectIDSave" value="">
				</form>

				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>

				<br>
				<div id="divProjectGrid">
					<%
						Billing projectObj = new Billing();
						out.print(projectObj.readProject());
					%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
