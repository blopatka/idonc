<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>New Address</title>
    </head>
    <body>
        <f:view>
            <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            <h1>New address</h1>
            <h:form>
                <h:panelGrid columns="2">
                    <h:outputText value="Id:"/>
                    <h:inputText id="id" value="#{address.address.id}" title="Id" />
                    <h:outputText value="Street:"/>
                    <h:inputText id="street" value="#{address.address.street}" title="Street" />
                    <h:outputText value="HouseNumber:"/>
                    <h:inputText id="houseNumber" value="#{address.address.houseNumber}" title="HouseNumber" />
                    <h:outputText value="City:"/>
                    <h:inputText id="city" value="#{address.address.city}" title="City" />
                    <h:outputText value="Zip:"/>
                    <h:inputText id="zip" value="#{address.address.zip}" title="Zip" />
                    <h:outputText value="CountryId:" rendered="#{address.address.countryId == null}"/>
                    <h:selectOneMenu id="countryId" value="#{address.address.countryId}" title="CountryId" rendered="#{address.address.countryId == null}">
                        <f:selectItems value="#{address.countryIds}"/>
                    </h:selectOneMenu>
                </h:panelGrid>
                <h:commandLink action="#{address.create}" value="Create"/>
                <br>
                <h:commandLink action="address_list" value="Show All Address"/>
                <br>
                <a href="/IDONC_CRUD_WEB/index.jsp">Back to index</a>
            </h:form>
        </f:view>
    </body>
</html>
