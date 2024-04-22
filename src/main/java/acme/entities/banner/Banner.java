
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

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	@NotNull
	private Date				updateMoment;

	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	@NotNull
	private Date				displayPeriodStart;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date				displayPeriodEnd;

	@URL
	@Length(max = 255)
	private String				picture;

	@NotBlank
	@Length(max = 75)
	private String				slogan;

	@URL
	@Length(max = 255)
	private String				webDoc;

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
