<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="manager.user-story.list.label.title" path="title" width="40%"/>
	<acme:list-column code="manager.user-story.list.label.estimatedCost" path="estimatedCost" width="30%"/>
	<acme:list-column code="manager.user-story.list.label.priority" path="priority" width="30%"/>
</acme:list>