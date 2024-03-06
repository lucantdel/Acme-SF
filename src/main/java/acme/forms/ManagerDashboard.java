
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	int							mustUserStories;

	int							shouldUserStories;

	int							couldUserStories;

	int							wontUserStories;

	double						avgUserStoryEstimatedCost;

	double						devUserStoryEstimatedCost;

	double						minUserStoryEstimatedCost;

	double						maxUserStoryEstimatedCost;

	double						avgProjectCost;

	double						devProjectCost;

	double						minProjectCost;

	double						maxProjectCost;

}
