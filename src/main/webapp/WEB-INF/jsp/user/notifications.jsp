<%@ include file="../header.jsp" %>
<%@ taglib prefix="twatt" uri="/WEB-INF/jsp/tld/TwattTagsDescriptor.tld"%>
<div class="container">
    <div class="main-content-wrapper">
        <div class="main-content">
            <div class="panel panel-info">
                <div class="panel-heading">Notificaciones</div>
                <c:choose>
                    <c:when test="${empty local_user.notifications}">
                        <div class="panel-body">
                            <p>No tienes notificaciones</p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th>Foto</th>
                                <th>User</th>
                                <th>Mensaje</th>
                                <th>Fecha</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${local_user.notifications}" var="notification">
                                <twatt:notification notification="${notification}"/>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>
<%@ include file="../footer.jsp" %>