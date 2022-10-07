package implement.trie;

import java.util.LinkedList;
import java.util.List;

public class TrieTreeNode {

    private char character;
    private List<TrieTreeNode> children;


        public TrieTreeNode(char character) {
            this.character = character;
            this.children = new LinkedList();
        }

        public char getCharacter() {
            return character;
        }
        public void setCharacter(char character) {
            this.character = character;
        }
        public List<TrieTreeNode> getChildren() {
            return children;
        }
        public void setChildren(List<TrieTreeNode> children) {
            this.children = children;
        }


}
