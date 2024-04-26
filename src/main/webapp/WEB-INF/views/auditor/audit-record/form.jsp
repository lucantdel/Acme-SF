<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
		<acme:input-textbox code="auditor.auditRecord.form.label.codeAR" path="codeAR"/>
		<acme:input-textbox code="auditor.auditRecord.form.label.startDate" path="startDate"/>
		<acme:input-textbox code="auditor.auditRecord.form.label.finishDate" path="finishDate"/>
		<acme:input-textbox code="auditor.auditRecord.form.label.score" path="score"/>
		<acme:input-textbox code="auditor.auditRecord.form.label.link" path="link"/>
		<acme:input-textbox code="auditor.auditRecord.form.label.draftMode" path="draftMode" readonly="true"/>
		<acme:input-textbox code="auditor.auditRecord.form.label.codeAudit" path="codeAuditCode" readonly="true"/>
		
		
		
		
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|update')}">
			<acme:submit code="auditor.auditRecord.form.button.update" action="/auditor/audit-record/update"/>
			<acme:submit code="auditor.auditRecord.form.button.delete" action="/auditor/audit-record/delete"/>
			<acme:submit code="auditor.auditRecord.form.button.publish" action="/auditor/audit-record/publish"/>
			
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="auditor.auditRecord.form.button.create" action="/auditor/audit-record/create"/>
		</jstl:when>		
	</jstl:choose>		
</acme:form>