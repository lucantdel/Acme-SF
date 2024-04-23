<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="developer.trainingModule.list.label.code" path="code" width="15%"/>
	<acme:list-column code="developer.trainingModule.list.label.creationMoment" path="creationMoment" width="30%"/>
	<acme:list-column code="developer.trainingModule.list.label.difficultyLevel" path="difficultyLevel" width="15%"/>
	<acme:list-column code="developer.trainingModule.list.label.project" path="project" width="15%"/>
	<acme:list-column code="developer.trainingModule.list.label.numberOfTrainingSessions" path="numberOfTrainingSessions" width="10%"/>
	<acme:list-column code="developer.trainingModule.list.label.draftMode" path="draftMode" width="15%"/>
	
</acme:list>
<acme:button code="developer.trainingModule.list.button.create" action="/developer/training-module/create"/>
	