
package acme.features.developers.trainingmodule;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.trainingModule.TrainingModule;
import acme.roles.Developer;

@Controller
public class DeveloperTrainingModuleController extends AbstractController<Developer, TrainingModule> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private DeveloperTrainingModuleListService		listService;

	@Autowired
	private DeveloperTrainingModuleShowService		showService;

	/*
	 * @Autowired
	 * private DeveloperTrainingModuleCreateService createService;
	 * 
	 * @Autowired
	 * private DeveloperTrainingModuleUpdateService updateService;
	 */
	@Autowired
	private DeveloperTrainingModuleDeleteService	deleteService;

	//@Autowired
	//private DeveloperTrainingModulePublishService	publishService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("delete", this.deleteService);
		/*
		 * super.addBasicCommand("create", this.createService);
		 * super.addBasicCommand("update", this.updateService);
		 * 
		 * 
		 * super.addCustomCommand("publish", "update", this.publishService);
		 */
	}

}
