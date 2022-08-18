package run;

import engine.Commander;
import dTOUI.*;
import enigmaException.xmlException.XMLException;
import handle.input.HandleInputFromUser;
import machine.MachineImplement;

import java.util.Scanner;

public class MenuHandler {
    private HandleInputFromUser handler = new HandleInputFromUser();

    public HandleInputFromUser getInputHandler(){return  handler;}

    public int checkWHatTheUserWantToDo(){
        StringBuilder str = new StringBuilder();
        Scanner myObj = new Scanner(System.in);
        String userSelection;
        buildStrMenu(str);
        do{
            System.out.println("Please enter number between 1 to 8 :)");
            System.out.println(str);
            userSelection = myObj.nextLine();
        }while(!handler.checkInputOfSelectionFromMenu(userSelection));

        return userSelection.charAt(0) - '0'; //  עדכון למספר
    }

    public String buildStrMenu(StringBuilder str){
        str.append("Hey ! Please choose one of the options :");str.append(System.lineSeparator());
        str.append("1) Reading machine information from XML file.");str.append(System.lineSeparator());
        str.append("2) Displaying the last machine specifications.");str.append(System.lineSeparator());
        str.append("3) Selecting an initial code configuration.");str.append(System.lineSeparator());
        str.append("4) Automatically select an initial code configuration.");str.append(System.lineSeparator());
        str.append("5) Input processing.");str.append(System.lineSeparator());
        str.append("6) Reset current code.");str.append(System.lineSeparator());
        str.append("7) History and statistics");str.append(System.lineSeparator());
        str.append("8) Exit.");str.append(System.lineSeparator());
        return str.toString();
    }

    public String takePathFromUser(){
        System.out.println("Please enter full path of the XML File :) ");
        Scanner myObj = new Scanner(System.in);
        return myObj.nextLine();
    }

    public MachineImplement openXMLFile(DTO dto, Commander engine){
        DTOImportFromXML dtoXML = (DTOImportFromXML)dto;
        MachineImplement machine = null;
        boolean isValid = true;

        do{
        try{
            String path = dtoXML.getPath();
            machine = engine.createMachineFromXML(path);
            isValid = true;
        }
        catch (XMLException error){
            System.out.println(error.getMessage());
            if(handler.doUserWntToExit("")){
                isValid = true;
                return null;
            }
            else {
                dtoXML = new DTOImportFromXML(1, this.takePathFromUser());
                isValid = false;
            }
        }}while (!isValid);

        return machine;
    }
}
