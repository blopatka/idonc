<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>List Team</title>
    </head>
    <body>
        <f:view>
            <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            <h1>Listing Teams</h1>
            <h:form>
                <h:commandLink action="#{team.createSetup}" value="New Team"/>
                <br>
                <a href="/IDONC_CRUD_WEB/index.jsp">Back to index</a>
                <br>
                <h:outputText value="Item #{team.firstItem + 1}..#{team.lastItem} of #{team.itemCount}"/>&nbsp;
                <h:commandLink action="#{team.prev}" value="Previous #{team.batchSize}" rendered="#{team.firstItem >= team.batchSize}"/>&nbsp;
                <h:commandLink action="#{team.next}" value="Next #{team.batchSize}" rendered="#{team.lastItem + team.batchSize <= team.itemCount}"/>&nbsp;
                <h:commandLink action="#{team.next}" value="Remaining #{team.itemCount - team.lastItem}"
                               rendered="#{team.lastItem < team.itemCount && team.lastItem + team.batchSize > team.itemCount}"/>
                <h:dataTable value='#{team.teams}' var='item' border="1" cellpadding="2" cellspacing="0">
                    <h:column>
                        <f:facet name="header">
                            <h:outputText value="Id"/>
                        </f:facet>
                        <h:commandLink action="#{team.detailSetup}" value="#{item.id}"/>
                    </h:column>
                    <h:column>
                        <f:facet name="header">
                            <h:outputText value="TeamName"/>
                        </f:facet>
                        <h:outputText value="#{item.teamName}"/>
                    </h:column>
                    <h:column>
                        <h:commandLink value="Destroy" action="#{team.destroy}">
                            <f:param name="id" value="#{item.id}"/>
                        </h:commandLink>
                        <h:outputText value=" "/>
                        <h:commandLink value="Edit" action="#{team.editSetup}">
                            <f:param name="id" value="#{item.id}"/>
                        </h:commandLink>
                    </h:column>
                </h:dataTable>
            </h:form>
        </f:view>
    </body>
</html>
