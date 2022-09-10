package dTOUI;

import java.util.*;

public class DTOSecretCodeFromUser {
    LinkedList<Integer> rotorsIdPositions = new LinkedList<>();
    List<Character> rotorsStartPosition = new ArrayList<>();
    List<Integer> reflectorIdChosen = new ArrayList<>();
    Map<Character,Character> plugBoardFromUser = new HashMap<>();

    public LinkedList<Integer> getRotorsIdPositions(){return rotorsIdPositions;}
    public List<Character> getRotorsStartPosition(){return rotorsStartPosition;}
    public List<Integer> getReflectorIdChosen(){return reflectorIdChosen;}
    public Map<Character,Character> getPlugBoardFromUser(){return plugBoardFromUser;}
}
