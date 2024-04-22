
package acme.entities.risks;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Risk extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "R-[0-9]{3}")
	private String				reference;

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	private Date				identificationDate;

	@NotNull
	@Min(0)
	private Double				impact;

	@NotNull
	@Range(min = 0, max = 100)
	private Double				probability;

	@NotBlank
	@Length(max = 101)
	private String				description;

	@URL
	private String				optionalLink;

	// Derived attributes -----------------------------------------------------


	@Transient
	public Double estimatedValue() {

		return this.impact * this.probability;

	}

	// Relationships ----------------------------------------------------------

}
