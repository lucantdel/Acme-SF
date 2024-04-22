<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<h2>
	<acme:message code="sponsor.dashboard.form.title.sponshorship-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.sponsorship-link"/>
		</th>
		<td>
			<acme:print value="${avgSponsorshipsAmount}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.avg-sponsorship-amount"/>
		</th>
		<td>
			<acme:print value="${avgSponsorshipsAmount}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.dev-sponsorship-amount"/>
		</th>
		<td>
			<acme:print value="${devSponsorshipsAmount}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.min-sponsorship-amount"/>
		</th>
		<td>
			<acme:print value="${minSponsorshipsAmount}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.max-sponsorship-amount"/>
		</th>
		<td>
			<acme:print value="${maxSponsorshipsAmount}"/>
		</td>
	</tr>	
</table>

<h2>
	<acme:message code="sponsor.dashboard.form.title.invoice-indicators"/>
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
		<td>
			<acme:print value="${avgInvoicesQuantity}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.dev-invoice-quantity"/>
		</th>
		<td>
			<acme:print value="${devInvoicesQuantity}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.min-invoice-quantity"/>
		</th>
		<td>
			<acme:print value="${minInvoicesQuantity}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.max-invoice-quantity"/>
		</th>
		<td>
			<acme:print value="${maxInvoicesQuantity}"/>
		</td>
	</tr>	
</table>


<acme:return/>

