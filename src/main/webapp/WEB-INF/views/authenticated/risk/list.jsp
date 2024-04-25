<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="authenticated.risk.list.label.reference" path="reference" width="20%"/>
	<acme:list-column code="authenticated.risk.list.label.value" path="estimatedValue" width="20%"/>
	<acme:list-column code="authenticated.risk.list.label.identificationDate" path="identificationDate" width="60%"/>
</acme:list>
