package entities;

import entities_enum.Gender;
import entities_enum.PetType;

public class Pet {
    private String name;
    private PetType petType;
    private Gender petGender;
    private Address address;
    private Integer age;
    private Double weight;
    private String race;

    public Pet() {
    }

    public Pet(String name, PetType petType, Gender petGender, Address address, Integer age, Double weight, String race) {
        this.name = name;
        this.petType = petType;
        this.petGender = petGender;
        this.address = address;
        this.age = age;
        this.weight = weight;
        this.race = race;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PetType getPetType() {
        return petType;
    }

    public void setPetType(PetType petType) {
        this.petType = petType;
    }

    public Gender getPetGender() {
        return petGender;
    }

    public void setPetGender(Gender petGender) {
        this.petGender = petGender;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Pet name: ").append(name).append("\n");
        sb.append("Pet type: ").append(petType).append("\n");
        sb.append("Pet gender: ").append(petGender).append("\n");
        address.toString(sb);
        sb.append("Pet age: ").append(age).append(" years\n");
        sb.append("Pet weight").append(String.format("%.2f", weight)).append(" kg\n");
        sb.append("Pet race: ").append(race).append("\n");


        return sb.toString();
    }
}
