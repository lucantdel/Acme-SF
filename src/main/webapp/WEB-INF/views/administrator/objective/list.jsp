<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="administrator.objective.list.label.title" path="title" width="25%"/>
	<acme:list-column code="administrator.objective.list.label.startMoment" path="startMoment" width="25%"/>
	<acme:list-column code="administrator.objective.list.label.endMoment" path="endMoment" width="25%"/>
	<acme:list-column code="administrator.objective.list.label.priority" path="priority" width="25%"/>
	<acme:list-payload path="payload"/>
</acme:list>

<acme:button test="${showCreate}" code="administrator.objective.list.button.create" action="/administrator/objective/create"/>