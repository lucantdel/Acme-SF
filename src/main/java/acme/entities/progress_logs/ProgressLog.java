
package acme.entities.progress_logs;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.client.data.AbstractEntity;
import acme.entities.contracts.Contract;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ProgressLog extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@NotNull
	@Column(unique = true)
	@Pattern(regexp = "PG-[A-Z]{1,2}-[0-9]{4}", message = "Record id must match pattern PG-[A-Z]{1,2}-[0-9]{4}")
	@NotBlank
	private String				recordId;

	@NotNull
	@Min(1)
	private double				completenessPercentage;

	@NotNull
	@NotBlank
	@Length(min = 0, max = 100)
	private String				progressComment;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@Past
	private Date				registrationMoment;

	@NotNull
	@NotBlank
	@Length(min = 0, max = 75)
	private String				responsiblePerson;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Contract			contract;

}
