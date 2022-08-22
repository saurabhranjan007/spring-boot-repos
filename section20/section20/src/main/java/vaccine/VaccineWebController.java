package vaccine;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@Controller
public class VaccineWebController {

    @Autowired
    private VaccineService service;

    // if it was not a "Reactive Application", we could have returned a String template
    // since this is a Reactive one, we return "Mono<String>" - which returns a single thymeleaf template

    @GetMapping("/")
    public Mono<String> getVaccines(Model model) {

        model.addAllAttributes("vaccines: ",service.getVaccines());
        return Mono.just("index");
    }

}
