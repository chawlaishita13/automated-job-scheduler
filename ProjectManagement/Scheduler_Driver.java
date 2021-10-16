package ProjectManagement;


import java.io.*;
import java.net.URL;
import java.util.ArrayList;

import PriorityQueue.MaxHeap;
import PriorityQueue.Pair;
import RedBlack.RBTree;
import RedBlack.RedBlackNode;
import Trie.Trie;
import Trie.TrieNode;
public class Scheduler_Driver extends Thread implements SchedulerInterface {
	
	Trie<Project> trie=new Trie<Project>();
	ArrayList<User> userList=new ArrayList<User>();
	MaxHeap<Job> heap=new MaxHeap<Job>();
	RBTree<String,Job> tree=new RBTree<String,Job>();
	RBTree<String,Job> utree=new RBTree<String,Job>();
	static int jobsdone=0;
	static int globalTime=0;
	ArrayList<Job> completedJobs=new ArrayList<Job>();
	ArrayList<Job> incompleteJobs= new ArrayList<Job>();
	ArrayList<Job> allJobs= new ArrayList<Job>();

    public static void main(String[] args) throws IOException {


        Scheduler_Driver scheduler_driver = new Scheduler_Driver();
        File file;
        if (args.length == 0) {
            URL url = Scheduler_Driver.class.getResource("INP");
            file = new File(url.getPath());
        } else {
            file = new File(args[0]);
        }

        scheduler_driver.execute(file);
    }

    public void execute(File commandFile) throws IOException {


        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(commandFile));

            String st;
            while ((st = br.readLine()) != null) {
                String[] cmd = st.split(" ");
                if (cmd.length == 0) {
                    System.err.println("Error parsing: " + st);
                    return;
                }
                String project_name, user_name;
                Integer start_time, end_time;

                long qstart_time, qend_time;

                switch (cmd[0]) {
                    case "PROJECT":
                        handle_project(cmd);
                        break;
                    case "JOB":
                        handle_job(cmd);
                        break;
                    case "USER":
                        handle_user(cmd[1]);
                        break;
                    case "QUERY":
                        handle_query(cmd[1]);
                        break;
                    case "": // HANDLE EMPTY LINE
                        handle_empty_line();
                        break;
                    case "ADD":
                        handle_add(cmd);
                        break;
                   
                    //--------- New Queries
                    case "NEW_PROJECT":
                    case "NEW_USER":
                    case "NEW_PROJECTUSER":
                    case "NEW_PRIORITY":
                        timed_report(cmd);
                        break;
                    case "NEW_TOP":
                        qstart_time = System.nanoTime();
                        timed_top_consumer(Integer.parseInt(cmd[1]));
                        qend_time = System.nanoTime();
                      //  System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                        break;
                    case "NEW_FLUSH":
                        qstart_time = System.nanoTime();
                        timed_flush( Integer.parseInt(cmd[1]));
                        qend_time = System.nanoTime();
                      //  System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                        break;
                    default:
                        System.err.println("Unknown command: " + cmd[0]);
                }

            }


