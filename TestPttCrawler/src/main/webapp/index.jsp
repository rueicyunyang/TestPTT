<html>
<head>
	<title>Bootstrap Example</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

	<style>
		.div-center {
			width: 600px;
			height: 400px;
			background-color: #fff;
	  
			position: absolute; 
			left: 0;
			right: 0;
			top: 0;
			bottom: 0;
			margin: auto;
			height：100%;
	
			padding: 1em 2em;
			border-bottom: 2px solid #ccc;
		}	
	</style>
</head>
<body>

	<div class="container">
		<div class="div-center">
			<h2>PTT Crawler</h2>
			<form action="ForwardToResultServlet.do" method="post">
				<div class="form-group">
				  <label for="id">ID:</label>
				  <input type="text" class="form-control" id="id" name="id">
				</div>
				<button type="submit" class="btn btn-primary">Query</button>
			</form>
		</div>
	</div>
</body>
</html>
