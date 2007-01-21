<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Edit Team</title>
    </head>
    <body>
        <f:view>
            <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            <h1>Edit team</h1>
            <h:form>
                <h:inputHidden value="#{team.team}" immediate="true"/>
                <h:panelGrid columns="2">
                    <h:outputText value="Id:"/>
                    <h:outputText value="#{team.team.id}" title="Id" />
                    <h:outputText value="TeamName:"/>
                    <h:inputText id="teamName" value="#{team.team.teamName}" title="TeamName" />
                </h:panelGrid>
                <h:commandLink action="#{team.edit}" value="Save"/>
                <br>
                <h:commandLink action="team_list" value="Show All Team"/>
                <br>
                <a href="/IDONC_CRUD_WEB/index.jsp">Back to index</a>
            </h:form>
        </f:view>
    </body>
</html>
