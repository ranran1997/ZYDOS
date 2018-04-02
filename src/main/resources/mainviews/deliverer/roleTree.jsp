<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>

<c:forEach var="role" items="${roles}">
    <option value="${role.id}">
        <c:forEach begin="1" end="${level}" step="1">
            &nbsp;&nbsp;
        </c:forEach>
        ${role.name}
    </option>
    <c:if test="${not empty role.childRole}">
        <c:set var="roles" value="${role.childRole}" scope="request"/>
        <c:set var="level" value="${level+1}" scope="request"/>
        <jsp:include page="roleTree.jsp"/>
    </c:if>
</c:forEach>
<c:set var="level" value="${level-1}" scope="request"/>