package menu;

import entities.Address;
import entities.Form;
import entities.Pet;
import entities_enum.Gender;
import entities_enum.PetType;
import repositories.FileHandler;
import service.PetInputProcessor;
import service.PetService;

import java.util.Scanner;

public class MainMenu {
    public static FileHandler fh = new FileHandler();
    public static Form questions = new Form();
    public static PetService ps = new PetService();
    public static PetInputProcessor pis = new PetInputProcessor();

    public static final String NOT_INFORMED = "NOT INFORMED";

    public void start(Scanner scanner) {
        fh.readFormFile(questions);
        menu(scanner);
    }

    public void menu(Scanner scanner) {
        boolean activeSystem = true;

        while (activeSystem) {
            try {
                System.out.println("\nVETERINARY SYSTEM MANAGEMENT");
                System.out.println("------------------------------");
                System.out.println("[1] Register a new pet");
                System.out.println("[2] List pets");
                System.out.println("[3] Change registered pet data");
                System.out.println("[4] Delete registered pet");
                System.out.println("[5] Exit");
                System.out.println("------------------------------");
                System.out.print("Choose an option: ");
                int option = Integer.parseInt(scanner.nextLine());

                switch (option) {
                    case 1:
                        registerNewPet(scanner);
                        break;
                    case 2:
                        System.out.println("------------------------------");
                        System.out.println("[1] List all pets");
                        System.out.println("[2] List pets by criteria");
                        System.out.println("------------------------------");
                        System.out.print("Choose: ");

                        switch (Integer.parseInt(scanner.nextLine())) {
                            case 1:
                                listAllRegisteredPets(scanner);
                                break;
                            case 2:
                                listByCriteria(scanner);
                                break;
                            default:
                                throw new IllegalArgumentException("Insert a valid option");
                        }
                        break;
                    case 3:
                        changePetData(scanner);
                        break;
                    case 4:
                        deletePetData(scanner);
                        break;
                    case 5:
                        System.out.println("Leaving...");
                        activeSystem = false;
                        break;
                    default:
                        throw new IllegalArgumentException("Insert a valid option");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nError: insert a number\n");
            } catch (RuntimeException e) {
                System.out.println("\nError: " + e.getMessage() + "\n");
            }
        }
    }

    public void registerNewPet(Scanner scanner) {
        boolean activeSystem = true;

        while (activeSystem) {
            try {
                System.out.println("\n       REGISTER A PET       ");
                System.out.println("------------------------------");

                String ask;

                String name = "";
                int type = 1;
                int gender = 1;
                String state = "";
                String city = "";
                String neighborhood = "";
                String age = "";
                String weight = "";
                String race = "";

                for (int i = 0; i < questions.getQuestionListSize(); i++) {
                    ask = questions.getQuestion(i).trim().toLowerCase();
                    System.out.println(questions.getQuestion(i));

                    if (ask.contains("type")) {
                        System.out.println("---------");
                        System.out.println("[1] DOG");
                        System.out.println("[2] CAT");
                        System.out.println("---------");
                        System.out.print("Choose: ");
                        type = Integer.parseInt(scanner.nextLine());
                        System.out.println();
                    } else if (ask.contains("gender")) {
                        System.out.println("---------");
                        System.out.println("[1] FEMALE");
                        System.out.println("[2] MALE");
                        System.out.println("---------");
                        System.out.print("Choose: ");
                        gender = Integer.parseInt(scanner.nextLine());
                        System.out.println();
                    } else if (ask.contains("address")) {
                        System.out.print("I) State: ");
                        state = pis.processString(scanner.nextLine());
                        System.out.print("II) City: ");
                        city = pis.processString(scanner.nextLine());
                        System.out.print("III) Neighborhood: ");
                        neighborhood = pis.processString(scanner.nextLine());
                        System.out.println();
                    } else {
                        String answer = scanner.nextLine();

                        if (ask.contains("name")) {
                            name = pis.processName(answer);
                        }
                        else if (ask.contains("age")) {
                            age = pis.processAge(answer);

                        }
                        else if (ask.contains("weight")) {
                            weight = pis.processWeight(answer);
                        }
                        else if (ask.contains("race")) {
                            race = pis.processRace(answer);
                        }
                    }
                }

                Address local = new Address(state, city, neighborhood);

                Pet pet = new Pet(name, PetType.valueOf(type),
                        Gender.valueOf(gender), local, age, weight,
                        race);

                ps.addPetList(pet);
                fh.writePetDataFile(pet);

                System.out.println("\nPET REGISTERED\n");
                activeSystem = false;

            } catch (NumberFormatException e) {
                System.out.println("Error: must be a valid number");
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public void listAllRegisteredPets(Scanner scanner){
        System.out.println("\n         PET DATA         ");
        System.out.println("------------------------------");
        fh.readPetDataFile();

    }

    public void listByCriteria(Scanner scanner){
        System.out.println("\n         PET DATA         ");
        System.out.println("------------------------------");

        boolean activeSystem = true;
        String[] criteria = new String[2];

        try {
            System.out.println("[1] DOG");
            System.out.println("[2] CAT");
            System.out.println("---------");
            System.out.print("Choose the pet type: ");
            int fOption = Integer.parseInt(scanner.nextLine());
            while (activeSystem) {
                System.out.println("\n------------------------------");
                System.out.println("          [1]     [2]           ");
                System.out.println("------------------------------");
                System.out.print("How many criteria? ");
                int sOption = Integer.parseInt(scanner.nextLine());
                for(int i = 0; i < sOption; i++) {
                    System.out.println("[1] Name ");
                    System.out.println("[2] Gender ");
                    System.out.println("[3] Age ");
                    System.out.println("[4] Race ");
                    System.out.println("------------------------------");
                    System.out.print("Choose the criteria: ");
                    int tOption = Integer.parseInt(scanner.nextLine());
                    switch (tOption) {
                        case 1:
                            System.out.print("Insert the pet name and/or the surname: ");
                            criteria[i] = scanner.nextLine();
                            System.out.println();
                            break;
                        case 2:
                            System.out.print("Insert the pet gender (Male / Female): ");
                            criteria[i] = scanner.nextLine().toUpperCase().trim();
                            System.out.println();
                            break;
                        case 3:
                            System.out.print("Insert the pet age: ");
                            criteria[i] = scanner.nextLine().trim();
                            System.out.println();
                            break;
                        case 4:
                            System.out.print("Insert the pet race: ");
                            criteria[i] = scanner.nextLine().trim();
                            System.out.println();
                            break;
                        default:
                            throw new NumberFormatException("Invalid option");
                    }
                }
                fh.readPetDataByCriteria(fOption, criteria);

                activeSystem = false;
            }
        }
        catch (NumberFormatException e){
            System.out.println("Error: insert a valid number");
        }
    }

    public void changePetData(Scanner scanner) {
        listByCriteria(scanner);
        boolean activeSystem = true;
        while (activeSystem) {
            try {
                System.out.println("------------------------------");
                System.out.print("Select the number of the pet to change data: ");
                int option = Integer.parseInt(scanner.nextLine());

                int newType = fh.getTypeByFile(option);
                int newGender = fh.getGenderByFile(option);

                System.out.println("------------------------------");
                System.out.print("1) New Pet Name: ");
                String newName = pis.processName(scanner.nextLine());
                System.out.println("2) New address where the pet was found: ");
                System.out.print("2.1) State: ");
                String newState = pis.processString(scanner.nextLine());
                System.out.print("2.2) City: ");
                String newCity = pis.processString(scanner.nextLine());
                System.out.print("2.3) Neighborhood: ");
                String newNeighborhood = pis.processString(scanner.nextLine());

                Address newAddress = new Address(newState, newCity, newNeighborhood);

                System.out.print("New pet age: ");
                String newAge = pis.processAge(scanner.nextLine());
                System.out.print("New weight: ");
                String newWeight = pis.processWeight(scanner.nextLine());
                System.out.print("New pet race: ");
                String newRace = pis.processRace(scanner.nextLine());

                Pet pet = new Pet(newName, PetType.valueOf(newType),
                        Gender.valueOf(newGender), newAddress, newAge,
                        newWeight, newRace);

                fh.writePetDataFile(pet);
                //PROCESSO DE SOBRE-ESCRITA FEITO. CONTINUAR APÓS CRIAR O MÓDULO DE DELEÇÃO DE ARQUIVOS

                activeSystem = false;

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e);
            }
        }
    }

    public void deletePetData (Scanner scanner) {
        listByCriteria(scanner);

        System.out.println("------------------------------");
        System.out.print("Select the number of the pet to delete data: ");
        int option = Integer.parseInt(scanner.nextLine());

        if (fh.deletePetData(option, scanner)) {
            System.out.println("\nPET SUCCESSFULLY REMOVED\n");
        } else {
            System.out.println("\nReturning to menu...\n");
        }
    }
}
