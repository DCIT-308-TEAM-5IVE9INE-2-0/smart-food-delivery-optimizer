package edu.ug.smartdelivery.service;

import edu.ug.smartdelivery.model.Road;

public class StudentIdParameterService {
    private static final int[] MEMBER_STUDENT_IDS = {
            22020618,
            22012447,
            22166686,
            22146249,
            22106332,
            22042260,
            22042713,
            22370501,
            22411093,
            22399487,
            22262272,
            22306912,
            22308781,
            22382964,
            22413798,
            22402374,
            22408680
    };

    public StudentIdParameters calculateParameters() {
        return calculateParameters(MEMBER_STUDENT_IDS);
    }

    public StudentIdParameters calculateParameters(int[] studentIds) {
        validateStudentIds(studentIds);
        int digitSum = sumDigits(studentIds);
        int lastTwoDigitSum = sumLastTwoDigits(studentIds);
        long fullIdSum = sumFullIds(studentIds);
        int finalDigitSum = sumFinalDigits(studentIds);

        int priorityWeight = (digitSum % 5) + 1;
        int routePenalty = (lastTwoDigitSum % 7) + 1;
        int hashTableInitialSize = nextPrimeAfter((int) (fullIdSum % 50) + 50);
        long randomDataSeed = fullIdSum % 1_000_000;
        int dynamicProgrammingCapacity = (finalDigitSum % 20) + 10;

        return new StudentIdParameters(
                studentIds,
                digitSum,
                lastTwoDigitSum,
                fullIdSum,
                finalDigitSum,
                priorityWeight,
                routePenalty,
                hashTableInitialSize,
                randomDataSeed,
                dynamicProgrammingCapacity
        );
    }

    public String formulaSummary(StudentIdParameters parameters) {
        return "Student-ID parameters: "
                + "priorityWeight=(digitSum % 5)+1=" + parameters.priorityWeight()
                + ", routePenalty=(lastTwoDigitSum % 7)+1=" + parameters.routePenalty()
                + ", hashTableInitialSize=nextPrimeAfter((fullIdSum % 50)+50)=" + parameters.hashTableInitialSize()
                + ", randomDataSeed=fullIdSum % 1000000=" + parameters.randomDataSeed()
                + ", dynamicProgrammingCapacity=(finalDigitSum % 20)+10=" + parameters.dynamicProgrammingCapacity();
    }

    public int priorityDispatchScore(int urgency) {
        return priorityDispatchScore(urgency, calculateParameters());
    }

    public int priorityDispatchScore(int urgency, StudentIdParameters parameters) {
        if (urgency < 0) {
            throw new IllegalArgumentException("urgency cannot be negative");
        }
        return urgency * parameters.priorityWeight();
    }

    public double routeCost(Road road) {
        return routeCost(road, calculateParameters());
    }

    public double routeCost(Road road, StudentIdParameters parameters) {
        if (road == null) {
            throw new IllegalArgumentException("road cannot be null");
        }
        return (road.travelTimeMinutes() * road.roadConditionWeight()) + parameters.routePenalty();
    }

    private void validateStudentIds(int[] studentIds) {
        if (studentIds == null || studentIds.length == 0) {
            throw new IllegalArgumentException("studentIds are required");
        }
        for (int studentId : studentIds) {
            if (studentId <= 0) {
                throw new IllegalArgumentException("student IDs must be positive");
            }
        }
    }

    private int sumDigits(int[] studentIds) {
        int sum = 0;
        for (int studentId : studentIds) {
            int value = studentId;
            while (value > 0) {
                sum += value % 10;
                value /= 10;
            }
        }
        return sum;
    }

    private int sumLastTwoDigits(int[] studentIds) {
        int sum = 0;
        for (int studentId : studentIds) {
            sum += studentId % 100;
        }
        return sum;
    }

    private long sumFullIds(int[] studentIds) {
        long sum = 0;
        for (int studentId : studentIds) {
            sum += studentId;
        }
        return sum;
    }

    private int sumFinalDigits(int[] studentIds) {
        int sum = 0;
        for (int studentId : studentIds) {
            sum += studentId % 10;
        }
        return sum;
    }

    private int nextPrimeAfter(int value) {
        int candidate = value + 1;
        while (!isPrime(candidate)) {
            candidate++;
        }
        return candidate;
    }

    private boolean isPrime(int value) {
        if (value < 2) {
            return false;
        }
        for (int divisor = 2; divisor * divisor <= value; divisor++) {
            if (value % divisor == 0) {
                return false;
            }
        }
        return true;
    }
}
