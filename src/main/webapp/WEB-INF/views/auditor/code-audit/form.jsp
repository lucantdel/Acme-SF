<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>

	<jstl:if test="${acme:anyOf(_command, 'create|update')}">

		<acme:input-textbox code="auditor.codeAudit.form.label.code" path="code"/>
		<acme:input-textbox code="auditor.codeAudit.form.label.execution" path="execution"/>
		<acme:input-textbox code="auditor.codeAudit.form.label.type" path="type"/>
		<acme:input-textbox code="auditor.codeAudit.form.label.correctiveActions" path="correctiveActions"/>
		<acme:input-textbox code="auditor.codeAudit.form.label.optionalLink" path="optionalLink"/>
		<acme:input-select code="auditor.codeAudit.form.label.project" path="project" choices="${projects}"/>
		
	</jstl:if>
	<jstl:if test="${_command == 'delete'}">

		<acme:input-textbox code="auditor.codeAudit.form.label.code" path="code"/>
		<acme:input-textbox code="auditor.codeAudit.form.label.execution" path="execution"/>
		<acme:input-textbox code="auditor.codeAudit.form.label.type" path="type"/>
		<acme:input-textbox code="auditor.codeAudit.form.label.correctiveActions" path="correctiveActions"/>
		<acme:input-textbox code="auditor.codeAudit.form.label.optionalLink" path="optionalLink"/>
		
	</jstl:if>
			<jstl:if test="${acme:anyOf(_command, 'show|publish')}">
	
		<acme:input-textbox code="auditor.codeAudit.form.label.code" path="code"/>
		<acme:input-textbox code="auditor.codeAudit.form.label.execution" path="execution"/>
		<acme:input-textbox code="auditor.codeAudit.form.label.type" path="type"/>
		<acme:input-textbox code="auditor.codeAudit.form.label.correctiveActions" path="correctiveActions"/>
		<acme:input-textbox code="auditor.codeAudit.form.label.optionalLink" path="optionalLink"/>
		<acme:input-textbox code="auditor.codeAudit.form.label.project" path="project" readonly="true"/>
		<acme:input-textbox code="auditor.codeAudit.form.label.Mark" path="Mark" readonly="true"/>
		<acme:input-textbox code="auditor.codeAudit.form.label.draftMode" path="draftMode" readonly="true"/>
	
	</jstl:if>
		
		
		
		
		<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish')}">
			<acme:submit code="auditor.codeAudit.form.button.update" action="/auditor/code-audit/update"/>
			<acme:submit code="auditor.codeAudit.form.button.delete" action="/auditor/code-audit/delete"/>
			<acme:submit code="auditor.codeAudit.form.button.publish" action="/auditor/code-audit/publish"/>
			<acme:submit code="auditor.auditRecord.form.button.create" action="/auditor/audit-record/create"/>			
			
			
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="auditor.codeAudit.form.button.create" action="/auditor/code-audit/create"/>
			
		</jstl:when>		
	</jstl:choose>		
</acme:form>