<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>


<acme:form>

<jstl:if test="${published==false}">

		<jstl:if test="${acme:anyOf(_command, 'show|publish|delete|create|update')}">
					<acme:input-textbox code="auditor.auditRecord.form.label.codeAR" path="codeAR"/>
					<acme:input-textbox code="auditor.auditRecord.form.label.startDate" path="startDate"/>
					<acme:input-textbox code="auditor.auditRecord.form.label.finishDate" path="finishDate"/>
					<acme:input-textbox code="auditor.auditRecord.form.label.score" path="score"/>
					<acme:input-textbox code="auditor.auditRecord.form.label.link" path="link"/>
					<acme:input-textbox code="auditor.auditRecord.form.label.published" path="published" readonly="true"/>
					<acme:input-textbox code="auditor.auditRecord.form.label.codeAudit" path="codeAuditCode" readonly="true"/>
		</jstl:if>


		
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|update|publish')}">
			<acme:submit code="auditor.auditRecord.form.button.update" action="/auditor/audit-record/update?auditRecord=${id}"/>
			<acme:submit code="auditor.auditRecord.form.button.delete" action="/auditor/audit-record/delete"/>
			<acme:submit code="auditor.auditRecord.form.button.publish" action="/auditor/audit-record/publish?auditRecord=${id}"/>
			
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="auditor.auditRecord.form.button.create" action="/auditor/audit-record/create?codeAuditId=${codeAuditId}"/>
		</jstl:when>		
	</jstl:choose>	
	
</jstl:if>	
<jstl:if test="${published==true}">

		<acme:input-textbox code="auditor.auditRecord.form.label.codeAR" path="codeAR" readonly="true"/>
		<acme:input-textbox code="auditor.auditRecord.form.label.startDate" path="startDate" readonly="true"/>
		<acme:input-textbox code="auditor.auditRecord.form.label.finishDate" path="finishDate" readonly="true"/>
		<acme:input-textbox code="auditor.auditRecord.form.label.score" path="score" readonly="true"/>
		<acme:input-textbox code="auditor.auditRecord.form.label.link" path="link" readonly="true"/>
		<acme:input-textbox code="auditor.auditRecord.form.label.published" path="published" readonly="true"/>
		<acme:input-textbox code="auditor.auditRecord.form.label.codeAudit" path="codeAuditCode" readonly="true"/>
	
</jstl:if>
</acme:form>
