package vaccine;


import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class VaccineService {

    // will return "Flux" of vaccine
    public Flux<Vaccine> getVaccines() {
        return Flux.just(new Vaccine("Pfizer"), new Vaccine("J&J"), new Vaccine("Covaxin"));
    }
}
