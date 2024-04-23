<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
		<acme:list-column code="auditor.codeAudit.list.label.code" path="code"/>
		<acme:list-column code="auditor.codeAudit.list.label.execution" path="execution"/>
		<acme:list-column code="auditor.codeAudit.list.label.type" path="type"/>
		<acme:list-column code="auditor.codeAudit.list.label.project" path="project"/>
		<acme:list-column code="auditor.codeAudit.list.label.auditor" path="auditor"/>
		<acme:list-column code="auditor.codeAudit.list.label.Mark" path="Mark"/>
		
</acme:list>

<jstl:if test="${_command == 'list-mine'}">
	<acme:button code="auditor.codeAudit.list.button.create" action="/auditor/code-audit/create"/>
</jstl:if>	