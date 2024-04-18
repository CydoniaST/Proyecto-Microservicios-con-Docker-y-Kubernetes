package es.codeurjc.eolopark.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * This service simulate the report creation
 *
 * To simulate a long computational time, Thread.sleep(...) is used.
 *
 * When a report is updated, reportUpdatesService is used to save the update on database and to notify users
 */
@Service
public class ReportGenerator {

    Logger logger = LoggerFactory.getLogger(ReportGenerator.class);

    @Autowired
    private EoloParkUpdatesService eoloParkUpdatesService;

    @Async
    protected void createReport(Long parkId, String reportCreationData) {

        logger.info("createReport: "+parkId);

        simulateProcessTime();
        eoloParkUpdatesService.eoloParkUpdated(parkId, 25, "No completado");

        simulateProcessTime();
        eoloParkUpdatesService.eoloParkUpdated(parkId, 50, "No completado");

        simulateProcessTime();
        eoloParkUpdatesService.eoloParkUpdated(parkId, 75, "No completado");

        simulateProcessTime();
        eoloParkUpdatesService.eoloParkUpdated(parkId, 100, "ABC_"+reportCreationData);

    }

    private void simulateProcessTime() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
