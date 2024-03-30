<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="developer.trainingModule.form.label.code" path="code"/>
	<acme:input-moment code="developer.trainingModule.form.label.creationMoment" path="creationMoment"/>
	<acme:input-moment code="developer.trainingModule.form.label.updateMoment" path="updateMoment"/>
	<acme:input-textarea code="developer.trainingModule.form.label.details" path="details"/>
	<acme:input-textbox code="developer.trainingModule.form.label.difficultyLevel" path="difficultyLevel"/>
	<acme:input-textbox code="developer.trainingModule.form.label.project" path="project"/>
	<acme:input-integer code="developer.trainingModule.form.label.totaltime" path="totalTime"/>
	<acme:input-url code="developer.trainingModule.form.label.link" path="link"/>
</acme:form>