
package acme.forms;

import java.util.Map;

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

	Map<String, Double>			avgProjectCost;
	Map<String, Double>			devProjectCost;
	Map<String, Double>			minProjectCost;
	Map<String, Double>			maxProjectCost;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
