/**
 * 
 */
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
	var status = validateApproveForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}

	// If valid------------------------
	var type = ($("#hidApproveIdSave").val() == "") ? "POST" : "PUT";
	var formData = new FormData($("#formApprove")[0]);
	 console.log(formData);
	$.ajax({
		url : "ApproveAPI",
		type : type,
		data : formData,
		dataType : "text",
		complete : function(response, status)
		{
			onApproveSaveComplete(response.responseText, status);
		},
		 processData : false,
		 contentType :false
	});
});

function onApproveSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);

		if (resultSet.status.trim() == "success") 
		{
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();

			$("#divApproveGrid").html(resultSet.data);
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

	$("#hidApproveIdSave").val("");
	$("#formApprove")[0].reset();
}

// UPDATE==========================================
$(document).on(
		"click",
		".btnUpdate",
		function(event) {
			$("#hidApproveIdSave").val(
					$(this).closest("tr").find('#hidApproveIdUpdate').val());
			$("#CUSID").val($(this).closest("tr").find('td:eq(0)').text());
			$("#CUSName").val($(this).closest("tr").find('td:eq(1)').text());
			$("#Amount").val($(this).closest("tr").find('td:eq(2)').text());
			$("#Bank").val($(this).closest("tr").find('td:eq(3)').text());
			$("#CardNo").val($(this).closest("tr").find('td:eq(4)').text());
			$("#paymentDate").val($(this).closest("tr").find('td:eq(5)').text());
			$("#PayStatus").val($(this).closest("tr").find('td:eq(6)').text());
			$("#ApproveDate").val($(this).closest("tr").find('td:eq(7)').text());
			
		});
//CLIENT-MODEL=========================================================================
function validateApproveForm() {
	
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
	if ($("#paymentDate").val().trim() == "") {
		return "Insert paymentDate.";
	}
	if ($("#PayStatus").val().trim() == "") {
		return "Insert PayStatus.";
	}
	if ($("#ApproveDate").val().trim() == "") {
		return "Insert ApproveDate.";
	}

	return true;
}

// REMOVE===========================================
$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "ApproveAPI",
		type : "DELETE",
		data : "AID=" + $(this).data("approveid"),
		dataType : "text",
		complete : function(response, status) {
			onApproveDeleteComplete(response.responseText, status);
		}
	});
});

function onApproveDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);

		if (resultSet.status.trim() == "success") {

			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();

			$("#divApproveGrid").html(resultSet.data);

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

