
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

	int							mustUserStories;

	int							shouldUserStories;

	int							couldUserStories;

	int							wontUserStories;

	double						avgUserStoryEstimatedCost;

	double						devUserStoryEstimatedCost;

	int							minUserStoryEstimatedCost;

	int							maxUserStoryEstimatedCost;

	double						avgProjectCost;

	double						devProjectCost;

	double						minProjectCost;

	double						maxProjectCost;

}
