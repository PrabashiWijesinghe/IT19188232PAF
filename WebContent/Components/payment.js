$(document).ready(function() {
	$("#alertSuccess").hide();
	$("#alertError").hide();
});

// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();

	// Form validation-------------------
	var status = validatePaymentForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}

	// If valid------------------------
	var type = ($("#hidPaymentIdSave").val() == "") ? "POST" : "PUT";
	var formData = new FormData($("#formPayment")[0]);
	 console.log(formData);
	$.ajax({
		url : "PaymentAPI",
		type : type,
		data : formData,
		dataType : "text",
		complete : function(response, status)
		{
			onPaymentSaveComplete(response.responseText, status);
		},
		 processData : false,
		 contentType :false
	});
});

function onPaymentSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);

		if (resultSet.status.trim() == "success") 
		{
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();

			$("#divPaymentGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") 
		{
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}

	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}

	$("#hidPaymentIdSave").val("");
	$("#formPayment")[0].reset();
}

// UPDATE==========================================
$(document).on(
		"click",
		".btnUpdate",
		function(event) {
			$("#hidPaymentIdSave").val(
					$(this).closest("tr").find('#hidPaymentIdUpdate').val());
			$("#CUSID").val($(this).closest("tr").find('td:eq(0)').text());
			$("#CUSName").val($(this).closest("tr").find('td:eq(1)').text());
			$("#Amount").val($(this).closest("tr").find('td:eq(2)').text());
			$("#Bank").val($(this).closest("tr").find('td:eq(3)').text());
			$("#CardNo").val($(this).closest("tr").find('td:eq(4)').text());
			$("#CVV").val($(this).closest("tr").find('td:eq(5)').text());
			$("#paymentDate").val($(this).closest("tr").find('td:eq(6)').text());
			
		});
//CLIENT-MODEL=========================================================================
function validatePaymentForm() {
	
	if ($("#CUSID").val().trim() == "") {
		return "Insert Customer ID.";
	}
	if ($("#CUSName").val().trim() == "") {
		return "Insert Customer Name.";
	}
	if ($("#Amount").val().trim() == "") {
		return "Insert Amount.";
	}
	if ($("#Bank").val() == "0"){
		return "Insert Bank.";
	}
	if ($("#CardNo").val().trim() == "") {
		return "Insert CardNo.";
	}
	if ($("#CVV").val().trim() == "") {
		return "Insert CVV.";
	}
	if ($("#paymentDate").val().trim() == "") {
		return "Insert paymentDate.";
	}

	return true;
}

// REMOVE===========================================
$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "PaymentAPI",
		type : "DELETE",
		data : "PID=" + $(this).data("paymentid"),
		dataType : "text",
		complete : function(response, status) {
			onPaymentDeleteComplete(response.responseText, status);
		}
	});
});

function onPaymentDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);

		if (resultSet.status.trim() == "success") {

			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();

			$("#divPaymentGrid").html(resultSet.data);

		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}

	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}

