
package acme.features.manager.userstory;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.projects.UserStory;
import acme.roles.Manager;

@Service
public class ManagerUserStoryDeleteService extends AbstractService<Manager, UserStory> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerUserStoryRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.authorise();
	}

	@Override
	public void load() {
		super.load();
	}

	@Override
	public void bind(final UserStory object) {
		super.bind(object);
	}

	@Override
	public void validate(final Collection<UserStory> objects) {
		super.validate(objects);
	}

	@Override
	public void perform(final UserStory object) {
		super.perform(object);
	}

	@Override
	public void unbind(final Collection<UserStory> objects) {
		super.unbind(objects);
	}

}
