
package acme.forms;

import java.util.Map;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SponsorDashboard extends AbstractForm {
	// Serialisation identifier -----------------------------------------------

	private static final long		serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	// Número total de facturas con un impuesto menor o igual al 21.00%
	private Map<String, Integer>	totalNumberOfInvoicesWithLowTax;

	// Número total de patrocinios con un enlace
	private Map<String, Integer>	totalNumberOfSponsorshipsWithLink;

	// Promedio, desviación, mínimo y máximo del monto de los patrocinios
	private Double					avgSponsorshipsAmount;
	private Double					devSponsorshipsAmount;
	private Double					minSponsorshipsAmount;
	private Double					maxSponsorshipsAmount;

	// Promedio, desviación, mínimo y máximo de la cantidad de facturas
	private Double					avgInvoicesQuantity;
	private Double					devInvoicesQuantity;
	private Double					minInvoicesQuantity;
	private Double					maxInvoicesQuantity;

}
