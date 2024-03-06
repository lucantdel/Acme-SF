
package acme.roles;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
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


	//Enumerado for type of client
	public enum Type {
		COMPANY, INDIVIDUAL
	}


	@Pattern(regexp = "CLI-[0-9]{4}", message = "Identification must match pattern CLI-[0-9]{4}")
	@NotBlank
	private String	identification;

	@NotBlank
	@Length(max = 75)
	private String	companyName;

	private Type	type;

	@Email
	@NotBlank
	private String	email;

	@URL
	private String	optionalLink;

}
