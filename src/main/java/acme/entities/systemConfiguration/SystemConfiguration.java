
package acme.entities.systemConfiguration;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SystemConfiguration extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@Pattern(regexp = "^([A-Z]{3},)*[A-Z]{3}$")
	@NotNull
	public String				acceptedCurrencies;

	@Pattern(regexp = "^[A-Z]{3}$")
	@NotNull
	public String				systemCurrency;

}
