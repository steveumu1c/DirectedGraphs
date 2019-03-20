package directedGraph;

/*Finally checked exception classes should be defined for the cases where a cycle occurs and when an
invalid class name is specified.*/
public class CycleDiscoveredException extends Exception {

    public CycleDiscoveredException() {
       super("Directed Graph Cycle Occurred");
    }
}