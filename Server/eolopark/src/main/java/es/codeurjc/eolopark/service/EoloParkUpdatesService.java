package es.codeurjc.eolopark.service;

import es.codeurjc.eolopark.model.EoloPark;
import es.codeurjc.eolopark.model.Report;
import es.codeurjc.eolopark.repository.ReportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EoloParkUpdatesService {

    Logger logger = LoggerFactory.getLogger(EoloParkUpdatesService.class);

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ServerService serverService;
    private EoloPark eoloPark;

    public void eoloParkUpdated(Long eoloParkId, int progress, String completed) {

        Report report = reportRepository.findById(eoloParkId).orElseThrow();

        report.setProgress(progress);
        report.setCompleted(completed);

        reportRepository.save(report);

        serverService.notifyReportUpdate(report);
    }

}
