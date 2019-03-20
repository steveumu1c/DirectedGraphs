package directedGraph;

/*Finally checked exception classes should be defined for the cases where a cycle occurs and when an
invalid class name is specified.*/
public class ClassNameException extends Exception {

    public ClassNameException() {
        super("Invalid Class Name Specified");
    }
}
