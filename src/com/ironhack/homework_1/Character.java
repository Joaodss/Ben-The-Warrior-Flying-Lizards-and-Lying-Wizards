package com.ironhack.homework_1;

import java.util.ArrayList;
import java.util.List;
import  java.lang.reflect.*;
import java.util.Scanner;

public abstract class Character {
    /*
    id - unique identifier
    name - String
    hp - number representing the health points
    isAlive - flag to determine if the Player is alive
    */

    private int id;
    private String name;
    private double hp;
    private boolean isAlive;

    public static boolean hardcore = false;
    private static int idCount = 0;
    private static String[] classNames = {"Warrior", "Wizard", "Archer"};
    private static Class[] possibleClasses = {Warrior.class, Wizard.class, Archer.class};

    public Character(){
        this.isAlive = true;
        setName();
        setId();
    }

    public Character(String name){
        this.isAlive = true;
        setName(name);
        setId();
    }

    public int getId() {
        return id;
    }

    public void setId() {
        this.id = ++idCount;
    }

    public String getName() {
        return name;
    }

    public void setName(){
        this.name = Names.randomName();
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getHp() {
        return hp;
    }

    public void setHp(){
        this.hp = 100;
    }

    public void setHp(double hp) {
        this.hp = hp;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }



    abstract double attack(Character character);
    abstract void receiveDamage(double damage);
    abstract String printStats();

    abstract String toCsvFormat();

    public static Character addCharacter(String[] parameters){
        switch(parameters[0]){
            case "Warrior":
                if(parameters.length == 5){
                    try{
                        return new Warrior(parameters[1].trim(), Integer.parseInt(parameters[2].trim()),
                                Integer.parseInt(parameters[3].trim()), Double.parseDouble(parameters[4].trim()));
                    }catch (NumberFormatException e){
                        return null;
                    }
                }else{
                    System.err.println("ERROR: incorrect number of parameters for Warrior. This Character will not be added to the party");
                }

            case "Wizard":
                if(parameters.length == 5){
                    try{
                        return new Wizard(parameters[1].trim(), Integer.parseInt(parameters[2].trim()),
                                Integer.parseInt(parameters[3].trim()), Double.parseDouble(parameters[4].trim()));
                    }
                    catch (NumberFormatException e){
                        return null;
                    }
                }else{
                    System.err.println("ERROR: incorrect number of parameters for Wizard. This Character will not be added to the party");
                    return null;
                }

            default:
                System.err.println("ERROR: Character " + parameters[0] + " not found");
                return null;
        }
    }
    public static Character getRandom() throws NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        int rndClassIndex = (int) Math.floor(Math.random() * possibleClasses.length);
        Character charRnd = (Character) possibleClasses[rndClassIndex].newInstance();
        return charRnd;
    }

    public static Character createCustom(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose a class for your new Character: ");
        boolean chosen = false;
        while (chosen == false) {
            for (int i = 0; i < classNames.length; i++) {
                System.out.println((i + 1) + ". " + classNames[i]);
            }
            String input = scanner.nextLine();
            try {
                int choice = Integer.parseInt(input);
                switch (choice){
                    case 1:
                        chosen = true;
                        return Warrior.createCustom();
                    case 2:
                        chosen = true;
                        return Wizard.createCustom();
                    default:
                        break;
                }

            }
            catch (NumberFormatException e){
                System.out.println("Please choose a valid option!");
            }
        }
        return null;
    }
}
