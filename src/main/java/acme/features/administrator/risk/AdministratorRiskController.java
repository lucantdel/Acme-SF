
package acme.features.administrator.risk;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Administrator;
import acme.entities.risks.Risk;

@Controller
public class AdministratorRiskController extends AbstractController<Administrator, Risk> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AdministratorRiskListService	listService;

	@Autowired
	private AdministratorRiskShowService	showService;

	@Autowired
	private AdministratorRiskDeleteService	deleteService;

	@Autowired
	private AdministratorRiskUpdateService	updateService;

	@Autowired
	private AdministratorRiskCreateService	createService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("create", this.createService);
	}
}
