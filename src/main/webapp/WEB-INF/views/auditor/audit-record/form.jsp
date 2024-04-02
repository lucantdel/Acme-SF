<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
		<acme:input-textbox code="auditor.auditRecord.form.label.code" path="code"/>
		<acme:input-textbox code="auditor.auditRecord.form.label.startDate" path="startDate"/>
		<acme:input-textbox code="auditor.auditRecord.form.label.finishDate" path="finishDate"/>
		<acme:input-textbox code="auditor.auditRecord.form.label.score" path="score"/>
		<acme:input-textbox code="auditor.auditRecord.form.label.codeAudit" path="codeAudit"/>
		
</acme:form>