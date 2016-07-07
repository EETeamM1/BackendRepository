<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link href="resources/lib/css/bootstrap.min.css" rel="stylesheet"
	type="text/css">
<link href="resources/lib/css/enigma.css" rel="stylesheet"
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
					<li><a href="#">Reports</a></li>
					<li><a href="entities">Entities</a></li>
					<li><a href="#">Requests<span class="badge"
							id="requestCount">4</span></a></li>
				</ul>
			</div>
		</div>
	</nav>
	<!---Header ends--->

	<div class="body-content" style="margin-top: -40px;">
		<div class="row" style="border: 1px solid #ddd; padding: 10px;"vertical-align:center;"">
			<div class="col-sm-3">
				<table class="table borderless">
					<tr>
						<td>Total</td>
						<td>Issued</td>
						<td>Available
					</tr>
					</tr>
					<tr>
						<td>100</td>
						<td>40</td>
						<td>60</td>
					</tr>
				</table>
			</div>
			<div align="right" style="vertical-align: center;">
				<div class="form-group col-md-8">
					<a class="btn" href="#"><i class="fa fa-windows"
						style="font-size: 30px;"></i></a> <a class="btn" href="#"><i
						class="fa fa-android" style="font-size: 30px;"></i></a> <a class="btn"
						href="#"><i class="fa fa-apple" style="font-size: 30px;"></i></a>

					<div class="input-group">
						<input type="text" name="username" id="search_box" tabindex="1"
							class="form-control" placeholder="Search" value="" autofocus>
						<span class="input-group-addon"><i
							class="glyphicon glyphicon-search"></i></span>
					</div>
				</div>
			</div>
		</div>
		<div style="margin-top: 20px;"></div>

		<div id="device_list">
			<div class="col-sm-4 list-group-item">
				<img src="http://placehold.it/400x250/000/fff"
					class="img-thumbnail col-sm-3" alt="apple" width="100" height="50">
				<label class="control-label">Device:
					<button type="button" class="btn btn-link" data-toggle="modal"
						data-target="#Iphone6">Iphone6</button>
				</label> <br> <label class="control-label">Status:Available</label></br>
				<button type="button" class="btn btn-default" data-toggle="modal"
					data-target="#myModal">Issue</button>
			</div>

			<div class="col-sm-4 list-group-item">
				<img src="resources/lib/images/android.png"
					class="img-thumbnail col-sm-3" alt="apple" width="100" height="50">
				<label class="control-label">Device:
					<button type="button" class="btn btn-link" data-toggle="modal"
						data-target="#Android">Android 4.3</button>
				</label> <br> <label class="control-label">Status:On Repair</label></br>
			</div>
		</div>
	</div>

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
</body>
</html>
