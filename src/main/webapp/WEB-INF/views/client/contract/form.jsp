<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="acme" uri="http://acme-framework.org/" %>

<acme:form>
    <acme:input-textbox code="client.contract.form.label.code" path="code"/>
    <acme:input-moment code="client.contract.form.label.moment" path="moment"/>
    <acme:input-textbox code="client.contract.form.label.provider" path="provider"/>
    <acme:input-textbox code="client.contract.form.label.customer" path="customer"/>
    <acme:input-textarea code="client.contract.form.label.goals" path="goals"/>
    <acme:input-money code="client.contract.form.label.budget" path="budget"/>
    
    <jstl:if test="${acme:anyOf(_command, 'show|update|delete|publish')}">
	<acme:input-textbox code="client.contract.form.label.project" path="projectTitle" readonly="true"/>	
	</jstl:if>
	
	<jstl:if test="${_command == 'create'}">
	<acme:input-select code="client.contract.form.label.project" path="project" choices="${projects}"/>	
	</jstl:if>
	    
    <jstl:choose>
    	<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true }">
    		<acme:submit code="client.contract.form.button.update" action="/client/contract/update"/>
			<acme:submit code="client.contract.form.button.delete" action="/client/contract/delete"/>
			<acme:submit code="client.contract.form.button.publish" action="/client/contract/publish"/>			
		</jstl:when>
    	<jstl:when test="${_command == 'create'}">
			<acme:submit code="client.contract.form.button.create" action="/client/contract/create"/>
		</jstl:when>
    </jstl:choose>
    
</acme:form>