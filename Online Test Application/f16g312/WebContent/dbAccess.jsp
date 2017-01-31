<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib prefix="t" uri="http://myfaces.apache.org/tomahawk"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns:ui="http://java.sun.com/jsf/facelets">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Database Access</title>
</head>
<body>
<f:view>
<h:form>
<h:outputText value="Login Time:" style="color:green" />
<h:outputText value="#{loginBean.login_date}" style="color:green" />
<br/>
<h:outputText value="Machine IP Address:" style="color:green" />
<h:outputText value="#{loginBean.clientIp}" style="color:green" />
 </h:form>
<f:verbatim>
<table>
<tr>
	<td><div align="center"><h3> Database for Online Test Application</h3></div></td>
	<td><div align="center"></div></td>

</tr>
</table>
<hr />
<br />
</f:verbatim>
<h:panelGrid columns="2">

		<h:form>
		
			<h:panelGrid columns="7">
			<h:commandButton type="submit" value="Table List" action="#{databaseAccessBean.listTables}" />
			<h:commandButton type="submit" value="Column List" action="#{databaseAccessBean.listColumns}" />
			<h:commandButton type="submit" value="Process SQL Query" action="#{databaseAccessBean.processSQLQuery}" />
			<h:commandButton type="submit" value="Create Tables" action="#{databaseAccessBean.createTables}" />
			<h:commandButton type="submit" value="Drop Tables" action="#{databaseAccessBean.dropTables}" />
			<h:commandButton type="submit" value="Logout" action="#{loginBean.logout}" />
						
		</h:panelGrid>
		
		<h:messages styleClass="errorMessage" style="color:red" />
		
		<h:panelGrid columns ="80">
		
		<h:selectManyListbox value="#{databaseAccessBean.selectedTables}" size="5" rendered="#{databaseAccessBean.renderTableList}">
		<f:selectItems value="#{databaseAccessBean.tables}" />
		</h:selectManyListbox>
		
    	
    	<h:selectManyListbox value="#{databaseAccessBean.columns}" size="5">
    	<f:selectItems value="#{databaseAccessBean.columns}" />
    	</h:selectManyListbox>	
    		
        <h:inputTextarea cols="70" rows="10" value="#{databaseAccessBean.query}"/>
		</h:panelGrid>
			
			
		
		<t:dataTable value="#{databaseAccessBean.displayRows}" 
		var="rowNumber"
		rendered="#{databaseAccessBean.renderQuery}"
		border="1" cellspacing="0" cellpadding="1"
		columnClasses="columnClass1 border"
		headerClass="headerClass"
		footerClass="footerClass"
		rowClasses="rowClass2"
		styleClass="dataTableEx"
		width="800">
			
	<h:column>
	<h:outputText value="#{rowNumber}" escape="false"></h:outputText>

	</h:column>
		
	
			
</t:dataTable>
			
			
		</h:form>

</h:panelGrid>
</f:view>

</body>
</html>