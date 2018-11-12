<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Query Result</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<link rel="stylesheet" href="https://cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css">
	<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>

	<style type="text/css">

		.loader {
			border: 10px solid #f3f3f3;
			border-radius: 50%;
			border-top: 10px solid #3498db;
			width: 50px;
			height: 50px;
			-webkit-animation: spin 2s linear infinite; /* Safari */
			animation: spin 2s linear infinite;

			position: absolute; 
			left: 0;
			right: 0;
			top: 0;
			bottom: 0;
			margin: auto;	  
		}

		@keyframes spin {
		  0% { transform: rotate(0deg); }
		  100% { transform: rotate(360deg); }
		}
		</style>
</head>
<body>
	<div class="loader"></div>
	<div class="container">
		<h1>Query Result</h1>
		<table id="queryResultTable" class="table table-striped" style="width:100%">
	      
	       <!-- Header Table -->
	       <thead>
	            <tr>
	                <th>pushId</th>
					<th>pushContent</th>
	            </tr>
	        </thead>

	        <!-- Footer Table -->
	        <tfoot>
	            <tr>
	                <th>pushId</th>
					<th>pushContent</th>
	            </tr>
	        </tfoot>
	    </table>
	</div>
	<div style="text-align:center">
		<form action="ForwardToResultServlet.do" method="get">
			<button type="submit" class="btn btn-primary">回首頁</button>
		</form>
	</div>
</body>
<script>
	function showLoading(){
		$(".loader").show();
		$(".btn").hide();
		$(".container").hide();
	}
	function hideLoading(){
		$(".loader").hide();
		$(".btn").show();
		$(".container").show();
	}

	 showLoading();

	var queryId = "${queryId}";
	$(document).ready( 
			$.ajax({

		  	type : "POST",
		    url: "/TestPttCrawler/PttGossipingTestCrawler.do",
		    data: {
		        "queryId": queryId
		    },
		    success : function(msg) {
		    	//var msgObj=JSON.parse(msg);
		    	//$("#queryResultTable").dataTable();
		    	$("#queryResultTable").dataTable({
		    		  data: JSON.parse(msg),
		    		  columns: [
		    		      { data: 'pushId'},
		    		      { data: 'pushContent'}
		    		  ]
		    	});
//		    	alert("success");
		    	hideLoading();
            }
		 })		 
	);
</script>
</html>