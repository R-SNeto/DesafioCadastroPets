package menu;

import entities.Address;
import entities.Form;
import entities.Pet;
import entities_enum.Gender;
import entities_enum.PetType;
import repositories.FileHandler;
import service.PetService;

import java.util.Scanner;

public class MainMenu {
    static FileHandler fh = new FileHandler();
    static Form questions = new Form();
    static PetService ps = new PetService();

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
                System.out.println("[2] Change registered pet data");
                System.out.println("[3] Delete registered pet");
                System.out.println("[4] List all registered pets");
                System.out.println("[5] List pets by some criteria (age, name, race)");
                System.out.println("[6] Exit");
                System.out.println("------------------------------");
                System.out.print("Choose an option: ");
                int option = Integer.parseInt(scanner.nextLine());

                switch (option) {
                    case 1:
                        registerNewPet(scanner);
                        break;
                    case 2:
                        changePetData(scanner);
                        break;
                    case 3:
                        //deletePetData(scanner);
                        break;
                    case 4:
                        //listAllRegisteredPets(scanner);
                        break;
                    case 5:
                        //listByCriteria
                        break;
                    case 6:
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
                        state = scanner.nextLine();
                        if (state.isBlank()) state = NOT_INFORMED;
                        System.out.print("II) City: ");
                        city = scanner.nextLine();
                        if (city.isBlank()) city = NOT_INFORMED;
                        System.out.print("III) Neighborhood: ");
                        neighborhood = scanner.nextLine();
                        if (neighborhood.isBlank()) neighborhood = NOT_INFORMED;
                        System.out.println();
                    } else {
                        String answer = scanner.nextLine();

                        if (ask.contains("name")) {
                            if (answer.isBlank()){
                                name = NOT_INFORMED;
                            }else {
                                if (ps.validateName(answer)) {
                                    name = answer;
                                } else {
                                    throw new IllegalArgumentException("must have a surname or not be a especial character");
                                }
                            }
                        }
                        else if (ask.contains("age")) {
                            if (answer.isBlank()){
                                age = NOT_INFORMED;
                            }else {
                                int fAge = Integer.parseInt(answer);
                                if (ps.isCorrectAge(fAge)) {
                                    age = String.valueOf(fAge);
                                } else {
                                    throw new IllegalArgumentException("must be a valid age");
                                }
                            }

                        }
                        else if (ask.contains("weight")) {
                            if (answer.isBlank()){
                                weight = NOT_INFORMED;
                            }else {
                                double fWeight = Double.parseDouble(answer);
                                if (ps.isCorrectWeight(fWeight)) {
                                    weight = String.valueOf(fWeight);
                                } else {
                                    throw new IllegalArgumentException("must be a valid weight");
                                }
                            }
                        }
                        else if (ask.contains("race")) {
                            if (answer.isBlank()) {
                                race = NOT_INFORMED;
                            } else {
                                if (ps.validateRace(answer)) {
                                    race = answer;
                                } else {
                                    throw new IllegalArgumentException("must be a valid race");
                                }
                            }
                        }
                    }
                }

                Address local = new Address(state, city, neighborhood);

                Pet pet = new Pet(name, PetType.valueOf(type),
                        Gender.valueOf(gender), local, age, weight,
                        race);

                ps.addPetList(pet);
                System.out.println(pet);
                System.out.println("\nPET REGISTERED\n");
                activeSystem = false;

            } catch (NumberFormatException e) {
                System.out.println("Error: must be a number");
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public void changePetData(Scanner scanner) {
    }
}
