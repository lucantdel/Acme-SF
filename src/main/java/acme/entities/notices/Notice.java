
package acme.entities.notices;

import java.util.Date;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Notice extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Past
	@NotNull
	private Date				instantiationMoment;

	@NotBlank
	@Size(max = 75)
	private String				title;

	@NotBlank
	@Size(max = 75)
	@Pattern(regexp = "^\\w+ - \\w+, \\w+$")
	private String				author;

	@NotBlank
	@Size(max = 100)
	private String				message;

	@Email
	private String				emailAddress;

	@URL
	private String				link;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

	//	// Constructor ----------------------------------------------------------
	//	public Notice(@Past final Date instantiationMoment, @NotBlank final String title, final String username, final String surname, final String name, @NotBlank final String message) {
	//		this.instantiationMoment = instantiationMoment;
	//		this.title = title;
	//		this.author = username + " - " + surname + ", " + name;
	//		this.message = message;
	//	}

}
