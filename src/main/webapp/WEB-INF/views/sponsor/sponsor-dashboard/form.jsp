<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<h2>
	<acme:message code="sponsor.dashboard.form.label.sponsorship-general-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.sponsorship-link"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfSponsorshipsWithLink}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.avg-sponsorship-amount"/>
		</th>
		<jstl:forEach var="entry" items="${avgSponsorshipsAmount}">
			<tr>
				<td>
					<acme:print value="${entry.value}"/>
					<acme:print value="${entry.key}"/>
				</td>
			</tr>
		</jstl:forEach>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.dev-sponsorship-amount"/>
		</th>
		<jstl:forEach var="entry" items="${devSponsorshipsAmount}">
			<tr>
				<td>
					<acme:print value="${entry.value}"/>
					<acme:print value="${entry.key}"/>
				</td>
			</tr>
		</jstl:forEach>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.min-sponsorship-amount"/>
		</th>
		<jstl:forEach var="entry" items="${minSponsorshipsAmount}">
			<tr>
				<td>
					<acme:print value="${entry.value}"/>
					<acme:print value="${entry.key}"/>
				</td>
			</tr>
		</jstl:forEach>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.max-sponsorship-amount"/>
		</th>
		<jstl:forEach var="entry" items="${maxSponsorshipsAmount}">
			<tr>
				<td>
					<acme:print value="${entry.value}"/>
					<acme:print value="${entry.key}"/>
				</td>
			</tr>
		</jstl:forEach>
	</tr>	
</table>

<h2>
	<acme:message code="sponsor.dashboard.form.label.invoice-general-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.invoice-tax"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfInvoicesWithLowTax}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.avg-invoice-quantity"/>
		</th>
		<jstl:forEach var="entry" items="${avgInvoicesQuantity}">
			<tr>
				<td>
					<acme:print value="${entry.value}"/>
					<acme:print value="${entry.key}"/>
				</td>
			</tr>
		</jstl:forEach>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.dev-invoice-quantity"/>
		</th>
		<jstl:forEach var="entry" items="${devInvoicesQuantity}">
			<tr>
				<td>
					<acme:print value="${entry.value}"/>
					<acme:print value="${entry.key}"/>
				</td>
			</tr>
		</jstl:forEach>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.min-invoice-quantity"/>
		</th>
		<jstl:forEach var="entry" items="${minInvoicesQuantity}">
			<tr>
				<td>
					<acme:print value="${entry.value}"/>
					<acme:print value="${entry.key}"/>
				</td>
			</tr>
		</jstl:forEach>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.max-invoice-quantity"/>
		</th>
		<jstl:forEach var="entry" items="${maxInvoicesQuantity}">
			<tr>
				<td>
					<acme:print value="${entry.value}"/>
					<acme:print value="${entry.key}"/>
				</td>
			</tr>
		</jstl:forEach>
	</tr>	
</table>

