
package acme.features.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.sponsorships.Sponsorship;

@Repository
public interface SponsorSponsorshipRepository extends AbstractRepository {

	@Query("select sph from Sponsorship sph where sph.id = :id")
	Sponsorship findOneSponsorshipById(int id);

	@Query("select sph from Sponsorship sph where sph.sponsor.id = :id")
	Collection<Sponsorship> findSponsorshipsBySponsorId(int id);

	//	@Query("select sph from Sponsorship sph where sph.draftMode = false")
	//	Collection<Sponsorship> findPublishedSponsorships();

}
