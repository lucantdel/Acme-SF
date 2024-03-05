
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

	private Integer				mustUserStories;

	private Integer				shouldUserStories;

	private Integer				couldUserStories;

	private Integer				wontUserStories;

	private Double				avgUserStoryEstimatedCost;

	private Double				devUserStoryEstimatedCost;

	private Double				minUserStoryEstimatedCost;

	private Double				maxUserStoryEstimatedCost;

	private Double				avgProjectCost;

	private Double				devProjectCost;

	private Double				minProjectCost;

	private Double				maxProjectCost;

}
