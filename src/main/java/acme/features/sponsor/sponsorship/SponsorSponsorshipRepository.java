
package acme.features.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.sponsorships.Sponsorship;

@Repository
public interface SponsorSponsorshipRepository extends AbstractRepository {

	@Query("select p from Sponsorship p where p.id = :id")
	Sponsorship findOneSponsorshipById(int id);

	@Query("select p from Sponsorship p where p.draftMode = false")
	Collection<Sponsorship> findPublishedSponsorships();

}
