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

        if (files == null){
            throw new RuntimeException("File not found");
        }

        String C1 = criteria[0].toUpperCase().trim();
        String C2 = "";
        if (criteria[1] != null) {
            C2 = criteria[1].toUpperCase().trim();
        }
        String petType = String.valueOf(PetType.valueOf(option));

        for (File f : files) {
            boolean fileMatch = false;

            boolean findContains1 = false;
            boolean findContains2 = false;
            boolean findContains3 = false;

            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String line;

                while ((line = br.readLine()) != null) {
                    String formattedLine = line.toUpperCase().trim();
                    boolean contains1 = formattedLine.contains(petType);
                    boolean contains2 = formattedLine.contains(C1);
                    boolean contains3 = C2.isBlank() || formattedLine.contains(C2);

                    if (contains1) findContains1 = true;
                    if (contains2) findContains2 = true;
                    if (contains3) findContains3 = true;

                    if (findContains1 && findContains2 && findContains3) {
                        fileMatch = true;
                        break;
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (fileMatch) {
                try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                    String line;

                    while ((line = br.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void changePetData () {

    }
}
