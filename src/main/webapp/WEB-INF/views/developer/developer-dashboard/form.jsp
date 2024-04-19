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

</table>


<acme:return/>