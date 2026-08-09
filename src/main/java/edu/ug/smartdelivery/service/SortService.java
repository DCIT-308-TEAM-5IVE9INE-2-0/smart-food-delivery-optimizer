package edu.ug.smartdelivery.service;

import edu.ug.smartdelivery.algorithm.sort.InsertionSort;
import edu.ug.smartdelivery.algorithm.sort.MergeSort;
import edu.ug.smartdelivery.algorithm.sort.QuickSort;
import edu.ug.smartdelivery.algorithm.sort.SelectionSort;
import edu.ug.smartdelivery.model.Order;
import java.util.Comparator;

public class SortService {
    private final SelectionSort selectionSort = new SelectionSort();
    private final InsertionSort insertionSort = new InsertionSort();
    private final MergeSort mergeSort = new MergeSort();
    private final QuickSort quickSort = new QuickSort();

    public void selectionSortOrders(Order[] orders, Comparator<Order> comparator) {
        selectionSort.sort(orders, comparator);
    }

    public void insertionSortOrders(Order[] orders, Comparator<Order> comparator) {
        insertionSort.sort(orders, comparator);
    }

    public void mergeSortOrders(Order[] orders, Comparator<Order> comparator) {
        mergeSort.sort(orders, comparator);
    }

    public void quickSortOrders(Order[] orders, Comparator<Order> comparator) {
        quickSort.sort(orders, comparator);
    }
}
