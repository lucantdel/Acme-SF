
package acme.features.sponsor.dashboard;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.data.datatypes.Money;
import acme.client.repositories.AbstractRepository;
import acme.entities.invoices.Invoice;
import acme.entities.sponsorships.Sponsorship;
import acme.entities.systemConfiguration.SystemConfiguration;
import acme.roles.Sponsor;

@Repository
public interface SponsorDashboardRepository extends AbstractRepository {

	@Query("select s from Sponsor s where s.id = :sponsorId")
	Sponsor findSponsorById(int sponsorId);

	@Query("select s from Sponsorship s where s.sponsor.id = :sponsorId")
	Collection<Sponsorship> findManySponsorshipsBySponsorId(int sponsorId);

	@Query("select i from Invoice i where i.sponsorship.sponsor.id = :sponsorId")
	Collection<Invoice> findManyInvoicesBySponsorId(int sponsorId);

	// cantidades de dinero por entidad
	@Query("select s.amount from Sponsorship s where s.sponsor.id = :sponsorId and s.draftMode = false")
	Collection<Money> findManyPublishedAmountsBySponsorId(int sponsorId);

	@Query("select i.quantity from Invoice i where i.sponsorship.sponsor.id = :sponsorId and i.draftMode = false")
	Collection<Money> findManyPublishedQuantitiesBySponsorId(int sponsorId);

	@Query("select s from SystemConfiguration s")
	List<SystemConfiguration> findSystemConfiguration();

}
