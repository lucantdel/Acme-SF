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
				<jstl:if test="${totalNumberOfCodeAuditsDynamic == 0}">
					<acme:message code="auditor.dashboard.form.label.error"/>
				</jstl:if>
				<jstl:if test="${totalNumberOfCodeAuditsDynamic != 0}">
					<acme:print value="${totalNumberOfCodeAuditsDynamic}"/>
				</jstl:if>
				
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.code-audits-static"/>
		</th>
		<td>
				<jstl:if test="${totalNumberOfCodeAuditsStatic == 0}">
					<acme:message code="auditor.dashboard.form.label.error"/>
				</jstl:if>
				<jstl:if test="${totalNumberOfCodeAuditsStatic != 0}">
					<acme:print value="${totalNumberOfCodeAuditsStatic}"/>
				</jstl:if>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.avr-audit-records"/>
		</th>
		<td>
				<jstl:if test="${averageNumberOfAuditRecords == 0}">
					<acme:message code="auditor.dashboard.form.label.error"/>
				</jstl:if>
				<jstl:if test="${averageNumberOfAuditRecords != 0}">
					<acme:print value="${averageNumberOfAuditRecords}"/>
				</jstl:if>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.max-audit-records"/>
		</th>
		<td>
		
				<jstl:if test="${minimunNumberOfAuditRecords == 0}">
					<acme:message code="auditor.dashboard.form.label.error"/>
				</jstl:if>
				<jstl:if test="${minimunNumberOfAuditRecords != 0}">
					<acme:print value="${minimunNumberOfAuditRecords}"/>
				</jstl:if>
		
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.min-audit-records"/>
		</th>
		<td>
		
				<jstl:if test="${maximunNumberOfAuditRecords == 0}">
					<acme:message code="auditor.dashboard.form.label.error"/>
				</jstl:if>
				<jstl:if test="${maximunNumberOfAuditRecords != 0}">
					<acme:print value="${maximunNumberOfAuditRecords}"/>
				</jstl:if>	
		
		</td>
	</tr>
		<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.dev-audit-records"/>
		</th>
		<td>
		
				<jstl:if test="${deviationOfAuditRecords == 0}">
					<acme:message code="auditor.dashboard.form.label.error"/>
				</jstl:if>
				<jstl:if test="${deviationOfAuditRecords != 0}">
					<acme:print value="${deviationOfAuditRecords}"/>
				</jstl:if>	
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
		
				<jstl:if test="${maximumTimeOfThePeriodlength == 0}">
					<acme:message code="auditor.dashboard.form.label.error"/>
				</jstl:if>
				<jstl:if test="${maximumTimeOfThePeriodlength != 0}">
					<acme:print value="${maximumTimeOfThePeriodlength}"/>
				</jstl:if>	
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.min-period-audit-records"/>
		</th>
		<td>
		
				<jstl:if test="${minimunTimeOfThePeriodlength == 0}">
					<acme:message code="auditor.dashboard.form.label.error"/>
				</jstl:if>
				<jstl:if test="${minimunTimeOfThePeriodlength != 0}">
					<acme:print value="${minimunTimeOfThePeriodlength}"/>
				</jstl:if>	
		
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.dev-period-audit-records"/>
		</th>
		<td>
		
				<jstl:if test="${deviationTimeOfThePeriodlength == 0}">
					<acme:message code="auditor.dashboard.form.label.error"/>
				</jstl:if>
				<jstl:if test="${deviationTimeOfThePeriodlength != 0}">
					<acme:print value="${deviationTimeOfThePeriodlength}"/>
				</jstl:if>	
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.avr-period-audit-records"/>
		</th>
		<td>
		
				<jstl:if test="${avegageTimeOfThePeriodlength == 0}">
					<acme:message code="auditor.dashboard.form.label.error"/>
				</jstl:if>
				<jstl:if test="${avegageTimeOfThePeriodlength != 0}">
					<acme:print value="${avegageTimeOfThePeriodlength}"/>
				</jstl:if>	
		</td>
	</tr>
</table>


<acme:return/>

