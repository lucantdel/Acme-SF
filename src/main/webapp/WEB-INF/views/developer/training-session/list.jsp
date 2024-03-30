<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="developer.trainingSession.list.label.code" path="code" width="20%"/>
	<acme:list-column code="developer.trainingSession.list.label.startPeriod" path="startPeriod" width="40%"/>
	<acme:list-column code="developer.trainingSession.list.label.endPeriod" path="endPeriod" width="40%"/>
</acme:list>