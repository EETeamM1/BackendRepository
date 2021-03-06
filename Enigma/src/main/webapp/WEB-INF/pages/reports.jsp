<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@page session="true"%>
<html>
<head>
<link href="resources/lib/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="resources/lib/css/enigma.css" rel="stylesheet"	type="text/css">
<link href="resources/lib/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<link href="resources/lib/css/jquery-ui.min.css" rel="stylesheet" type="text/css">
<script src="resources/lib/js/jquery.js"></script>
<script src="resources/lib/js/bootstrap.min.js"></script>
<script src="resources/lib/js/jquery-ui.min.js"></script>
<script src="resources/app/js/common.js"></script>
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
	<div class="container body-content" style="margin-top: 133px;">
	
		<div class="row-content">
			<div>
				<ul class="nav nav-tabs" role="tablist">
					<li role="presentation" class="active"><a href="#default"
						role="tab" data-toggle="tab">All Reports</a></li>
	
					<li role="presentation"><a href="#deviceReport" role="tab"
						data-toggle="tab" id="tabDeviceReport" >Device Reports</a></li>
						
					<li role="presentation"><a href="#userReport" role="tab"
						data-toggle="tab" id="tabUserReport">User Reports</a></li>
				</ul>
			</div>
		</div>
		
		<!-- Tab Panes -->
		<div class="tab-content">
			<div role="tabpanel" class="tab-pane fade in active" id="default">
				<div class="" style="overflow:none;">
					<div id="barchart_top5" style="margin:20px;padding 10px;background-color:#ffffff;"></div>
					<div id="device_issue_trend" style="margin:20px;padding 10px;background-color:#ffffff;"></div>
					<div id="device_submit_trend" style="margin:20px;padding 10px;background-color:#ffffff;"></div>			
				</div>
			</div>
			<div role="tabpanel" class="tab-pane fade in" id="deviceReport">
				<div class="" style="overflow:none;">
					<center><div class="row-content">
						<input class="n3" type="text" id="deviceAutocomplete" device-id="" placeholder="Search Device" autofocus/>
						<span style="font-size:25px; padding:5px;" class="calendar-icon-device"><i class="fa fa-calendar"></i></span>
						<button id="fetchDeviceReport" class="btn btn-primary">Search</button>
					</div>
					<div id="device_timeline_range" class="row-content hide" style="margin-top: 15px;"><span class="calendar-range">1d</span><span class="calendar-range">1w</span><span class="calendar-range">1m</span><span class="calendar-range">3m</span></div></center>
					<div id="device_timeline" style="margin:20px;padding 10px;background-color:#ffffff;"></div>	
				</div>
			</div>
			<div role="tabpanel" class="tab-pane fade in" id="userReport">
				<div class="" style="overflow:none">
					<center><div class="row-content">
						<input class="n3" type="text" id="userAutocomplete" user-id="" placeholder="Search User" autofocus/>
						<span style="font-size:25px; padding:5px;" class="calendar-icon-user"><i class="fa fa-calendar"></i></span>
						<button id="fetchUserReport" class="btn btn-primary">Search</button>
					</div>
					<div id="user_timeline_range" class="row-content hide" style="margin-top: 15px;"><span class="calendar-range-user">1d</span><span class="calendar-range-user">1w</span><span class="calendar-range-user">1m</span><span class="calendar-range-user">3m</span></div></center>
					<div id="user_timeline" style="margin:20px;padding 10px;background-color:#ffffff;"></div>	
				</div>
			</div>
		</div>
	</div>
	<div id="alertModal" class="modal fade">
	  <div class="modal-dialog modal-sm">
	    <div class="modal-content">
	      <!-- dialog body -->
	      <div class="modal-body">
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	        <span id="alertMessage"></span>
	      </div>
	      <div class="modal-footer"><button type="button" class="btn btn-primary" data-dismiss="modal">OK</button></div>
	    </div>
	  </div>
	</div>
	<script src="resources/app/js/report.js"></script>
	<sec:authorize access="hasRole('ROLE_ADMIN')"><script src="resources/app/js/approval-requests.js"></script></sec:authorize>
</body>