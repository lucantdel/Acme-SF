<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-select code="manager.project-user-story.form.label.project-title" path="projectTitle" choices="${projectChoices}"/>
	<acme:input-select code="manager.project-user-story.form.label.user-story-title" path="userStoryTitle" choices="${userStoryChoices}"/>
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|delete')}">
			<acme:submit code="manager.project-user-story.form.button.delete" action="/manager/project-user-story/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="manager.project-user-story.form.button.create" action="/manager/project-user-story/create"/>
		</jstl:when>		
	</jstl:choose>	
</acme:form>