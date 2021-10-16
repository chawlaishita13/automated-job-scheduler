package ProjectManagement;

import java.util.ArrayList;

public class Project {
	String name;
	int budget,priority;
	static int enter=0;
	ArrayList<Job> unfinished=new ArrayList<>();
	ArrayList<Job> jobList=new ArrayList<>();
	public Project(String n,int p,int b) {
		name=n;
		budget=b;
		priority=p;
		enter++;
	}
}
