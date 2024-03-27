
package acme.features.any.contract;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Any;
import acme.entities.contracts.Contract;

@Controller
public class AnyContractController extends AbstractController<Any, Contract> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyContractListService	listService;

	@Autowired
	private AnyContractShowService	showService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
	}
}
