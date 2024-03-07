
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDashboard extends AbstractForm {

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	// Total number of progress logs with completeness rate below 25%
	private Integer				totalProgressLogsBelow25;

	// Total number of progress logs with completeness rate between 25% and 50%
	private Integer				totalProgressLogs25To50;

	// Total number of progress logs with completeness rate between 50% and 75%
	private Integer				totalProgressLogs50To75;

	// Total number of progress logs with completeness rate above 75%
	private Integer				totalProgressLogsAbove75;

	// Average budget of the contracts
	private double				avgContractBudget;

	// Deviation of the contract budgets
	private double				devContractBudget;

	// Minimum contract budget
	private double				minContractBudget;

	// Maximum contract budget
	private double				maxContractBudget;

}
