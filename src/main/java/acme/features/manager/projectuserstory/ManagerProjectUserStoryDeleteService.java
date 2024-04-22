
package acme.features.manager.projectuserstory;

import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.projects.ProjectUserStory;
import acme.roles.Manager;

@Service
public class ManagerProjectUserStoryDeleteService extends AbstractService<Manager, ProjectUserStory> {

}
