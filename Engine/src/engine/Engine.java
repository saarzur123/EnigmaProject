package engine;

import dTOUI.DTOHistoryStatistics;
import dTOUI.DTOMachineDetails;
import dTOUI.DTOSecretCodeFromUser;
import decryption.manager.DecryptionManager;
import history.statistics.HistoryAndStatisticsForMachine;
import history.statistics.mapSourceDecodedAndTime.SourceAndDecodedAndTime;
import history.statistics.sourceAndDecoded.SourceAndDecodedString;
import machine.MachineImplement;
import machine.SecretCode;
import machine.detail.MachineDetails;
import automation.SecretCodeRandomAutomation;
import handle.xml.build.XMLToObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Engine implements Commander {
    private MachineImplement machine;
    private SecretCode secretCode;
    private MachineDetails machineDetailsPresenter;
    private DecryptionManager decryptionManager;
    private HistoryAndStatisticsForMachine historyAndStatisticsForMachine = new HistoryAndStatisticsForMachine();
    private SecretCodeRandomAutomation secretCodeRandomAutomation = new SecretCodeRandomAutomation();

    public MachineImplement getMachine(){return machine;}
    public SecretCode getSecretCode(){return secretCode;}
    public MachineDetails getMachineDetailsPresenter(){return machineDetailsPresenter;}
    public HistoryAndStatisticsForMachine getHistoryAndStatisticsForMachine(){return historyAndStatisticsForMachine;}

    public DecryptionManager getDecryptionManager() {
        return decryptionManager;
    }

    @Override
    public MachineImplement createMachineFromXML(String path){
        XMLToObject converter = new XMLToObject();
        MachineImplement machine = converter.machineFromXml(path);///throws exception

            if(machine != null) {//replace machine only if the new machine is valid
                this.machine = machine;
                this.secretCode = null;
                this.machineDetailsPresenter = new MachineDetails(machine, secretCode);
                this.historyAndStatisticsForMachine = new HistoryAndStatisticsForMachine();
                this.decryptionManager = converter.createDecryptionManager();
                decryptionManager.setMachine(machine);
            }

        return machine;
    }

    @Override
    public String showLastMachineDetails(DTOMachineDetails dtoDetails)
    {
        String msg = "Last machine in use description:" + System.lineSeparator();
        msg += "Amount of rotors in use / amount of possible rotors: " + dtoDetails.getNumberOfRotorInUse()+" / "+dtoDetails.getTotalNumberOfRotors() + System.lineSeparator();
        msg+="Reflectors number: "+dtoDetails.getTotalNumberOfReflectors() + System.lineSeparator();
        msg+="Until now there were " + dtoDetails.getHowMuchMsgHaveBeenProcessed()+" messages processed in machine"+ System.lineSeparator();
        msg+="Current secret code: " + dtoDetails.getCurrSecretCodeDescription()+ System.lineSeparator();
        msg+="First secret code: " + dtoDetails.getFirstSecreteCodeDescription()+ System.lineSeparator();
        return msg;
    }

    @Override
    public String showHistoryAnsStatistics(DTOHistoryStatistics dtoHistoryStatistics){
        int i = 0;
        String msg="";
        Map<Integer, List<SourceAndDecodedAndTime>> mapOfData = dtoHistoryStatistics.getMapOfAllData();
        if(mapOfData.size() == 0){
            msg +="No information to show you :(" + System.lineSeparator();
        }
        for (SecretCode secretCode : dtoHistoryStatistics.getSecretCodesHistory()){
            msg+=secretCode.toString()+System.lineSeparator();
            List<SourceAndDecodedAndTime> list = mapOfData.get(i);
            for (SourceAndDecodedAndTime sourceAndDecodedAndTime : list){
                msg+="#. <" + sourceAndDecodedAndTime.getSourceAndDecoded().getSourceMsg() + "> --> <" + sourceAndDecodedAndTime.getSourceAndDecoded().getDecodesMsg()
                        + "> (" + sourceAndDecodedAndTime.getTimeToProcess() + ")"+ System.lineSeparator();
            }
            i++;
        }
        return msg;
    }

    @Override
    public String processData(String inStr){
        String outStr = "";
        if(inStr != null) {
            long start = System.nanoTime();
            outStr = (machine.encodingAndDecoding(inStr, secretCode.getInUseRotors(), secretCode.getPlugBoard(),
                    secretCode.getInUseReflector()));
            long end = System.nanoTime();
            int indexInSecretCodeList = historyAndStatisticsForMachine.getSecretCodeHistory().indexOf(secretCode);
            historyAndStatisticsForMachine.getDataForEachSecretCode().get(indexInSecretCodeList).add(new SourceAndDecodedAndTime(new SourceAndDecodedString(inStr, outStr), end - start));
            secretCode.changeNotchInSchema();
        }
        return outStr;
    }

    @Override
    public void getSecretCodeFromUser(DTOSecretCodeFromUser userDto, boolean isExit){
        secretCode = new SecretCode(machine);

        if(isExit) secretCode = null;
        else secretCode.determineSecretCode(userDto.getRotorsIdPositions(),userDto.getRotorsStartPosition(),
                userDto.getReflectorIdChosen().get(0),userDto.getPlugBoardFromUser());
        updateAfterSecretCode();
    }

    @Override
    public void getRandomSecretCode(){
        secretCode = new SecretCode(machine);
        secretCode = secretCodeRandomAutomation.getSecretCodeAutomation(machine);
        updateAfterSecretCode();
    }

    private void updateAfterSecretCode(){
        if(secretCode != null){
            machineDetailsPresenter.addSecretCode(secretCode);
            historyAndStatisticsForMachine.addSecretCodeToMachineHistory(secretCode);
            historyAndStatisticsForMachine.getDataForEachSecretCode().put(historyAndStatisticsForMachine.getSecretCodeHistory().indexOf(secretCode), new ArrayList<>());
            decryptionManager.setSecretCode(secretCode);
        }
    }

@Override
    public boolean validateUserChoiceAndResetSecretCode(){
        boolean isValid = true;

        if(secretCode != null) {
            secretCode.resetSecretCode();
            secretCode.changeNotchInSchema();
        }
        else isValid = false;
        return isValid;
    }
}
