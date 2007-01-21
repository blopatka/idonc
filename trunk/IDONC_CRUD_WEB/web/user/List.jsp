<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>List User</title>
    </head>
    <body>
        <f:view>
            <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            <h1>Listing Users</h1>
            <h:form>
                <h:commandLink action="#{user.createSetup}" value="New User"/>
                <br>
                <a href="/IDONC_CRUD_WEB/index.jsp">Back to index</a>
                <br>
                <h:outputText value="Item #{user.firstItem + 1}..#{user.lastItem} of #{user.itemCount}"/>&nbsp;
                <h:commandLink action="#{user.prev}" value="Previous #{user.batchSize}" rendered="#{user.firstItem >= user.batchSize}"/>&nbsp;
                <h:commandLink action="#{user.next}" value="Next #{user.batchSize}" rendered="#{user.lastItem + user.batchSize <= user.itemCount}"/>&nbsp;
                <h:commandLink action="#{user.next}" value="Remaining #{user.itemCount - user.lastItem}"
                               rendered="#{user.lastItem < user.itemCount && user.lastItem + user.batchSize > user.itemCount}"/>
                <h:dataTable value='#{user.users}' var='item' border="1" cellpadding="2" cellspacing="0">
                    <h:column>
                        <f:facet name="header">
                            <h:outputText value="Id"/>
                        </f:facet>
                        <h:commandLink action="#{user.detailSetup}" value="#{item.id}"/>
                    </h:column>
                    <h:column>
                        <f:facet name="header">
                            <h:outputText value="Username"/>
                        </f:facet>
                        <h:outputText value="#{item.username}"/>
                    </h:column>
                    <h:column>
                        <f:facet name="header">
                            <h:outputText value="Email"/>
                        </f:facet>
                        <h:outputText value="#{item.email}"/>
                    </h:column>
                    <h:column>
                        <f:facet name="header">
                            <h:outputText value="FirstName"/>
                        </f:facet>
                        <h:outputText value="#{item.firstName}"/>
                    </h:column>
                    <h:column>
                        <f:facet name="header">
                            <h:outputText value="SecondName"/>
                        </f:facet>
                        <h:outputText value="#{item.secondName}"/>
                    </h:column>
                    <h:column>
                        <f:facet name="header">
                            <h:outputText value="LastName"/>
                        </f:facet>
                        <h:outputText value="#{item.lastName}"/>
                    </h:column>
                    <h:column>
                        <f:facet name="header">
                            <h:outputText value="WebPage"/>
                        </f:facet>
                        <h:outputText value="#{item.webPage}"/>
                    </h:column>
                    <h:column>
                        <f:facet name="header">
                            <h:outputText value="AddressId"/>
                        </f:facet>
                        <h:outputText value="#{item.addressId}"/>
                    </h:column>
                    <h:column>
                        <f:facet name="header">
                            <h:outputText value="TeamId"/>
                        </f:facet>
                        <h:outputText value="#{item.teamId}"/>
                    </h:column>
                    <h:column>
                        <h:commandLink value="Destroy" action="#{user.destroy}">
                            <f:param name="id" value="#{item.id}"/>
                        </h:commandLink>
                        <h:outputText value=" "/>
                        <h:commandLink value="Edit" action="#{user.editSetup}">
                            <f:param name="id" value="#{item.id}"/>
                        </h:commandLink>
                    </h:column>
                </h:dataTable>
            </h:form>
        </f:view>
    </body>
</html>
