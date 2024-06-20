<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="any.contract.form.label.code" path="code"/>
		<acme:input-textbox code="any.contract.form.label.providerName" path="providerName"/>	
	<acme:input-textbox code="any.contract.form.label.customerName" path="customerName"/>	
	<acme:input-textarea code="any.contract.form.label.goals" path="goals" />
	<acme:input-money code="any.contract.form.label.budget" path="budget"/>
	<acme:input-select code="any.contract.form.label.project" path="project" choices="${projects}"/>
	
	<acme:button code="any.contract.form.button.progress-logs" action="/any/progress-log/list?masterId=${id}"/>
	
</acme:form>