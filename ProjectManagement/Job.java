package ProjectManagement;

public class Job implements Comparable<Job> {
	String name;
	User user;
	Project project;
	int runtime;
	int arrivaltime;
	int completedTime;
	int priority;
	boolean complete=false;
	static int count=0;
	int x;
	public Job(String n,Project p,User u,int time){
		name=n;
		project=p;
		user=u;
		runtime=time;
		priority=p.priority;
		count++;
		x=count;
		
	}
    @Override
    public int compareTo(Job job) {
    	if(job.priority==this.priority) {
    		return this.x-job.x;
    	}
    	else
    		return job.priority-this.priority;
    }
}