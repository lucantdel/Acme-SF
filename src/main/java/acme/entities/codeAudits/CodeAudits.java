
package acme.entities.codeAudits;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CodeAudits extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	//	// Attributes -------------------------------------------------------------

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}-[0-9]{3}")
	private String				code;

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	private Date				execution;

	@NotNull
	private CodeAuditsStatus	type;

	@NotBlank
	@Length(max = 100)
	private String				correctiveActions;

	@NotNull
	@NotBlank
	private String				mark;

	@URL
	private String				optionalLink;

	//	// Derived attributes -----------------------------------------------------

	//	// Relationships ----------------------------------------------------------

	//	@NotNull
	//	@ManyToOne(optional = false)
	//	private project 			project;

	//	@NotNull
	//	@ManyToOne(optional = false)
	//	private auditor 			auditor;

}
