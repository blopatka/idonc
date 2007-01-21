<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Edit User</title>
    </head>
    <body>
        <f:view>
            <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            <h1>Edit user</h1>
            <h:form>
                <h:inputHidden value="#{user.user}" immediate="true"/>
                <h:panelGrid columns="2">
                    <h:outputText value="Id:"/>
                    <h:outputText value="#{user.user.id}" title="Id" />
                    <h:outputText value="Username:"/>
                    <h:inputText id="username" value="#{user.user.username}" title="Username" />
                    <h:outputText value="Email:"/>
                    <h:inputText id="email" value="#{user.user.email}" title="Email" />
                    <h:outputText value="FirstName:"/>
                    <h:inputText id="firstName" value="#{user.user.firstName}" title="FirstName" />
                    <h:outputText value="SecondName:"/>
                    <h:inputText id="secondName" value="#{user.user.secondName}" title="SecondName" />
                    <h:outputText value="LastName:"/>
                    <h:inputText id="lastName" value="#{user.user.lastName}" title="LastName" />
                    <h:outputText value="WebPage:"/>
                    <h:inputText id="webPage" value="#{user.user.webPage}" title="WebPage" />
                    <h:outputText value="AddressId:"/>
                    <h:selectOneMenu id="addressId" value="#{user.user.addressId}" title="AddressId">
                        <f:selectItems value="#{user.addressIds}"/>
                    </h:selectOneMenu>
                    <h:outputText value="TeamId:"/>
                    <h:selectOneMenu id="teamId" value="#{user.user.teamId}" title="TeamId">
                        <f:selectItems value="#{user.teamIds}"/>
                    </h:selectOneMenu>
                </h:panelGrid>
                <h:commandLink action="#{user.edit}" value="Save"/>
                <br>
                <h:commandLink action="user_list" value="Show All User"/>
                <br>
                <a href="/IDONC_CRUD_WEB/index.jsp">Back to index</a>
            </h:form>
        </f:view>
    </body>
</html>
