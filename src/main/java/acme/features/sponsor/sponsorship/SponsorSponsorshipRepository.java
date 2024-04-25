
package acme.features.sponsor.sponsorship;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.invoices.Invoice;
import acme.entities.projects.Project;
import acme.entities.sponsorships.Sponsorship;
import acme.entities.systemConfiguration.SystemConfiguration;
import acme.roles.Sponsor;

@Repository
public interface SponsorSponsorshipRepository extends AbstractRepository {

	@Query("select sph from Sponsorship sph where sph.id = :id")
	Sponsorship findOneSponsorshipById(int id);

	@Query("select sph from Sponsorship sph where sph.sponsor.id = :id")
	Collection<Sponsorship> findSponsorshipsBySponsorId(int id);

	@Query("select s from Sponsor s where s.id = :sponsorId")
	Sponsor findOneSponsorById(int sponsorId);

	@Query("select p from Project p where p.id = :projectId")
	Project findOneProjectById(int projectId);

	@Query("select s from Sponsorship s where s.code = :sponsorshipCode")
	Sponsorship findOneSponsorshipByCode(String sponsorshipCode);

	@Query("select i from Invoice i where i.sponsorship.id = :sponsorshipId")
	Collection<Invoice> findAllInvoicesBySponsorshipId(int sponsorshipId);

	@Query("select p from Project p")
	Collection<Project> findAllProjects();

	@Query("select s from SystemConfiguration s")
	List<SystemConfiguration> findSystemConfiguration();

}
