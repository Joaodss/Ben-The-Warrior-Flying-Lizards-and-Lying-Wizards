package com.ironhack.homework_1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Party {
    private final int MAX_STARTING_SIZE = 12;
    private List<Character> partyCharacters;
//    private String partyName;     // Not sure if you want to give the party a name

    public Party(List<Character> partyCharacters) {
        this.partyCharacters = partyCharacters;
    }

    public Party() {
        this.partyCharacters = new ArrayList<>();
    }

    // ========== GETTERS AND SETTERS ==========
    public List<Character> getPartyCharacters() {
        return partyCharacters;
    }

    public void setPartyCharacters(List<Character> partyCharacters) {
        this.partyCharacters = partyCharacters;
    }

    // ========== Party Creation ==========
    // Method to select the initial size of the party
    private int inputPartySize() {
        System.out.println("Input the size of the party:    ( maximum " + MAX_STARTING_SIZE + " Characters )");
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            try {
                int choice = Integer.parseInt(input);
                if (choice > 0 && choice < MAX_STARTING_SIZE) return choice;
                System.out.println("Please input a valid choice! Maximum " + MAX_STARTING_SIZE + ".");
            } catch (NumberFormatException e) {
                System.out.println("Please input a valid number! Maximum " + MAX_STARTING_SIZE + ".");
            }
        }
    }

    // TODO(JA) Needs to be tested and implemented - waiting for merge to test.
    // Generates random party (party still needs to be previously initialized)
    public void generateRandomParty(int partySize) {
        partyCharacters.add(Character.getRandom());
    }


    // TODO(JA) Needs to be tested and implemented - waiting for merge to test.
    // Generates custom party (party still needs to be previously initialized)
    public void generateCustomParty(int partySize) {
        for (int i = 0; i < partySize; i++) {
            partyCharacters.add(Character.createCustom());

            //optional, used to stop creation method
            int nextChoice = inputContinueCreating();
            if (nextChoice == -1) return;       // exits
            if (nextChoice == 1) {              // create remaining party size as random. and exits
                generateRandomParty(partySize - i - 1);
                return;
            }

        }
    }

    // Checks if the user wants to continue custom creation
    private static int inputContinueCreating() {
        System.out.println("Continue custom creation?   (y)Yes   (r)Randomise Remaining   (n)Exit");
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            try {
                switch (input) {
                    case "y":
                        return 0;
                    case "r":
                        return 1;
                    case "n":
                        return -1;
                    default:
                        System.out.println("Please choose a valid option!");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please choose a valid option!");
            }
        }
    }

    // Deletes all elements from party
    public void clearParty(){
        partyCharacters.clear();
    }


    // ========== Characters in Party ==========
    //Adds character to party list
    // TODO(JA) - Might be useful to merge with similar methods (when cleaning the code)
    public void addCharacterToParty(Character character) {
        partyCharacters.add(character);
    }

    //Removes character from party list (by character)
    public void removeCharacter(Character character) {
        this.getPartyCharacters().remove(character);
    }

    //Removes character from party list (by index)
    public void removeCharacter(int i) {
        this.getPartyCharacters().remove(i);
    }

    // Returns a random character from the party
    public Character selectRandomCharacter() {
        Random rand = new Random();
        return partyCharacters.get(rand.nextInt(partyCharacters.size()));
    }

    // TODO(JA) Needs to be tested - logPartyStats method needs to work.
    // Selects character from the party
    public Character selectCharacter() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose a Character from your party: ");
//        logPartyStats();   // Might be betters to have a print method separate for each character (and print as loop)
        // Maybe something like this but as a Character Method
        for (int i = 0; i < partyCharacters.size(); i++) {
            System.out.println("(" + (i + 1) + ")" + "\t" + partyCharacters.get(i).getClass().getSimpleName());
            System.out.println("\t\tName: " + partyCharacters.get(i).getName);
            System.out.println("\t\tHP: " + partyCharacters.get(i).getHp);
            if (partyCharacters.get(i).getClass().getSimpleName().equals("Warrior")) {
                System.out.println("\t\tStamina: " + partyCharacters.get(i).getStamina);
                System.out.println("\t\tStrength: " + partyCharacters.get(i).getStrength + "\n");
            }
            if (partyCharacters.get(i).getClass().getSimpleName().equals("Wizard")) {
                System.out.println("\t\tMana: " + partyCharacters.get(i).getMana);
                System.out.println("\t\tIntelligence: " + partyCharacters.get(i).getIntelligence + "\n");
            }
        }
        while (true) {
            String input = scanner.nextLine();
            try {
                int choice = Integer.parseInt(input);
                if (choice > 0 && choice < partyCharacters.size()) return partyCharacters.get(choice-1);
            } catch (NumberFormatException e) {
                System.out.println("Please choose a valid option!");
            }
        }
    }

    // Returns the index of the character in the party list (can be useful for further methods) (not in use)
    public int getIdxInParty(Character character) {
        return this.getPartyCharacters().indexOf(character);
    }


    // ========== Party Log ==========
    // TODO(JA) Needs to be tested - not sure if getMethods from Warrior and Wizard can be used on Character "character".
    // Prints each character from the party with their stats.
    // (Can be divided if we create a method to print a single character stats)
    public void logPartyStats() {
//        System.out.println(partyName.toUpperCase());
        for (Character character : partyCharacters) {
            System.out.println("Class: " + character.getClass().getSimpleName());
            System.out.println("ID: " + character.getId);
            System.out.println("Name: " + character.getName);
            System.out.println("HP: " + character.getHp);

            if (character.getClass().getSimpleName().equals("Warrior")) {
                System.out.println("Stamina: " + character.getStamina);
                System.out.println("Strength: " + character.getStrength + "\n");
            }
            if (character.getClass().getSimpleName().equals("Wizard")) {
                System.out.println("Mana: " + character.getMana);
                System.out.println("Intelligence: " + character.getIntelligence + "\n");
            }
        }
    }
}
