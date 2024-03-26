<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="acme" uri="http://acme-framework.org/" %>

<acme:form>
    <acme:input-textbox code="any.progressLog.form.label.recordId" path="recordId"/>
    <acme:input-double code="any.progressLog.form.label.completenessPercentage" path="completenessPercentage"/>
    <acme:input-textarea code="any.progressLog.form.label.progressComment" path="progressComment"/>
    <acme:input-moment code="any.progressLog.form.label.registrationMoment" path="registrationMoment"/>
    <acme:input-textbox code="any.progressLog.form.label.responsiblePerson" path="responsiblePerson"/>
</acme:form>
