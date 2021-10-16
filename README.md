# README

### TRIE

Person 

1) getName= returns name of person
2) compareTo= compares persons based on their names
Trie

1) insert= inserts a new element in trie
2) search= searches for the required element in trie
3) startsWith= checks if any word with given prefix is present or not and returns the last visited node
4) printTrie= prints all the nodes that have the given prefix
5) delete= deletes the required node from trie 
6) print= prints all the levels of trie
7) printLevel= prints a particular level in trie

TrieNode
1) getValue= returns the value associated node


### PRIORITY QUEUE

MAXHEAP

1) insert =inserts the element in maxHeap by calling upheapify function in which if the new key is less than its parent, then we don’t need to do anything. Otherwise, we need to traverse up to fix the violated heap property.
2) search = searches for element in maxHeap 
3) extractMax = returns the maximum element present in maxHeap by calling downheapify function. 

STUDENT

1) compareTo = compares the students based on marks
2) getName = returns name of student

### REDBLACK

RBTree

1) rightrotate= right rotates the node
2) leftrotate= left rotates the node
3) insert= inserts the element in red black tree and rotates and recolors accordingly to fix rb tree violation.
4) search= searches for the given element in the red black tree

RedBlackNode

1) colour=returns the colour of node
2) getValue= returns the value associated with node
3) getValues= returns the list associated with the nodes
4) compareTo=compares the nodes based on their values

### PROJECT MANAGEMENT

1) completedJobs=list that contains all the jobs that have been executed
2) incompleteJob=list that conatains all the jobs which cannot be executed

JOB
1) compareTo = compares jobs based on their priority(similar to priority of their project)

PROJECT
1) constructor 

SCHEDULER_DRIVER
1) execute = checks which case is to be executed and performs accordingly
2) run_to_completion = it executes jobs till there are no jobs left, or no jobs can be executed because of budget issues.
3) print_stats = prints the whole list of successfully executed job and whole list of unexecuted jobs
4) schedule = checks if the job can be executed and adds to list of successfully executed job else it adds to list of unexecuted jobs 
5) handle_project = creates new project and adds to trie
6) handle_job = creates new job only if user and project exists and adds to maxHeap
7) handle_user= cretaes new user
8) handle_query= checks the status of job.
9) handle_empty_line= it tries to execute the job with highest priority (using schedule) if not possible then it prints unsufficient budget and further tries to find if we can execute the next job
10) handle_add= increases the budget of the given project
11) New_user= searched the utree RBTree and then returned the required jobs form the arraylist maintained there.
12) New_project= searched the tree RBTree and then returned the required jobs from the arraylist of the jobs maintained there.
13) New_projectuser= searched for the project in ‘projects’ and then through the jobs maintained in the arraylist, I searched for the user in it and then returned the required jobs from there.
14) New_priority= searched the tree RBTree and for every project, I am finding the jobs having priority >= the given priority and appending them to a maintained list and returning it.
15) Top_consumer=  According to conditions mentioned in the interface, extracted the maximum element for top number of times and returned it.
16) timed_flush=  executed jobs satisfying the wait time and sufficient budget condition and rest jobs were sent to a new heap which was finally sent to the original one . 

USER

1) compareTo = compares user based on their names


