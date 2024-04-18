package es.codeurjc.eolopark.service;

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

    public void eoloParkUpdated(Long eoloParkId, int progress, String reportData) {

        Report report = reportRepository.findById(eoloParkId).orElseThrow();

        report.setProgress(progress);
        report.setReportData(reportData);

        reportRepository.save(report);

        serverService.notifyReportUpdate(report);
    }

}
