<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="manager.user-story.list.label.title" path="title" width="25%"/>
	<acme:list-column code="manager.user-story.list.label.estimatedCost" path="estimatedCost" width="25%"/>
	<acme:list-column code="manager.user-story.list.label.priority" path="priority" width="25%"/>
	<acme:list-column code="manager.user-story.list.label.draftMode" path="draftMode" width="25%"/>
	<acme:list-payload path="payload"/>
</acme:list>

<jstl:if test="${_command == 'list-mine' }">
	<acme:button test="${showCreate}" code="manager.user-story.list.button.create" action="/manager/user-story/create"/>
</jstl:if>