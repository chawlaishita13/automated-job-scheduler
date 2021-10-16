package Trie;

import java.util.ArrayList;


public class Trie<T> implements TrieInterface<T> {
	
	private TrieNode<T> root;
	boolean b=true;
	public Trie() {
		root=new TrieNode('*');
	}

    public boolean delete(String word) {
       return delete(root,word);
    }
    private boolean delete(TrieNode node,String word) {
    	if(word.length()==0) {
			node.eow=false;
			return true;
		}
		char ch=word.charAt(0);
		String row=word.substring(1);
		for(int i=0;i<node.list.size();i++) {
			if(((TrieNode)node.list.get(i)).letter==ch) {
				TrieNode child=(TrieNode) node.list.get(i);
				boolean b= delete((TrieNode) node.list.get(i),row);
				
				if(child.eow==false && child.list.size()==0) {
					node.list.remove(child);
			}
				return b;
		}
			
			
		}
		return false;
    }

    
    public TrieNode search(String word) {
        return search(root,word);
    }
    private TrieNode search(TrieNode node,String word) {
    	if(word.length()==0) {
			return node;
		}
		char ch=word.charAt(0);
		for(int i=0;i<node.list.size();i++) {
			if(((TrieNode)node.list.get(i)).letter==ch) {
				return search((TrieNode)node.list.get(i),word.substring(1));
			}
		}
		
		return null;
		
    }

    
    public TrieNode startsWith(String prefix) {
    	return startsWith(root,prefix);
        
    }
    private TrieNode startsWith(TrieNode node,String prefix) {
    	if(prefix.length()==0) {
			return node;
		}
		char ch=prefix.charAt(0);
		for(int i=0;i<node.list.size();i++) {
			if(((TrieNode)node.list.get(i)).letter==ch) {
				return startsWith((TrieNode)node.list.get(i),prefix.substring(1));
			}
		}
		
		return null;
    }

   
    public void printTrie(TrieNode trieNode) {
    	ArrayList<T> store=new ArrayList<>();
    	printTrie(trieNode,store);
    	for(int i=0;i<store.size();i++)
    		System.out.println(store.get(i));
    }
    
    private void printTrie(TrieNode node,ArrayList<T> store) {
    	if(node.eow==true) {
    		store.add((T) node.person);
    		return;
    	}
    	for(int i=0;i<node.list.size();i++) {
    		printTrie(((TrieNode)node.list.get(i)),store);
    	}
    }
    
    public boolean insert(String word, T value) {
        return insert(root,word,value);
    }
    
    private boolean insert(TrieNode node,String word,T value) {
    	if(word.length()==0) {
			node.eow=true;
			node.person=value;
			return false;
		}
    	
		char ch=word.charAt(0);
		for(int i=0;i<node.list.size();i++) {
			if(((TrieNode)node.list.get(i)).letter==ch) {
				return insert((TrieNode) node.list.get(i),word.substring(1),value);
			}
		
		}
		TrieNode nn=new TrieNode(ch);
		node.list.add(nn);
		return insert(nn,word.substring(1),value)||true;
				
	
		
		
		
		
    }

    public void printLevel(int level) {
    	ArrayList<Character> store=new ArrayList<>();
    	printLevel(root,level,1,store);
    	sort(store);
    	if(store.isEmpty())
    		b=false;
    	System.out.print("Level"+level+":");
    	
    	for(int i=0;i<store.size();i++) {
    		System.out.print(store.get(i));
    		if(!(i==store.size()-1))
    			System.out.print(",");
    	}
    	//System.out.println(store.get(p));
    	System.out.println();
    }
    private void printLevel(TrieNode node,int level,int count,ArrayList<Character> store) {
    	if(level==count) {
    		for(int i=0;i<node.list.size();i++) {
    			if(((TrieNode)node.list.get(i)).letter!=' ')
    				store.add(((TrieNode)node.list.get(i)).letter);
    		}
    		return;
    	}
    	
    	for(int i=0;i<node.list.size();i++) {
    		printLevel((TrieNode) node.list.get(i),level,count+1,store);
    	}
    }

    public void print() {
    	System.out.println("-------------");
    	System.out.println("Printing Trie");
    	b=true;
    	int i=1;
    	while(b==true) {
    		printLevel(i);
    		i++;
    	}
    	System.out.println("-------------");
    }
    
    public static void sort(ArrayList<Character> str) {
		for(int i=0;i<str.size();i++) {
			char temp;
			
			for(int j=i+1;j<str.size();j++) {
				if(str.get(i).compareTo(str.get(j))>0) {
					temp=str.get(i);
					str.set(i, str.get(j));
					str.set(j, temp);
				}
			}
		}
	}

}

