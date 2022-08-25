package org.example.camunda.process.solution;

import io.camunda.zeebe.spring.client.EnableZeebeClient;
import io.camunda.zeebe.spring.client.annotation.ZeebeDeployment;
import org.hibersap.configuration.AnnotationConfiguration;
import org.hibersap.session.SessionManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableZeebeClient
//@ZeebeDeployment(resources = "classpath*:/models/*.*")
public class ProcessApplication {

  public static void main(String[] args) {
      SpringApplication.run(ProcessApplication.class, args);
  }


}
