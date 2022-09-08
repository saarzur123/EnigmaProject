package decryption.manager;

import java.util.HashSet;
import java.util.Set;

public class Dictionary {
    private Set<String> dictionaryFilteredWords = new HashSet<>();
    private Set<Character> excludeWords = new HashSet<>();

    public Dictionary(String dictionaryWords, String excludeWords){
        setExcludeWords(excludeWords);
        setFilteredDictionary(dictionaryWords);
    }

    private void setExcludeWords(String excludeWords){
        int size = excludeWords.length();
        for (int i = 0; i < size; i++) {
            this.excludeWords.add(excludeWords.charAt(i));
        }
    }

    private void setFilteredDictionary(String dictionaryWords){
        String[] wordsArr = dictionaryWords.split(" ");
        for(String word : wordsArr){
            word = word.trim();
            word = filterWords(word);
            if(word != "") dictionaryFilteredWords.add(word);
        }
    }

    private String filterWords(String wordToFilter){
        String word = wordToFilter;
        int size = word.length();
        int i;
        for (i = 0; i+1 < size; i++) {
            if(excludeWords.contains(word.charAt(i))){
                String start = word.substring(0,i);
                String end = word.substring(i+1,size);
                word = start.concat(end);
                i--;
                size--;
            }
        }
        if(excludeWords.contains(word.charAt(i))) {
            word = word.substring(0, i);
        }
        return word;
    }

    public boolean isStringInDictionary(String stringToCheckInDictionary){
        String[] wordsArr = stringToCheckInDictionary.split(" ");
        for(String word : wordsArr){
            word = word.trim();
            word = filterWords(word);
            if(word != "") {
                if(!dictionaryFilteredWords.contains(word)){
                    return false;
                }
            }
        }
        return true;
    }
}
