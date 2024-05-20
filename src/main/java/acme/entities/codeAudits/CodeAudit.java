
package acme.entities.codeAudits;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.entities.projects.Project;
import acme.roles.Auditor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CodeAudit extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	//	// Attributes -------------------------------------------------------------

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}-[0-9]{3}")
	protected String			code;

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	protected Date				execution;

	@NotNull
	protected CodeAuditsStatus	type;

	@NotBlank
	@Length(max = 100)
	protected String			correctiveActions;

	@URL
	protected String			optionalLink;

	protected boolean			published;

	//	// Derived attributes -----------------------------------------------------


	@Transient
	public String Mark(final List<String> lista) {
		Map<String, Integer> frecuencia = new HashMap<>();

		for (String str : lista)
			frecuencia.put(str, frecuencia.getOrDefault(str, 0) + 1);

		String moda = null;
		int maxFrecuencia = 0;
		for (Map.Entry<String, Integer> entry : frecuencia.entrySet())
			if (entry.getValue() > maxFrecuencia) {
				moda = entry.getKey();
				maxFrecuencia = entry.getValue();
			}

		return moda;
	}

	//	// Relationships ----------------------------------------------------------


	@NotNull
	@ManyToOne(optional = false)
	protected Project	project;

	@NotNull
	@ManyToOne(optional = false)
	protected Auditor	auditor;

}
