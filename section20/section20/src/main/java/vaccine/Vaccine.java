package vaccine;

public class Vaccine {

    // overriding the "equals" method 'coz when we test two vaccines later on
    // it does not try to compare them using Object reference
    @Override
    public boolean equals(Object obj) {
        Vaccine vaccine = null;
        if (obj instanceof Vaccine) {
            vaccine = (Vaccine)obj;
        }
        return this.name.equals(vaccine.name);
    }
    // here it will basically check if the names of the two vaccines are equal
    // then those two objects are equal

    private String name;
    private boolean delivered;

    // Vaccine Constructor
    public Vaccine(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }
}
