<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@page session="true"%>
<head>
<link rel="shortcut icon" type="image/png" href="includes/uimgs/fav.ico">
<link href="resources/lib/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="resources/lib/css/enigma.css" rel="stylesheet" type="text/css">
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
					<li><a href="welcome">Home</a></li>
					<sec:authorize access="hasRole('ROLE_ADMIN')">
						<li><a href="report">Reports</a></li>
						<li><a href="entities">Entities</a></li>
						<li><a href="#" data-toggle="popover" id="ApprovalRequests">Requests<span
								class="badge" id="requestCount"></span></a></li>
					</sec:authorize>
				</ul>
			</div>
		</div>
	</nav>
	</div>
	<!---Header ends--->

	<span class="hide" id="userIdA">${pageContext.request.userPrincipal.name}</span>
	<div  class="container body-content" style="margin-top: 133px;">
	<div class="panel panel-login">
	<div class="panel-body">
    	<div class="row col-md-12" ><center>
    		<table id="profile_table">
    			<tr>
    				<td><label class="control-label" >User Name</label></td>
    				<td>:</td>
    				<td><label><span id="userName"></span></label></td>
    			</tr>
    			<tr>
    				<td><label class="control-label" >User Id</label></td>
    				<td>:</td>
    				<td><label><span id="userId"></span></label></td>
    			</tr>
    			<tr>
    				<td colspan="2"></td>
    				<td><button type="button" class="btn btn-default" data-toggle="modal" data-target="#password">Update Password</button></td>
    			</tr>
    		</table></center>
    		</div>
    	</div>
    </div>
    </div>
    	
    	
    	<div class="modal fade" id="password" role="dialog">
         	<div class="modal-dialog modal-md">
      			<div class="modal-content">
        		<div class="modal-header">
          			<h3 class="modal-title">Update Password</h3>
        		</div>
        		
          			<form id="form_update_user" role="form">
							<div class="form-group col-sm-4">
								<label class="control-label">Current Password</label> <input
									type="password" class="form-control" id="ud_password"
									placeholder="Current Password" required oninput="setCustomValidity('')">
							</div>

							<div class="form-group col-sm-4">
								<label class="control-label">New Password </label> <input
									type="password" class="form-control" id="ud_new_password"
									placeholder="New Password" data-validation="length" required oninput="setCustomValidity('')">
							</div>

							<div class="form-group col-sm-4">
								<label class="control-label">Confirm Password</label> <input
									type="password" class="form-control" id="ud_confirm_password"
									name="password" placeholder="Confirm Password" required oninput="setCustomValidity('')">
							</div>
							<div class="modal-footer">
								<a href="#" id="update_password_btn" class="btn btn-primary" role="button">Update</a>
				                <button type="submit" class="hide"></button>
          						<button type="button" class="btn btn-danger" data-dismiss="modal">Cancel</button>
          						</div>
        		    </form>
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

	<script language="javascript" src="resources/app/js/profile.js"></script>
</body>