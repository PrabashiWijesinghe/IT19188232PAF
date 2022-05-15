<%@page import="com.Payment"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Customer Payment</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.6.0.js"></script>
<script src="Components/payment.js"></script>
</head>
<body>

<div class="container">
 <div class="row">
  <div class="col-md-8 mx-auto">

 
 <h2 class="m-3">Customer payments</h2>
 <form id="formPayment" name="formPayment" method="post" enctype="multipart/form-data" action="Payment.jsp">
 
	
	<br> CUSID:
	<input id="CUSID" name="CUSID" type="text"  class="form-control form-control-sm">
	<br> CUSName:
	<input id="CUSName" name="CUSName" type="text" class="form-control form-control-sm">
	<br>Amount :
	<input id="Amount" name="Amount" type="text" class="form-control form-control-sm">
	<br>Bank:
	<select id="Bank" name="Bank" class="form-control form-control-sm">
						 <option value="0">--Select Bank--</option>
						 <option value="Sampath"> Sampath Bank</option>
						 <option value="NSB">NSB Bank</option>
						 <option value="BOC">BOC Bank</option>
						 <option value="Commercial">Commercial Bank</option>
						 <option value="HNB">HNB Bank</option>
						 <option value="DFCC">DFCC Bank</option>
						 <option value="Nationstrust">Nations TRUST Bank</option>
	</select>
	<br> CardNo:
	<input id="CardNo" name="CardNo" type="text" class="form-control form-control-sm">
	<br> CVV:
	<input id="CVV" name="CVV" type="text" class="form-control form-control-sm">
	<br> PaymentDate:
	<input id="PaymentDate" name="PaymentDate" type="text" class="form-control form-control-sm">
	
	<br> 
	
	<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
	<input type="hidden" id="hidPaymentIdSave" name="hidPaymentIdSave" value="">
</form>
<br>

<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>

				<br>
				<h4>All Payments Details</h4>
				<div id="divPaymentGrid">
					<%
						Payment paymentObj = new Payment();
						out.print(paymentObj.readPayment());
					%>
				</div>


</div>
</div>
</div>


</body>
</html>
























	