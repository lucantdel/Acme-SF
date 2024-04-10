<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.claim.form.label.code" path="code" width="33%"/>
	<acme:list-column code="any.claim.form.label.instantiationMoment" path="instantiationMoment" width="33%"/>
	<acme:list-column code="any.claim.form.label.heading" path="heading" width="33%"/>
</acme:list>