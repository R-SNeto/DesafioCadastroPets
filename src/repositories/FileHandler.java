package repositories;

import entities.Form;

import java.io.*;

public class FileHandler {
    File file = new File("formulario.txt");

    public void writeFormFile(){
        if(!file.exists()) {
            String[] questions = {"1 - What's the pet name?",
                    "2 - What's the pet type (Dog/Cat)?",
                    "3 - What's the pet gender?",
                    "4 - What's the address where he was found?",
                    "5 - What's the pet age?",
                    "6 - What's pet weight?",
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


}
