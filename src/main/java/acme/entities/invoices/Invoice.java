
package acme.entities.invoices;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.Transient;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.entities.sponsorships.Sponsorship;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Invoice extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "IN-[0-9]{4}-[0-9]{4}")
	protected String			code;

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				registrationTime;

	// al menos un mes de antelación al registrationTime

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				dueDate;

	@NotNull
	@Min(0)
	protected double			quantity;

	@Min(0)
	protected double			taxApplied;

	@URL
	protected String			link;

	// Derived attributes -----------------------------------------------------


	@Min(0)
	@Transient
	public double totalAmount() {
		return this.quantity + this.taxApplied;
	}

	// Relationships ----------------------------------------------------------


	@NotNull
	@ManyToOne(optional = false)

	protected Sponsorship sponsorship;


}
