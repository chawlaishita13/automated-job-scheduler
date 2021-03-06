package PriorityQueue;

public class Student implements Comparable<Student> {
    private String name;
    private Integer marks;

    public Student(String trim, int parseInt) {
    	name=trim;
    	marks=parseInt;
    }


    public int compareTo(Student student) {
        return student.marks-this.marks;
        //o<t 1
    }

    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
    	return "Student{name='"+name+"', marks="+marks+"}";
    }
}