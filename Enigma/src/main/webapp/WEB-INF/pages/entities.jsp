<head>
<link rel="shortcut icon" type="image/png" href="includes/uimgs/fav.ico">
<link href="resources/lib/css/bootstrap.min.css" rel="stylesheet"
	type="text/css">
<link href="resources/lib/css/enigma.css" rel="stylesheet"
	type="text/css">
<script language="javascript" src="resources/lib/js/jquery.js"></script>
<script language="javascript" src="resources/lib/js/bootstrap.min.js"></script>
<script language="javascript" src="resources/app/js/constants.js"></script>
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
					<li ><a href="welcome">Home</a></li>
					<li><a href="#">Profile</a></li>
					<li><a href="#">Reports</a></li>
					<li class="active"><a href="entities">Entities</a></li>
					<li><a href="#">Requests<span class="badge"
							id="requestCount">4</span></a></li>
				</ul>
			</div>
		</div>
	</nav>
	<!---Header ends--->

	<div class="body-content">
		<div id="entities" class="row row-content">
			<div class="col-md-12">
				<ul class="nav nav-tabs" role="tablist">
					<li role="presentation" class="active"><a href="#devices"
						role="tab" data-toggle="tab">Devices</a></li>

					<li role="presentation"><a href="#user" role="tab"
						data-toggle="tab">Users</a></li>
						
					<li role="presentation"><a href="#masterData" role="tab"
						data-toggle="tab">Master Data</a></li>
				</ul>
			</div>
		</div>

		<!-- Tab Panes -->

		<div class="tab-content">
			<div role="tabpanel" class="tab-pane fade in active" id="devices">
				<div class="row row-content">
					<form class="form" role="form" id="form_device_submit">
						<p style="padding: 10px"></p>
						<div class="col-xs-12 col-sm-10">
							<div class="form-group col-sm-3">
								<label for="deviceId" class="control-label">Device Id</label> <input
									type="text" class="form-control" id="deviceId" name="deviceId"
									placeholder="Enter Device Id" required oninput="setCustomValidity('')">
							</div>

							<div class="form-group col-sm-3">
								<label for="deviceName" class="control-label">Device
									Name </label> <input type="text" class="form-control" id="deviceName"
									name="deviceName" placeholder="Enter Device Name" required oninput="setCustomValidity('')">
							</div>

							<div class="form-group col-sm-3">
								<label for="manufacturer" class="control-label">Manufacturer</label>
								<input type="text" class="form-control" id="manufacturer"
									name="manufacturer" placeholder="Enter Manufacturer" required oninput="setCustomValidity('')">
							</div>

							<div class="form-group col-sm-3">
								<label for="OS" class="control-label">Operating System</label> <input
									type="text" class="form-control" id="OS" name="OS"
									placeholder="Enter Operating System" required oninput="setCustomValidity('')">
							</div>
							<div class="form-group col-sm-3">
								<label for="OSVersion" class="control-label">OS Version</label>
								<input type="text" class="form-control" id="OSVersion"
									name="OSVersion" placeholder="Enter OS Version" required oninput="setCustomValidity('')">
							</div>

							<div class="form-group col-sm-3">
								<label for="yearOfManufacturing" class="control-label">Year
									Of Manufacturing</label> <input type="text" class="form-control"
									id="yearOfManufacturing" name="yearOfManufacturing"
									placeholder="Enter Year Of Manufacturing"  required oninput="setCustomValidity('')">
							</div>

							<div class="form-group col-sm-3">
								<label for="timeoutPeriod" class="control-label">Timeout
									Period</label> <input type="text" class="form-control"
									id="timeoutPeriod" name="timeoutPeriod"
									placeholder="Enter Timeout Period" required oninput="setCustomValidity('')">
							</div>
						</div>
						<div class="form-group col-sm-6">
							<div class="row">
								<div class="form-group col-sm-3 col-sm-offset-1">
									<input type="submit" name="device-submit" id="device-submit"
										tabindex="4" class="form-control btn btn-login" value="Submit">
								</div>
								<div class="form-group col-sm-3 col-sm-offset-1">
									<input type="reset" name="device-reset" id="device-reset"
										tabindex="4" class="form-control btn btn-login" value="Reset">
								</div>
							</div>
						</div>

					</form>
				</div>
			</div>


			<div role="tabpanel" class="tab-pane fade" id="user">
				<div class="row row-content">
					<form class="form" id="form_user_save">
						<p style="padding: 10px"></p>
						<div class="col-xs-12 col-sm-10">
							<div class="form-group col-sm-3">
								<label for="userId" class="control-label">User Id</label> <input
									type="text" class="form-control" id="userId" name="userId"
									placeholder="Enter User Id" oninput="setCustomValidity('')" required>
							</div>

							<div class="form-group col-sm-3">
								<label for="userName" class="control-label">User Name </label> <input
									type="text" class="form-control" id="userName" name="userName"
									placeholder="Enter User Name" oninput="setCustomValidity('')" required>
							</div>

							<div class="form-group col-sm-3">
								<label for="password" class="control-label">Password</label> <input
									type="password" class="form-control" id="user_password"
									name="password" placeholder="Enter Password" required oninput="setCustomValidity('')">
							</div>
						</div>
						<div class="form-group col-sm-6">
							<div class="row">
								<div class="form-group col-sm-3 col-sm-offset-1">
									<input type="submit" name="device-submit" id="user-submit"
										tabindex="4" class="form-control btn btn-login" value="Submit">
								</div>
								<div class="form-group col-sm-3 col-sm-offset-1">
									<input type="reset" name="device-reset" id="user-reset"
										tabindex="4" class="form-control btn btn-login" value="Reset">
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
			
			
			<div role="tabpanel" class="tab-pane fade" id="masterData">
				<div class="row row-content">
					<form id="form_master" role="form">
						<p style="padding: 10px"></p>
						<div class="col-xs-12 col-sm-10">
							<div class="form-group col-sm-3">
								<label for="master_current" class="control-label">Current Password</label> <input
									type="password" class="form-control" id="md_password"
									placeholder="Current Password" required oninput="setCustomValidity('')">
							</div>

							<div class="form-group col-sm-3">
								<label for="master_new" class="control-label">New Password </label> <input
									type="password" class="form-control" id="md_new_password"
									placeholder="New Password" data-validation="length" required oninput="setCustomValidity('')">
							</div>

							<div class="form-group col-sm-3">
								<label for="master_new" class="control-label">Confirm Password</label> <input
									type="password" class="form-control" id="md_confirm_password"
									name="password" placeholder="Confirm Password" required oninput="setCustomValidity('')">
							</div>
						</div>
						<div class="form-group col-sm-6">
							<div class="row">
								<div class="form-group col-sm-3 col-sm-offset-1">
									<input type="submit" id="master-submit"
										tabindex="4" class="form-control btn btn-login" value="Submit">
								</div>
								<div class="form-group col-sm-3 col-sm-offset-1">
									<input type="reset"  id="master-reset"
										tabindex="4" class="form-control btn btn-login" value="Reset">
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	
	<script language="javascript" src="resources/app/js/entities.js"></script>
</body>