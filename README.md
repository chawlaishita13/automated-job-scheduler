#README

TRIE
Person 
getName= returns name of person
compareTo= compares persons based on their names
Trie
insert= inserts a new element in trie
search= searches for the required element in trie
startsWith= checks if any word with given prefix is present or not and returns the last visited node
printTrie= prints all the nodes that have the given prefix
delete= deletes the required node from trie 
print= prints all the levels of trie
printLevel= prints a particular level in trie
TrieNode
getValue= returns the value associated node


PRIORITY QUEUE
MAXHEAP
insert =inserts the element in maxHeap by calling upheapify function in which if the new key is less than its parent, then we don’t need to do anything. Otherwise, we need to traverse up to fix the violated heap property.
search = searches for element in maxHeap 
extractMax = returns the maximum element present in maxHeap by calling downheapify function. 
STUDENT
compareTo = compares the students based on marks
getName = returns name of student

REDBLACK
RBTree
rightrotate= right rotates the node
leftrotate= left rotates the node
insert= inserts the element in red black tree and rotates and recolors accordingly to fix rb tree violation.
search= searches for the given element in the red black tree
RedBlackNode
colour=returns the colour of node
getValue= returns the value associated with node
getValues= returns the list associated with the nodes
compareTo=compares the nodes based on their values

PROJECT MANAGEMENT

completedJobs=list that contains all the jobs that have been executed
incompleteJob=list that conatains all the jobs which cannot be executed

JOB
compareTo = compares jobs based on their priority(similar to priority of their project)
PROJECT
constructor 
SCHEDULER_DRIVER
execute = checks which case is to be executed and performs accordingly
run_to_completion = it executes jobs till there are no jobs left, or no jobs can be executed because of budget issues.
print_stats = prints the whole list of successfully executed job and whole list of unexecuted jobs
schedule = checks if the job can be executed and adds to list of successfully executed job else it adds to list of unexecuted jobs 
handle_project = creates new project and adds to trie
handle_job = creates new job only if user and project exists and adds to maxHeap
handle_user= cretaes new user
handle_query= checks the status of job.
handle_empty_line= it tries to execute the job with highest priority (using schedule) if not possible then it prints unsufficient budget and further tries to find if we can execute the next job
handle_add= increases the budget of the given project
New_user= searched the utree RBTree and then returned the required jobs form the arraylist maintained there.
New_project= searched the tree RBTree and then returned the required jobs from the arraylist of the jobs maintained there.
New_projectuser= searched for the project in ‘projects’ and then through the jobs maintained in the arraylist, I searched for the user in it and then returned the required jobs from there.
New_priority= searched the tree RBTree and for every project, I am finding the jobs having priority >= the given priority and appending them to a maintained list and returning it.
Top_consumer=  According to conditions mentioned in the interface, extracted the maximum element for top number of times and returned it.
timed_flush=  executed jobs satisfying the wait time and sufficient budget condition and rest jobs were sent to a new heap which was finally sent to the original one . 
USER
compareTo = compares user based on their names


