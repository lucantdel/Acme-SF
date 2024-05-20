<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
		<acme:input-textbox code="auditor.codeAudit.form.label.code" path="code" readonly="true"/>
		<acme:input-textbox code="auditor.codeAudit.form.label.execution" path="execution" readonly="true"/>
		<acme:input-textbox code="auditor.codeAudit.form.label.type" path="type" readonly="true"/>
		<acme:input-textbox code="auditor.codeAudit.form.label.correctiveActions" path="correctiveActions" readonly="true"/>
		<acme:input-textbox code="auditor.codeAudit.form.label.optionalLink" path="optionalLink" readonly="true"/>
		<acme:input-select code="auditor.codeAudit.form.label.project" path="project" choices="${projects}" readonly="true"/>
		<acme:input-textbox code="auditor.codeAudit.form.label.published" path="published" readonly="true"/>
		
		<acme:button code="any.codeAudit.form.button.audit-record-list" action="/any/audit-record/list?codeAuditId=${id}"/>				
		

</acme:form>