<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
	"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.0.0-rc2/css/bootstrap.css" type="text/css" media="all" />  
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css" />
	<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.0.0/js/bootstrap.min.js"></script>
	<title>Twatter! - Where the words are blah and the hashtags don't matter</title>
</head>
<body>
	<%@ include file="navbar.jsp" %>
<c:choose>
	<c:when  test="${!empty error}">
		<div class="alert alert-danger alert-dismissable">
		  	<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
  			<p><c:out value="${error}"></c:out></p>
		</div>
	</c:when>
</c:choose>
<c:choose>
	<c:when  test="${!empty success}">
		<div class="alert alert-success alert-dismissable">
		  	<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
  			<p><c:out value="${success}"></c:out></p>
		</div>
	</c:when>
</c:choose>