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
        <c:if test="${user not null and user.id == twatt.creator.id}">
            <form method="POST" action="twattDelete">
                <input type="hidden" name="userId" value="<c;out value='${user.id}/>'"/>
                <input type="hidden" name="twattId" value="<c:out value='${twatt.id}'/>"/>
                <input type="submit" class="pull-right btn btn-lg btn-primary"value="Eliminar"/>
            </form>
        </c:if>
    </td>
</tr>