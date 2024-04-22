<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="manager.project-user-story.form.label.project-title" path="projectTitle"/>
	<acme:input-textbox code="manager.project-user-story.form.label.user-story-title" path="userStoryTitle"/>
</acme:form>