<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="sponsor.invoice.list.label.code" path="code" width="20%"/>
	<acme:list-column code="sponsor.invoice.list.label.total-amount" path="totalAmount" width="25%"/>	
	<acme:list-column code="sponsor.invoice.list.label.tax" path="tax" width="11%"/>	
	<acme:list-column code="sponsor.invoice.list.label.registration-time" path="registrationTime" width="17%"/>
	<acme:list-column code="sponsor.invoice.list.label.due-date" path="dueDate" width="17%"/>
	<acme:list-column code="sponsor.invoice.list.label.draftMode" path="draftMode" width="10%"/>
	<acme:list-payload path="payload"/>
</acme:list>

<acme:button test="${showCreate}" code="sponsor.invoice.list.button.create-form" action="/sponsor/invoice/create?masterId=${masterId}"/>