package repositories;

import entities.Form;
import entities.Pet;
import entities_enum.PetType;

import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class FileHandler {
    File folder = new File("PetData");
    File file = new File("formulario.txt");

    public void writeFormFile() {
        if (!file.exists()) {
            String[] questions = {"1 - What's the pet name?",
                    "2 - What's the pet type (Dog/Cat)?",
                    "3 - What's the pet gender?",
                    "4 - What's the address where he was found?",
                    "5 - What's the pet age (years)?",
                    "6 - What's pet weight (kg)?",
                    "7 - What's pet race?"};

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                boolean newFile = file.createNewFile();
                if (newFile) {
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

    public void readFormFile(Form questions) {
        if (!file.exists()) {
            writeFormFile();
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                questions.addQuestion(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createPetDataFolder() {
        folder.mkdir();
    }

    public void writePetDataFile(Pet pet) {
        if (!folder.exists()) {
            createPetDataFolder();
        }
        LocalDateTime creationDate = LocalDateTime.now()
                .atZone(ZoneId.systemDefault()).toLocalDateTime();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter tf = DateTimeFormatter.ofPattern("HHmm");

        String[] cutName = pet.getName().toUpperCase().split("\\s+");
        StringBuilder formattedPetName = new StringBuilder();

        for (String p : cutName) {
            formattedPetName.append(p);
        }

        String fileName = creationDate.format(df)
                + "T"
                + creationDate.format(tf) + "-"
                + formattedPetName + ".txt";

        File fileInDirectory = new File(folder, fileName);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileInDirectory))) {
            bw.write(String.valueOf(pet));
            bw.newLine();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void readPetDataFile() {
        File[] files = folder.listFiles();

        if (files != null) {
            for (File f : files) {
                File filesData = new File(folder, f.getName());
                try (BufferedReader br = new BufferedReader(new FileReader(filesData))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        System.out.println(line);
                    }
                    System.out.println("------------------------------");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            throw new RuntimeException("Unfounded file");
        }
    }

    public void readPetDataByCriteria(int option, String[] criteria) {
        File[] files = folder.listFiles();

        String C1 = criteria[0];
        String crit1 = C1.toUpperCase().trim();
        String C2 = "";
        String crit2 = "";
        String petType = String.valueOf(PetType.valueOf(option));
        String path;

        if (criteria.length > 1) {
            C2 = criteria[1];
            crit2 = C2.toUpperCase().trim();
        }
        if (files != null) {
            for (File f : files) {
                File filesData = new File(folder, f.getName());
                try (BufferedReader br = new BufferedReader(new FileReader(filesData))) {
                    String line;
                    while ((line = br.readLine()) != null){
                        if(line.toUpperCase().trim().contains(petType)) {
                            if (!C2.isBlank()) {
                                if (line.toUpperCase().trim().contains(crit1) && line.toUpperCase().trim().contains(crit2)) {
                                    path = f.getPath();
                                } else {
                                    throw new RuntimeException("Characteristics not found");
                                }
                            } else {
                                if (line.toUpperCase().trim().contains(crit1)) {
                                    path = f.getPath();
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            throw new RuntimeException("File not found");
        }
    }

    public void readPetDataByCriteriaExtension () {

    }
}
