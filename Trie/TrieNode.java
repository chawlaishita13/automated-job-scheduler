package Trie;

import java.util.ArrayList;


import Util.NodeInterface;


public class TrieNode<T> implements NodeInterface<T> {
	ArrayList<TrieNode<T>> list=new ArrayList<TrieNode<T>>();
	char letter;
	boolean eow;
	T person;
	public TrieNode(char ch) {
		letter=ch;
	}
   
    public T getValue() {
        return person;
    }


}
