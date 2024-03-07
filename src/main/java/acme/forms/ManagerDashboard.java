
package acme.forms;

import acme.client.data.AbstractForm;
import acme.client.data.datatypes.Money;
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

	Money						avgProjectCost;

	Money						devProjectCost;

	Money						minProjectCost;

	Money						maxProjectCost;

}
