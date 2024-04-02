
package acme.features.administrator.systemConfiguration;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.data.accounts.Administrator;
import acme.client.repositories.AbstractRepository;
import acme.entities.systemConfiguration.SystemConfiguration;

@Repository
public interface AdministratorSystemConfigurationRepository extends AbstractRepository {

	@Query("select a from Administrator a where a.id = :administratorId")
	Administrator findOneAdministratorById(int administratorId);

	@Query("SELECT sc FROM SystemConfiguration sc")
	SystemConfiguration findActualSystemConfiguration();

}
