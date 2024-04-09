<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="any.trainingModule.form.label.code" path="code"/>
	<acme:input-moment code="any.trainingModule.form.label.creationMoment" path="creationMoment"/>
	<acme:input-moment code="any.trainingModule.form.label.updateMoment" path="updateMoment"/>
	<acme:input-textarea code="any.trainingModule.form.label.details" path="details"/>
	<acme:input-textbox code="any.trainingModule.form.label.difficultyLevel" path="difficultyLevel"/>
	<acme:input-textbox code="any.trainingModule.form.label.project" path="project"/>
	<acme:input-integer code="any.trainingModule.form.label.totaltime" path="totalTime"/>
	<acme:input-url code="any.trainingModule.form.label.link" path="link"/>

</acme:form>