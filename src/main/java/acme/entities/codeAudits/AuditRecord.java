
package acme.entities.codeAudits;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.roles.Auditor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AuditRecord extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	//	// Attributes -------------------------------------------------------------

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "AU-[0-9]{4}-[0-9]{3}")
	protected String			codeAR;

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	protected Date				startDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	protected Date				finishDate;

	@Pattern(regexp = "A\\+|A|B|C|F|F-")
	protected String			score;

	@URL
	@Length(max = 255)
	protected String			Link;

	protected boolean			published;


	//	// Derived attributes -----------------------------------------------------
	public boolean validatePeriod() {
		boolean res = true;
		if (this.startDate != null && this.finishDate != null) {
			long diferenciaEnMilisegundos = Math.abs(this.finishDate.getTime() - this.startDate.getTime());
			long diferenciaEnHoras = diferenciaEnMilisegundos / (1000 * 60 * 60);
			if (diferenciaEnHoras < 1)
				res = false;
		}
		return res;
	}

	//	// Relationships -----------------------------------------------------


	@ManyToOne
	protected CodeAudit	codeAudit;

	@NotNull
	@ManyToOne(optional = false)
	protected Auditor	auditor;

}
