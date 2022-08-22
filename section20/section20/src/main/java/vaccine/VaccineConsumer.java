package vaccine;

import java.util.function.Consumer;

public class VaccineConsumer implements Consumer<Vaccine> {

    @Override
    public void accept(Vaccine vaccine) {
        System.out.println(vaccine.getName());
        System.out.println(vaccine.isDelivered());
    }
}
