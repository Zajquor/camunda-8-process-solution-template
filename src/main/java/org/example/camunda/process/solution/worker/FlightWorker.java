package org.example.camunda.process.solution.worker;

import com.sap.conn.jco.JCoContext;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.ZeebeWorker;
import org.example.camunda.process.solution.service.FlightListBapi;
import org.hibersap.configuration.AnnotationConfiguration;
import org.hibersap.configuration.xml.SessionManagerConfig;
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

    @ZeebeWorker(type = "callSap")
    public void handleJobCallSAP(final JobClient client, final ActivatedJob job) {
//      Create and open SAP session
        sessionManager = createSessionManager();
        session = sessionManager.openSession();

//      create flighlist with wanted parameters
        FlightListBapi flightList = new FlightListBapi("DE", "Frankfurt",
                "DE", "Berlin",
                null, false, 10);

//      use SAP sesison, get and present data
        session.execute(flightList);
        flightList.showResult(flightList); // can be left out in prod
        session.close();

//      Handing the Data over to Camunda
        client.newCompleteCommand(job.getKey())
                .variables("{\"Airline\": \"" + flightList.getAirlineCarrier() + "\"}")
                .send()
                .exceptionally( throwable -> { throw new RuntimeException("Could not complete job " + job, throwable); });
    }

    public static SessionManager createSessionManager() {
        SessionManagerConfig cfg = new SessionManagerConfig("A12")
                .setProperty("jco.client.client", System.getenv("SAP_MANDATE"))
                .setProperty("jco.client.user", System.getenv("SAP_USR"))
                .setProperty("jco.client.passwd", System.getenv("SAP_PW"))
                .setProperty("jco.client.lang", "de")
                .setProperty("jco.client.ashost", System.getenv("SAP_HOST"))
                .setProperty("jco.client.sysnr", System.getenv("SAP_SYSNR"))
                .setProperty("jco.destination.pool_capacity", "5")
                .addAnnotatedClass(FlightListBapi.class);

        AnnotationConfiguration configuration = new AnnotationConfiguration(cfg);
        return configuration.buildSessionManager();
    }
}
