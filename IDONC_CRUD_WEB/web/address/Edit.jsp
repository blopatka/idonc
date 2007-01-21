<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Edit Address</title>
    </head>
    <body>
        <f:view>
            <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            <h1>Edit address</h1>
            <h:form>
                <h:inputHidden value="#{address.address}" immediate="true"/>
                <h:panelGrid columns="2">
                    <h:outputText value="Id:"/>
                    <h:outputText value="#{address.address.id}" title="Id" />
                    <h:outputText value="Street:"/>
                    <h:inputText id="street" value="#{address.address.street}" title="Street" />
                    <h:outputText value="HouseNumber:"/>
                    <h:inputText id="houseNumber" value="#{address.address.houseNumber}" title="HouseNumber" />
                    <h:outputText value="City:"/>
                    <h:inputText id="city" value="#{address.address.city}" title="City" />
                    <h:outputText value="Zip:"/>
                    <h:inputText id="zip" value="#{address.address.zip}" title="Zip" />
                    <h:outputText value="CountryId:"/>
                    <h:selectOneMenu id="countryId" value="#{address.address.countryId}" title="CountryId">
                        <f:selectItems value="#{address.countryIds}"/>
                    </h:selectOneMenu>
                </h:panelGrid>
                <h:commandLink action="#{address.edit}" value="Save"/>
                <br>
                <h:commandLink action="address_list" value="Show All Address"/>
                <br>
                <a href="/IDONC_CRUD_WEB/index.jsp">Back to index</a>
            </h:form>
        </f:view>
    </body>
</html>
