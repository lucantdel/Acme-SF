<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="administrator.banner.list.label.updateMoment" path="updateMoment" width="60%"/>
	<acme:list-column code="administrator.banner.list.label.displayPeriodStart" path="displayPeriodStart" width="60%"/>
	<acme:list-column code="administrator.banner.list.label.displayPeriodEnd" path="displayPeriodEnd" width="60%"/>
	<acme:list-column code="administrator.banner.list.label.slogan" path="slogan" width="60%"/>
			
</acme:list>

<acme:button code="administrator.banner.list.button.create" action="/administrator/banner/create"/>

