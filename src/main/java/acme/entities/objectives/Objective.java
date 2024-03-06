
package acme.entities.objectives;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
public class Objective extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	private Date				instantiationMoment;

	@NotBlank
	@Length(max = 75)
	private String				title;

	@NotBlank
	@Length(max = 100)
	private String				description;


	public enum Priority {
		Low, Medium, High
	}


	@Enumerated(EnumType.STRING)
	private Priority	priority;

	private boolean		status;

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	private Date		startMoment;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@PastOrPresent
	private Date		endMoment;

	@URL
	private String		optionalLink;

}
