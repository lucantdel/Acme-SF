<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
		<acme:list-column code="auditor.codeAudit.form.label.code" path="code"/>
		<acme:list-column code="auditor.codeAudit.form.label.execution" path="execution"/>
		<acme:list-column code="auditor.codeAudit.form.label.type" path="type"/>
		<acme:list-column code="auditor.codeAudit.form.label.project" path="project"/>
</acme:list>