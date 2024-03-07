
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditorDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;
	// Attributes -------------------------------------------------------------

	Integer						totalNumberOfCodeAuditsStatic;
	Integer						totalNumberOfCodeAuditsDinamic;
	Integer						totalNumbreOfTypesOfAuditRecords;

	Double						averageNumberOfAuditRecords;
	Integer						minimunNumberOfAuditRecords;
	Integer						maximunNumberOfAuditRecords;

	Double						avegageTimeOfThePeriodLenght;
	Double						deviationTimeOfThePeriodLenght;
	Double						minimunTimeOfThePeriodLenght;
	Double						maximumTimeOfThePeriodLenght;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
