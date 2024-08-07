
package acme.entities.trainingSession;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.entities.trainingModule.TrainingModule;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(indexes = {
	@Index(columnList = "training_module_id")
})
public class TrainingSession extends AbstractEntity {
	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Pattern(regexp = "TS-[A-Z]{1,3}-[0-9]{3}")
	@NotBlank
	@Column(unique = true)
	protected String			code;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	protected Date				startPeriod;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	protected Date				endPeriod;

	@NotBlank
	@Length(max = 75)
	protected String			location;

	@NotBlank
	@Length(max = 75)
	protected String			instructor;

	@NotBlank
	@Email
	protected String			contactEmail;

	@URL
	protected String			link;

	@NotNull
	protected boolean			draftMode;

	// Derived attributes ----------------------------------------------------

	// Relationships ----------------------------------------------------------

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected TrainingModule	trainingModule;

}
