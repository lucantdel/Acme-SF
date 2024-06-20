
package acme.features.sponsor.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.invoices.Invoice;
import acme.entities.sponsorships.Sponsorship;

@Repository
public interface SponsorDashboardRepository extends AbstractRepository {

	// Sponsorships ops

	@Query("select count(sp) from Sponsorship sp where sp.sponsor.id = :sponsorId and sp.draftMode = false and (sp.link is not null)")
	int findSponsorshipsWithLink(int sponsorId);

	@Query("select sp.amount.currency, avg(sp.amount.amount) from Sponsorship sp where sp.sponsor.id = :sponsorId and sp.draftMode = false group by sp.amount.currency")
	Collection<Object[]> avgSponsorshipAmountGroupingByCurrency(int sponsorId);

	@Query("select sp.amount.currency, stddev(sp.amount.amount) from Sponsorship sp where sp.sponsor.id = :sponsorId and sp.draftMode = false group by sp.amount.currency")
	Collection<Object[]> devSponsorshipAmountGroupingByCurrency(int sponsorId);

	@Query("select sp.amount.currency, min(sp.amount.amount) from Sponsorship sp where sp.sponsor.id = :sponsorId and sp.draftMode = false group by sp.amount.currency")
	Collection<Object[]> minSponsorshipAmountGroupingByCurrency(int sponsorId);

	@Query("select sp.amount.currency, max(sp.amount.amount) from Sponsorship sp where sp.sponsor.id = :sponsorId and sp.draftMode = false group by sp.amount.currency")
	Collection<Object[]> maxSponsorshipAmountGroupingByCurrency(int sponsorId);

	@Query("select sp from Sponsorship sp where sp.sponsor.id = :sponsorId and sp.draftMode = false")
	Collection<Sponsorship> findAllPublishedSponsorshipsBySponsorId(int sponsorId);

	//Invoices ops

	@Query("select count(i) from Invoice i where i.sponsorship.sponsor.id = :sponsorId and i.draftMode = false and (i.tax <= 21)")
	int findInvoicesTax21(int sponsorId);

	@Query("select i.quantity.currency, avg(i.quantity.amount) from Invoice i where i.sponsorship.sponsor.id = :sponsorId and i.draftMode = false group by i.quantity.currency")
	Collection<Object[]> avgInvoiceQuantityGroupingByCurrency(int sponsorId);

	@Query("select i.quantity.currency, stddev(i.quantity.amount) from Invoice i where i.sponsorship.sponsor.id = :sponsorId and i.draftMode = false group by i.quantity.currency")
	Collection<Object[]> devInvoiceQuantityGroupingByCurrency(int sponsorId);

	@Query("select i.quantity.currency, min(i.quantity.amount) from Invoice i where i.sponsorship.sponsor.id = :sponsorId and i.draftMode = false group by i.quantity.currency")
	Collection<Object[]> minInvoiceQuantityGroupingByCurrency(int sponsorId);

	@Query("select i.quantity.currency, max(i.quantity.amount) from Invoice i where i.sponsorship.sponsor.id = :sponsorId and i.draftMode = false group by i.quantity.currency")
	Collection<Object[]> maxInvoiceQuantityGroupingByCurrency(int sponsorId);

	@Query("select i from Invoice i where i.sponsorship.sponsor.id = :sponsorId and i.draftMode = false")
	Collection<Invoice> findAllPublishedInvoicesBySponsorId(int sponsorId);

}
