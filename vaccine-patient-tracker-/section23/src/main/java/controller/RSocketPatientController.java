package controller;

import model.Claim;
import model.ClinicalData;
import model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;


@Controller
public class RSocketPatientController {
    Logger logger = LoggerFactory.getLogger(RSocketPatientController.class);

    // RSocket REQUEST/RESPONSE protocol >>
    // request/response - will return "Mono" of "ClinicalData" type
    @MessageMapping("get-patient-data") // gets bound to this particular patient data
    public Mono<ClinicalData> requestResponse(@RequestBody Patient patient) {

        // now that we have "logger" method, we will implement that here to get some info
        logger.info("Patient Received: "+patient);
        return Mono.just(new ClinicalData(90, "80/120"));
    }

    // RSocket FIRE/FORGET protocol >>
    @MessageMapping("/patient-checkout")
    public Mono<Void> fireAndForget(Patient patient) {
        logger.info("Patient Checking out: "+patient);
        logger.info("Billing Initiated..");
        return Mono.empty().then();
        // Since by using Fire/Forget we don't expect any response, here returning an empty "Mono"
    }

    // RSocket REQUEST-STREAM protocol >>
    // This method will return a FLux of Claim information
    @MessageMapping("/claim-stream")
    public Flux<Claim> requestStream() {
        return Flux.just(
                new Claim(100f, "x-ray"),
                new Claim(1000f, "MRI-Scan"),
                new Claim(200f, "General Checkup")
            ).delayElements(Duration.ofSeconds(2));
        // Once the above defined route is hit,this method will get called and
        // this request stream will stream back all the claims

        // By adding "delayElements", we're essentially delaying the duration of each response
        // by the defined time (here 2 seconds)
    }
}
