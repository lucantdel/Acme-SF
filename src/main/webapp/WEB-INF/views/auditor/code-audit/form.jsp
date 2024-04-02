<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
		<acme:input-textbox code="auditor.codeAudit.form.label.code" path="code"/>
		<acme:input-textbox code="auditor.codeAudit.form.label.execution" path="execution"/>
		<acme:input-textbox code="auditor.codeAudit.form.label.type" path="type"/>
		<acme:input-textbox code="auditor.codeAudit.form.label.correctiveActions" path="correctiveActions"/>
		<acme:input-textbox code="auditor.codeAudit.form.label.optionalLink" path="optionalLink"/>
		<acme:input-textbox code="auditor.codeAudit.form.label.project" path="project"/>
		
</acme:form>