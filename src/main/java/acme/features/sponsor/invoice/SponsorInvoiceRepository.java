
package acme.features.sponsor.invoice;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.invoices.Invoice;
import acme.entities.sponsorships.Sponsorship;
import acme.entities.systemConfiguration.SystemConfiguration;
import acme.roles.Sponsor;

public interface SponsorInvoiceRepository extends AbstractRepository {

	@Query("select s from Sponsorship s where s.id = :sponsorshipId")
	Sponsorship findOneSponsorshipById(int sponsorshipId);

	@Query("select s from Sponsor s where s.id = :sponsorId")
	Sponsor findOneSponsorById(int sponsorId);

	@Query("select i from Invoice i where i.id = :invoiceId")
	Invoice findOneInvoiceById(int invoiceId);

	@Query("select i from Invoice i where i.code = :invoiceCode")
	Invoice findOneInvoiceByCode(String invoiceCode);

	@Query("select i from Invoice i where i.sponsorship.id = :sponsorshipId")
	Collection<Invoice> findAllInvoicesByMasterId(int sponsorshipId);

	@Query("select i.sponsorship from Invoice i where i.id = :invoiceId")
	Sponsorship findOneSponsorshipByInvoiceId(int invoiceId);

	@Query("select s from SystemConfiguration s")
	List<SystemConfiguration> findSystemConfiguration();

}
