
package acme.features.sponsor.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface SponsorDashboardRepository extends AbstractRepository {

	// Número total de facturas con un impuesto menor o igual al 21.00%
	@Query("select count(i) from Invoice i where i.tax <= 21.0")
	int totalNumberOfInvoicesWithLowTax();
	// Número total de patrocinios con un enlace
	@Query("select count(sph) from Sponsorship sph where sph.link IS NOT NULL")
	int totalNumberOfSponsorshipsWithLink();

	// Promedio, desviación, mínimo y máximo del monto de los patrocinios
	@Query("select avg(sph.amount) from Sponsorship sph")
	double avgSponsorshipsAmount();

	@Query("select stddev(sph.amount) from Sponsorship sph")
	double devSponsorshipsAmount();

	@Query("select min(sph.amount) from Sponsorship sph")
	double minSponsorshipsAmount();

	@Query("select max(sph.amount) from Sponsorship sph")
	double maxSponsorshipsAmount();

	// Promedio, desviación, mínimo y máximo de la cantidad de facturas
	@Query("select avg(i.quantity) from Invoice i")
	double avgInvoicesQuantity();

	@Query("select stddev(i.quantity) from Invoice i")
	double devInvoicesQuantity();

	@Query("select min(i.quantity) from Invoice i")
	double minInvoicesQuantity();

	@Query("select max(i.quantity) from Invoice i")
	double maxInvoicesQuantity();

}
