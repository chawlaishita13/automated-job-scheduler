package ProjectManagement;

public class jobReport implements JobReport_ {
	String user;
	String proj;
	int budget;
	int arrtime;
	int comptime;
	
	public jobReport(Job j) {
		user=j.user.name;
		proj=j.project.name;
		budget=j.project.budget;
		arrtime=j.arrivaltime;
		comptime=j.completedTime;
	}
	

	
	@Override
	public String user() {
		// TODO Auto-generated method stub
		return user;
	}

	@Override
	public String project_name() {
		// TODO Auto-generated method stub
		return proj;
	}

	@Override
	public int budget() {
		// TODO Auto-generated method stub
		return budget;
	}

	@Override
	public int arrival_time() {
		// TODO Auto-generated method stub
		return arrtime;
	}

	@Override
	public int completion_time() {
		// TODO Auto-generated method stub
		return comptime;
	}

}
