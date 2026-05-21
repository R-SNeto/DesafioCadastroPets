package repositories;

import entities.Form;
import entities.Pet;
import entities_enum.PetType;
import exceptions.PetNotFoundException;
import service.PetInputProcessor;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileHandler {
    private final File petDataFolder = new File("PetData");
    private final File deletedPetDataFolder = new File ("DeletedPetData");
    private final File file = new File("formulario.txt");

    private PetInputProcessor pis = new PetInputProcessor();
    private List<String> auxPathList;
    private List<String> auxFileNameList;

    public void addAuxPathList (String path) {
        auxPathList.add(path);
    }

    public void removeAuxPathList (String path) {
        auxPathList.remove(path);
    }

    public void addAuxFileNameList (String name) {
        auxFileNameList.add(name);
    }

    public void removeAuxFileNameList (String name) {
        auxFileNameList.remove(name);
    }

    public List<String> getAuxPathList() {
        return new ArrayList<>(auxPathList);
    }

    public List<String> getAuxFileNameList() {
        return new ArrayList<>(auxFileNameList);
    }

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
        petDataFolder.mkdir();
    }

    public void createDeletedPetDataFolder() {
        deletedPetDataFolder.mkdir();
    }

    public void writePetDataFile(Pet pet) {
        if (!petDataFolder.exists()) {
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

        File fileInDirectory = new File(petDataFolder, fileName);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileInDirectory))) {
            bw.write(String.valueOf(pet));
            bw.newLine();

        } catch (IOException | DateTimeException e) {
            throw new RuntimeException(e);
        }
    }

    public void readPetDataFile() {
        File[] files = petDataFolder.listFiles();

        if (files != null) {
            for (File f : files) {
                File filesData = new File(petDataFolder, f.getName());
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
            throw new RuntimeException("File not found");
        }
    }

    public void readPetDataByCriteria(int option, String[] criteria) {
        File[] files = petDataFolder.listFiles();
        auxPathList = new ArrayList<>();
        auxFileNameList = new ArrayList<>();

        if (files == null){
            throw new RuntimeException("File not found");
        }

        String C1 = criteria[0].toUpperCase().trim();
        String C2 = "";
        if (criteria[1] != null) {
            C2 = criteria[1].toUpperCase().trim();
        }
        String petType = String.valueOf(PetType.valueOf(option));

        int controlVariable = 0;

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
                        if (!auxPathList.contains(f.getAbsolutePath())) {
                            addAuxPathList(f.getAbsolutePath());
                            addAuxFileNameList(f.getName());
                        }
                        break;
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (fileMatch) {
                try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                    String line;

                    System.out.println("       PET #" + (controlVariable + 1));
                    System.out.println("-------------------");
                    while ((line = br.readLine()) != null) {
                        System.out.println(line);

                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                controlVariable++;
            }
        }
        if (controlVariable == 0) throw new PetNotFoundException("Pet not found");
    }

    public String getPathByList (int option) {
        return getAuxPathList().get(option - 1);
    }

    public String getFieldValue(int option, String fieldName) {
        try (BufferedReader br = new BufferedReader(new FileReader(getAuxPathList().get(option - 1)))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.toLowerCase().contains(fieldName.toLowerCase())) {
                    String[] fields = line.split(": ");
                        return fields[1];
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public int getTypeByFile(int option) {
       String type = getFieldValue(option, "type");

       if (type.equals("DOG")) return 1;
       else if (type.equals("CAT")) return 2;

       return 0;
    }

    public int getGenderByFile(int option)   {
        String type = getFieldValue(option, "gender");

        if (type.equals("FEMALE")) return 1;
        else if (type.equals("MALE")) return 2;

        return 0;
    }

    public boolean deletePetData(int option, Scanner scanner) {
        if (!deletedPetDataFolder.exists()) {
            createDeletedPetDataFolder();
        }

        String path = getPathByList(option);
        File destination = new File (deletedPetDataFolder, getAuxFileNameList().get(option - 1));
        String fileName = destination.getName();

        if (pis.processConfirmation(scanner)) {
            try {
                Path source = Path.of(path);

                Files.copy(source, destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
                Files.delete(source);

                removeAuxPathList(path);
                removeAuxFileNameList(fileName);

                return true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            return false;
        }
    }
}
