/**
 * CCDSALG MCO1
 * 
 * Algorithms Implemented:
 *  - Merge Sort 
 *  - Heap Sort 
 * 
 * Gagala, Maglente, Osena, Valdez
 */

import java.util.*;

public class Main {

    /** ========== MAIN FUNCTION ========== */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter DNA string (a, c, g, t only): ");
        String text = sc.nextLine().trim().toLowerCase();

        int n = text.length();
        Integer[] indicesMerge = new Integer[n];
        Integer[] indicesHeap = new Integer[n];
        for (int i = 0; i < n; i++) {
            indicesMerge[i] = i;
            indicesHeap[i] = i;
        }

        // --- prints all suffixes ---
        System.out.println("\nAll suffixes:");
        for (int i = 0; i < n; i++) {
            System.out.println(i + ": " + text.substring(i));
        }

        // --- merge sort ---
        long startMerge = System.nanoTime();
        mergeSort(indicesMerge, text, 0, n - 1);
        long endMerge = System.nanoTime();

        System.out.println("\nSorted suffixes (Merge Sort):");
        for (int i : indicesMerge) {
            System.out.println(i + ": " + text.substring(i));
        }

        System.out.print("\nSuffix Array (Merge Sort): ");
        for (int i : indicesMerge) System.out.print(i + " ");
        System.out.println();

        // --- heap sort ---
        long startHeap = System.nanoTime();
        heapSort(indicesHeap, text);
        long endHeap = System.nanoTime();

        System.out.println("\nSorted suffixes (Heap Sort):");
        for (int i : indicesHeap) {
            System.out.println(i + ": " + text.substring(i));
        }

        System.out.print("\nSuffix Array (Heap Sort): ");
        for (int i : indicesHeap) System.out.print(i + " ");
        System.out.println();

        // --- results ---
        double mergeTime = (endMerge - startMerge) / 1e6;
        double heapTime = (endHeap - startHeap) / 1e6;
        System.out.printf("\nExecution Time:\nMerge Sort: %.3f ms\nHeap Sort: %.3f ms\n", mergeTime, heapTime);

        // --- empirical tests ---
        runEmpiricalTest();
    }


    /** ========== MERGE SORT ========== */
    public static void mergeSort(Integer[] arr, String text, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(arr, text, left, mid);
            mergeSort(arr, text, mid + 1, right);
            merge(arr, text, left, mid, right);
        }
    }

    /** ========== HEAP SORT ========== */
    public static void heapSort(Integer[] arr, String text) {
        int n = arr.length;

        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(arr, text, n, i);

        for (int i = n - 1; i > 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            heapify(arr, text, i, 0);
        }
    }

    /** ========== EMPIRICAL TESTING AND UTITILITY FUNCTIONS ========== */
    public static void runEmpiricalTest() {
        int[] sizes = {128, 256, 512, 1024, 2048};
        int k = 10;

        System.out.println("\nEmpirical Running Time Analysis");
        System.out.println("n\tAlgorithm\tAvg. Time (ms)\tSample Size");


