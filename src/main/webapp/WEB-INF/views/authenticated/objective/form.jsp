<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="any.project.form.label.title" path="title"/>
	<acme:input-textarea code="any.project.form.label.description" path="description"/>
	<acme:input-select code="any.project.form.label.priority" path="priority" choices="${priorities}"/>
	<acme:input-checkbox code="any.project.form.label.status" path="status"/>
	<acme:input-moment code="any.project.form.label.instantiationMoment" path="instantiationMoment"/>
	<acme:input-moment code="any.project.form.label.startMoment" path="startMoment"/>
	<acme:input-moment code="any.project.form.label.endMoment" path="endMoment"/>
	<acme:input-url code="any.project.form.label.optionalLink" path="optionalLink"/>

	<%-- <acme:button code="any.project.form.button.duties" action="/any/duty/list?masterId=${id}"/> --%>
</acme:form>