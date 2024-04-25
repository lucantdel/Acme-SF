<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="manager.project-user-story.list.label.project-title" path="project" width="50%"/>
	<acme:list-column code="manager.project-user-story.list.label.user-story-title" path="userStory" width="50%"/>
</acme:list>

<acme:button test="${showCreate}" code="manager.project-user-story.list.button.create" action="/manager/project-user-story/create"/>