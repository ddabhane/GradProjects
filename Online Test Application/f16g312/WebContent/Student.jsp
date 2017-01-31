<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Student</title>
</head>
<body>
<f:view>
<h:form>	
<h:outputText value="Login Time:" style="color:green" />
<h:outputText value="#{loginBean.login_date}" style="color:green" />
<br/>
<h:outputText value="Machine IP Address:" style="color:green" />
<h:outputText value="#{loginBean.clientIp}" style="color:green" />	
<div id="title" align="center"><h1>Welcome to Student Page</h1>
<div id="Help" align="center"><h4><a href="View_studenthelp.jsp">Help</a></h4></div>
<table align="center" bordercolor="white" cellspacing = "1" cellpadding = "10">
<tr>
<td style="width: 122px; "><div id ="Quiz" align="center"><h4><a href=Take_assessment.jsp>Take quiz</a></h4></div></td>
<td><div id ="Quiz1" align="center"><h4><a href=View_assessment.jsp>View Scores</a></h4></div></td>
</tr>
</table>
</div>
<h:commandButton type="submit" value="Back to Instructor" action="#{loginBean.viewInstructor}" rendered ="#{loginBean.renderInstructor}" />
<h:commandButton type="submit" value="Logout" action="#{loginBean.logout}" />
</h:form>	
</f:view>
</body>
</html>