package DTOUI;

public class DTOImportFromXML extends DTO implements Setter,Getter{

    private String pathOfXMLFile;
    public DTOImportFromXML(int number, String path){
        super(number);
        pathOfXMLFile = path;
    }

    public String getPath(){
        return pathOfXMLFile;
    }
}
