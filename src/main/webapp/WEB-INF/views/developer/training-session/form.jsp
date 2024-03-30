<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="developer.trainingSession.form.label.code" path="code"/>
	<acme:input-moment code="developer.trainingSession.form.label.startPeriod" path="startPeriod"/>
	<acme:input-moment code="developer.trainingSession.form.label.endPeriod" path="endPeriod"/>
	<acme:input-textarea code="developer.trainingSession.form.label.location" path="location"/>
	<acme:input-textbox code="developer.trainingSession.form.label.instructor" path="instructor"/>
	<acme:input-email code="developer.trainingSession.form.label.contactEmail" path="contactEmail"/>
	<acme:input-url code="developer.trainingSession.form.label.link" path="link"/>
</acme:form>