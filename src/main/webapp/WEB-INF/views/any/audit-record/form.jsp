	
<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>	
		<acme:input-textbox code="auditor.auditRecord.form.label.codeAR" path="codeAR" readonly="true"/>
		<acme:input-textbox code="auditor.auditRecord.form.label.startDate" path="startDate" readonly="true"/>
		<acme:input-textbox code="auditor.auditRecord.form.label.finishDate" path="finishDate" readonly="true"/>
		<acme:input-textbox code="auditor.auditRecord.form.label.score" path="score" readonly="true"/>
		<acme:input-textbox code="auditor.auditRecord.form.label.link" path="link" readonly="true"/>
		<acme:input-textbox code="auditor.auditRecord.form.label.published" path="published" readonly="true"/>
		<acme:input-textbox code="auditor.auditRecord.form.label.codeAudit" path="codeAuditCode" readonly="true"/>
</acme:form>