package com.ironhack.homework_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class PartyCreator {
    public void importParty(Party party, String csvFile) throws FileNotFoundException {

        // String split into directory and name of the file
        String[] directoryAndFile = directoryAndName(csvFile);
        String directory = directoryAndFile[0];
        csvFile = directoryAndFile[1];

        // Add ".csv" to the file name if it was not provided
        try{
            csvFile = csvCheck(csvFile, true);
        }catch(IllegalArgumentException e){
            throw new FileNotFoundException(e.getMessage());
        }

        // Search the file in the parties folder and if not found check with the directory provided
        File file = new File("parties/" + csvFile);
        if(!file.exists()) {
            file = new File(directory + csvFile);
            if (!file.exists()) {
                throw new FileNotFoundException("File not found! Make sure the .csv file is in the folder parties or the correct directory is provided.");
            }
        }

        party.clearParty();

        // Scanner initialization
        Scanner scanner = new Scanner(file);

        // While loop to add each Character to the Party until file ended or party is full
        while(scanner.hasNextLine()){
            if (party.getPartyCharacters().size() < Menu.getPartySize()){
                String[] newCharacter = scanner.nextLine().split(",");
                Character character = Character.addCharacter(newCharacter);
                if(character != null){
                    party.addCharacter(Character.addCharacter(newCharacter));
                }
            }else{
                Printer.printFormatted("Party limit reached, unable to add more characters! If needed change in settings!");
                break;
            }
        }
        scanner.close();
    }

    public void saveParty(Party party, String csvFile) throws IOException {

        // Split string into directory and name of file
        String[] directoryAndFile = directoryAndName(csvFile);
        String directory = directoryAndFile[0];
        csvFile = directoryAndFile[1];

        // Check if correct file extension was provided
        try{
            csvFile = csvCheck(csvFile, false);
        }catch(IllegalArgumentException e){
            throw new FileNotFoundException(e.getMessage());
        }

        directoryAndFile[1] = csvFile;

        // Delete all invalid characters
        csvFile = csvFile.replaceAll("[\\\\/:*?\"<>|«»']", "");

        // add parties directory and open FileWriter
        csvFile = "parties/".concat(csvFile);
        FileWriter fileWriter = new FileWriter(csvFile, false);

        // Get Characters from Party and save them in the file
        List<Character> characterList = party.getPartyCharacters();

        for(Character character : characterList){
            fileWriter.write(character.toCsvFormat());
        }
        fileWriter.flush();
        fileWriter.close();

        // Message indicating the party was saved and warning messages if file name or directory were changed
        if(csvFile.equals(".csv")){
            Printer.printFormatted("Warning: party saved in file parties/unnamed.csv");
        }else if(!csvFile.equals("parties/".concat(directoryAndFile[1])) || (!directory.equals("") && !directory.equals("parties/"))){
            Printer.printFormatted("Warning: party saved in " + csvFile);
        }else{
            Printer.printFormatted("File saved to " + csvFile);
        }

    }
    public void randomParty(Party party) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        // Check how many party characters can be added
        int remainingSpaces = Menu.getPartySize() - party.getPartyCharacters().size();
        // Print message if it is not possible to add new characters and add characters if it is possible until limit is reached
        if (remainingSpaces == 0) Printer.printFormatted("Party limit reached, unable to add more characters! If needed change in settings!");
        for(int i = 0; i < remainingSpaces; i++){
            party.addCharacter(Character.getRandom());
        }
    }

    public void addCharacter(Party party){
        // add a new character to the Party passed in through the createCustom method
        // if party limit was already reached do not add a new character
        if (party.getPartyCharacters().size() < Menu.getPartySize()){
            party.addCharacter(Character.createCustom());
        }else{
            Printer.printFormatted("Party limit reached, unable to add more characters! If needed change in settings!");
        }
    }
    public void removeCharacter(Party party){
        // Remove the character selected
        party.removeCharacter(party.selectCharacter());
    }

    public void addCharacter(String csvFile) throws IOException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        // check if file has a valid name and extension
        try{
            csvFile = csvCheck(csvFile, true);
        }catch(IllegalArgumentException e){
            throw new FileNotFoundException(e.getMessage());
        }

        // Separate file name from directory
        String[] directoryAndFile = directoryAndName(csvFile);
        String directory = directoryAndFile[0];
        csvFile = directoryAndFile[1];

        // if a directory different from the parties folder was provided display a warning
        if(!directory.equals("") && !directory.equals("parties/")){
            Printer.printFormatted("Warning: file saved in parties folder!");
        }

        csvFile = "parties/".concat(csvFile);
        FileWriter fileWriter = new FileWriter(csvFile,true);

        // Create character through user input and save file in the csv file
        fileWriter.write(Objects.requireNonNull(Character.createCustom()).toCsvFormat());
        fileWriter.flush();
        fileWriter.close();
    }

    private String csvCheck(String csvFile, boolean toRead) throws IllegalArgumentException{

        // Check if file name exceeds maximum length
        if(csvFile.length() > 100){
            throw new IllegalArgumentException("Make sure the file name has less than 100 characters!");
        }

        // Add .csv to the file name if it was not provided
        if(!csvFile.contains(".")){
            csvFile = csvFile.concat(".csv");
        }else{
            if(csvFile.contains(".") && !csvFile.endsWith(".csv") && toRead) {
                throw new IllegalArgumentException("Make sure the file extension is .csv!");
            }else if(csvFile.contains(".") && !csvFile.endsWith(".csv") && !toRead){
                Printer.printFormatted("Warning: file extension changed to .csv");
                csvFile = csvFile.split("\\.")[0];
                csvFile = csvFile.concat(".csv");
            }else if(!csvFile.endsWith(".csv")){
                csvFile = csvFile.concat(".csv");
            }
        }
        return csvFile;
    }

    private String[] directoryAndName(String csvFile){
        //Split string into directory and name of the file
        String[] csvFileParts = csvFile.split("/");
        if(csvFileParts.length == 1){
            return new String[]{"", csvFile};
        }else{
            String directory = "";
            for(int i = 0; i < csvFileParts.length - 2; i++){
                directory = directory.concat(csvFileParts[i]).concat("/");
            }
            return new String[]{directory, csvFileParts[csvFileParts.length-1]};
        }
    }
}
