
package acme.entities.codeAudits;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.entities.projects.Project;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CodeAudits extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	//	// Attributes -------------------------------------------------------------

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}-[0-9]{3}")
	protected String			code;

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	protected Date				execution;

	@NotNull
	protected CodeAuditsStatus	type;

	@NotBlank
	@Length(max = 100)
	protected String			correctiveActions;

	@URL
	protected String			optionalLink;

	//	// Derived attributes -----------------------------------------------------


	@Transient
	protected String Mark() {
		//The mark is the mode of the marks of its associated codeRecords
		return null;
	}

	//	// Relationships ----------------------------------------------------------


	@NotNull
	@ManyToOne(optional = false)
	protected Project project;

}
