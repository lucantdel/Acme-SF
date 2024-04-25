<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="manager.user-story.form.label.title" path="title"/>
	<acme:input-textarea code="manager.user-story.form.label.description" path="description"/>
	<acme:input-integer code="manager.user-story.form.label.estimatedCost" path="estimatedCost"/>
	<acme:input-textarea code="manager.user-story.form.label.acceptanceCriteria" path="acceptanceCriteria"/>
	<acme:input-select code="manager.user-story.form.label.priority" path="priority" choices="${priorities}"/>
	<acme:input-url code="manager.user-story.form.label.link" path="link"/>
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true && isMine == true }">
			<acme:submit code="manager.user-story.form.button.update" action="/manager/user-story/update"/>
			<acme:submit code="manager.user-story.form.button.delete" action="/manager/user-story/delete"/>
			<acme:submit code="manager.user-story.form.button.publish" action="/manager/user-story/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="manager.user-story.form.button.create" action="/manager/user-story/create"/>
		</jstl:when>		
	</jstl:choose>	
</acme:form>
