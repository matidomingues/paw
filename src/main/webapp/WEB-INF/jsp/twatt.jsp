    <%@ taglib prefix="twatt" uri="/WEB-INF/jsp/tld/TwattTagsDescriptor.tld"%>
    <c:if test="${not twatt.deleted}" >
    <tr>
        <td>
            <div>
            <img src="${pageContext.request.contextPath}/bin/image/<c:out value='${user_username}'/>">
            </div>
        </td>
        <td>
            <c:out value="${twatt.creator.username}"></c:out>
        </td>
        <td>
            <twatt:messag-converter twatt="${twatt}"/>
        </td>
        <td>
            <c:out value="${twatt.timestamp}"></c:out>
        </td>
        <td>
            <c:if test="${not empty user_id and user_id == twatt.creator.id}">
                <form method="POST" action="/bin/twatt/delete">
                    <input type="hidden" name="twattId" value="<c:out value='${twatt.id}'/>"/>
                    <input type="submit" class="pull-right btn btn-primary" value="Eliminar"/>
                </form>
            </c:if>
        </td>
    </tr>
    </c:if>