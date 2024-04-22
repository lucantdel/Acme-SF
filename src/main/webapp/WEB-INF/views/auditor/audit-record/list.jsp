<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
		<acme:list-column code="auditor.auditRecord.form.label.code" path="code"/>
		<acme:list-column code="auditor.auditRecord.form.label.score" path="score"/>
		<acme:list-column code="auditor.auditRecord.form.label.codeAudit" path="codeAudit"/>
</acme:list>

<acme:button code="auditor.auditRecord.list.button.create" action="/auditor/audit-record/create"/>
