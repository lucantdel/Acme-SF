
package acme.entities.userstories;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.entities.projects.Project;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserStory extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(max = 75)
	private String				title;

	@NotBlank
	@Length(max = 100)
	private String				description;

	@NotNull
	@Min(1)
	private Integer				estimatedCost; // in hours

	@NotBlank
	@Length(max = 100)
	private String				acceptanceCriteria;

	private Priority			priority;

	@URL
	private String				link;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

	@NotNull
	@ManyToOne(optional = false)
	private Project				project;

}
