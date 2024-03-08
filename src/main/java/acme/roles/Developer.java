
package acme.roles;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Developer extends AbstractRole {
	/*
	 * here is a new project-specific role called developer, which has the following profile data:
	 * degree (not blank, shorter than 76 characters),
	 * a specialisation (not blank, shorter than 101 characters),
	 * list of skills (not blank, shorter than 101 characters),
	 * an email, and an optional link with further information
	 */
	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(max = 75)
	private String				degree;

	@NotBlank
	@Length(max = 100)
	private String				specialisation;

	@NotBlank
	@Length(max = 100)
	private String				skills;

	@NotBlank
	@Email
	private String				contactEmail;

	@URL
	private String				link;
}
