<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page session="false" %>


    <html>
        <head>
            <title>Companies</title>

            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-theme.min.css" type="text/css" />
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />

            <%--Ajax auto update--%>
            <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
            <script type="text/javascript">
                window.setInterval(function() {
                    $('#updating').load('/companies.html #updating');
                }, 1000 * 5);   // 5 seconds
            </script>

        </head>


        <body>
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-2">
                    </div>
                    <div class="col-md-8">

                        <div id = updating>

                            <c:if test="${!empty message}">
                                <div class="jumbotron">
                                    <h1>${message}</h1>
                                    <p>
                                        You see this page because some error happened while processing Your request
                                        You can get more info by the following link
                                    </p>
                                    <p>
                                        <a class="btn btn-primary btn-large" href="#">Learn more</a>
                                    </p>
                                </div>
                            </c:if>

                            <c:if test="${!empty companies}">
                                <h1>Companies List:</h1>

                                <br>

                                    <c:forEach items="${companies}" var="company">
                                    <ul>
                                        <li>
                                        <%--<tr>--%>
                                                                                                                            <%-- TODO: fix these spaces --%>
                                            <td>${company.id}</td>                                                          <span style='padding-left:10px;'> </span>
                                            <td>${company.name}</td>                                                        <span style='padding-left:10px;'> </span>
                                            <td>${company.aproximatedEarnings}</td>                                         <span style='padding-left:10px;'> </span>
                                            <td>${company.childEarnings}</td>                                               <span style='padding-left:10px;'> </span>
                                            <td><a href="<c:url value='/edit/${company.id}'/>">Edit</a></td>                <span style='padding-left:10px;'> </span>
                                            <td><a href="<c:url value='/remove/${company.id}'/>">Delete</a></td>
                                        <%--</tr>--%>

                                        <c:forEach var="company" items="${company.childCompanies}">
                                            <c:set var="company" value="${company}" scope="request"/>
                                            <jsp:include page="../pages/node.jsp"/>
                                        </c:forEach>

                                        </li>
                                    </ul>
                                    </c:forEach>
                                   </tbody>



                                </table>
                            </c:if>
                        </div>
                    </div>
                    <div class="col-md-2">
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-md-12">

                    <div class="row">
                        <div class="col-md-2">
                        </div>

                        <div class="col-md-4">

                            <c:url var="addAction" value="/companies/add"/>
                            <form:form cssClass="form-horizontal" role="form" action="${addAction}" commandName="companyToEdit">

                                <c:if test="${empty companyToEdit.name}">
                                    <h4>Add company:</h4>
                                </c:if>

                                <c:if test="${!empty companyToEdit.name}">
                                    <h4>Edit company:</h4>
                                    <div class="form-group">
                                        <form:label cssClass="col-sm-2 control-label" for="inputEmail3" path="id">
                                            ID
                                        </form:label>
                                        <form:input path="id" readonly="true" size="8" disabled="true"/>
                                        <form:hidden path="id"/>
                                    </div>
                                </c:if>

                                <div class="form-group">
                                    <form:label cssClass="col-sm-2 control-label" for="text" path="name">
                                        Name
                                    </form:label>
                                    <form:input path="name"/>
                                </div>

                                <div class="form-group">
                                    <form:label cssClass="col-sm-2 control-label" for="text" path="aproximatedEarnings">
                                        Earnings
                                    </form:label>
                                    <form:input path="aproximatedEarnings"/>
                                </div>

                                <div class="form-group">
                                    <form:label cssClass="col-sm-2 control-label" for="text" path="parent.id">
                                        Parent Id
                                    </form:label>
                                    <form:input path="parent.id"/>
                                </div>

                                <c:if test="${!empty companyToEdit.name}">
                                    <div class="form-group">
                                        <div class="col-sm-offset-2 col-sm-10">
                                            <button type="submit" class="btn btn-default" value="">
                                                Edit Company
                                            </button>
                                        </div>
                                    </div>
                                </c:if>

                                <c:if test="${empty companyToEdit.name}">
                                    <div class="form-group">
                                        <div class="col-sm-offset-2 col-sm-10">
                                            <button type="submit" class="btn btn-default" value="">
                                                Add Company
                                            </button>
                                        </div>
                                    </div>
                                </c:if>

                            </form:form>
                        </div>
                        <div class="col-md-6">
                        </div>
                    </div>
                </div>
            </div>

        <script src="../../js/jquery.min.js"></script>
        <script src="../../bootstrap.min.js"></script>
        <script src="../../scripts.js"></script>

        </body>
    </html>
