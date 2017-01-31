<?xml version="1.0" encoding="ISO-8859-1" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:t="http://myfaces.apache.org/tomahawk" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" xmlns:c="http://java.sun.com/jsp/jstl/core" version="2.0" >
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
	<link rel="stylesheet" type="text/css" href="style.css" media="all" />
</head>
<body>
<div align="center">
<h1>Score Card</h1>
<h6></h6>
</div>
<div align="center"><h1 style="color:black">GRAPHICAL ANALYSIS OF SCORES</h1></div>
<a href="Instructor.jsp">Main Page</a>
<div id="logout" align="center">
<br />
<f:view>
<h:form>
<div align="right">
<h:commandButton  type="submit" value="Logout" action="#{loginBean.logout}" /> 
</div>

</h:form>
</f:view>

</div>
	<f:view>
	<h:form>
		<div class="message">
<h:selectOneMenu value="#{chartAnalysisBean.courseName}" styleClass="margin2 drop-one">
				<f:selectItem itemValue="" itemLabel="Select the course"/>				
        		<f:selectItem itemValue="ADBMS" itemLabel="Advance Database Management Systems" />
        		<f:selectItem itemValue="Stats" itemLabel="Statistics" />
        		<f:selectItem itemValue="Accounting" itemLabel="Accounting"/>
		</h:selectOneMenu>
	
	<h:commandButton type="submit" 
				value="List Assessments" action="#{chartAnalysisBean.populateAssessments}" />
		
	<h:selectOneMenu valueChangeListener="#{chartAnalysisBean.selectAsssessment}" styleClass="margin2 select-one">
				<c:forEach var="assessment" items="#{chartAnalysisBean.assessmentsList}">
					<f:selectItem itemValue="#{assessment}" itemLabel="#{assessment}" />
				</c:forEach>
	</h:selectOneMenu>
	
	<h:commandButton type="submit" 
				value="Visualize!" action="#{chartAnalysisBean.createCharts}" />
				
	<br/>
	<h:graphicImage alt="Pie Chart" value="#{chartAnalysisBean.pieChartPath}" width="400" height="400"></h:graphicImage>
	<h:graphicImage alt="Bar Chart" value="#{chartAnalysisBean.barChartPath}" width="400" height="400"></h:graphicImage>
	<h:graphicImage alt="Histogram" value="#{chartAnalysisBean.histChartPath}" width="400" height="400"></h:graphicImage>
	</div>
	</h:form>
		
</f:view>
</body>
</html>
</jsp:root>