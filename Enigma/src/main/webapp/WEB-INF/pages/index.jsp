<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@page session="true"%>
<html>
<head>
<link href="resources/lib/css/bootstrap.min.css" rel="stylesheet"
	type="text/css">
<link href="resources/lib/css/jquery-ui.min.css" rel="stylesheet"
	type="text/css">
<link href="resources/lib/css/enigma.css" rel="stylesheet"
	type="text/css">
<link href="resources/lib/css/font-awesome.min.css" rel="stylesheet"
	type="text/css">
<script src="resources/lib/js/jquery.js"></script>
<script src="resources/lib/js/bootstrap.min.js"></script>
<script src="resources/lib/js/jquery-ui.min.js"></script>
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
    							<li><a href="/profile" style="border-bottom:none;">Profile</a></li>
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
					<li class="active"><a href="#" id="home">Home</a></li>
					<sec:authorize access="hasRole('ROLE_ADMIN')">
			        <li><a href="report">Reports</a></li>
					<li><a href="entities">Entities</a></li>
					<li><a href="#" data-toggle="popover" id="ApprovalRequests">Requests<span class="badge"
							id="requestCount"></span></a></li>
					</sec:authorize>
					<sec:authorize access="hasRole('ROLE_USER')">
					<li id="myDevices"><a href="#" >My Devices</a></li>
					</sec:authorize>
				</ul>
			</div>
		</div>
	</nav>
	<div class="container search-bar-top" >
			<div class="col-sm-2">
				<table class="table borderless">
					<tr>
						<td class="n1 font-bold">Total</td>
						<td class="n1 font-bold">Available</td>
						<td class="n1 font-bold">Issued</td>
						<td class="n1 font-bold">Pending</td>
					</tr>
					<tr>
						<td><span class="n3 font-bold" id="totalDevices"></span></td>
						<td><span class="n3 font-bold" id="availableDevices"></span></td>
						<td><span class="n3 font-bold" id="issuedDevices"></span></td>
						<td><span class="n3 font-bold" id="pendingDevices"></span></td>
					</tr>
				</table>
			</div>
			<div class="col-sm-3"></div>
			<div class="col-sm-7" style="border: 1px solid #cdcdcd; padding: 10px;">
				<div class="input-group">
					<span class="input-group-addon windows_icon"><i
						class="fa fa-windows"></i></span> <span
						class="input-group-addon android_icon"><i
						class="fa fa-android"></i></span> <span
						class="input-group-addon apple_icon"><i class="fa fa-apple"></i></span><span
						class="input-group-addon" style="border:none;"></span>
					<input type="text" id="search_box" style="border-color: #cdcdcd;" tabindex="1"
						class="form-control" placeholder="Search" value="">
				</div>
			</div>
		</div>
	</div>
	<!---Header ends--->

	<div class="container body-content">
		
		<div id="device_list" style="margin: auto;">			
			<!-- Device list from server -->
		</div>
	</div>

	<!-- device template -->
	<script id="deviceStatusTemplate" type="text/x-jQuery-tmpl">
		<div class="col-md-4 list-group-item device_block">
				<div style="display:none">@{searchKeywords}</div>
				<div class="col-md-1" style="padding:5px;"><i class="fa fa-@{img}" style="font-size:30px;"></i></div>
				<div class="col-md-11"><label class="control-label t2 n1 font-bold">Device <br/>
					<button type="button" class="btn-link list_device_name t1 r1" id="@{deviceId}" style="padding:0px;border:none;font-weight:bold;" data-toggle="modal">@{device_name}</button>
				</label><br/><label class="control-label t2 n1 font-bold hide">DeviceId <br/><span class="t1 n3"> @{deviceId}</span></label><!--<br/>--><label class="control-label t2 n1 font-bold">OS Version <br/><span class="t1 n3"> @{OSVersion}</span></label><br/><label class="control-label t2 n1 font-bold">Status <br/> <span class="list_status t1 n3">@{status}</span><span class="@{hideIssuedDetails}"> - ( @{user_name} )</span></label></br></div>
				<div style="text-align:right; margin-right: 10px;">
				<button type="button" class="btn btn-success issue_device_modal @{hideAvailable}" device-id="@{deviceId}" device-name="@{device_name}" data-toggle="modal">Issue</button>
				<button type="button" class="btn btn-warning submit_device @{hideIssued}" device-id="@{deviceId}" device-name="@{device_name}" user-id="@{user_id}" data-toggle="modal">Submit</button>
				</div>
			</div>
	</script>

	<!-- Modal -->
	<div class="modal fade" id="deviceIssueModal" role="dialog">
		<div class="modal-dialog modal-md">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title">Issue to</h3>
				</div>
				<div class="modal-body">
					<div class="col-sm-6">
						<label>Select User to Issue device</label>
						<input type="text" id="userAutocomplete" class="form-control t2"
							placeholder="Search User" autofocus>
					</div>
					<div class="col-sm-6">
						<label class="control-label n1 font-bold">Device <br/> <span
							class="n3" id="issue_modal_name"></span></label><br /> <label class="control-label n1 font-bold">Devic
							Id <br/> <span class="n3" id="issue_modal_device_id"></span>
						</label><br /> <label class="control-label n1 font-bold">User <br/> <span
							class="n3" id="issue_modal_issued_to"></span></label><br /> <label
							class="control-label hide">UserId  <span
							class="n3" id="issue_modal_user_id"></span></label><br />
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger" data-dismiss="modal">Cancel</button>
					<a href="#" id="issue_btn" class="btn btn-success hide" role="button">Issue</a>
				</div>
			</div>
		</div>
	</div>
	<div class="hide" id="isAdmin"><sec:authorize access="hasRole('ROLE_ADMIN')">true</sec:authorize></div>
	<div class="hide" id="loggedInUserId">${pageContext.request.userPrincipal.name}</div>
	<div class="modal fade" id="deviceDetailModal" role="dialog">
		<div class="modal-dialog modal-md">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title">Device Details</h3>
				</div>
				<div class="modal-body">
					<table>
						<tbody>
							<tr>
								<td class="n1 font-bold">Name</td>
								<td>:&nbsp&nbsp&nbsp</td>
								<td class="n3 font-bold" id="device_detail_modal_name"></td>
							</tr>
							<tr>
								<td class="n1 font-bold">Unique Id</td>
								<td>:&nbsp&nbsp&nbsp</td>
								<td class="n3 font-bold" id="device_detail_modal_id"></td>
							</tr>
							<tr>
								<td class="n1 font-bold">Manufacturer</td>
								<td>:&nbsp&nbsp&nbsp</td>
								<td class="n3 font-bold" id="device_detail_modal_manufacturer"></td>
							</tr>
							<tr>
								<td class="n1 font-bold">OS</td>
								<td>:&nbsp&nbsp&nbsp</td>
								<td class="n3 font-bold" id="device_detail_modal_os"></td>
							</tr>
							<tr>
								<td class="n1 font-bold">OS Version</td>
								<td>:&nbsp&nbsp&nbsp</td>
								<td class="n3 font-bold" id="device_detail_modal_os_version"></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger" data-dismiss="modal">Cancel</button>
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
	<div id="confirmModal" class="modal fade">
	<div class="modal-dialog modal-sm">
	 <div class="modal-content ">
	  <div class="modal-body">
	    Are you sure?<br/>
	    <table>
			<tbody>
				<tr>
					<td class="n1 font-bold">Device</td>
					<td>:&nbsp&nbsp&nbsp</td>
					<td class="n3 font-bold" id="confirmModal_device_detail_modal_name"></td>
				</tr>
				<tr>
					<td class="n1 font-bold">Unique Id</td>
					<td>:&nbsp&nbsp&nbsp</td>
					<td class="n3 font-bold" id="confirmModal_device_detail_modal_id"></td>
				</tr>
				<tr>
					<td class="n1 font-bold">User</td>
					<td>:&nbsp&nbsp&nbsp</td>
					<td class="n3 font-bold" id="confirmModa_device_detail_modal_user"></td>
				</tr>
			</tbody>
		</table>
	  </div>
	  <div class="modal-footer">
	    <button type="button" data-dismiss="modal" class="btn btn-primary" id="confirmModalYes">Yes</button>
	    <button type="button" data-dismiss="modal" class="btn btn-warning">No</button>
	  </div>
	  </div>
	  </div>
	</div>
	
	<script src="resources/lib/js/jquery.tmpl.min.js"></script>
	<script src="resources/app/js/index.js"></script>
	<script src="resources/app/js/approval-requests.js"></script>
</body>
</html>
