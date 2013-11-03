<%@include file="../header.jsp"%>
<div class="container">
    <div class="main-content-wrapper">
        <div class="main-content">
            <div class="panel panel-info">
                <div class="panel-heading">Twatts Favoritos</div>
                <c:choose>
                    <c:when test="${empty favourites}">
                        <div class="panel-body">
                            <p>No tienes ning&uacute;n favorito</p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th>Foto</th>
                                <th>User</th>
                                <th>Tweet</th>
                                <th>Fecha</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${favourites}" var="twatt">
                                <%@include file="../twatt.jsp"%>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>
<%@include file="../footer.jsp"%>