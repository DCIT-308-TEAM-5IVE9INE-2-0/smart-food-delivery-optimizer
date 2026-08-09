package edu.ug.smartdelivery.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ug.smartdelivery.model.Location;
import edu.ug.smartdelivery.model.Road;
import org.junit.jupiter.api.Test;

class RouteServiceTest {
    @Test
    void buildsGraphFromLocationsAndRoadsUsingWeightedTravelCost() {
        Location[] locations = {
                new Location(1, "Legon Hall", "UG", "Hostel", 0, 0),
                new Location(2, "Bush Canteen", "UG", "Restaurant", 0, 0)
        };
        Road[] roads = {
                new Road(1, 1, 2, 0.7, 5, 1.2, true)
        };

        var graph = new RouteService().buildGraph(locations, roads);

        assertEquals(2, graph.vertexCount());
        assertEquals(2, graph.edgeCount());
        assertEquals(6.0, graph.neighborsOf(1)[0].weight());
    }
}
