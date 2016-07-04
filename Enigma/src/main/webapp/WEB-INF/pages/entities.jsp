<head>
<link rel="shortcut icon" type="image/png" href="includes/uimgs/fav.ico">
<link href="resources/lib/css/bootstrap.min.css" rel="stylesheet"
	type="text/css">
<link href="resources/lib/css/enigma.css" rel="stylesheet"
	type="text/css">
<script language="javascript" src="resources/lib/js/jquery.js"></script>
<script language="javascript" src="resources/lib/js/bootstrap.min.js"></script>
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
					<li><a href="welcome">Home</a></li>
					<li><a href="welcome">Profile</a></li>
					<li><a href="welcome">Reports</a></li>
					<li class="active"><a href="welcome">Entities</a></li>
					<li><a href="welcome">Requests<span class="badge"
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
	</ul>
			</div>
		</div>
		
<!-- Tab Panes -->

	<div class="tab-content">
		<div role="tabpanel" class="tab-pane fade in active" id="devices">
			<div class="row row-content">
                <form class="form" role="form">
                <p style="padding:10px"></p>
                <div class="col-xs-12 col-sm-10">
                    <div class="form-group col-sm-3">
								<label for="deviceId" class="control-label">Device Id</label> <input
									type="text" class="form-control" id="deviceId" name="deviceId"
									placeholder="Enter Device Id">
                    </div>
                    
                     <div class="form-group col-sm-3">
								<label for="deviceName" class="control-label">Device
									Name </label> <input type="text" class="form-control" id="deviceName"
									name="deviceName" placeholder="Enter Device Name">
                    </div>
                    
                    <div class="form-group col-sm-3">
                        <label for="manufacturer" class="control-label">Manufacturer</label>
								<input type="text" class="form-control" id="manufacturer"
									name="manufacturer" placeholder="Enter Manufacturer">
                    </div>
                    
                     <div class="form-group col-sm-3">
								<label for="OS" class="control-label">Operating System</label> <input
									type="text" class="form-control" id="OS" name="OS"
									placeholder="Enter Operating System">
                     </div>
                  	<div class="form-group col-sm-3">
                        <label for="OSVersion" class="control-label">OS Version</label>
								<input type="text" class="form-control" id="OSVersion"
									name="OSVersion" placeholder="Enter OS Version">
                    </div>
                    
                    <div class="form-group col-sm-3">
								<label for="yearOfManufacturing" class="control-label">Year
									Of Manufacturing</label> <input type="text" class="form-control"
									id="yearOfManufacturing" name="yearOfManufacturing"
									placeholder="Enter Year Of Manufacturing">
                    </div>
                    
                    <div class="form-group col-sm-3">
								<label for="timeoutPeriod" class="control-label">Timeout
									Period</label> <input type="text" class="form-control"
									id="timeoutPeriod" name="timeoutPeriod"
									placeholder="Enter Timeout Period">
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
                <form class="form" role="form">
                <p style="padding:10px"></p>
                <div class="col-xs-12 col-sm-10">
                    <div class="form-group col-sm-3">
								<label for="userId" class="control-label">User Id</label> <input
									type="text" class="form-control" id="userId" name="userId"
									placeholder="Enter User Id">
                    </div>
                    
                     <div class="form-group col-sm-3">
								<label for="userName" class="control-label">User Name </label> <input
									type="text" class="form-control" id="userName" name="userName"
									placeholder="Enter User Name">
                    </div>
                    
                    <div class="form-group col-sm-3">
								<label for="password" class="control-label">Password</label> <input
									type="password" class="form-control" id="password"
									name="password" placeholder="Enter Password">
                    </div>
                 </div>
                   <div class="col-xs-12 col-sm-9">
                  <p style="padding:10px"></p>
                  	<button type="submit" class="btn btn-default col-md-1">Create</button>
                  	<button type="reset" class="btn btn-default col-md-1">Cancel</button>
                </div>
                </form>
		     </div>
	     </div>
		</div>
	</div>
</body>