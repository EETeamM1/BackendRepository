<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<body>
	<!---Header start--->
	<div class="navbar-fixed-top">
	<nav class="navbar navbar-first">
        <div class="container navbar-first-container">
            <div class="navbar-header">
                <a class="navbar-brand" href="#"><img height="40" alt="logo" src="resources/lib/images/logo.png"></a>
            </div>
        </div>
    </nav>
	<nav class="navbar navbar-inverse second-navbar">
		<div class="container"></div>
	</nav>
	</div>
	<!---Header ends--->

	<!-- Login page body start -->
	<!-- left side content text -->
	<div class="container body-content">
		<div class="col-md-6 hidden-sm hidden-xs">
			<div class="media">
				<!-- <div class="media-left">
					<a href="#"> <img class="media-object" src="..."
						alt="SampleImage">
					</a>
				</div> -->
				<div class="media-body">
					<h3>Use</h3>
					<p>This function enables you to issue the devices to the user
						and store additional attributes. System will prompt with the login
						screen and logged user entry maintain in record. Easy and
						efficient way to track the device. Comprehensive user reports to
						see the activity of particular user, device etc.</p>
					<br />
					<h3>Integration</h3>
					<p>You usually define login on the initial screen to use
						device. The device allocation is tracked if device issued and not
						used or kept ideal.</p>
					<br />
					<h3>Prerequisites</h3>
					<p>The devices to be allocated to one another must have records
						in database. User must have to login to use the device. User not
						allowed to transfer the device and he has to re-login to use the
						device. User should be prompted to re-login after a certain
						configurable time.</p>
					<br />
					<h3>Features</h3>
					<p>
						1. Device allocation history.<br /> 2. One time one device issued
						to one user.<br /> 3. To track the unused device whether not
						issued or issued but not using.<br /> 4. To generate the
						comprehensive reports.<br /> 5. To track the location of device.<br />
					</p>
				</div>
			</div>
		</div>
		<!-- right side login pannel -->
		<div class="col-md-6">
			<div class="panel panel-login">
				<div class="panel-heading">
					<div class="row">
						<div class="col-xs-6">
							<a href="#" class="active" id="login-form-link">Login</a>
						</div>
					</div>
					<hr>
				</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-lg-12">
							<c:if test="${not empty error}">
								<div class="alert alert-danger" role="alert">${error}</div>
							</c:if>
							<c:if test="${not empty msg}">
								<div class="alert alert-success" role="alert">${msg}</div>
							</c:if>
							<form id="login-form"
								action="<c:url value='/j_spring_security_check' />"
								method='POST' role="form" style="display: block;">
								<div class="form-group">
									<label for="username" class="cols-sm-2 control-label">Username</label>
									<div class="cols-sm-10">
										<div class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-user"></i></span> <input type="text"
												name="username" id="username" tabindex="1"
												class="form-control" placeholder="Username" value=""
												autofocus>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label for="password" class="cols-sm-2 control-label">Password</label>
									<div class="cols-sm-10">
										<div class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-lock"></i></span> <input type="password"
												name="password" id="password" tabindex="2"
												class="form-control" placeholder="Password">
										</div>
									</div>
								</div>
								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
								<div class="form-group">
									<div class="row">
										<div class="col-sm-6 col-sm-offset-3">
											<input type="submit" name="login-submit" id="login-submit"
												tabindex="4" class="form-control btn btn-login"
												value="Log In">
										</div>
									</div>
								</div>
								<div class="form-group">
									<div class="row">
										<div class="col-lg-12">
											<div class="text-center">
												<a href="" tabindex="5" class="forgot-password">Forgot
													Password?</a>
											</div>
										</div>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Login page body ends -->
</body>