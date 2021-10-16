package ProjectManagement;

public class userReport implements UserReport_ {
	String user;
	int consumed;
	public userReport(User u) {
		user=u.name;
		consumed=u.budgetConsumed;
	}
	@Override
	public String user() {
		// TODO Auto-generated method stub
		return user;
	}

	@Override
	public int consumed() {
		// TODO Auto-generated method stub
		return consumed;
	}
	
}
