<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page session="false" %>

<%--${company.title}--%>




    <ul>
        <tr>
        <td>${company.id}</td>

        <span style='padding-left:20px;'> </span>

        <td>${company.name}</td>

        <span style='padding-left:20px;'> </span>

        <td>${company.aproximatedEarnings}</td>

        <span style='padding-left:20px;'> </span>

        <td><a href="<c:url value='/edit/${company.id}'/>">Edit</a></td>

        <span style='padding-left:20px;'> </span>

        <td><a href="<c:url value='/remove/${company.id}'/>">Delete</a></td>



    <c:forEach var="company" items="${company.childCompanies}">
        <c:set var="company" value="${company}" scope="request"/>
        <jsp:include page="node.jsp"/>
    </c:forEach>
        </tr>
    </ul>







