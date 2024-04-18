package es.codeurjc.eolopark.service;

import es.codeurjc.eolopark.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
