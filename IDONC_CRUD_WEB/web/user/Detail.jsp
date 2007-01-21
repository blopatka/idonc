<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Detail of User</title>
    </head>
    <body>
        <f:view>
            <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            <h1>Detail of user</h1>
            <h:form>
                <h:panelGrid columns="2">
                    <h:outputText value="Id:"/>
                    <h:outputText value="#{user.user.id}" title="Id" />
                    <h:outputText value="Username:"/>
                    <h:outputText value="#{user.user.username}" title="Username" />
                    <h:outputText value="Email:"/>
                    <h:outputText value="#{user.user.email}" title="Email" />
                    <h:outputText value="FirstName:"/>
                    <h:outputText value="#{user.user.firstName}" title="FirstName" />
                    <h:outputText value="SecondName:"/>
                    <h:outputText value="#{user.user.secondName}" title="SecondName" />
                    <h:outputText value="LastName:"/>
                    <h:outputText value="#{user.user.lastName}" title="LastName" />
                    <h:outputText value="WebPage:"/>
                    <h:outputText value="#{user.user.webPage}" title="WebPage" />
                    <h:outputText value="AddressId:"/>
                    <h:outputText value="#{user.user.addressId}" title="AddressId" />
                    <h:outputText value="TeamId:"/>
                    <h:outputText value="#{user.user.teamId}" title="TeamId" />
                </h:panelGrid>
                <h:commandLink action="user_edit" value="Edit" />
                <br>
                <h:commandLink action="user_list" value="Show All User"/>
                <br>
                <a href="/IDONC_CRUD_WEB/index.jsp">Back to index</a>
            </h:form>
        </f:view>
    </body>
</html>
