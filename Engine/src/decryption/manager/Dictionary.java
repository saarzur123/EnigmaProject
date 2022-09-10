package decryption.manager;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class Dictionary implements Serializable{
    private Set<String> dictionaryFilteredWords = new HashSet<>();
    private Set<Character> excludeWords = new HashSet<>();
    private String dictionaryWords;
    private String excludeWordsStr;

    public Dictionary(String dictionaryWords, String excludeWords){
        this.dictionaryWords = dictionaryWords;
        this.excludeWordsStr = excludeWords;
        setExcludeWords(excludeWords);
        setFilteredDictionary(dictionaryWords);
    }

    public Set<String> getDictionaryFilteredWords() {
        return dictionaryFilteredWords;
    }

    public String getDictionaryWords() {
        return dictionaryWords;
    }

    public String getExcludeWordsStr() {
        return excludeWordsStr;
    }

//    public static Dictionary dictionaryCopy(Dictionary dictionary){
//        String dictWords = dictionary.getDictionaryWords();
//        String excWords = dictionary.getExcludeWordsStr();
//        return new Dictionary(dictWords,excWords);
//    }





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

    public String filterWords(String wordToFilter){
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
        if(i == size - 1 && size > 0) {
            if(excludeWords.contains(word.charAt(i))){
                word = word.substring(0, i);
            }
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
