
package acme.features.manager.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.features.manager.userstory.ManagerUserStoryRepository;
import acme.roles.Manager;

@Service
public class ManagerProjectUpdateService extends AbstractService<Manager, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerUserStoryRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		// TODO Auto-generated method stub
		super.authorise();
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		super.load();
	}

	@Override
	public void bind(final Project object) {
		// TODO Auto-generated method stub
		super.bind(object);
	}

	@Override
	public void validate(final Project object) {
		// TODO Auto-generated method stub
		super.validate(object);
	}

	@Override
	public void perform(final Project object) {
		// TODO Auto-generated method stub
		super.perform(object);
	}

	@Override
	public void unbind(final Project object) {
		// TODO Auto-generated method stub
		super.unbind(object);
	}

}
