<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>List Country</title>
    </head>
    <body>
        <f:view>
            <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            <h1>Listing Countrys</h1>
            <h:form>
                <h:commandLink action="#{country.createSetup}" value="New Country"/>
                <br>
                <a href="/IDONC_CRUD_WEB/index.jsp">Back to index</a>
                <br>
                <h:outputText value="Item #{country.firstItem + 1}..#{country.lastItem} of #{country.itemCount}"/>&nbsp;
                <h:commandLink action="#{country.prev}" value="Previous #{country.batchSize}" rendered="#{country.firstItem >= country.batchSize}"/>&nbsp;
                <h:commandLink action="#{country.next}" value="Next #{country.batchSize}" rendered="#{country.lastItem + country.batchSize <= country.itemCount}"/>&nbsp;
                <h:commandLink action="#{country.next}" value="Remaining #{country.itemCount - country.lastItem}"
                               rendered="#{country.lastItem < country.itemCount && country.lastItem + country.batchSize > country.itemCount}"/>
                <h:dataTable value='#{country.countrys}' var='item' border="1" cellpadding="2" cellspacing="0">
                    <h:column>
                        <f:facet name="header">
                            <h:outputText value="Id"/>
                        </f:facet>
                        <h:commandLink action="#{country.detailSetup}" value="#{item.id}"/>
                    </h:column>
                    <h:column>
                        <f:facet name="header">
                            <h:outputText value="CountryName"/>
                        </f:facet>
                        <h:outputText value="#{item.countryName}"/>
                    </h:column>
                    <h:column>
                        <h:commandLink value="Destroy" action="#{country.destroy}">
                            <f:param name="id" value="#{item.id}"/>
                        </h:commandLink>
                        <h:outputText value=" "/>
                        <h:commandLink value="Edit" action="#{country.editSetup}">
                            <f:param name="id" value="#{item.id}"/>
                        </h:commandLink>
                    </h:column>
                </h:dataTable>
            </h:form>
        </f:view>
    </body>
</html>
