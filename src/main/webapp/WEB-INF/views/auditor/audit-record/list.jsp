<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
		<acme:list-column code="auditor.auditRecord.list.label.codeAR" path="codeAR"/>
		<acme:list-column code="auditor.auditRecord.list.label.score" path="score"/>
		<acme:list-column code="auditor.auditRecord.list.label.draftMode" path="draftMode"/>
		<acme:list-column code="auditor.auditRecord.list.label.codeAudit" path="codeAudit"/>
		
		
</acme:list>

<jstl:if test="${_command == 'list-mine'}">
	<acme:button code="auditor.auditRecord.list.button.create" action="/auditor/audit-record/create"/>
</jstl:if>	