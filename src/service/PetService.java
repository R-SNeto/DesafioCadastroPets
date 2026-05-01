package service;

import entities.Pet;

import java.util.ArrayList;
import java.util.List;

public class PetService {

    List<Pet> petList = new ArrayList<>();

    public void addPetList(Pet pet){
        petList.add(pet);
    }

    public void removePetList(Pet pet){
        petList.remove(pet);
    }

    public boolean hasSurname(String name){
        if(!name.contains(" ")){
            return false;
        } else{
            return true;
        }
    }
}
