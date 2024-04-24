
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

	int							totalNumberOfCodeAuditsStatic;
	int							totalNumberOfCodeAuditsDynamic;

	Double						averageNumberOfAuditRecords;
	Integer						minimunNumberOfAuditRecords;
	Integer						maximunNumberOfAuditRecords;
	Double						deviationOfAuditRecords;

	Double						avegageTimeOfThePeriodlength;
	Double						deviationTimeOfThePeriodlength;
	Double						minimunTimeOfThePeriodlength;
	Double						maximumTimeOfThePeriodlength;
	// TODO Auto-generated method stub

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
