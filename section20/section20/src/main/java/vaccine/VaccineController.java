package vaccine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;


@RestController
public class VaccineController {

    @Autowired
    private VaccineService service;

    // will return a Flux of vaccines, whenever they are ready
    @GetMapping("/vaccines")
    public Flux<Vaccine> getVaccines() {
        return service.getVaccines();
    }

    // Note: we know how important it is to .subscribe() in Flux, but when we are using "Web FLux".
    //       We don't need to do that Spring does that internally, we can just specify the return as FLux
    //       and Spring will return Flux w/o us having to subscribe (gets takes care by "web flux").

}
