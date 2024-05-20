
package acme.features.auditor.dashboard;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.forms.AuditorDashboard;
import acme.roles.Auditor;

@Service
public class AuditorDashboardShowService extends AbstractService<Auditor, AuditorDashboard> {

	@Autowired
	private AuditorDashboardRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}
	@Override
	public void load() {

		int auditorId;
		auditorId = super.getRequest().getPrincipal().getActiveRoleId();

		AuditorDashboard dashboard;
		int totalNumberOfCodeAuditsDynamic;
		int totalNumberOfCodeAuditsStatic;
		Double averageNumberOfAuditRecords;
		Integer minimunNumberOfAuditRecords;
		Integer maximunNumberOfAuditRecords;
		List<Integer> numberOfAuditRecords;
		Double deviationOfAuditRecords;
		Long maximumTimeOfThePeriodlength;
		Long avegageTimeOfThePeriodlength;
		Long minimunTimeOfThePeriodlength;
		Long deviationTimeOfThePeriodlength;

		numberOfAuditRecords = this.repository.numberOfAuditRecord(auditorId);
		maximunNumberOfAuditRecords = numberOfAuditRecords.stream().max(Comparator.comparingInt(Integer::intValue)).orElseThrow(null);
		minimunNumberOfAuditRecords = numberOfAuditRecords.stream().min(Comparator.comparingInt(Integer::intValue)).orElseThrow(null);
		averageNumberOfAuditRecords = numberOfAuditRecords.stream().mapToInt(Integer::intValue).average().orElse(0.0);
		deviationOfAuditRecords = AuditorDashboardShowService.calcularDesvioEstandar(numberOfAuditRecords);

		totalNumberOfCodeAuditsDynamic = this.repository.totalNumberOfCodeAuditsDynamic(auditorId);
		totalNumberOfCodeAuditsStatic = this.repository.totalNumberOfCodeAuditsStatic(auditorId);
		maximumTimeOfThePeriodlength = this.repository.maxPeriod(auditorId);
		avegageTimeOfThePeriodlength = this.repository.avgPeriod(auditorId);
		minimunTimeOfThePeriodlength = this.repository.minPeriod(auditorId);
		deviationTimeOfThePeriodlength = this.repository.stddevPeriod(auditorId);
		dashboard = new AuditorDashboard();
		dashboard.setTotalNumberOfCodeAuditsDynamic(totalNumberOfCodeAuditsDynamic);
		dashboard.setTotalNumberOfCodeAuditsStatic(totalNumberOfCodeAuditsStatic);
		dashboard.setAverageNumberOfAuditRecords(averageNumberOfAuditRecords);
		dashboard.setMaximunNumberOfAuditRecords(maximunNumberOfAuditRecords);
		dashboard.setMinimunNumberOfAuditRecords(minimunNumberOfAuditRecords);
		dashboard.setDeviationOfAuditRecords(deviationOfAuditRecords);
		dashboard.setMaximumTimeOfThePeriodlength(maximumTimeOfThePeriodlength / (1000 * 60));
		dashboard.setAvegageTimeOfThePeriodlength(avegageTimeOfThePeriodlength / (1000 * 60));
		dashboard.setMinimunTimeOfThePeriodlength(minimunTimeOfThePeriodlength / 1000);
		dashboard.setDeviationTimeOfThePeriodlength(deviationTimeOfThePeriodlength / (1000 * 60));

		super.getBuffer().addData(dashboard);

	}

	@Override
	public void unbind(final AuditorDashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, //
			"totalNumberOfCodeAuditsDynamic", "totalNumberOfCodeAuditsStatic",//
			"averageNumberOfAuditRecords", "maximunNumberOfAuditRecords", "minimunNumberOfAuditRecords", "deviationOfAuditRecords",//
			"maximumTimeOfThePeriodlength", "avegageTimeOfThePeriodlength", "minimunTimeOfThePeriodlength", "deviationTimeOfThePeriodlength");

		super.getResponse().addData(dataset);

	}

	public static double calcularDesvioEstandar(final List<Integer> recuentos) {

		double promedio = recuentos.stream().mapToInt(Integer::intValue).average().orElse(0.0);

		double sumaCuadradosDiferencias = recuentos.stream().mapToDouble(count -> Math.pow(count - promedio, 2)).sum();

		double desvioEstandar = Math.sqrt(sumaCuadradosDiferencias / recuentos.size());

		return desvioEstandar;
	}

}
