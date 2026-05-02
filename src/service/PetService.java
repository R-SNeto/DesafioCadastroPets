package service;

import entities.Pet;

import java.util.ArrayList;
import java.util.List;

public class PetService {

    List<Pet> petList = new ArrayList<>();

    public void addPetList(Pet pet){
        if(pet != null) {
            petList.add(pet);
        }else{
            throw new RuntimeException("The system doesn't accept empty answers");
        }
    }

    public void removePetList(String name){
        petList.remove(name);
    }

    public boolean hasSurname(String name){
        if(!name.contains(" ")){
            return false;
        } else{
            return true;
        }
    }
}
