
package acme.forms;

import java.util.Map;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDashboard extends AbstractForm {

	protected static final long	serialVersionUID	= 1L;

	double						totalNumProgressLogLessThan25;

	double						totalNumProgressLogBetween25And50;

	double						totalNumProgressLogBetween50And75;

	double						totalNumProgressLogAbove75;

	Map<String, Double>			averagePerCurrency;

	Map<String, Double>			deviationPerCurrency;

	Map<String, Double>			maximumPerCurrency;

	Map<String, Double>			minimumPerCurrency;

	String[]					supportedCurrencies;

}
