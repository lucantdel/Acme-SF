
package acme.features.client.contract;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.contract.Contract;
import acme.entities.projects.Project;
import acme.roles.Client;

@Repository
public interface ClientContractRepository extends AbstractRepository {

	@Query("select ct from Contract ct")
	Collection<Contract> getAllContract();

	@Query("select ct from Contract ct where ct.id = :id")
	Contract getContractById(int id);

	@Query("select cl from Client cl where cl.id = :id")
	Client getClientById(int id);

	@Query("select pr from Project pr where pr.id = :id")
	Project getProjectById(int id);

	@Query("select p from Project p where p.draftMode = false")
	Collection<Project> getPublishedProjects();

}
