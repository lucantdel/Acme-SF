<%@ page language="java" %>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>
<acme:list>
    <acme:list-column code="client.contract.list.label.code" path="code" width="20%"/>
    <acme:list-column code="client.contract.list.label.moment" path="moment" width="20%"/>
    <acme:list-column code="client.contract.list.label.provider" path="provider" width="20%"/>
    <acme:list-column code="client.contract.list.label.customer" path="customer" width="20%"/>
    <acme:list-column code="client.contract.list.label.project" path="project" width="20%"/>
</acme:list>
<jstl:if test="${_command == 'list'}">
	<acme:button code="client.contract.list.button.create" action="/client/contract/create"/>
</jstl:if>