<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="any.claim.form.label.code" path="code"/>
	<acme:input-textbox code="any.claim.form.label.heading" path="heading"/>
	<acme:input-textarea code="any.claim.form.label.description" path="description"/>
	<acme:input-textarea code="any.claim.form.label.department" path="department"/>
	<acme:input-email code="any.claim.form.label.email" path="email"/>
	<acme:input-url code="any.claim.form.label.link" path="link"/>
	


	<jstl:choose>
		
		<jstl:when test="${_command == 'create'}">
			<acme:input-checkbox code="any.claim.form.label.confirm" path="confirm"/>	
			<acme:submit code="any.claim.form.button.publish" action="/any/claim/create"/>
		</jstl:when>		
	</jstl:choose>	
	
</acme:form>