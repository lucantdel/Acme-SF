<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<h2>
	<acme:message code="sponsor.dashboard.form.title.general-indicators"/>
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
			<acme:message code="sponsor.dashboard.form.label.sponsorship-link"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfSponsorshipsWithLink}"/>
		</td>
	</tr>
	</table>

<jstl:forEach var="currency" items="${supportedCurrencies}">
    <h2>
        <acme:message code="sponsor.dashboard.form.label.sponsorship-general-indicators"/>
        <acme:message code="${currency}"/>
    </h2>

    <table class="table table-sm">
        <tr>
            <th scope="row">
                <acme:message code="sponsor.dashboard.form.label.avg-sponsorship-amount"/>
            </th>
            <td>
                <acme:print value="${avgSponsorshipsAmount[currency]}"/>
            </td>
        </tr>
        <tr>
            <th scope="row">
                <acme:message code="sponsor.dashboard.form.label.dev-sponsorship-amount"/>
            </th>
            <td>
                <acme:print value="${devSponsorshipsAmount[currency]}"/>
            </td>
        </tr>
        <tr>
            <th scope="row">
                <acme:message code="sponsor.dashboard.form.label.min-sponsorship-amount"/>
            </th>
            <td>
                <acme:print value="${minSponsorshipsAmount[currency]}"/>
            </td>
        </tr>   
        <tr>
            <th scope="row">
                <acme:message code="sponsor.dashboard.form.label.max-sponsorship-amount"/>
            </th>
            <td>
                <acme:print value="${maxSponsorshipsAmount[currency]}"/>
            </td>
        </tr>
    </table>
</jstl:forEach>

<jstl:forEach var="currency" items="${supportedCurrencies}">
    <h2>
        <acme:message code="sponsor.dashboard.form.label.invoice-general-indicators"/>
        <acme:message code="${currency}"/>
    </h2>

    <table class="table table-sm">
        <tr>
            <th scope="row">
                <acme:message code="sponsor.dashboard.form.label.avg-invoice-quantity"/>
            </th>
            <td>
                <acme:print value="${avgInvoicesQuantity[currency]}"/>
            </td>
        </tr>
        <tr>
            <th scope="row">
                <acme:message code="sponsor.dashboard.form.label.dev-invoice-quantity"/>
            </th>
            <td>
                <acme:print value="${devInvoicesQuantity[currency]}"/>
            </td>
        </tr>
        <tr>
            <th scope="row">
                <acme:message code="sponsor.dashboard.form.label.min-invoice-quantity"/>
            </th>
            <td>
                <acme:print value="${minInvoicesQuantity[currency]}"/>
            </td>
        </tr>   
        <tr>
            <th scope="row">
                <acme:message code="sponsor.dashboard.form.label.max-invoice-quantity"/>
            </th>
            <td>
                <acme:print value="${maxInvoicesQuantity[currency]}"/>
            </td>
        </tr>
    </table>
</jstl:forEach>