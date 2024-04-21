<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<h2>
	<acme:message code="manager.dashboard.form.title.project-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.avg-project-cost"/>
		</th>
		<td>
			<acme:print value="${avgProjectCost}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.dev-project-cost"/>
		</th>
		<td>
			<acme:print value="${devProjectCost}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.min-project-cost"/>
		</th>
		<td>
			<acme:print value="${minProjectCost}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.max-project-cost"/>
		</th>
		<td>
			<acme:print value="${maxProjectCost}"/>
		</td>
	</tr>	
</table>

<h2>
	<acme:message code="manager.dashboard.form.title.user-story-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.avg-user-story-estimated-cost"/>
		</th>
		<td>
			<acme:print value="${avgUserStoryEstimatedCost}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.dev-user-story-estimated-cost"/>
		</th>
		<td>
			<acme:print value="${devUserStoryEstimatedCost}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.min-user-story-estimated-cost"/>
		</th>
		<td>
			<acme:print value="${minUserStoryEstimatedCost}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.max-user-story-estimated-cost"/>
		</th>
		<td>
			<acme:print value="${maxUserStoryEstimatedCost}"/>
		</td>
	</tr>	
</table>

<h2>
	<acme:message code="manager.dashboard.form.title.user-story-priorities"/>
</h2>

<div>
	<canvas id="canvas"></canvas>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		var data = {
			labels : [
					"MUST", "SHOULD", "COULD", "WONT"
			],
			datasets : [
				{
					data : [
						<jstl:out value="${mustUserStories}"/>, 
						<jstl:out value="${shouldUserStories}"/>, 
						<jstl:out value="${couldUserStories}"/>,
						<jstl:out value="${wontUserStories}"/>
					]
				}
			]
		};
		var options = {
			scales : {
				yAxes : [
					{
						ticks : {
							suggestedMin : 0.0,
							suggestedMax : 5.0
						}
					}
				]
			},
			legend : {
				display : false
			}
		};
	
		var canvas, context;
	
		canvas = document.getElementById("canvas");
		context = canvas.getContext("2d");
		new Chart(context, {
			type : "bar",
			data : data,
			options : options
		});
	});
</script>

<acme:return/>

