
package acme.forms;

import java.util.Map;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SponsorDashboard extends AbstractForm {
	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	// Número total de facturas con un impuesto menor o igual al 21.00%
	int							totalNumberOfInvoicesWithLowTax;

	// Número total de patrocinios con un enlace
	int							totalNumberOfSponsorshipsWithLink;

	// Promedio, desviación, mínimo y máximo del monto de los patrocinios
	Map<String, Double>			avgSponsorshipsAmount;
	Map<String, Double>			devSponsorshipsAmount;
	Map<String, Double>			minSponsorshipsAmount;
	Map<String, Double>			maxSponsorshipsAmount;

	// Promedio, desviación, mínimo y máximo de la cantidad de facturas
	Map<String, Double>			avgInvoicesQuantity;
	Map<String, Double>			devInvoicesQuantity;
	Map<String, Double>			minInvoicesQuantity;
	Map<String, Double>			maxInvoicesQuantity;

	// Posible implementacion en el form: 
	// <acme:print value="${avgSponsorshipsAmount[defaultCurrency]}"/>
	//String[]					defaultCurrency;
}
