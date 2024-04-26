<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<h2>
	<acme:message code="auditor.dashboard.form.title.code-audits-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.code-audits-dinamic"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfCodeAuditsDynamic}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.code-audits-static"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfCodeAuditsStatic}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.avr-audit-records"/>
		</th>
		<td>
			<acme:print value="${averageNumberOfAuditRecords}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.max-audit-records"/>
		</th>
		<td>
			<acme:print value="${minimunNumberOfAuditRecords}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.min-audit-records"/>
		</th>
		<td>
			<acme:print value="${maximunNumberOfAuditRecords}"/>
		</td>
	</tr>
		<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.dev-audit-records"/>
		</th>
		<td>
			<acme:print value="${deviationOfAuditRecords}"/>
		</td>
	</tr>	
</table>

<h2>
	<acme:message code="auditor.dashboard.form.title.period-audit-records-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.max-period-audit-records"/>
		</th>
		<td>
			<acme:print value="${maximumTimeOfThePeriodlength}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.min-period-audit-records"/>
		</th>
		<td>
			<acme:print value="${minimunTimeOfThePeriodlength}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.dev-period-audit-records"/>
		</th>
		<td>
			<acme:print value="${deviationTimeOfThePeriodlength}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.avr-period-audit-records"/>
		</th>
		<td>
			<acme:print value="${avegageTimeOfThePeriodlength}"/>
		</td>
	</tr>
</table>


<acme:return/>

