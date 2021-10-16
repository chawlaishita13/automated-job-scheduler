package ProjectManagement;

import java.util.ArrayList;

public class User implements Comparable<User> {

	String name;
	int maxT=0;
	int budgetConsumed=0;
	
	public User(String n) {
		name=n;
	}
    @Override
    public int compareTo(User user) {
    	if(user.budgetConsumed==this.budgetConsumed)
    		return this.maxT-user.maxT;
    	else
    		return user.budgetConsumed-this.budgetConsumed;
}
}

