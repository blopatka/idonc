<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Edit Country</title>
    </head>
    <body>
        <f:view>
            <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            <h1>Edit country</h1>
            <h:form>
                <h:inputHidden value="#{country.country}" immediate="true"/>
                <h:panelGrid columns="2">
                    <h:outputText value="Id:"/>
                    <h:outputText value="#{country.country.id}" title="Id" />
                    <h:outputText value="CountryName:"/>
                    <h:inputText id="countryName" value="#{country.country.countryName}" title="CountryName" />
                </h:panelGrid>
                <h:commandLink action="#{country.edit}" value="Save"/>
                <br>
                <h:commandLink action="country_list" value="Show All Country"/>
                <br>
                <a href="/IDONC_CRUD_WEB/index.jsp">Back to index</a>
            </h:form>
        </f:view>
    </body>
</html>
