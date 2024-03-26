<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>
<acme:list>
    <acme:list-column code="contract.list.label.instantiationMoment" path="instantiationMoment" width="20%"/>
    <acme:list-column code="contract.list.label.providerName" path="providerName" width="20%"/>
    <acme:list-column code="contract.list.label.customerName" path="customerName" width="20%"/>
    <acme:list-column code="contract.list.label.goals" path="goals" width="20%"/>
    <acme:list-column code="contract.list.label.budget" path="budget" width="20%"/>
</acme:list>

