package PriorityQueue;

import java.util.ArrayList;



public class MaxHeap<T extends Comparable> implements PriorityQueueInterface<T> {
	
	public ArrayList<T> data=new ArrayList<>();


	public void insert(T item) {

		

	data.add(item);
//	int i=0;
//	int max=pair.flag;
//	boolean b=false;
//	int x=data.size();
//	while(i<x-1) {
//		Pair p=data.get(i);
//		if(p.value.compareTo(pair.value)==0 && p.flag>=pair.flag) {
//			max=Integer.max(p.flag,max);
//			b=true;
//		
//		}
//		i++;
//	}
//	
//	if(b) {
//		max=max+1;
//		pair.flag=max;
//	}
	
		upheapify(data.size() - 1);
	}

	private void upheapify(int ci) {

		int pi = (ci - 1) / 2;
		if (data.get(ci).compareTo(data.get(pi)) < 0) {
			swap(pi, ci);
			upheapify(pi);
		}

	}

	private void swap(int i, int j) {

		T ith = data.get(i);
		T jth = data.get(j);

		data.set(i, jth);
		data.set(j, ith);
	}

	

	public T remove() {

		swap(0, data.size() - 1);
		T temp = data.remove(data.size() - 1);

		downheapify(0);
		return temp;

	}

	private void downheapify(int pi) {

		int lci = 2 * pi + 1;
		int rci = 2 * pi + 2;

		int mini = pi;
		int x=data.size();
		if (lci < x && data.get(lci).compareTo(data.get(mini)) < 0) {
			mini = lci;
		}

		if (rci < x && data.get(rci).compareTo(data.get(mini)) < 0) {
			mini = rci;
		}

		if (mini != pi) {
			swap(mini, pi);
			downheapify(mini);
		}

	}



	@Override
	public T extractMax() {
		T temp=null;
		if(data.size()>=1)
		temp= remove();
		if(temp!=null)
			return temp;
		else
			return null;
	}
	public int size() {
		return data.size();
	}



}