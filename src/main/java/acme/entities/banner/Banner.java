
package acme.entities.banner;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Banner extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	@NotNull
	protected Date				updateMoment;

	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	@NotNull
	protected Date				displayPeriodStart;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	protected Date				displayPeriodEnd;

	@URL
	@NotNull
	@Length(max = 255)
	protected String			picture;

	@NotBlank
	@Length(max = 75)
	protected String			slogan;

	@URL
	@NotNull
	@Length(max = 255)
	protected String			webDoc;

	// Derived attributes -----------------------------------------------------


	public boolean validateDisplayPeriod() {
		boolean res = true;
		if (this.displayPeriodStart != null && this.displayPeriodEnd != null) {
			long differenceInDays = (this.displayPeriodEnd.getTime() - this.displayPeriodStart.getTime()) / 86400000;
			if (differenceInDays < 7)
				res = false;
		}
		return res;
	}

	// Relationships ----------------------------------------------------------

}