            run_to_completion();
            print_stats();

        } catch (FileNotFoundException e) {
            System.err.println("Input file Not found. " + commandFile.getAbsolutePath());
        } catch (NullPointerException ne) {
            ne.printStackTrace();

        }
    }
    

    @Override
    public ArrayList<JobReport_> timed_report(String[] cmd) {
        long qstart_time, qend_time;
        ArrayList<JobReport_> res = null;
        switch (cmd[0]) {
            case "NEW_PROJECT":
                qstart_time = System.nanoTime();
                res = handle_new_project(cmd);
                qend_time = System.nanoTime();
            //    System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                break;
            case "NEW_USER":
                qstart_time = System.nanoTime();
                res = handle_new_user(cmd);
                qend_time = System.nanoTime();
           //     System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));

                break;
            case "NEW_PROJECTUSER":
                qstart_time = System.nanoTime();
                res = handle_new_projectuser(cmd);
                qend_time = System.nanoTime();
            //    System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                break;
            case "NEW_PRIORITY":
                qstart_time = System.nanoTime();
                res = handle_new_priority(cmd[1]);
                qend_time = System.nanoTime();
            //    System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                break;
        }

        return res;
    }

    @Override
    public ArrayList<UserReport_> timed_top_consumer(int top) {
 //   	System.out.println("Top query");
        ArrayList<UserReport_> userR=new ArrayList<>();
        int size=userList.size();

        MaxHeap<User> h=new MaxHeap<User>();
        for(int i=0;i<size;i++) {
        	h.insert(userList.get(i));
        }
        if(size-top<0) {
        	for(int k=size-1;k>=0;k--) {
        		userR.add(new userReport(h.extractMax()));
        	}
        }
        else
    	for(int k=size-1;k>size-top-1;k--) {
    		userR.add(new userReport(h.extractMax()));
    	}
        for(int i=0;i<userR.size();i++)
        	System.out.println(userR.get(i).user()+" "+userR.get(i).consumed());
    	return userR;
    }
    @Override
    public void timed_handle_user(String name){
    	User user=new User(name);
    	userList.add(user);
    }
    @Override
    public void timed_handle_job(String[] cmd){
    	
    	String jobname=cmd[1];
    	Project project=null;
    	User user=null;
    	int time=0;
    	 Object search = trie.search(cmd[2]);
         if (search != null) {
            project=(Project) (((TrieNode) search).getValue());
            time=Integer.parseInt(cmd[4]);
            
         } 
        if(project==null) {
         
         return;
        }
    	int i=0;
    	while(i<userList.size()) {
    		if(userList.get(i).name.compareTo(cmd[3])==0) {
    			user=userList.get(i);
    		}
    		i++;
    	}
    	if(user==null) {
    		
    		return;
    	}
    	Job job =new Job(jobname, project, user, time);
    	job.arrivaltime=globalTime;
    	project.jobList.add(job);
    	tree.insert(job.project.name, job);
    	utree.insert(job.user.name, job);
    	heap.insert(job);
    	allJobs.add(job);
    }
    @Override
    public void timed_handle_project(String[] cmd){
    	String name=cmd[1];
    	int priority=Integer.parseInt(cmd[2]);
    	int budget=Integer.parseInt(cmd[3]);
    	
    	Project project =new Project(name,priority,budget);
    	trie.insert(name,project);
    }
    @Override
    public void timed_run_to_completion(){
    	Job job=heap.extractMax();
    	while(job!=null) {

    	
    	while(job!=null &&  !(job.runtime<job.project.budget||job.runtime==job.project.budget)) {

			job.project.unfinished.add(job);
			incompleteJobs.add(job);
			job=heap.extractMax();
    		}
    	
    	if(job!=null) {
    		completedJobs.add(job);
    		jobsdone++;
    		job.complete=true;
    		globalTime+=job.runtime;
    		job.completedTime=globalTime;
    		job.project.budget=job.project.budget-job.runtime;
    		job.user.budgetConsumed+=job.runtime;
    		job.user.maxT=job.completedTime;
    		job=heap.extractMax();
    	}
    	}
    }

    @Override
    public void timed_flush(int waittime) {
    	
    	int g=globalTime;
    	MaxHeap<Job> temp=new MaxHeap<Job>();
    	while(heap.size()!=0) {
    		Job job=heap.extractMax();
    		if(g-job.arrivaltime>=waittime && job.runtime<=job.project.budget) {
    			jobsdone++;
    			job.complete=true;
    			globalTime+=job.runtime;
    			job.completedTime=globalTime;
    			job.project.budget=job.project.budget-job.runtime;
    			job.user.budgetConsumed+=job.runtime;
    			job.user.maxT=job.completedTime;
    			completedJobs.add(job);
    			System.out.println(job.name+" "+job.user.name+job.arrivaltime+" "+job.completedTime);
    		}
    		else {
    			temp.insert(job);
    		}
    	}
    	heap=temp;
    	
    	
    	}
    	
    
    

    private ArrayList<JobReport_> handle_new_priority(String s) {
 //   	System.out.println("Priority query");
    	ArrayList<JobReport_> jobR=new ArrayList<>();
    	int size=allJobs.size();
    	for(int i=0;i<size;i++) {
    		Job job=allJobs.get(i);
    		if(job.complete==false && job.priority>=Integer.parseInt(s)) {
    			jobReport jr=new jobReport(job);
    			jobR.add(jr);
    		}
    	}
    	for(int i=0;i<jobR.size();i++)
    		System.out.println("Job{user='"+jobR.get(i).user()+"', project='"+", end_time="+jobR.get(i).completion_time()+"'}");
    		return jobR;
    }

    private ArrayList<JobReport_> handle_new_projectuser(String[] cmd) {
   // 	System.out.println("Project user query");
    	ArrayList<JobReport_> jobR=new ArrayList<>();

    	RedBlackNode<String,Job> node=tree.search(cmd[1]);
    	if(node.getValues() != null) {
    		for(int i=0;i<node.getValues().size();i++) {
    			Job job=node.getValues().get(i);
    			int a=job.arrivaltime;
    			if(job.user.name.equals(cmd[2]) && a>=Integer.parseInt(cmd[3]) && a<=Integer.parseInt(cmd[4])) {
    				jobReport jr=new jobReport(job);
    				jobR.add(jr);
    			}
    		}
    	}
//    	if(jobR.isEmpty())
//    		return jobR;
//    	return sort(jobR);
    	ArrayList<JobReport_> jobRi=sort(jobR);
    	for(int i=0;i<jobRi.size();i++)
    	System.out.println("Job{user='"+jobRi.get(i).toString()+"', project='"+", end_time="+jobRi.get(i).completion_time()+"'}");
    	return jobRi;
        	
        }
    
    private ArrayList<JobReport_> sort(ArrayList<JobReport_> jobR) {
    	ArrayList<jobReport> unf=new ArrayList<>();
    	ArrayList<JobReport_> f=new ArrayList<>();
    	for(int i=0;i<jobR.size();i++) {
    		jobReport jr=(jobReport) jobR.get(i);
    		if(jr.comptime==0)
    			unf.add(jr);
    		else
    			f.add(jr);
    	}
    	
    	for(int i=0;i<f.size();i++) {
			jobReport temp;
			
			for(int j=i+1;j<f.size();j++) {
				if(f.get(i).completion_time() > f.get(j).completion_time()) {
					temp=(jobReport) f.get(i);
					f.set(i, f.get(j));
					f.set(j, temp);
				}
			}
		}
    	for(int i=0;i<unf.size();i++) {
    		f.add(unf.get(i));
    	}
    	return f;
    	
    	
    }

    private ArrayList<JobReport_> handle_new_user(String[] cmd) {
    	System.out.println("User query");
    	ArrayList<JobReport_> jobR=new ArrayList<>();
    	RedBlackNode<String,Job> node=utree.search(cmd[1]);
    	if(node.getValues() != null) {
    		for(int i=0;i<node.getValues().size();i++) {
    			Job job=node.getValues().get(i);
    			int a=job.arrivaltime;
    			if(a>=Integer.parseInt(cmd[2]) && a<=Integer.parseInt(cmd[3])) {
    				jobReport jr=new jobReport(job);
    				jobR.add(jr);
    			}
    		}
    	}
    	for(int i=0;i<jobR.size();i++)
    		System.out.println("Job{user='"+jobR.get(i).toString()+"', project='"+", end_time="+jobR.get(i).completion_time()+"'}");
    	
    		return jobR;
    	

    	
    }

    private ArrayList<JobReport_> handle_new_project(String[] cmd) {
    	System.out.println("Project query");
    	ArrayList<JobReport_> jobR=new ArrayList<>();
    	RedBlackNode<String,Job> node=tree.search(cmd[1]);
    	if(node.getValues() != null) {
    		for(int i=0;i<node.getValues().size();i++) {
    			Job job=node.getValues().get(i);
    			int a=job.arrivaltime;
    			if(a>=Integer.parseInt(cmd[2]) && a<=Integer.parseInt(cmd[3])) {
    				jobReport jr=new jobReport(job);
    				jobR.add(jr);
    			}
    		}
    	}
    	for(int i=0;i<jobR.size();i++)
    		System.out.println("Job{user='"+jobR.get(i).toString()+"', project='"+", end_time="+jobR.get(i).completion_time()+"'}");
    	
		return jobR;
    }




    public void schedule() {
            execute_a_job();
    }

    public void run_to_completion() {

    	Job job=heap.extractMax();
    	while(job!=null) {
    	System.out.println("Running code");
    	
    	
    	System.out.println("Remaining jobs: "+(heap.size()+1));
    	
    	while(job!=null &&  !(job.runtime<job.project.budget||job.runtime==job.project.budget)) {
    		
    		System.out.println("Executing: "+job.name+" from: "+job.project.name);
    		System.out.println("Un-sufficient budget.");
			job.project.unfinished.add(job);
			incompleteJobs.add(job);
	//		tree.insert(job.project.name, job);
			job=heap.extractMax();
    		}
    	
    	if(job!=null) {
    		completedJobs.add(job);
    		jobsdone++;
    		job.complete=true;
    		globalTime+=job.runtime;
    		job.completedTime=globalTime;
    		job.project.budget=job.project.budget-job.runtime;
    		job.user.budgetConsumed+=job.runtime;
    		job.user.maxT=job.completedTime;
    		System.out.println("Executing: "+job.name+" from: "+job.project.name);
    		System.out.println("Project: "+job.project.name+" budget remaining: "+job.project.budget);
    		System.out.println("System execution completed");
    		job=heap.extractMax();
    	}
    	}
    }

    public void print_stats() {
    	System.out.println("--------------STATS---------------");
    	System.out.println("Total jobs done: "+jobsdone);
    	for(int i=0;i<completedJobs.size();i++) {
    		Job job=completedJobs.get(i);
    		System.out.println("Job{user='"+job.user.name+"', project='"+job.project.name+"', jobstatus=COMPLETED, execution_time="+job.runtime+", end_time="+job.completedTime+", name='"+job.name+"'}");
    	}
    	System.out.println("------------------------");
    	System.out.println("Unfinished jobs:");
    	int s=incompleteJobs.size();
    	for(int i=0;i<incompleteJobs.size();i++) {
    		Job job=incompleteJobs.get(i);
    		System.out.println("Job{user='"+job.user.name+"', project='"+job.project.name+"', jobstatus=REQUESTED, execution_time="+job.runtime+", end_time="+null+", name='"+job.name+"'}");
    		
    	}
    	System.out.println("Total unfinished jobs: "+s+" ");
    	System.out.println("--------------STATS DONE---------------");

    }

    public void handle_add(String[] cmd) {
    	System.out.println("ADDING Budget");
    	Project project=null;
    	Object search = trie.search(cmd[1]);
        if (search != null) {
           project=(Project) (((TrieNode) search).getValue());
         } 
        if(project!=null) {
        project.budget=project.budget+Integer.parseInt(cmd[2]);
        for(int i=0;i<project.unfinished.size();i++) {
        	heap.insert(project.unfinished.get(i));
            for(int j=0;j<incompleteJobs.size();j++) {
            	if(incompleteJobs.get(j).project.name.compareTo(project.name)==0) {
            		incompleteJobs.remove(j);
            	}
            }
        	
        }
        project.unfinished=new ArrayList<>();
    }
    }

    public void handle_empty_line() {
       schedule();
    }


    public void handle_query(String key) {
    	System.out.println("Querying");
    	for(int i=0;i<allJobs.size();i++) {
    		Job job=allJobs.get(i);
    		
    		if(job.name.equals(key)) {
    			if(job.complete) {
    				System.out.println(job.name+": COMPLETED");
    			}
    			else
    				System.out.println(job.name+": NOT FINISHED");
    			return;
    		}
    	}
    	System.out.println(key+": NO SUCH JOB");
    }

    public void handle_user(String name) {
    	System.out.println("Creating user");
    	User user=new User(name);
    	userList.add(user);
    }

    public void handle_job(String[] cmd) {
    	System.out.println("Creating job");
    	String jobname=cmd[1];
    	Project project=null;
    	User user=null;
    	int time=0;
    	 Object search = trie.search(cmd[2]);
         if (search != null) {
            project=(Project) (((TrieNode) search).getValue());
            time=Integer.parseInt(cmd[4]);
            
         } 
        if(project==null) {
         System.out.println("No such project exists. "+cmd[2]);
         return;
        }
    	int i=0;
    	while(i<userList.size()) {
    		if(userList.get(i).name.compareTo(cmd[3])==0) {
    			user=userList.get(i);
    		}
    		i++;
    	}
    	if(user==null) {
    		System.out.println("No such user exists: "+cmd[3]);
    		return;
    	}
    	Job job =new Job(jobname, project, user, time);
    	job.arrivaltime=globalTime;
    	project.jobList.add(job);
    	tree.insert(job.project.name, job);
    	utree.insert(job.user.name, job);
    	heap.insert(job);
    	allJobs.add(job);
    }

    public void handle_project(String[] cmd) {
    	System.out.println("Creating project");
    	String name=cmd[1];
    	int priority=Integer.parseInt(cmd[2]);
    	int budget=Integer.parseInt(cmd[3]);
    	
    	Project project =new Project(name,priority,budget);
    	trie.insert(name,project);
    }

    public void execute_a_job() {
    	System.out.println("Running code");
    	
    	Job job=heap.extractMax();
    	System.out.println("Remaining jobs: "+(heap.size()+1));
    	
    	while(job!=null ) {
    		System.out.println("Executing: "+job.name+" from: "+job.project.name);
    		if(job.runtime<job.project.budget||job.runtime==job.project.budget) {
    			completedJobs.add(job);
    			jobsdone++;
    			job.complete=true;
    			globalTime+=job.runtime;
    			job.completedTime=globalTime;
    			job.project.budget=job.project.budget-job.runtime;
    			job.user.budgetConsumed+=job.runtime;
    			job.user.maxT=job.completedTime;
    			System.out.println("Project: "+job.project.name+" budget remaining: "+job.project.budget);
        		System.out.println("Execution cycle completed");
    			break;
    		}
    		else {
    			System.out.println("Un-sufficient budget.");
    			job.project.unfinished.add(job);
    			incompleteJobs.add(job);
    	//		tree.insert(job.project.name, job);
    		}
    		job=heap.extractMax();
    	}
    	
    }
    
    public void sortt(ArrayList<Job> list) {
    	int size=list.size();
    	for(int i=0;i<size;i++) {
    		for(int j=i+1;j<size;j++) {
    			Job x=list.get(i);
    			Job y=list.get(j);
    			if(x.project.priority<y.project.priority) {
    				list.set(i, y);
    				list.set(j, x);
    			}
    			else if(x.project.priority==y.project.priority && x.project.enter>y.project.enter) {
    				list.set(i, y);
					list.set(j, x);
    			}
    			else if(x.project.name.equals(y.project.name)) {
    				if(x.x>y.x) {
    					list.set(i, y);
    					list.set(j, x);
    				}
    			}
    		}
    	}
    }
}

