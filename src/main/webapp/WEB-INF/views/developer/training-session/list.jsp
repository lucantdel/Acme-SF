<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="developer.trainingSession.list.label.code" path="code" width="15%"/>
	<acme:list-column code="developer.trainingSession.list.label.startPeriod" path="startPeriod" width="35%"/>
	<acme:list-column code="developer.trainingSession.list.label.endPeriod" path="endPeriod" width="35%"/>
	<acme:list-column code="developer.trainingSession.list.label.draftMode" path="draftMode" width="15%"/>
	<acme:list-payload path="payload"/>
</acme:list>
	
	<acme:button  code="developer.trainingSession.list.button.create" action="/developer/training-session/create?trainingModuleId=${trainingModuleId}"/>

