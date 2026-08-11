package edu.ug.smartdelivery.service;

import edu.ug.smartdelivery.algorithm.graph.BreadthFirstSearch;
import edu.ug.smartdelivery.algorithm.graph.DepthFirstSearch;
import edu.ug.smartdelivery.algorithm.graph.DijkstraShortestPath;
import edu.ug.smartdelivery.algorithm.graph.KruskalMinimumSpanningTree;
import edu.ug.smartdelivery.algorithm.graph.MinimumSpanningTreeResult;
import edu.ug.smartdelivery.algorithm.graph.PrimMinimumSpanningTree;
import edu.ug.smartdelivery.algorithm.graph.ShortestPathResult;
import edu.ug.smartdelivery.algorithm.graph.TraversalResult;
import edu.ug.smartdelivery.datastructure.graph.AdjacencyListGraph;
import edu.ug.smartdelivery.datastructure.graph.CustomGraph;
import edu.ug.smartdelivery.datastructure.graph.GraphVertex;
import edu.ug.smartdelivery.model.Location;
import edu.ug.smartdelivery.model.Road;

public class RouteService {
    private final StudentIdParameterService studentIdParameterService;

    public RouteService() {
        this(new StudentIdParameterService());
    }

    public RouteService(StudentIdParameterService studentIdParameterService) {
        if (studentIdParameterService == null) {
            throw new IllegalArgumentException("studentIdParameterService cannot be null");
        }
        this.studentIdParameterService = studentIdParameterService;
    }

    public AdjacencyListGraph buildGraph(Location[] locations, Road[] roads) {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        StudentIdParameters parameters = studentIdParameterService.calculateParameters();
        for (Location location : locations) {
            graph.addVertex(new GraphVertex(location.locationId(), location.name()));
        }
        for (Road road : roads) {
            double weightedTravelCost = studentIdParameterService.routeCost(road, parameters);
            graph.addEdge(road.fromLocationId(), road.toLocationId(), weightedTravelCost, road.bidirectional());
        }
        return graph;
    }

    public TraversalResult reachableLocations(CustomGraph graph, int startLocationId) {
        return new BreadthFirstSearch().traverse(graph, startLocationId);
    }

    public TraversalResult depthTraversal(CustomGraph graph, int startLocationId) {
        return new DepthFirstSearch().traverse(graph, startLocationId);
    }

    public ShortestPathResult shortestRoute(CustomGraph graph, int sourceLocationId, int destinationLocationId) {
        return new DijkstraShortestPath().findShortestPath(graph, sourceLocationId, destinationLocationId);
    }

    public MinimumSpanningTreeResult primNetwork(CustomGraph graph, int startLocationId) {
        return new PrimMinimumSpanningTree().build(graph, startLocationId);
    }

    public MinimumSpanningTreeResult kruskalNetwork(CustomGraph graph) {
        return new KruskalMinimumSpanningTree().build(graph);
    }
}
