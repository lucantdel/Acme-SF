
package acme.entities.notices;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
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
public class Notice extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				instantiationMoment;

	@NotBlank
	@Length(max = 75)
	protected String			title;

	@NotBlank
	@Length(max = 100)
	protected String			message;

	@Email
	protected String			email;

	@URL
	protected String			link;

	// Derived attributes -----------------------------------------------------

	// uso de username, name and surname que despues podremos hacer cuando lo demos en los services

	@NotBlank
	@Length(max = 75)
	@Pattern(regexp = "^\\w+ - \\w+, \\w+$")
	@Transient
	protected String			author;

	// Relationships ----------------------------------------------------------

	//	// Constructor ----------------------------------------------------------
	// He pensado en hacer un constructor para aplicar la regla de negocio de author mientras no tenemos service
	//	public Notice(@Past final Date instantiationMoment, @NotBlank final String title, final String username, final String surname, final String name, @NotBlank final String message) {
	//		this.instantiationMoment = instantiationMoment;
	//		this.title = title;
	//		this.author = username + " - " + surname + ", " + name;
	//		this.message = message;
	//	}

}
