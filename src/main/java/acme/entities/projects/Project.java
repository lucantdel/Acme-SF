
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

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	@Pattern(regexp = "[A-Z]{3}-[0-9]{4}")
	@NotBlank
	@Column(unique = true)
	protected String			code;

	@NotBlank
	@Length(max = 75)
	protected String			title;

	@NotBlank
	@Length(max = 100)
	protected String			projectAbstract;

	protected String			indication;

	@NotNull
	@Min(0)
	protected Money				cost;

	@URL
	protected String			link;


	protected boolean hasFatalErrors() {
		return !(this.indication != null && !this.indication.isEmpty());
	}

}
