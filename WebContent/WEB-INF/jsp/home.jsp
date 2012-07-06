<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="../css/bootstrap.css" />
<script type="text/javascript" src="../JavaScripts/wfms.js"></script>
<script type="text/javascript" src="../JavaScripts/jquery-latest.js"></script>
<script type="text/javascript" src="../JavaScripts/jquery.tablesorter.js"></script>
 <style type="text/css">
      body {
        padding-top: 60px;
        padding-bottom: 40px;
      }
      .sidebar-nav {
        padding: 9px 0;
      }
    </style>
<title>WFMS</title>
</head>
<body>
<div class="navbar navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container-fluid">
          <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>
          <a class="brand" href="#">Web Content Management </a>
          <div class="nav-collapse">
            <ul class="nav">
              <li class="active"><a href="#">Home</a></li>
              <li><a href="#about">About</a></li>
              <li><a href="#contact">Contact</a></li>
            </ul>
            <p class="navbar-text pull-right"><a href="<c:url value="/j_spring_security_logout" />"> Logout</a></p>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span3">
				<h3>My Directory</h3>
				<div id="treeView" class="well sidebar-nav"></div>
				<h3>Shared Folders</h3>
				<div id="sharedFolders" class="well sidebar-nav"></div>
			</div>
			
			<div class="span9">
          		<div id="right" class="hero-unit"></div>
          	</div>
		</div>
	</div>
<script>
$(document).ready(function() {
	$("#treeView").append(Directory_to_ul(${json}));
	var sharedFjsons = ${sharedNodesJson};
	var n = sharedFjsons.length;
	for(var i= 0 ; i < n; i++){
		$("#sharedFolders").append(sharedFolder_to_ul(sharedFjsons[i]));
	}
});
</script>
</body>
</html>