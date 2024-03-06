
package acme.forms;

import java.util.Map;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeveloperDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Integer						totalNumberOfTrainingModulesWithUpdateMoment;
	Integer						totalNumberOfTrainingSessionsWithLink;

	//String represents the code of a TrainingModule and  Double is the associated value
	Map<String, Double>			averageTimeByTM;
	Map<String, Double>			minimumTimeByTM;
	Map<String, Double>			maximumTimeByTM;
	Map<String, Double>			standardDeviationTimeByTM;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
