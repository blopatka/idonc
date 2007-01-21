<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>List Address</title>
    </head>
    <body>
        <f:view>
            <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            <h1>Listing Addresss</h1>
            <h:form>
                <h:commandLink action="#{address.createSetup}" value="New Address"/>
                <br>
                <a href="/IDONC_CRUD_WEB/index.jsp">Back to index</a>
                <br>
                <h:outputText value="Item #{address.firstItem + 1}..#{address.lastItem} of #{address.itemCount}"/>&nbsp;
                <h:commandLink action="#{address.prev}" value="Previous #{address.batchSize}" rendered="#{address.firstItem >= address.batchSize}"/>&nbsp;
                <h:commandLink action="#{address.next}" value="Next #{address.batchSize}" rendered="#{address.lastItem + address.batchSize <= address.itemCount}"/>&nbsp;
                <h:commandLink action="#{address.next}" value="Remaining #{address.itemCount - address.lastItem}"
                               rendered="#{address.lastItem < address.itemCount && address.lastItem + address.batchSize > address.itemCount}"/>
                <h:dataTable value='#{address.addresss}' var='item' border="1" cellpadding="2" cellspacing="0">
                    <h:column>
                        <f:facet name="header">
                            <h:outputText value="Id"/>
                        </f:facet>
                        <h:commandLink action="#{address.detailSetup}" value="#{item.id}"/>
                    </h:column>
                    <h:column>
                        <f:facet name="header">
                            <h:outputText value="Street"/>
                        </f:facet>
                        <h:outputText value="#{item.street}"/>
                    </h:column>
                    <h:column>
                        <f:facet name="header">
                            <h:outputText value="HouseNumber"/>
                        </f:facet>
                        <h:outputText value="#{item.houseNumber}"/>
                    </h:column>
                    <h:column>
                        <f:facet name="header">
                            <h:outputText value="City"/>
                        </f:facet>
                        <h:outputText value="#{item.city}"/>
                    </h:column>
                    <h:column>
                        <f:facet name="header">
                            <h:outputText value="Zip"/>
                        </f:facet>
                        <h:outputText value="#{item.zip}"/>
                    </h:column>
                    <h:column>
                        <f:facet name="header">
                            <h:outputText value="CountryId"/>
                        </f:facet>
                        <h:outputText value="#{item.countryId}"/>
                    </h:column>
                    <h:column>
                        <h:commandLink value="Destroy" action="#{address.destroy}">
                            <f:param name="id" value="#{item.id}"/>
                        </h:commandLink>
                        <h:outputText value=" "/>
                        <h:commandLink value="Edit" action="#{address.editSetup}">
                            <f:param name="id" value="#{item.id}"/>
                        </h:commandLink>
                    </h:column>
                </h:dataTable>
            </h:form>
        </f:view>
    </body>
</html>
