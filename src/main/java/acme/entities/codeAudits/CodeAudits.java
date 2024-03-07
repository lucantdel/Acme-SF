
package acme.entities.codeAudits;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CodeAudits extends AbstractEntity {

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

	@NotNull
	@NotBlank
	protected String			mark;

	@URL
	protected String			optionalLink;

	//	// Derived attributes -----------------------------------------------------


	@NotNull
	@NotBlank
	public static String Mark(final List<String> marks) {
		Map<String, Long> scoreFrequency = marks.stream().collect(Collectors.groupingBy(String::toString, Collectors.counting()));

		long maxFrequency = 0;
		String mark = null;
		List<String> ls = new ArrayList<>();

		for (Map.Entry<String, Long> entry : scoreFrequency.entrySet())
			if (entry.getValue() > maxFrequency) {
				maxFrequency = entry.getValue();
				mark = entry.getKey();
				ls.add(mark);
			}
		if (ls.size() > 1)
			Collections.shuffle(ls);
		return ls.get(0);
	}

	//	// Relationships ----------------------------------------------------------

	//	@NotNull
	//	@ManyToOne(optional = false)
	//	protected project 			project;

	//	@NotNull
	//	@ManyToOne(optional = false)
	//	protected auditor 			auditor;

}
