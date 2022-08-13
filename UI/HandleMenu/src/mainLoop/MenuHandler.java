package mainLoop;

import dTOUI.*;
import enigmaExceptions.xmlExceptions.XMLException;
import handleInput.HandleInputFromUser;
import historyAndStatistics.mapSourceDecodedAndTime.SourceAndDecodedAndTime;
import machine.MachineImplement;
import machineDetails.SecretCode;
import xmlHandle.importFromXML.XMLToObject;

import java.util.List;
import java.util.Map;
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


    public void showHistoryAnsStatistics(DTOHistoryStatistics dtoHistoryStatistics){
        int i = 0;
        Map<Integer, List<SourceAndDecodedAndTime>> mapOfData = dtoHistoryStatistics.getMapOfAllData();
        for (SecretCode secretCode : dtoHistoryStatistics.getSecretCodesHistory()){
            System.out.println(secretCode.toString());
            List<SourceAndDecodedAndTime> list = mapOfData.get(i);
            for (SourceAndDecodedAndTime sourceAndDecodedAndTime : list){
                System.out.println("#. <" + sourceAndDecodedAndTime.getSourceAndDecoded().getSourceMsg() + "> --> <" + sourceAndDecodedAndTime.getSourceAndDecoded().getDecodesMsg()
                + "> (" + sourceAndDecodedAndTime.getTimeToProcess() + ")");
            }
            i++;
        }
    }


    public MachineImplement openXMLFile(DTO dto){
        XMLToObject converter = new XMLToObject();
        DTOImportFromXML dtoXML = (DTOImportFromXML)dto;
        MachineImplement machine = null;
        boolean isValid = true;

        do{
        try{
            machine = converter.machineFromXml(dtoXML.getPath());
            isValid = true;
        }
        catch (XMLException error){
            System.out.println(error.getMessage());
            dtoXML = new DTOImportFromXML(1, this.takePathFromUser());
            isValid = false;
        }}while (!isValid);

        return machine;
    }

    public void showLastMachineDetails(DTOMachineDetails dtoDetails)
    {
        String msg = "Last machine in use description:" + System.lineSeparator();
        msg += "Amount of rotors in use / amount of possible rotors: " + dtoDetails.getNumberOfRotorInUse()+" / "+dtoDetails.getTotalNumberOfRotors() + System.lineSeparator();
        msg += "Notch places for each rotor:" + System.lineSeparator();
        msg+="Reflectors number: "+dtoDetails.getTotalNumberOfReflectors() + System.lineSeparator();
        msg+="Until now there were " + dtoDetails.getHowMuchMsgHaveBeenProcessed()+" messages processed in machine"+ System.lineSeparator();
        msg+="Current secret code: " + dtoDetails.getCurrSecretCodeDescription()+ System.lineSeparator();
        msg+="First secret code: " + dtoDetails.getFirstSecreteCodeDescription()+ System.lineSeparator();
        System.out.println(msg);
    }
}
