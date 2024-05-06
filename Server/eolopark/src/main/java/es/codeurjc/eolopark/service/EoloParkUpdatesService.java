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

    public void eoloParkUpdated(Long eoloParkId, double progress, boolean completed, EoloPark eoloPark) {

        Report report = reportRepository.findById(eoloParkId).orElseThrow();

        report.setProgress(progress);
        report.setCompleted(completed);
        report.setEoloPark(eoloPark);

        reportRepository.save(report);

        serverService.notifyReportUpdate(report);
    }

}
