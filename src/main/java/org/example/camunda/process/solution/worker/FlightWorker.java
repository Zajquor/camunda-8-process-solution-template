package org.example.camunda.process.solution.worker;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.ZeebeWorker;
import org.example.camunda.process.solution.service.FlightListBapi;
import org.hibersap.configuration.AnnotationConfiguration;
import org.hibersap.session.Session;
import org.hibersap.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FlightWorker {

    private static final Logger LOG = LoggerFactory.getLogger(FlightWorker.class);
    SessionManager sessionManager;
    Session session;

/*    public FlightWorker() {
        sessionManager = createSessionManager();
    }*/

    @ZeebeWorker(type = "callSap")
    public void handleJobCallSAP(final JobClient client, final ActivatedJob job) {
        LOG.info("Create Session.");
        sessionManager = createSessionManager();
        session = sessionManager.openSession();
        LOG.info("Session created. Try to get the FlightList.");
        FlightListBapi flightList = new FlightListBapi( "DE", "Frankfurt",
                "DE", "Berlin",
                null, false, 10 );
        try {
            session.execute( flightList );
            flightList.showResult( flightList );
        }
        finally {
            session.close();
        }
        LOG.info("Verbindung zu SAP aufgebaut.");
        System.out.println("Worker funktioniert und wurde ausgeführt.");
        client.newCompleteCommand(job.getKey())
//                .variables("{\"AirlineId\": 42}")
                .variables("Airline: " + flightList.getFromCountryKey())
                .send()
                .exceptionally( throwable -> { throw new RuntimeException("Could not complete job " + job, throwable); });
    }
    public static SessionManager createSessionManager() {
        AnnotationConfiguration configuration = new AnnotationConfiguration("A12");
        return configuration.buildSessionManager();
    }
}
