<%@ include file="header.jsp" %>
	<div class="error"><c:out value="${error}" /></div>
	<h1><c:out value="{$user.username}"/></h1>
	<c:out value="{$user.name}"> </c:out><c:out value="{$user.surname}"></c:out>
	<h2><c:out value="{$user.description}"></c:out></h2>
<%@ include file="footer.jsp" %>