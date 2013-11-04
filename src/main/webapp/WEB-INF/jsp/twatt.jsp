    <%@ taglib prefix="twatt" uri="/WEB-INF/jsp/tld/TwattTagsDescriptor.tld"%>
    <c:if test="${not twatt.deleted and (not empty local_user and not twatt.creator.privacy)}" >
    <tr>
        <td>
            <div>
            <img src="${pageContext.request.contextPath}/bin/image/<c:out value='${twatt.creator.username}'/>">
            </div>
        </td>
        <td>
            <twatt:author-printer twatt="${twatt}"/>
        </td>
        <td>
            <twatt:message-printer twatt="${twatt}"/>
        </td>
        <td>
            <c:out value="${twatt.timestamp}"></c:out>
        </td>
        <td>
            <twatt:actions-printer twatt="${twatt}" user="${local_user}"/>
        </td>
    </tr>
    </c:if>