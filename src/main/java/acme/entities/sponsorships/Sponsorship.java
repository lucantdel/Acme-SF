
package acme.entities.sponsorships;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.entities.projects.Project;
import acme.roles.Sponsor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Sponsorship extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "[A-Z]{1,3}-[0-9]{3}")
	protected String			code;

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				moment;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				startDuration;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				finalDuration;

	@Min(0)
	protected double			amount;

	@NotNull
	protected SponsorshipType	type;

	@Email
	protected String			email;

	@URL
	protected String			link;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
	@NotNull
	@ManyToOne(optional = false)
	protected Project			project;


	//	finalmente será implemnetada como @NotNull @ManyTone hasta Sponsor ya que asi un sponsor puede hacer varios patrocinios y un sponsorship es ralizado por unicamente un sponsor
	// hacinedo asi una relacion 1..N a 1 desde sponsorship a sponsor
	@NotNull
	@ManyToOne(optional = false)
	protected Sponsor			sponsor;


}
