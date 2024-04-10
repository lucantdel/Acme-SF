<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.claim.form.label.code" path="code" width="14%"/>
	<acme:list-column code="any.claim.form.label.instantiationMoment" path="instantiationMoment" width="14%"/>
	<acme:list-column code="any.claim.form.label.heading" path="heading" width="14%"/>
	<acme:list-column code="any.claim.form.label.description" path="description" width="14%"/>
	<acme:list-column code="any.claim.form.label.department" path="department" width="14%"/>
	<acme:list-column code="any.claim.form.label.email" path="email" width="14%"/>
	<acme:list-column code="any.claim.form.label.link" path="link" width="14%"/>
</acme:list>