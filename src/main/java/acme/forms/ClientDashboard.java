
package acme.forms;

import java.util.Map;

import acme.client.data.AbstractForm;
import acme.datatypes.Statistics;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long		serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	private Map<String, Integer>	numCompletenessProgressLogs;

	private Statistics				contractBudgetStatistics;

}
