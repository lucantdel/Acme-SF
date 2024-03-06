
package acme.forms;

import java.time.Duration;

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

	Duration					avegageTimeOfThePeriodLenght;
	Duration					deviationTimeOfThePeriodLenght;
	Duration					minimunTimeOfThePeriodLenght;
	Duration					maximumTimeOfThePeriodLenght;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
