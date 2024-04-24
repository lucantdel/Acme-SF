<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="administrator.risk.form.label.reference" path="reference"/>
	<acme:input-moment code="administrator.risk.form.label.identificationDate" path="identificationDate"/>
	<acme:input-double code="administrator.risk.form.label.impact" path="impact"/>
	<acme:input-double code="administrator.risk.form.label.probability" path="probability" placeholder="administrator.risk.form.placeholder.probability"/>
	<acme:input-textarea code="administrator.risk.form.label.description" path="description"/>
	<jstl:if test="${acme:anyOf(_command, 'show|delete')}">
		<acme:input-double code="administrator.risk.form.label.value" path="estimatedValue" readonly="true"/>
	</jstl:if>
	<acme:input-url code="administrator.risk.form.label.optionalLink" path="optionalLink"/>
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete')}">
			<acme:submit code="administrator.risk.form.button.delete" action="/administrator/risk/delete"/>
			<acme:submit code="administrator.risk.form.button.update" action="/administrator/risk/update"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="administrator.form.risk.button.create" action="/administrator/risk/create"/>
		</jstl:when>	
	</jstl:choose>	
</acme:form>