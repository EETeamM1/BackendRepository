<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page isELIgnored="true"%>
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
						<td>Available</td>
						<td>Issued</td>
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
					<span class="input-group-addon windows_icon"><i class="fa fa-windows"></i></span>
					<span class="input-group-addon android_icon"><i class="fa fa-android"></i></span>
					<span class="input-group-addon apple_icon"><i class="fa fa-apple"></i></span>
					<input type="text" id="search_box" tabindex="1"
						class="form-control typeahead"  data-provide="typeahead" placeholder="Search" value=""> <span
						class="input-group-addon"><i
						class="glyphicon glyphicon-search"></i></span>
				</div>
			</div>
		</div>
		<div style="margin: 20px;" class="row"></div>
		<div id="device_list" style="margin: auto;">			
			<!-- Device list from server -->
		</div>
	</div>

	<!-- device template -->
	<script id="deviceStatusTemplate" type="text/x-jQuery-tmpl">
		<div class="col-sm-4 list-group-item device_block" style="margin: 10px; padding: 10px;">
				<div style="display:none">${searchKeywords}</div>
				<div class="col-sm-3"><i class="fa fa-${img}" style="font-size:30px;"></i></div>
				<div class="col-sm-9"><label class="control-label">Device:
					<button type="button" class="btn btn-link list_device_name" data-toggle="modal"
						data-target="#Iphone6">${device_name}</button>
				</label> <br> <label class="control-label">Status: <span class="list_status">${status}</span></label></br></div>
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
	
	<script type="text/javascript"
		src="http://ajax.aspnetcdn.com/ajax/jquery.templates/beta1/jquery.tmpl.js"></script>
 
	<script language="javascript" src="resources/app/js/index.js"></script>
</body>
</html>
