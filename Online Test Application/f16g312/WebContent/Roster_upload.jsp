<?xml version="1.0" encoding="ISO-8859-1" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:t="http://myfaces.apache.org/tomahawk" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" version="2.0">
    <jsp:directive.page language="java"
        contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" />
    <jsp:text>
        <![CDATA[ <?xml version="1.0" encoding="ISO-8859-1" ?> ]]>
    </jsp:text>
    <jsp:text>
        <![CDATA[ <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> ]]>
    </jsp:text>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Upload Roster</title>
</head>


<body>
<f:view>
<h:form>
<h:outputText value="Login Time:" style="color:green" />
<h:outputText value="#{loginBean.login_date}" style="color:green" />
<br/>
<h:outputText value="Machine IP Address:" style="color:green" />
<h:outputText value="#{loginBean.clientIp}" style="color:green" />			
<div id="title" align="center"><h1>Upload Student Roster</h1></div>
<div id="Back" align="center"><h4><a href="Instructor.jsp">Home</a></h4></div>
</h:form>
<h:form enctype="multipart/form-data">
		<h:messages styleClass="errorMessage" style="color:red" />

<h:panelGrid columns="2" cellspacing = "2" cellpadding = "10" >
<h:commandButton type="submit" action= "#{actionBeanFile.displayCourses}" value="Select Course" />
  
    <h:selectOneMenu value="#{actionBeanFile.selected_course}">
    <f:selectItems value="#{actionBeanFile.courselist}" />
	</h:selectOneMenu>
 </h:panelGrid> 
<br/>
<br/>

<h:panelGrid columns="2">
<h:outputLabel value="Select Delimiter" />
   
    <h:selectOneMenu value="#{actionBeanFile.delimiterType}">
    <f:selectItem itemValue="Tab" itemLabel="Tab" />
     <f:selectItem itemValue="Comma" itemLabel="Comma" />
	</h:selectOneMenu>
	
</h:panelGrid> 

<h:panelGrid columns="2">
<h:outputLabel value="Has Header?" />
   
    <h:selectOneMenu value="#{actionBeanFile.hasHeader}">
    <f:selectItem itemValue="Yes" itemLabel="Yes" />
     <f:selectItem itemValue="No" itemLabel="No" />
      
	</h:selectOneMenu>
</h:panelGrid>

<h:panelGrid columns="2">
<h:outputLabel value="Select Roster to upload:" style="color:blue" />
<t:inputFileUpload id="fileUpload" label="File to upload"
storage="default" value="#{actionBeanFile.uploadedFile}"
size="60"/>
</h:panelGrid>
<br/>

<h:panelGrid columns="2">
<h:outputLabel value="File label:" style="color:blue"  />
<h:inputText id="fileLabel"
value= "#{actionBeanFile.fileLabel}"
size="60" />

</h:panelGrid>
<br/>
<h:outputLabel value=" "/>
<h:commandButton id="upload"
action= "#{actionBeanFile.processUploadScoreRoster}"
value="Submit"/>
<br/>
<br/>

<h:panelGrid columns="2" cellspacing = "2" cellpadding = "10">
<h:commandButton type="submit" action= "#{actionBeanFile.processRosterDisplay}" value="Print Roster" />

<h:commandButton type="submit" action= "#{actionBeanFile.resetRoster}" value="Reset" />
</h:panelGrid>

</h:form>
<br/>
<br/>

<t:dataTable id = "dataTable" value="#{actionBeanFile.scoreRosterList}" var="rowNumber" rendered="#{actionBeanFile.renderList}"
		border="1" cellspacing="0" cellpadding="1"
		columnClasses="columnClass1 border"
		headerClass="headerClass"
		footerClass="footerClass"
		rowClasses="rowClass2"
		styleClass="dataTableEx"
		width="800">

<h:column>
	<f:facet name="header">
	<h:outputText>Last Name</h:outputText>
	</f:facet>
	<h:outputText value="#{rowNumber.lastName}"/>
</h:column>

<h:column>
<f:facet name="header">
<h:outputText>First Name</h:outputText>
</f:facet>
<h:outputText value="#{rowNumber.firstName}"/>
</h:column>

<h:column>
<f:facet name="header">
<h:outputText>Username</h:outputText>
</f:facet>
<h:outputText value="#{rowNumber.userName}"/>
</h:column>

<h:column>
<f:facet name="header">
<h:outputText>Student ID</h:outputText>
</f:facet>
<h:outputText value="#{rowNumber.studentID}"/>
</h:column>

<h:column>
	<f:facet name="header">
	<h:outputText>Last_Access</h:outputText>
	</f:facet>
	<h:outputText value="#{rowNumber.lastAccess}"/>
</h:column>

<h:column>
	<f:facet name="header">
	<h:outputText>Availability</h:outputText>
	</f:facet>
	<h:outputText value="#{rowNumber.availability}"/>
</h:column>

<h:column>
<f:facet name="header">
<h:outputText>Total</h:outputText>
</f:facet>
<h:outputText value="#{rowNumber.total}"/>
</h:column>

<h:column>
<f:facet name="header">
<h:outputText>Exam01</h:outputText>
</f:facet>
<h:outputText value="#{rowNumber.exam01}"/>
</h:column>

<h:column>
<f:facet name="header">
<h:outputText>Exam02</h:outputText>
</f:facet>
<h:outputText value="#{rowNumber.exam02}"/>
</h:column>
<h:column>
	<f:facet name="header">
	<h:outputText>Exam03</h:outputText>
	</f:facet>
	<h:outputText value="#{rowNumber.exam03}"/>
</h:column>

<h:column>
	<f:facet name="header">
	<h:outputText>Project</h:outputText>
	</f:facet>
	<h:outputText value="#{rowNumber.project}"/>
</h:column>

<h:column>
<f:facet name="header">
<h:outputText>Course</h:outputText>
</f:facet>
<h:outputText value="#{rowNumber.course}"/>
</h:column>

</t:dataTable>
</f:view>
</body>
</html>
</jsp:root>