package Trie;

public class Person {
	String name;
	String number;
    public Person(String name, String phone_number) {
    	this.name=name;
    	number=phone_number;
    }

    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
    	return "[Name: "+name+", Phone="+number+"]";
    }


}
