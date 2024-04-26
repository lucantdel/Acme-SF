<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="administrator.risk.list.label.reference" path="reference" width="20%"/>
	<acme:list-column code="administrator.risk.list.label.value" path="estimatedValue" width="20%"/>
	<acme:list-column code="administrator.risk.list.label.identificationDate" path="identificationDate" width="60%"/>
	<acme:list-payload path="payload"/>
</acme:list>
<acme:button code="administrator.risk.list.button.create" action="/administrator/risk/create"/>
