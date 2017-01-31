<?xml version="1.0" encoding="ISO-8859-1" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" version="2.0">
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
<title>Instructor Home</title>
</head>
<body>
<f:view>
<h:form>	
<h:outputText value="Login Time:" style="color:green" />
<h:outputText value="#{loginBean.login_date}" style="color:green" />
<br/>
<h:outputText value="Machine IP Address:" style="color:green" />
<h:outputText value="#{loginBean.clientIp}" style="color:green" />		
<div id="title" align="center"><h1>Welcome to Instructor page</h1>
<div id="Help" align="center"><h4><a href="View_instructorhelp.jsp">Help</a></h4></div>
<table align="center" bordercolor="white" cellspacing = "1" cellpadding = "10">
<tr><td><div id="Roster" align="center" ><h4><a href="Roster_upload.jsp">Roster</a></h4></div></td>
<td><div id="Course" align="center"><h4><a href="Registered.jsp">Courses</a></h4></div></td>
<td><div id="Assessment" align="center"><h4><a href="Assessment_upload.jsp">Assessment</a></h4></div></td>
<td><div id="studentresult" align="center"><h4><a href="View_student_result.jsp">View Student Results</a></h4></div></td>
<td><div id="student" align="center"><h4><a href="Student.jsp">View as Student</a></h4></div></td>
<td><div id="viewCharts" align="center"><h4><a href="GraphAnalysis.jsp">Graph Analysis</a></h4></div></td>

</tr>
</table></div>

<br/>
<h:commandButton type="submit" value="Logout" action="#{loginBean.logout}" />
</h:form>
</f:view>
</body>
</html>
</jsp:root>