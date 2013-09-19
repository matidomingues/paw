
    <c:if test="${not twatt.deleted}" >
    <tr>
        <td>
            <c:out value="${twatt.creator.username}"></c:out>
        </td>
        <td>
            <c:url value="${twatt.message}"></c:url>
        </td>
        <td>
            <c:out value="${twatt.timestamp}"></c:out>
        </td>
        <td>
            <c:if test="${not empty user and user.id == twatt.creator.id}">
                <form method="POST" action="${pageContext.request.contextPath}/twattDelete">
                    <input type="hidden" name="twattId" value="<c:out value='${twatt.id}'/>"/>
                    <input type="submit" class="pull-right btn btn-primary" value="Eliminar"/>
                </form>
            </c:if>
        </td>
    </tr>
    </c:if>