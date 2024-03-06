
package acme.entities.projects;


public class Project {


package acme.entities.projects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.client.data.datatypes.Money;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Project extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Pattern(regexp = "[A-Z]{3}-\\d{4}")
	@NotBlank
	@Column(unique = true)
	private String				code;

	@NotBlank
	@Length(max = 75)
	private String				title;

	@NotBlank
	@Length(max = 100)
	private String				projectAbstract;

	private String				indication;

	@NotNull
	@Min(0)
	private Money				cost;

	@URL
	private String				link;

	private boolean				published;

	// Derived attributes ----------------------------------------------------


	public boolean hasFatalErrors() {
		return !(this.indication != null && !this.indication.isEmpty());
	}

	// Relationships ----------------------------------------------------------


}
