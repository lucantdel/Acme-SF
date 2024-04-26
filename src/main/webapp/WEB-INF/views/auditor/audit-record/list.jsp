<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
		<acme:list-column code="auditor.auditRecord.list.label.codeAR" path="codeAR" width="25%"/>
		<acme:list-column code="auditor.auditRecord.list.label.score" path="score" width="25%"/>
		<acme:list-column code="auditor.auditRecord.list.label.draftMode" path="draftMode" width="25%"/>
		<acme:list-column code="auditor.auditRecord.list.label.codeAudit" path="codeAudit" width="25%"/>	
		<acme:list-payload path="payload"/>			
</acme:list>

