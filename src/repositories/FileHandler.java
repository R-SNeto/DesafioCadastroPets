package repositories;

import entities.Form;
import entities.Pet;

import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class FileHandler {
    File folder = new File("PetData");
    File file = new File("formulario.txt");

    public void writeFormFile(){
        if(!file.exists()) {
            String[] questions = {"1 - What's the pet name?",
                    "2 - What's the pet type (Dog/Cat)?",
                    "3 - What's the pet gender?",
                    "4 - What's the address where he was found?",
                    "5 - What's the pet age (years)?",
                    "6 - What's pet weight (kg)?",
                    "7 - What's pet race?"};

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                boolean newFile = file.createNewFile();
                if (newFile){
                    System.out.println("FILE CREATED SUCCESSFULLY");
                }
                for (String p : questions) {
                    bw.write(p);
                    bw.newLine();
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void readFormFile(Form questions){
        if(!file.exists()){
            writeFormFile();
        }
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;
            while((line = br.readLine()) != null) {
                questions.addQuestion(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createPetDataFolder(){
        folder.mkdir();
    }

    public void writePetDataFile(Pet pet){
        if(!folder.exists()){
            createPetDataFolder();
        }
        LocalDateTime creationDate = LocalDateTime.now()
                .atZone(ZoneId.systemDefault()).toLocalDateTime();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter tf = DateTimeFormatter.ofPattern("HHmm");

        String[] cutName = pet.getName().toUpperCase().split("\\s+");
        StringBuilder formattedPetName = new StringBuilder();

        for (String p: cutName){
            formattedPetName.append(p);
        }


        String fileName = creationDate.format(df)
                + "T"
                + creationDate.format(tf )+ "-"
                + formattedPetName + ".txt";

        System.out.println(fileName);

        File fileInDirectory = new File(folder, fileName);

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileInDirectory))){
            bw.write(String.valueOf(pet));
            bw.newLine();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
