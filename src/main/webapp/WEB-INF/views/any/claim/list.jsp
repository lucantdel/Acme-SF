<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.claim.list.label.code" path="code" width="33%"/>
	<acme:list-column code="any.claim.list.label.instantiationMoment" path="instantiationMoment" width="33%"/>
	<acme:list-column code="any.claim.list.label.heading" path="heading" width="33%"/>
	<acme:list-payload path="payload"/>
</acme:list>

<jstl:if test="${_command == 'list'}">
	<acme:button code="any.claim.list.button.create" action="/any/claim/create"/>
</jstl:if>