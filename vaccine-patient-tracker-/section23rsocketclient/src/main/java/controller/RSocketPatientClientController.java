package controller;

import model.Claim;
import model.ClinicalData;
import model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class RSocketPatientClientController {

    // Defining the Logger
    Logger logger = LoggerFactory.getLogger(RSocketPatientClientController.class);

    private final RSocketRequester rSocketRequester;

    Patient patient;

    public RSocketPatientClientController(@Autowired RSocketRequester.Builder builder) {
        // using logger to log some info for reference
        logger.info("Sending the RSocket request for patient: "+patient);
        // using tcp method for the communication, configuring use @port7000 for this RSocket project
        this.rSocketRequester = builder.tcp("localhost", 7000);
    }

    // RSocket REQUEST/RESPONSE protocol - Client Side
    @GetMapping("/request-response")
    public Mono<ClinicalData> requestResponse(Patient patient) {
        return rSocketRequester
                .route("get-patient-data")
                .data(patient)
                .retrieveMono(ClinicalData.class);
    }

    // RSocket FIRE/FORGET protocol - Client Side (expecting a Mono of Void in return)
    @PostMapping("/fire-and-forget")
    public Mono<Void> fireAndForget(@RequestBody Patient patient) {
        logger.info("Patient Being Checked out: "+patient);
        return rSocketRequester
                .route("patient-checkout")
                .data(patient)
                .retrieveMono(Void.class);
    }

    // RSocket REQUEST-STREAM protocol - Client side (expecting FLux of Claims in return)
    @GetMapping("/request-stream")
    public ResponseEntity<Flux<Claim>> requestStream() {
        logger.info("Patient claims are being processed..");
        Flux<Claim> data = rSocketRequester.route("/claim-stream").retrieveFlux(Claim.class);
        return ResponseEntity.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(data);

        // Here we can just tap into all the claims at once and return them in some format (like JSON)
        // but since we're using Request Stream protocol, we can stream the response one at a time,
        // and we do that by adding "ResponseEntity". By defining the media type as "TEXT_EVENT_STREAM".
        // BY adding this media type, we will be streaming the data as it comes in
    }
}
