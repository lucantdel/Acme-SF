<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<h2>
	<acme:message code="developer.developer-dashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="developer.developer-dashboard.form.label.total-number-tm-with-update-moment"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfTrainingModulesWithUpdateMoment}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="developer.developer-dashboard.form.label.total-number-ts-with-link"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfTrainingSessionsWithLink}"/>
		</td>
	</tr> 
	
	<jstl:if test="${totalNumberOfTrainingModulesWithUpdateMoment != 0 && totalNumberOfTrainingSessionsWithLink != 0}">
		<tr>
		<th scope="row">
			<acme:message code="developer.dashboard.form.label.averageTimeByTM"/>
		</th>
	<td>
			<acme:print value="${averageTimeByTM}"/>
		</td>
	</tr>
		<tr>
		<th scope="row">
			<acme:message code="developer.dashboard.form.label.minimumTimeByTM"/>
		</th>
		<td>
			<acme:print value="${minimumTimeByTM}"/>
		</td>
	</tr>
		<tr>
		<th scope="row">
			<acme:message code="developer.dashboard.form.label.maximumTimeByTM"/>
		</th>
		<td>
			<acme:print value="${maximumTimeByTM}"/>
		</td>
	</tr>
		<tr>
		<th scope="row">
			<acme:message code="developer.dashboard.form.label.standardDeviationTimeByTM"/>
		</th>
		<td>
			<acme:print value="${standardDeviationTimeByTM}"/>
		</td>
	</tr>
	</jstl:if>
	
	<jstl:if test="${totalNumberOfTrainingModulesWithUpdateMoment == 0 && totalNumberOfTrainingSessionsWithLink == 0}">
	
		<tr>
		<th scope="row">
			<acme:message code="developer.dashboard.form.label.averageTimeByTM"/>
		</th>
	<td>
			<acme:print value="N/A"/>
		</td>
	</tr>
		<tr>
		<th scope="row">
			<acme:message code="developer.dashboard.form.label.minimumTimeByTM"/>
		</th>
		<td>
			<acme:print value="N/A"/>
		</td>
	</tr>
		<tr>
		<th scope="row">
			<acme:message code="developer.dashboard.form.label.maximumTimeByTM"/>
		</th>
		<td>
			<acme:print value="N/A"/>
		</td>
	</tr>
		<tr>
		<th scope="row">
			<acme:message code="developer.dashboard.form.label.standardDeviationTimeByTM"/>
		</th>
		<td>
			<acme:print value="N/A"/>
		</td>
	</tr>
	
	</jstl:if>
		
	

</table>


<acme:return/>