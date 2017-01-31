<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>
<f:view>
<f:verbatim>
<center>
<h2>Login Page</h2>
<hr />
<a href="index.jsp">Main Page</a>
</center>
</f:verbatim>
<h:form >
<br>
<center>
<h4>Enter your details to login</h4>
<h:panelGrid columns="2" >
<h:outputLabel value="User Name:" />
<h:inputText id="userName" value="#{loginBean.username}"
required="true" requiredMessage="Please enter your username"  />
<h:message for="userName" style="color:red" showSummary="true" showDetail="false"></h:message>
<h:outputText value="" />
<h:outputLabel value="Password:" />
<h:inputSecret id="password" value="#{loginBean.password}" 
required="true" requiredMessage="Please enter your password" />
<h:message for="password" style="color:red" showSummary="true" showDetail="false"></h:message>
<h:outputLabel value=" " />
<br><h:commandButton type="submit" value="Login" action="#{loginBean.Validate}" />
</h:panelGrid>
<h:outputText value='Login error' style="color:red" rendered='#{loginBean.authenticationFailed}'/>
</h:form>
</center>
</f:view>
</body>
</html>