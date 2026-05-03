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

    public boolean validateName(String word){
        if (!word.isBlank()) {
            if (isEspecialCharacter(word) || !hasSurname(word)) {
                return false;
            }else{
                return true;
            }
        } else {
            return true;
        }
    }

    public boolean validateRace (String word){
        if(isEspecialCharacter(word) || isNumber(word)){
            return false;
        }
        return true;
    }

    public boolean hasSurname(String name){
        if(name.contains(" ")){
            return true;
        } else{
            return false;
        }
    }

    public boolean isCorrectAge(int age){
        if(age > 20 || age < 0){
            return false;
        }
        return true;
    }

    public boolean isCorrectWeight(double weight){
        if(weight > 60 || weight < 0.5){
            return false;
        }
        return true;
    }

    public boolean isEspecialCharacter(String word){
        for(int i = 0; i < word.length(); i++){
            char c = word.charAt(i);
            int ascii = (int) c;

            if ((ascii >= 33 && ascii <= 47) || (ascii >= 58 && ascii <= 64) ||
                    (ascii >= 91 && ascii <= 96) || (ascii >= 123 && ascii <= 127) ||
                    ascii == 145 || ascii == 146 || (ascii >= 155 && ascii <= 159) ||
                    (ascii >= 166 && ascii <= 180) || (ascii >= 184 && ascii <= 197) ||
                    (ascii >= 200 && ascii <= 209) || (ascii >= 217 && ascii <= 221) ||
                    ascii == 223 || ascii == 225 || (ascii >= 230 && ascii <= 232) ||
                    ascii >= 236){

                return true;
            }
        }
        return false;
    }

    public boolean isNumber(String word){
        for(int i = 0; i < word.length(); i++){
            char c = word.charAt(i);
            int ascii = (int) c;

            if (ascii >= 48 && ascii <= 57) return true;
        }
        return false;
    }
}
