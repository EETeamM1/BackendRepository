<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page isELIgnored="true" %>
<html>
<head>
<link href="resources/lib/css/bootstrap.min.css" rel="stylesheet"
	type="text/css">
<link href="resources/lib/css/enigma.css" rel="stylesheet"
	type="text/css">
	<link href="resources/lib/css/font-awesome.min.css" rel="stylesheet"
	type="text/css">
<script src="resources/lib/js/jquery.js"></script>
<script src="resources/lib/js/bootstrap.min.js"></script>
 
</head>
<body>

	<!---Header start--->
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#myNavbar">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="welcome">Inventory Management</a>
			</div>
			<div class="collapse navbar-collapse" id="myNavbar">
				<ul class="nav navbar-nav">
					<li class="active"><a href="welcome">Home</a></li>
					<li><a href="#">Profile</a></li>
					<li><a href="reports">Reports</a></li>
					<li><a href="entities">Entities</a></li>
					<li><a href="#">Requests<span class="badge"
							id="requestCount">4</span></a></li>
				</ul>
			</div>
		</div>
	</nav>
	<!---Header ends--->

	<div class="body-content" style="margin-top: -40px;">
		<div class="col-sm-12 row">
			<div class="col-sm-2">
				<table class="table borderless">
					<tr>
						<td>Total</td>
						<td>Issued</td>
						<td>Available
					</tr>
					</tr>
					<tr>
						<td><span id="totalDevices"></span></td>
						<td><span id="availableDevices"></span></td>
						<td><span id="issuedDevices"></span></td>
					</tr>
				</table>
			</div>
			<div class="col-sm-3"></div>
			<div class="col-sm-7" style="border: 1px solid #ddd; padding: 10px;">
					<div class="input-group">
					<span class="input-group-addon"><i class="fa fa-windows"></i></span>
					<span class="input-group-addon"><i class="fa fa-android"></i></span>
					<span class="input-group-addon"><i class="fa fa-apple"></i></span>
						<input type="text" name="username" id="search_box" tabindex="1"
							class="form-control" placeholder="Search" value=""> <span
							class="input-group-addon"><i
							class="glyphicon glyphicon-search"></i></span>
					</div>
				</div>
			</div>
		<div style="margin: 20px;" class="row"></div>
		<div id="device_list"  style="margin:auto;">
			<!-- Device list from server -->
		</div>
	</div>
	
	<!-- device template -->
	<script id="deviceStatusTemplate" type="text/x-jQuery-tmpl">
		<div class="col-sm-3 list-group-item" style="margin: 10px; padding: 10px;">
				<img src="resources/lib/images/${img}"
					class="img-thumbnail col-sm-1 list_thumbnail" alt="${img}" width="100" height="50">
				<label class="control-label">Device:
					<button type="button" class="btn btn-link list_device_name" data-toggle="modal"
						data-target="#Iphone6">${device_name}</button>
				</label> <br> <label class="control-label">Status: <span class="list_status">${status}</span></label></br>
				<button type="button" class="btn btn-default" data-toggle="modal"
					data-target="#myModal">${button}</button>
			</div>
	</script>

	<!-- Modal -->

	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog modal-md">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title">Issue to</h3>
				</div>
				<div class="modal-body">
					<input type="text" placeholder="Search" />
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					<a href="#" class="btn btn-default" role="button">Issue</a>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="Tablet" role="dialog">
		<div class="modal-dialog modal-md">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title">Device Info</h3>
				</div>
				<div class="modal-body">
					<label class="control-label">Device: Tablet</label> <br> <label
						class="control-label">Version: Windows 10</label></br> <label
						class="control-label">OS: Windows</label>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
				</div>
			</div>
		</div>

		<div class="modal fade" id="Android" role="dialog">
			<div class="modal-dialog modal-md">
				<div class="modal-content">
					<div class="modal-header">
						<h3 class="modal-title">Device Info</h3>
					</div>
					<div class="modal-body">
						<label class="control-label">Device: Lenovo</label> <br> <label
							class="control-label">Version: Android 4.3</label></br> <label
							class="control-label">OS: Android</label>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					</div>
				</div>
			</div>
		</div>

		<div class="modal fade" id="Iphone6" role="dialog">
			<div class="modal-dialog modal-md">
				<div class="modal-content">
					<div class="modal-header">
						<h3 class="modal-title">Device Info</h3>
					</div>
					<div class="modal-body">
						<label class="control-label">Device: Iphone6</label> <br> <label
							class="control-label">Version: Ios6</label></br> <label
							class="control-label">OS: IOS</label>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					</div>
				</div>
			</div>
		</div>

	</div>
	<script type="text/javascript"
		src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.4.4.js"></script>
	<script type="text/javascript"
		src="http://ajax.aspnetcdn.com/ajax/jquery.templates/beta1/jquery.tmpl.js"></script>
 
	<script language="javascript" src="resources/app/js/index.js"></script>
</body>
</html>
