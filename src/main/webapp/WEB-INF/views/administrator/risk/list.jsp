<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="administrator.risk.list.label.reference" path="reference" width="20%"/>
	<acme:list-column code="administrator.risk.list.label.value" path="value" width="20%"/>
	<acme:list-column code="administrator.risk.list.label.identificationDate" path="identificationDate" width="60%"/>
</acme:list>