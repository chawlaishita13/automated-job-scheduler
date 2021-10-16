package PriorityQueue;


	public class Pair<T extends Comparable>{
			int flag;
			public T value;
			public Pair(T v) {
				value=v;
				flag=1;
			}
			public int compareTo(Pair<T> pair) {
				if(this.value.compareTo(pair.value)==0) {
					if(this.flag<pair.flag) {
						
						return -1;
						
					}
					else {

						return 1;
					}
				}
				else {
					
					return this.value.compareTo(pair.value);
				}
			}
		}

