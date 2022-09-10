package subComponent.main.brute.force.dictionary.trie;

import java.util.ArrayList;
import java.util.List;

class TrieNode{
    private  TrieNode childran[] =new TrieNode[26];
    private boolean isEndOfWord;

    public TrieNode[] getChildran() {
        return childran;
    }

    public boolean isEndOfWord() {
        return isEndOfWord;
    }

    public void setEndOfWord(boolean endOfWord) {
        isEndOfWord = endOfWord;
    }
}
public class Trie {

    private TrieNode root;

    public Trie(){
        root = new TrieNode();
    }

    public void insert(String word){
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            if(node.getChildran()[word.charAt(i) - 'a'] == null){
                node.getChildran()[word.charAt(i) - 'a'] = new TrieNode();
            }
            node = node.getChildran()[word.charAt(i) - 'a'];
        }
        node.setEndOfWord(true);
    }

    public boolean search(String word){
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            if(node.getChildran()[word.charAt(i) - 'a'] == null){
                return false;
            }
            node = node.getChildran()[word.charAt(i) - 'a'];
        }
        return node.isEndOfWord();
    }

//    public List<String> startWith(String prefix){
//        TrieNode node = root;
//        ArrayList<String> startWithList = new ArrayList<>();
//        for (int i = 0; i < prefix.length(); i++) {
//            if(node.getChildran()[prefix.charAt(i) - 'a'] == null){
//                return false;
//            }
//            node = node.getChildran()[prefix.charAt(i) - 'a'];
//        }
//        return true;
//    }
    public static void main(String[] args){
        Trie obj = new Trie();
        obj.insert("ab");
        obj.insert("abati");
        boolean ans = obj.search("ab");
        //boolean ans1 = obj.startWith("aba");
    }
}
