<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="authenticated.developer.form.label.degree" path="degree"/>
	<acme:input-textbox code="authenticated.developer.form.label.specialisation" path="specialisation"/>
	<acme:input-textbox code="authenticated.developer.form.label.skills" path="skills"/>
	<acme:input-textbox code="authenticated.developer.form.label.contactEmail" path="contactEmail"/>
	<acme:input-textbox code="authenticated.developer.form.label.link" path="link"/>
	
	<acme:submit test="${_command == 'create'}" code="authenticated.developer.form.button.create" action="/authenticated/developer/create"/>
	<acme:submit test="${_command == 'update'}" code="authenticated.developer.form.button.update" action="/authenticated/developer/update"/>
</acme:form>