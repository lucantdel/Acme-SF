
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	protected Integer			mustUserStories;

	protected Integer			shouldUserStories;

	protected Integer			couldUserStories;

	protected Integer			wontUserStories;

	protected Double			avgUserStoryEstimatedCost;

	protected Double			devUserStoryEstimatedCost;

	protected Double			minUserStoryEstimatedCost;

	protected Double			maxUserStoryEstimatedCost;

	protected Double			avgProjectCost;

	protected Double			devProjectCost;

	protected Double			minProjectCost;

	protected Double			maxProjectCost;

}
