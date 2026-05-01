package service;

import entities.Pet;

import java.util.ArrayList;
import java.util.List;

public class PetService {

    List<Pet> petList = new ArrayList<>();

    public boolean hasSurname(String name){
        if(!name.contains(" ")){
            return false;
        } else{
            return true;
        }
    }
}
