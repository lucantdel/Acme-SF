
package acme.entities.contracts;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import acme.client.data.AbstractEntity;
import acme.entities.projects.Project;
import acme.roles.Client;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class Contract extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@NotNull
	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}-[0-9]{3}")
	private String				code;

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	private Date				instantiationMoment;
	@NotNull
	@NotBlank
	@Size(max = 75)
	@Column(name = "provider_name", length = 75)
	private String				providerName;
	@NotNull
	@NotBlank
	@Size(max = 75)
	@Column(name = "customer_name", length = 75)
	private String				customerName;
	@NotNull
	@NotBlank
	@Size(max = 100)
	private String				goals;
	@NotNull
	@Max(9999999999L)
	private double				budget;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Project				project;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Client				client;
}
