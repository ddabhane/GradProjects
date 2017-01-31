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
<title>Database Login</title>
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
<center>
<h4>DataBase Login</h4>
<a href="index.jsp">Main Page</a>
<hr />

</center>
</f:verbatim>

<h:form >

<br/>
<h:panelGrid columns="2">
<h:outputLabel value="User Name:" />
<h:inputText id="userName" value="#{loginBean.dbUserName}"
required="true" requiredMessage="Please enter your username"  />
<h:message for="userName" style="color:red" showSummary="true"
showDetail="false"></h:message>
<h:outputText value="" />
<h:outputLabel value="Password:" />
<h:inputSecret id="password" value="#{loginBean.dbPassword}"
required="true" requiredMessage="Please enter your password" />
<h:message for="password" style="color:red" showSummary="true"
showDetail="false"></h:message>
<h:outputText value="" />
<h:outputLabel value="Host:" />
<h:selectOneListbox value="#{loginBean.dbmsHost}" size="1">
<f:selectItem itemValue="131.193.209.54" itemLabel="server54"/>
<f:selectItem itemValue="131.193.209.57" itemLabel="server57"/>
<f:selectItem itemValue="131.193.211.9" itemLabel="sphere"/>
<f:selectItem itemValue="131.193.211.8" itemLabel="ids-server"/>
<f:selectItem itemValue="131.193.209.72" itemLabel="ids517-server"/>
<f:selectItem itemValue="localhost"/>
</h:selectOneListbox>

<h:outputLabel value="RDBMS:" />
<h:selectOneListbox value="#{loginBean.dbms}" size="1">
<f:selectItem itemValue="MySQL"/>
<f:selectItem itemValue="DB2"/>
<f:selectItem itemValue="Oracle"/>
</h:selectOneListbox>
<h:outputLabel value="Database Schema:" />
<h:inputText id="schema" value="#{loginBean.dbSchema}"
required="true" requiredMessage="Please enter database schema" />
<h:outputLabel value=" " />
<h:commandButton type="submit" value="Login"
action="#{loginBean.validateDBLogin}" />
</h:panelGrid>
<h:outputText value='DB Login error' style="color:red"
rendered='#{loginBean.authenticationFailed}'/>
</h:form>
</f:view>
</body>
</html>
</jsp:root>