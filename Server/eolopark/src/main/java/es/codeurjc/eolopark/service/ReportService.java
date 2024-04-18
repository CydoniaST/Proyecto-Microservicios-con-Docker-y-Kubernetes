package es.codeurjc.eolopark.service;

import es.codeurjc.eolopark.model.Report;
import es.codeurjc.eolopark.model.User;
import es.codeurjc.eolopark.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ReportGenerator reportGenerator;

    public Report createReport(String city, double area, User user) {

        Report report = new Report();

        reportRepository.save(report);

        reportGenerator.createReport(report.getId(), city, area, user);

        return report;
    }
}