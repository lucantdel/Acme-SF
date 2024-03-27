<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="acme" uri="http://acme-framework.org/" %>

<acme:form>
    <acme:input-moment code="contract.form.label.instantiationMoment" path="instantiationMoment"/>
    <acme:input-textbox code="contract.form.label.providerName" path="providerName"/>
    <acme:input-textbox code="contract.form.label.customerName" path="customerName"/>
    <acme:input-textarea code="contract.form.label.goals" path="goals"/>
    <acme:input-textbox code="contract.form.label.budget" path="budget"/>
</acme:form>
