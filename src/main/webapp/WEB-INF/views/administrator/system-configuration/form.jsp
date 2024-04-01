<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textarea code="administrator.system-configuration.form.label.acceptedCurrencies" path="acceptedCurrencies"/>
	<acme:input-textarea code="administrator.system-configuration.form.label.systemCurrency" path="systemCurrency"/>
	
	<acme:submit code="administrator.config.form.button.update" action="/administrator/system-configuration/update"/>
</acme:form>