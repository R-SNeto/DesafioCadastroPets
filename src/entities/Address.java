package entities;

public class Address {
    private String state;
    private String city;
    private String neighborhood;

    public Address() {
    }

    public Address(String state, String city, String neighborhood) {
        this.state = state;
        this.city = city;
        this.neighborhood = neighborhood;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String toString(StringBuilder sb) {
        sb.append("Address where the pet was found: ").append("\n");
        sb.append("I) State: ").append(getState()).append("\n");
        sb.append("II) City: ").append(getCity()).append("\n");
        sb.append("III Neighborhood: ").append(getNeighborhood()).append("\n");

        return sb.toString();
    }
}
