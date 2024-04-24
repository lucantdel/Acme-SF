
package acme.entities.contract;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.client.data.AbstractEntity;
import acme.client.data.datatypes.Money;
import acme.entities.projects.Project;
import acme.roles.Client;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Contract extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	/*
	 * Attributes
	 */

	@NotNull
	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}-[0-9]{3}")
	private String				code;

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date				moment;

	@NotNull
	@NotBlank
	@Length(max = 75)
	@Column(name = "provider_name", length = 75)
	private String				provider;

	@NotNull
	@NotBlank
	@Length(max = 75)
	@Column(name = "customer_name", length = 75)
	private String				customer;

	@NotNull
	@NotBlank
	@Length(max = 100)
	private String				goals;

	@NotNull
	private Money				budget;

	private Boolean				draftMode;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Project				project;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Client				client;

}