package implement.trie;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class TrieImplement {
    private TrieTreeNode root;

    /**
     *  Add the give word into the Trie
     * @param word
     */
    public void addWord(String word)
    {
        if(root == null)
        {
            root = new TrieTreeNode(' ');
        }
        TrieTreeNode start = root;
        char[] characters = word.toCharArray();
        for(char c : characters)
        {
            if( start.getChildren().size() == 0)
            {
                TrieTreeNode newNode = new TrieTreeNode(c);
                start.getChildren().add(newNode);
                start = newNode;
            }
            else
            {
                ListIterator iterator = start.getChildren().listIterator();
                TrieTreeNode node=null;
                while(iterator.hasNext())
                {
                    node = (TrieTreeNode)iterator.next();
                    if(node.getCharacter() >= c)
                        break;
                }
                if(node.getCharacter() == c)
                {
                    start = node;
                }
                else
                {
                    TrieTreeNode newNode = new TrieTreeNode(c);
                    iterator.add(newNode);
                    start = newNode;

                }
            }
        }

    }

    /**
     *  This method takes and prefix string and returns all possible string that can be formed from the given prefix
     * @param prefix
     * @return
     */
    public List<String> search(String prefix)
    {
        if(prefix == null || prefix.isEmpty())
            return null;

        char[] chars = prefix.toCharArray();
        TrieTreeNode start = root;
        boolean flag = false;
        for(char c : chars)
        {
            if(start.getChildren().size() > 0)
            {
                for(TrieTreeNode node : start.getChildren())
                {
                    if(node.getCharacter() == c)
                    {
                        start = node;
                        flag=true;
                        break;
                    }
                }
            }
            else
            {
                flag = false;
                break;
            }
        }
        if(flag)
        {
            List<String> matches = getAllWords(start,prefix);
            return matches;
        }

        return null;
    }

    /**
     *  This method returns list string that can be formed from the given node
     * @param start : Node from to start
     * @param prefix : String prefix that was formed till start node
     * @return
     */
    private List getAllWords(TrieTreeNode start,final String prefix)
    {
        if(start.getChildren().size() == 0)
        {
            List list = new LinkedList();
            list.add(prefix);
            return list;
        }
        else
        {
            List list = new LinkedList();
            for(TrieTreeNode n: start.getChildren())
            {
                list.addAll(getAllWords(n, prefix+n.getCharacter()));
            }
            return list;
        }

    }

}
