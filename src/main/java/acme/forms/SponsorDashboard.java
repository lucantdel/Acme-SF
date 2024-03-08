
package acme.forms;

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
	protected int				totalNumberOfInvoicesWithLowTax;

	// Número total de patrocinios con un enlace
	protected int				totalNumberOfSponsorshipsWithLink;

	// Promedio, desviación, mínimo y máximo del monto de los patrocinios
	protected double			avgSponsorshipsAmount;
	protected double			devSponsorshipsAmount;
	protected double			minSponsorshipsAmount;
	protected double			maxSponsorshipsAmount;

	// Promedio, desviación, mínimo y máximo de la cantidad de facturas
	protected double			avgInvoicesQuantity;
	protected double			devInvoicesQuantity;
	protected double			minInvoicesQuantity;
	protected double			maxInvoicesQuantity;

}
