
package acme.features.any.claim;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.claim.Claim;

@Repository
public interface AnyClaimRepository extends AbstractRepository {

	@Query("select p from Claim p where p.id = :id")
	Claim findOneClaimById(int id);

	@Query("select p from Claim p where p.draftMode = false")
	Collection<Claim> findPublishedClaims();

}
