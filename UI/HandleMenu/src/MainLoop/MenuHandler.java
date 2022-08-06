package MainLoop;

import DTOUI.*;
import EnigmaExceptions.XMLExceptions.XMLException;
import HandleInput.HandleInputFromUser;
import HistoryAndStatistics.HistoryAndStatisticsForMachine;
import Machine.MachineImplement;
import XMLHandle.ImportFromXML.XMLToObject;

import java.util.Scanner;

public class MenuHandler {
    private HandleInputFromUser handler = new HandleInputFromUser();

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





    public void actionInDTO(DTO dto){
        if(dto.getClass() == DTOImportFromXML.class)
            openXMLFile((DTOImportFromXML)dto);
        else if(dto.getClass() == DTOExit.class)
            System.exit(0);

    }

    public void openXMLFile(DTO dto){
        XMLToObject converter = new XMLToObject();
        DTOImportFromXML dtoXML = (DTOImportFromXML)dto;
        try{
            MachineImplement machine = converter.machineFromXml(dtoXML.getPath());
        }
        catch (XMLException error){
            System.out.println(error.getMessage());
            this.takePathFromUser();
            this.openXMLFile(dto);
        }
    }
}
