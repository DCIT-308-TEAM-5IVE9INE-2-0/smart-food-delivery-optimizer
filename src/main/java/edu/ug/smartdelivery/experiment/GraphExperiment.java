package edu.ug.smartdelivery.experiment;

import edu.ug.smartdelivery.algorithm.graph.BreadthFirstSearch;
import edu.ug.smartdelivery.algorithm.graph.DepthFirstSearch;
import edu.ug.smartdelivery.algorithm.graph.DijkstraShortestPath;
import edu.ug.smartdelivery.algorithm.graph.KruskalMinimumSpanningTree;
import edu.ug.smartdelivery.algorithm.graph.PrimMinimumSpanningTree;
import edu.ug.smartdelivery.datastructure.graph.AdjacencyListGraph;
import edu.ug.smartdelivery.datastructure.graph.GraphVertex;
import edu.ug.smartdelivery.util.MemoryTracker;
import edu.ug.smartdelivery.util.Timer;

public class GraphExperiment {
    private final BreadthFirstSearch breadthFirstSearch = new BreadthFirstSearch();
    private final DepthFirstSearch depthFirstSearch = new DepthFirstSearch();
    private final DijkstraShortestPath dijkstra = new DijkstraShortestPath();
    private final PrimMinimumSpanningTree prim = new PrimMinimumSpanningTree();
    private final KruskalMinimumSpanningTree kruskal = new KruskalMinimumSpanningTree();
    private final Timer timer = new Timer();
    private final MemoryTracker memoryTracker = new MemoryTracker();

    public ExperimentMeasurement[] run(int[] inputSizes, int trials) {
        ExperimentMeasurement[] results = new ExperimentMeasurement[inputSizes.length * trials * 5];
        int position = 0;
        for (int inputSize : inputSizes) {
            for (int trial = 1; trial <= trials; trial++) {
                AdjacencyListGraph graph = buildConnectedGraph(inputSize);
                results[position++] = measure("BFS", inputSize, trial, () -> breadthFirstSearch.traverse(graph, 1));
                results[position++] = measure("DFS", inputSize, trial, () -> depthFirstSearch.traverse(graph, 1));
                results[position++] = measure("Dijkstra", inputSize, trial, () -> dijkstra.findShortestPath(graph, 1, inputSize));
                results[position++] = measure("Prim MST", inputSize, trial, () -> prim.build(graph, 1));
                results[position++] = measure("Kruskal MST", inputSize, trial, () -> kruskal.build(graph));
            }
        }
        return results;
    }

    private AdjacencyListGraph buildConnectedGraph(int vertexCount) {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        for (int i = 1; i <= vertexCount; i++) {
            graph.addVertex(new GraphVertex(i, "Location " + i));
        }
        for (int i = 1; i < vertexCount; i++) {
            graph.addEdge(i, i + 1, 1 + (i % 7), true);
        }
        for (int i = 1; i + 3 <= vertexCount; i += 3) {
            graph.addEdge(i, i + 3, 2 + (i % 5), true);
        }
        return graph;
    }

    private ExperimentMeasurement measure(String algorithmName, int inputSize, int trial, Runnable operation) {
        double beforeMemory = memoryTracker.usedMemoryKb();
        timer.start();
        operation.run();
        long elapsed = timer.elapsedNanos();
        double memoryKb = Math.max(0, memoryTracker.usedMemoryKb() - beforeMemory);
        return new ExperimentMeasurement(algorithmName, inputSize, elapsed, memoryKb, trial);
    }
}
