
package acme.entities.currency_system;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CurrencySystem extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@Pattern(regexp = "^[A-Z]{3}$")
	@NotNull
	protected String			systemCurrency;

	@Pattern(regexp = "^([A-Z]{3},)*[A-Z]{3}$")
	@NotNull
	protected String			aceptedCurrencies;

}
