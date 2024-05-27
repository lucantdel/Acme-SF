<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="sponsor.sponsorship.list.label.code" path="code" width="20%"/>
	<acme:list-column code="sponsor.sponsorship.list.label.moment" path="moment" width="25%"/>
	<acme:list-column code="sponsor.sponsorship.list.label.amount" path="amount" width="20%"/>
	<acme:list-column code="sponsor.sponsorship.list.label.project" path="project" width="20%"/>
	<acme:list-column code="sponsor.sponsorship.list.label.draft-mode" path="draftMode" width="15%"/>
	<acme:list-payload path="payload"/>
		
</acme:list>

<jstl:if test="${_command == 'list'}">
	<acme:button code="sponsor.sponsorship.list.button.create-form" action="/sponsor/sponsorship/create"/>
</jstl:if>