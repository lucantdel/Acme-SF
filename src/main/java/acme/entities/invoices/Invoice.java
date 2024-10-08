
package acme.entities.invoices;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.client.data.datatypes.Money;
import acme.entities.sponsorships.Sponsorship;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(indexes = {
	@Index(columnList = "sponsorship_id"), @Index(columnList = "code")
})
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

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				dueDate;

	@Valid
	@NotNull
	protected Money				quantity;

	@NotNull
	@Min(0)
	@Max(100)
	private double				tax;

	@URL
	@Length(max = 255)
	protected String			link;

	protected boolean			draftMode;

	// Derived attributes -----------------------------------------------------


	@Transient
	public Money totalAmount() {
		Double amount = this.quantity.getAmount() + this.quantity.getAmount() * (this.tax / 100);
		Money value = new Money();
		value.setAmount(amount);
		value.setCurrency(this.quantity.getCurrency());
		return value;
	}

	// Relationships ----------------------------------------------------------


	@NotNull
	@ManyToOne(optional = false)

	protected Sponsorship sponsorship;

}
