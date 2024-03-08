
package acme.forms;

import java.util.Map;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdministratorDashboard extends AbstractForm {
	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	//String represents Role meanwhile Integer the total number of principals of that role
	Map<String, Integer>		totalNumberOfPrincipalsPerRole;

	Double						ratioOfNoticesWithBothEmailAndLink;
	Double						ratioOfCriticalObjectives;
	Double						ratioNonCriticalObjectives;

	//String represents the reference of a Risk and  Double is the associated value
	Map<String, Double>			averageValueByRisk;
	Map<String, Double>			minimumValueByRisk;
	Map<String, Double>			maximumValueByRisk;
	Map<String, Double>			standardDeviationValueByRisk;

	Double						averageNumberOfClaimsLastTenWeeks;
	Double						minimumNumberOfClaimsLastTenWeeks;
	Double						maximumNumberOfClaimsLastTenWeeks;
	Double						standardDeviationNumberOfClaimsLastTenWeeks;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
