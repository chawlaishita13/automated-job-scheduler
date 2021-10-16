package RedBlack;

import Util.RBNodeInterface;

import java.util.ArrayList;
import java.util.List;


public class RedBlackNode<T extends Comparable, E> implements RBNodeInterface<E> {
	
	T key;
	int colour;
	RedBlackNode parent,left,right;
	List<E> list=new ArrayList();
	E value;
	
	public RedBlackNode(T k, E val) {
		key=k;
		value=val;
		colour=0;
	}
    @Override
    public E getValue() {
        return value;
    }

    @Override
    public List<E> getValues() {
        return list;
    }
    public int getColour() {
    	return colour;
    }
}
