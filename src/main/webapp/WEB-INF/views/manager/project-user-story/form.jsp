<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|delete')}">
			<acme:input-select code="manager.project-user-story.form.label.project-title" path="project" choices="${projectChoices}" readonly="true"/>
			<acme:input-select code="manager.project-user-story.form.label.user-story-title" path="userStory" choices="${userStoryChoices}" readonly="true"/>
			<jstl:if test="${projectDraftMode==true}">
				<acme:submit code="manager.project-user-story.form.button.delete" action="/manager/project-user-story/delete"/>
			</jstl:if>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:input-select code="manager.project-user-story.form.label.project-title" path="project" choices="${projectChoices}"/>
			<acme:input-select code="manager.project-user-story.form.label.user-story-title" path="userStory" choices="${userStoryChoices}"/>
			<acme:submit code="manager.project-user-story.form.button.create" action="/manager/project-user-story/create"/>
		</jstl:when>
	</jstl:choose>
</acme:form>