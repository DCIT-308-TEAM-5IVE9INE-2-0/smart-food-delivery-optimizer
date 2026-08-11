package edu.ug.smartdelivery.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.ug.smartdelivery.model.Road;
import org.junit.jupiter.api.Test;

class StudentIdParameterServiceTest {
    @Test
    void calculatesParametersFromAllConfirmedMemberIds() {
        StudentIdParameters parameters = new StudentIdParameterService().calculateParameters();

        assertEquals(17, parameters.studentIds().length);
        assertEquals(461, parameters.digitSum());
        assertEquals(967, parameters.lastTwoDigitSum());
        assertEquals(378204167L, parameters.fullIdSum());
        assertEquals(67, parameters.finalDigitSum());
        assertEquals(2, parameters.priorityWeight());
        assertEquals(2, parameters.routePenalty());
        assertEquals(71, parameters.hashTableInitialSize());
        assertEquals(204167L, parameters.randomDataSeed());
        assertEquals(17, parameters.dynamicProgrammingCapacity());
    }

    @Test
    void calculationIsDeterministicForSameIds() {
        StudentIdParameterService service = new StudentIdParameterService();

        StudentIdParameters first = service.calculateParameters();
        StudentIdParameters second = service.calculateParameters();

        assertEquals(first.priorityWeight(), second.priorityWeight());
        assertEquals(first.routePenalty(), second.routePenalty());
        assertEquals(first.hashTableInitialSize(), second.hashTableInitialSize());
        assertEquals(first.randomDataSeed(), second.randomDataSeed());
        assertEquals(first.dynamicProgrammingCapacity(), second.dynamicProgrammingCapacity());
    }

    @Test
    void studentIdsAreDefensivelyCopied() {
        StudentIdParameters parameters = new StudentIdParameterService().calculateParameters();
        int[] studentIds = parameters.studentIds();

        studentIds[0] = 1;

        assertEquals(22020618, parameters.studentIds()[0]);
    }

    @Test
    void appliesParametersToPriorityAndRouteScores() {
        StudentIdParameterService service = new StudentIdParameterService();
        StudentIdParameters parameters = service.calculateParameters();
        Road road = new Road(1, 1, 2, 0.7, 5, 1.2, true);

        assertEquals(10, service.priorityDispatchScore(5, parameters));
        assertEquals(8.0, service.routeCost(road, parameters));
    }

    @Test
    void rejectsMissingStudentIds() {
        StudentIdParameterService service = new StudentIdParameterService();

        assertThrows(IllegalArgumentException.class, () -> service.calculateParameters(new int[0]));
    }

    @Test
    void rejectsInvalidStudentIds() {
        StudentIdParameterService service = new StudentIdParameterService();

        assertThrows(IllegalArgumentException.class, () -> service.calculateParameters(new int[] {22020618, 0}));
    }
}
