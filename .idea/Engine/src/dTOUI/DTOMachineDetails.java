package dTOUI;

public class DTOMachineDetails extends DTO{

    private int totalNumberOfRotor;
    private int numberOfRotorInUse;
    private int totalNumberOfReflectors;
    private int howMuchMsgHaveBeenProcessed;
    private String currSecretCodeDescription;
    private String firstSecreteCodeDescription;




    public DTOMachineDetails(int number, int totalRotorsNum, int rotorInUseNum, int totalReflectorNum, int processedMsgNum, String currSecretCodeDescription, String firstSecretCode){
        super(number);
        this.totalNumberOfRotor = totalRotorsNum;
        this.numberOfRotorInUse = rotorInUseNum;
        this.totalNumberOfReflectors = totalReflectorNum;
        this.howMuchMsgHaveBeenProcessed = processedMsgNum;
        this.currSecretCodeDescription = currSecretCodeDescription;
        this.firstSecreteCodeDescription = firstSecretCode;
    }

    public int getTotalNumberOfRotors(){return totalNumberOfRotor;}
    public int getNumberOfRotorInUse(){return numberOfRotorInUse;}
    public int getTotalNumberOfReflectors(){return totalNumberOfReflectors;}
    public int getHowMuchMsgHaveBeenProcessed(){return howMuchMsgHaveBeenProcessed;}
    public String getCurrSecretCodeDescription(){return currSecretCodeDescription;}
    public String getFirstSecreteCodeDescription(){return firstSecreteCodeDescription;}
}
