package directedGraph;

import java.util.LinkedList;
import java.util.List;

public class VertexNode {
    private List<Integer> adjacencyList = new LinkedList<>();
    private int value;
    private boolean visited = false;
    private boolean finished = false;

    public VertexNode(int value) {
        this.value = value;
    }

    public void addEdge(int vertex) {
        adjacencyList.add(vertex);
    }

    public void Reset() {
        finished = false;
        visited = false;
    }

    public List<Integer> getAdjacencyList() {
        return adjacencyList;
    }

    public int getValue() {
        return value;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(value).append(": [");
        for (int i = 0; i < adjacencyList.size() ; i++) {
            sb.append(adjacencyList.get(i))
              .append((i < adjacencyList.size()-1) ? ", " : "");
        }
        sb.append("]");
        return sb.toString();
    }

}
