<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="any.sponsorship.form.label.code" path="code"/>
	<acme:input-moment code="any.sponsorship.form.label.moment" path="moment"/>
	<acme:input-moment code="any.sponsorship.form.label.startDuration" path="startDuration"/>
	<acme:input-moment code="any.sponsorship.form.label.finalDuration" path="finalDuration"/>
	<acme:input-money code="any.sponsorship.form.label.amount" path="amount"/>
	<acme:input-textbox code="any.sponsorship.form.label.type" path="type"/>
	<acme:input-textbox code="any.sponsorship.form.label.email" path="email"/>
	<acme:input-url code="any.sponsorship.form.label.link" path="link"/>	
</acme:form>