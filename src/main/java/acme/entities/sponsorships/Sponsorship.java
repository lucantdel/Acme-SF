
package acme.entities.sponsorships;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
	protected TypeOfSponsorship	type;

	@Email
	protected String			contactEmail;

	@URL
	protected String			link;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
	@NotNull
	@ManyToOne(optional = false)
	protected Project			project;

	// un mismo patrocinio es realizado por su respectivo y unico patrocinador que lo realiza,
	// pero un patrocinador puede hacer varios patrocinios
	//  (Podría implementarla como @ManyToOne desde Sponsor, aunque esto contemplaría que un mismo patrocinio pueda ser realizados por varios patrocinadores)
	// luego debe ser @OneToOne

	@NotNull
	@OneToOne(optional = false)
	//	protected Sponsor		sponsor;
	protected String			sponsor;

}
