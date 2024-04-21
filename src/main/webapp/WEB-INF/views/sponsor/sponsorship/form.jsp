<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="any.project.form.label.code" path="code"/>
	<acme:input-moment code="any.project.form.label.moment" path="moment"/>
	<acme:input-moment code="any.project.form.label.startDuration" path="startDuration"/>
	<acme:input-moment code="any.project.form.label.finalDuration" path="finalDuration"/>
	<acme:input-double code="any.project.form.label.amount" path="amount"/>
	<acme:input-textbox code="any.project.form.label.type" path="type"/>
	<acme:input-textbox code="any.project.form.label.email" path="email"/>
	<acme:input-url code="any.project.form.label.link" path="link"/>	
</acme:form>