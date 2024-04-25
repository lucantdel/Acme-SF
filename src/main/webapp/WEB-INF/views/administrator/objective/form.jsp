<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
<jstl:choose>
		<jstl:when test="${_command == 'show'}">
			<acme:input-textbox code="administrator.objective.form.label.title" path="title" readonly="true"/>
			<acme:input-textarea code="administrator.objective.form.label.description" path="description" readonly="true"/>
			<acme:input-select code="administrator.objective.form.label.priority" path="priority" choices="${priorities}" readonly="true"/>
			<acme:input-checkbox code="administrator.objective.form.label.status" path="status" readonly="true"/>
			<acme:input-moment code="administrator.objective.form.label.instantiationMoment" path="instantiationMoment" readonly="true"/>
			<acme:input-moment code="administrator.objective.form.label.startMoment" path="startMoment" readonly="true"/>
			<acme:input-moment code="administrator.objective.form.label.endMoment" path="endMoment" readonly="true"/>
			<acme:input-url code="administrator.objective.form.label.optionalLink" path="optionalLink" readonly="true"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:input-textbox code="administrator.objective.form.label.title" path="title"/>
			<acme:input-textarea code="administrator.objective.form.label.description" path="description"/>
			<acme:input-select code="administrator.objective.form.label.priority" path="priority" choices="${priorities}"/>
			<acme:input-checkbox code="administrator.objective.form.label.status" path="status"/>
			<acme:input-moment code="administrator.objective.form.label.startMoment" path="startMoment"/>
			<acme:input-moment code="administrator.objective.form.label.endMoment" path="endMoment"/>
			<acme:input-url code="administrator.objective.form.label.optionalLink" path="optionalLink"/>
			<acme:input-checkbox code="administrator.objective.form.label.confirmation" path="confirmation"/>
	
			<acme:submit code="administrator.objective.form.button.create" action="/administrator/objective/create"/>
		</jstl:when>
	</jstl:choose>
</acme:form>