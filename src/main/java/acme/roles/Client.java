
package acme.roles;

import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Client extends AbstractRole {

	private static final long serialVersionUID = 1L;


	public enum Type {
		COMPANY, INDIVIDUAL
	}


	@NotNull
	@Pattern(regexp = "CLI-[0-9]{4}", message = "Identification must match pattern CLI-[0-9]{4}")
	@NotBlank
	private String	identification;

	@NotNull
	@NotBlank
	@Length(max = 75)
	private String	companyName;

	@NotNull
	@Valid
	private Type	type;

	@NotNull
	@Email
	@NotBlank
	private String	email;

	@URL
	private String	optionalLink;

}
