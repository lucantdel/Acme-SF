
package acme.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import acme.entities.banner.Banner;

@ControllerAdvice
public class BannerAdvisor {

	@Autowired
	protected RandomBannerRepository repo;


	@ModelAttribute("banner")
	public Banner getRandomBanner() {
		Banner result;
		try {
			result = this.repo.getActiveRandomBanner();
		} catch (final Throwable oops) {
			result = null;
		}
		return result;
	}

}
