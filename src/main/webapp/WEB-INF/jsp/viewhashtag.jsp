<%@ include file="header.jsp" %>
<div class="error"><c:out value="${error}" /></div>
<div class="container">
    <div class="main-content-wrapper">
        <div class="main-content">
            <div class="panel panel-info">
                <div class="panel-heading">Primer Twatt</div>

                    <c:choose>
                       <c:when test="${not empty hashtag and not hashtag.firstTweet.deleted}">
                        <table class="table table-striped">
                                <thead>
                                <tr>
                                    <th>User</th>
                                    <th>Tweet</th>
                                    <th>Fecha</th>
                                </tr>
                                </thead>
                            <tbody>
                            <c:set var="twatt" value="${hashtag.firstTweet}"/>
                                <%@include file="twatt.jsp" %>
                            </tbody>
                        </table>
                        </c:when>
                        <c:otherwise>
                            <div class="panel-body">
                                <p> El Twatt original ha sido borrado! </p>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="panel-heading">Twatts</div>
                <c:choose>
                    <c:when  test="${empty twatts}">
                        <div class="panel-body">
                            <p> No hay Twatts para mostrar </p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th>User</th>
                                <th>Tweet</th>
                                <th>Fecha</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${twatts}" var="twatt">
                                <%@include file="twatt.jsp" %>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
    <div class="sidebar panel panel-info">
        <div class="panel-heading">Informacion del Usuario</div>
        <table class="table">
            <tbody>
            <tr>
                <th>Hashtag</th>
                <td><c:out value="${hashtag.tagName}"></c:out></td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="footer.jsp" %>