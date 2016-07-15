<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
<head>
<link href="resources/lib/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="resources/lib/css/enigma.css" rel="stylesheet"	type="text/css">
<link href="resources/lib/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<script src="resources/lib/js/jquery.js"></script>
<script src="resources/lib/js/bootstrap.min.js"></script>
 <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
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
					<li class="active"><a href="report">Reports</a></li>
					<li><a href="entities">Entities</a></li>
					<li><a href="#">Requests<span class="badge"
							id="requestCount">4</span></a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<c:if test="${pageContext.request.userPrincipal.name != null}">
						<li><a href="/profile">Welcome
								${pageContext.request.userPrincipal.name}</a></li>
						<li><a href="<c:url value="/j_spring_security_logout"/>">
								Logout</a></li>
					</c:if>
				</ul>
			</div>
		</div>
	</nav>
	<!---Header ends--->
	
	<!-- Chart body starts here -->
	<div class="body-content" style="margin-top: -40px;">
		<div class="" style="overflow:none;">
			<div id="device_issue_trend" style="margin:20px;padding 10px;border:1px solid #eee"></div>
			<div id="device_submit_trend" style="margin:20px;padding 10px;border:1px solid #eee"></div>			
		</div>
	</div>
	<script src="resources/app/js/report.js"></script>
</body>