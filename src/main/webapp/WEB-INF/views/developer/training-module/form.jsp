<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="developer.trainingModule.form.label.code" path="code"/>
	<jstl:if test="${_command == 'create'}">
		<acme:input-moment code="developer.trainingModule.form.label.creationMoment" path="creationMoment" />
	</jstl:if>
	<jstl:if test="${acme:anyOf(_command, 'show|update|delete|publish')}">
		<acme:input-moment code="developer.trainingModule.form.label.creationMoment" path="creationMoment" readonly="true"/>
		<acme:input-moment code="developer.trainingModule.form.label.updateMoment" path="updateMoment" />
		<acme:input-integer code="developer.trainingModule.form.label.numberOfTrainingSessions" path="numberOfTrainingSessions" readonly="true" />
	</jstl:if>
	<acme:input-textarea code="developer.trainingModule.form.label.details" path="details"/>
	
	<acme:input-select code="developer.trainingModule.form.label.difficultyLevel" path="difficultyLevel" choices="${difficultyLevel}" />
	
	<acme:input-select code="developer.trainingModule.form.label.project" path="project" choices="${projects}"/>
	
	<acme:input-integer code="developer.trainingModule.form.label.totaltime" path="totalEstimatedTime"/>
	<acme:input-url code="developer.trainingModule.form.label.link" path="link"/>
	
	
	
		
	<jstl:choose>	 
		<jstl:when test="${_command == 'show' && draftMode == false}">
				<acme:button code="developer.trainingModule.form.button.training-session-list" action="/developer/training-session/list?trainingModuleId=${id}"/>
		</jstl:when>
		
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">

			<jstl:if test="${_command == 'show'}">
				<jstl:if test="${numberOfTrainingSessions==0}">
					<acme:button  code="developer.trainingModule.form.button.training-session-createFIRST" action="/developer/training-session/create?trainingModuleId=${id}"/>		
				</jstl:if>
				<jstl:if test="${numberOfTrainingSessions>0}">
					<acme:button code="developer.trainingModule.form.button.training-session-list" action="/developer/training-session/list?trainingModuleId=${id}"/>		
				</jstl:if>
			</jstl:if>
			<acme:submit code="developer.trainingModule.form.button.update" action="/developer/training-module/update"/>
			<acme:submit code="developer.trainingModule.form.button.delete" action="/developer/training-module/delete"/>
			<acme:submit code="developer.trainingModule.form.button.publish" action="/developer/training-module/publish"/>
		</jstl:when>
		
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="developer.trainingModule.form.button.create" action="/developer/training-module/create"/>
		</jstl:when>		
	</jstl:choose>
	
</acme:form>