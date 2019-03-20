package directedGraph;

/*In addition to the main class that defines the GUI, a second class is needed to define the directed
graph. It should be a generic class allowing for a generic type for the vertex names. In this
application those names will be strings. The graph should be represented as an array list of vertices
that contain a linked list of their associated adjacency lists. The adjacency lists should be lists of
integers that represent the index rather than vertex name itself. A hash map should be used to
associate vertex names with their index in the list of vertices*/
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DirectedGraphMethods<T>{
    private final List<VertexNode> vertexList = new ArrayList<>();
    private final Map<T,Integer> vertexNames = new HashMap<>();

    private DirectedGraphMethods() {
    }

    public static DirectedGraphMethods<String> initializeGraph(String filename) throws IOException {
        DirectedGraphMethods<String> g = new DirectedGraphMethods<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] strings = line.split(" ");
                for (int i = 0; i < strings.length; i++) {
                    if (!g.vertexNames.containsKey(strings[i])) {
                        g.vertexNames.putIfAbsent(strings[i], g.vertexList.size());
                        g.vertexList.add(new VertexNode(g.vertexList.size()));
                    }
                    if (i > 0) {
                        g.drawEdge(g.vertexNames.get(strings[0]), g.vertexNames.get(strings[i]));
                    }
                }
            }
        }
        return g;
    }

    public void drawEdge(int srcIndex, int destIndex) {
        vertexList.get(srcIndex).addEdge(destIndex);
    }
//    The correct recompilation order is any topological order of the subgraph that emanates from the
//    specified vertex. Topological orders are not unique, but the one that is to be used for this program is
//    the one generated using a depth-first search of the graph. The algorithm for generating this
//    topological order is shown below:
//    depth_first_search(vertex s)
//    if s is discovered
//    throw cycle detected exception
//    if s is finished
//    return
//    mark s as discovered
//    for all adjacent vertices v
//    depth_first_search(v)
//    mark s as finished
//    push s onto the stack
    public String topologicalOrder(T className) throws ClassNameException, CycleDiscoveredException {
        if (!vertexNames.containsKey(className)) {
            throw new ClassNameException();
        }
        Stack<VertexNode> topStack = new Stack<>();
        Integer id = vertexNames.get(className);
        depthFirstSearch(id, topStack);
        StringBuilder sb = new StringBuilder();
        while(!topStack.isEmpty()) {
            sb.append(getKeyFromValue(topStack.pop().getValue())).append(" ");
        }
        resetVertexFlags();
        return sb.toString();
    }

    private void depthFirstSearch(int index, Stack<VertexNode> topStack) throws CycleDiscoveredException {
        VertexNode vertex = vertexList.get(index);

        if (vertex.isVisited()) { throw new CycleDiscoveredException(); }

        if (vertex.isFinished()) { return; }

        vertex.setVisited(true);
        for (int i = 0; i < vertex.getAdjacencyList().size(); i++) {
            depthFirstSearch(vertex.getAdjacencyList().get(i),topStack);
        }
        vertex.setFinished(true);
        topStack.push(vertex);
    }

    private T getKeyFromValue(int value){
        return vertexNames.entrySet().stream()
                .filter(entry -> value == (entry.getValue()))
                .map(Map.Entry::getKey).findFirst().orElse(null);
    }

    private void resetVertexFlags() {
        for (VertexNode vertex : vertexList) {
            vertex.Reset();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (VertexNode v : vertexList) {
            sb.append(v).append("\n");
        }
        return sb.toString();
    }

}

