<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@page session="true"%>
<html>
<head>
<link href="resources/lib/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="resources/lib/css/enigma.css" rel="stylesheet"	type="text/css">
<link href="resources/lib/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<script src="resources/lib/js/jquery.js"></script>
<script src="resources/lib/js/bootstrap.min.js"></script>
<script src="resources/lib/js/google-chart-loader.js"></script>
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
                <ul class="nav navbar-nav navbar-right font-bold">
					<c:if test="${pageContext.request.userPrincipal.name != null}">
						<li><a href="/profile">${pageContext.request.userPrincipal.name}</a></li>
						<li><a href="<c:url value="/j_spring_security_logout"/>">
								Logout</a></li>
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
					<li><a href="#">Profile</a></li>
					<sec:authorize access="hasRole('ROLE_ADMIN')">
			        <li class="active"><a href="report">Reports</a></li>
					<li><a href="entities">Entities</a></li>
					<li><a href="#" data-toggle="popover" id="ApprovalRequests">Requests<span class="badge"
							id="requestCount"></span></a></li>
					</sec:authorize>
				</ul>
			</div>
		</div>
	</nav>
	</div>
	<!---Header ends--->
	
	<!-- Chart body starts here -->
	<div class="body-content" style="margin-top: 133px;">
		<div class="" style="overflow:none;">
			<div id="device_issue_trend" style="margin:20px;padding 10px;border:1px solid #eee;background-color:#ffffff;"></div>
			<div id="device_submit_trend" style="margin:20px;padding 10px;border:1px solid #eee;background-color:#ffffff;"></div>			
		</div>
	</div>
	<script src="resources/app/js/report.js"></script>
	<sec:authorize access="hasRole('ROLE_ADMIN')"><script src="resources/app/js/approval-requests.js"></script></sec:authorize>
</body>