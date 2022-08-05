package DTOUI;

public class DTOImportFromXML extends DTO{

    private String pathOfXMLFile;
    public DTOImportFromXML(int number, String path){
        super(number);
        pathOfXMLFile = path;
    }
}
