<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@page session="true"%>
<head>
<link rel="shortcut icon" type="image/png" href="includes/uimgs/fav.ico">
<link href="resources/lib/css/bootstrap.min.css" rel="stylesheet"
	type="text/css">
<link href="resources/lib/css/enigma.css" rel="stylesheet"
	type="text/css">
<script src="resources/lib/js/jquery.js"></script>
<script src="resources/lib/js/bootstrap.min.js"></script>
<script src="resources/app/js/common.js"></script>
</head>
<body>
	<!---Header start--->
	<div class="navbar-fixed-top">
	<nav class="navbar navbar-first">
        <div class="container navbar-first-container">
            <div class="navbar-header">
                <a class="navbar-brand" href="#"><img height="40" alt="logo" src="resources/lib/images/logo.png"></a>
            </div>
            <div class="collapse navbar-collapse">
                <ul class="nav navbar-nav navbar-right">
					<c:if test="${pageContext.request.userPrincipal.name != null}">
						<li role="presentation" class="dropdown">
						<a class="dropdown-toggle" data-toggle="dropdown" href="#" style="border-bottom:none;" role="button" aria-haspopup="true" aria-expanded="false">
      						${pageContext.request.userPrincipal.name} <span class="caret"></span></a>
    						<ul class="dropdown-menu">
    							<li><a href="profile" style="border-bottom:none;">Profile</a></li>
						<li><a href="<c:url value="/j_spring_security_logout"/>" style="border-bottom:none;">
								Logout</a></li>
    						</ul>
						</li>
					</c:if>
				</ul>
            </div>
        </div>
    </nav>
	<nav class="navbar navbar-inverse second-navbar">
		<div class="container">
			<div class="collapse navbar-collapse" id="myNavbar">
				<ul class="nav navbar-nav">
					<li><a href="welcome">Home</a></li>
					<sec:authorize access="hasRole('ROLE_ADMIN')">
						<li><a href="report">Reports</a></li>
						<li class="active"><a href="entities">Entities</a></li>
						<li><a href="#" data-toggle="popover" id="ApprovalRequests">Requests<span
								class="badge" id="requestCount"></span></a></li>
					</sec:authorize>
				</ul>
			</div>
		</div>
	</nav>
	</div>
	<!---Header ends--->

	<div class="container body-content" style="margin-top: 133px;">
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
									data-toggle="tooltip" title="IMI example: 1234567890"
									placeholder="Enter Device Id" required
									oninput="setCustomValidity('')">
							</div>

							<div class="form-group col-sm-3">
								<label for="deviceName" class="control-label">Device
									Name </label> <input type="text" class="form-control" id="deviceName"
									name="deviceName" data-toggle="tooltip"
									title="example: Iphone 6s" placeholder="Enter Device Name"
									required oninput="setCustomValidity('')">
							</div>

							<div class="form-group col-sm-3">
								<label for="manufacturer" class="control-label">Manufacturer</label>
								<input type="text" class="form-control" id="manufacturer"
									data-toggle="tooltip" title="example: Apple or Samsung"
									name="manufacturer" placeholder="Enter Manufacturer" required
									oninput="setCustomValidity('')">
							</div>

							<div class="form-group col-sm-3">
								<label for="OS" class="control-label">Operating System</label> <select
									class="form-control" id="OS" name="OS">
									<option value="Android">Android</option>
									<option value="Ios">Ios</option>
									<option value="Windows">Windows</option>
									<option value="Other">Other</option>
								</select>
							</div>
							<div class="form-group col-sm-3">
								<label for="OSVersion" class="control-label">OS Version</label>
								<input type="text" class="form-control" id="OSVersion"
									name="OSVersion" placeholder="Enter OS Version" required
									oninput="setCustomValidity('')">
							</div>

							<div class="form-group col-sm-3">
								<label for="yearOfManufacturing" class="control-label">Year
									Of Manufacturing</label> <input type="text" class="form-control"
									id="yearOfManufacturing" name="yearOfManufacturing"
									placeholder="Enter Year Of Manufacturing" required
									oninput="setCustomValidity('')">
							</div>

							<div class="form-group col-sm-3">
								<label for="timeoutPeriod" class="control-label">Timeout
									Period</label> <select class="form-control" id="timeoutPeriod"
									name="timeoutPeriod">
									<option value="00:30:00">30 Min</option>
									<option value="00:60:00">60 Min</option>
									<option value="00:90:00">90 Min</option>
									<option value="00:120:00">120 Min</option>
									<option value="00:180:00">180 Min</option>
									<option value="00:240:00">240 Min</option>
								</select>
							</div>
						</div>
						<div class="form-group col-sm-6">
							<div class="row">
								<div class="form-group col-sm-3 col-sm-offset-1">
									<input type="submit" name="device-submit" id="device-submit"
										tabindex="4" class="form-control btn btn-primary" value="Submit">
								</div>
								<div class="form-group col-sm-3 col-sm-offset-1">
									<input type="reset" name="device-reset" id="device-reset"
										tabindex="4" class="form-control btn btn-primary" value="Reset">
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
									placeholder="Enter User Id" oninput="setCustomValidity('')"
									required>
							</div>

							<div class="form-group col-sm-3">
								<label for="userName" class="control-label">User Name </label> <input
									type="text" class="form-control" id="userName" name="userName"
									placeholder="Enter User Name" oninput="setCustomValidity('')"
									required>
							</div>

							<div class="form-group col-sm-3">
								<label for="password" class="control-label">Password</label> <input
									type="password" class="form-control" id="user_password"
									name="password" placeholder="Enter Password" required
									oninput="setCustomValidity('')">
							</div>
						</div>
						<div class="form-group col-sm-6">
							<div class="row">
								<div class="form-group col-sm-3 col-sm-offset-1">
									<input type="submit" name="device-submit" id="user-submit"
										tabindex="4" class="form-control btn btn-primary" value="Submit">
								</div>
								<div class="form-group col-sm-3 col-sm-offset-1">
									<input type="reset" name="device-reset" id="user-reset"
										tabindex="4" class="form-control btn btn-primary" value="Reset">
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
								<label for="master_current" class="control-label">Current
									Password</label> <input type="password" class="form-control"
									id="md_password" placeholder="Current Password" required
									oninput="setCustomValidity('')">
							</div>

							<div class="form-group col-sm-3">
								<label for="master_new" class="control-label">New
									Password </label> <input type="password" class="form-control"
									id="md_new_password" placeholder="New Password"
									data-validation="length" required
									oninput="setCustomValidity('')">
							</div>

							<div class="form-group col-sm-3">
								<label for="master_new" class="control-label">Confirm
									Password</label> <input type="password" class="form-control"
									id="md_confirm_password" name="password"
									placeholder="Confirm Password" required
									oninput="setCustomValidity('')">
							</div>
						</div>
						<div class="form-group col-sm-6">
							<div class="row">
								<div class="form-group col-sm-3 col-sm-offset-1">
									<input type="submit" id="master-submit" tabindex="4"
										class="form-control btn btn-primary" value="Submit">
								</div>
								<div class="form-group col-sm-3 col-sm-offset-1">
									<input type="reset" id="master-reset" tabindex="4"
										class="form-control btn btn-primary" value="Reset">
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<script language="javascript" src="resources/app/js/entities.js"></script>
	<script language="javascript" src="resources/app/js/deviceValidation.js"></script>
	
	<sec:authorize access="hasRole('ROLE_ADMIN')">
		<script src="resources/app/js/approval-requests.js"></script>
	</sec:authorize>
</body>