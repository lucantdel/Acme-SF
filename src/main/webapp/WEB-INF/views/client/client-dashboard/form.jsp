

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="client.clientDashboard.form.label.average-contract-budget"/>
		</th>
		<td>
			<acme:print value="${contractBudgetStatistics.getAverage()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="client.clientDashboard.form.label.min-contract-budget"/>
		</th>
		<td>
			<acme:print value="${contractBudgetStatistics.getMinimum()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="client.clientDashboard.form.label.max-contract-budget"/>
		</th>
		<td>
			<acme:print value="${contractBudgetStatistics.getMaximum()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="client.clientDashboard.form.label.lin-dev-contract-budget"/>
		</th>
		<td>
			<acme:print value="${contractBudgetStatistics.getDeviation()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="client.clientDashboard.form.label.progress-Log-with-less25"/>
		</th>
		<td>
			<acme:print value="${numCompletenessProgressLogs.get('less25')}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="client.clientDashboard.form.label.progress-Log-25to50"/>
		</th>
		<td>
			<acme:print value="${numCompletenessProgressLogs.get('25to50')}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="client.clientDashboard.form.label.progress-Log-50to75"/>
		</th>
		<td>
			<acme:print value="${numCompletenessProgressLogs.get('50to75')}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="client.clientDashboard.form.label.progress-Log-with-over75"/>
		</th>
		<td>
			<acme:print value="${numCompletenessProgressLogs.get('over75')}"/>
		</td>
	</tr>	
</table>



<acme:return/>