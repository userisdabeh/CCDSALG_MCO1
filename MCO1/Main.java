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

    /* ============== MERGE FUNCTION ============== */
    public static void merge(Integer[] arr, String text, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        Integer[] leftArr = new Integer[n1];
        Integer[] rightArr = new Integer[n2];

        for (int i = 0; i < n1; i++) {
            leftArr[i] = arr[left + i];
        }

        for (int i = 0; i < n2; i++) {
            rightArr[i] = arr[mid + 1 + i];
        }

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            String leftSuffix = text.substring(leftArr[i]);
            String rightSuffix = text.substring(rightArr[j]);

            if (leftSuffix.compareTo(rightSuffix) <= 0) {
                arr[k] = leftArr[i];
                i++;
            } else {
                arr[k] = rightArr[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = leftArr[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = rightArr[j];
            j++;
            k++;
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

    /** ========== HEAPIFY ========== */
    private static void heapify(Integer[] arr, String text, int n, int i) {
        int largest = i;  
        int left = 2 * i + 1;   
        int right = 2 * i + 2;  
    
    
        if (left < n) {
           String leftSuffix = text.substring(arr[left]);
           String currentSuffix = text.substring(arr[largest]);

           if (leftSuffix.compareTo(currentSuffix) > 0) {
               largest = left;
           }
        }
    
        if (right < n) {
           String rightSuffix = text.substring(arr[right]);
           String currentSuffix = text.substring(arr[largest]);

           if (rightSuffix.compareTo(currentSuffix) > 0) {
               largest = right;
           }
        }
    
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
        
            heapify(arr, text, n, largest);
        }
    }

    /** ========== EMPIRICAL TESTING AND UTITILITY FUNCTIONS ========== */

    public static void runEmpiricalTest() {
        int[] sizes = {128, 256, 512, 1024, 2048};
        int k = 10;
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("Empirical Running Time Analysis");
        System.out.println("=".repeat(70));
        System.out.printf("%-10s %-20s %-20s %-15s%n", "n", "Algorithm", "Avg. Time (ms)", "Sample Size k");
        System.out.println("-".repeat(70));
        
        for (int n : sizes) {
            double totalMergeTime = 0, totalHeapTime = 0;
            
            for (int trial = 0; trial < k; trial++) {
                String testString = generateRandomDNAString(n);
                
                totalMergeTime += measureMergeSortTime(testString);
                totalHeapTime += measureHeapSortTime(testString);
            }
            
            double avgMergeTime = totalMergeTime / k, avgHeapTime = totalHeapTime / k;
            
            System.out.printf("%-10d %-20s %-20.3f %-15d%n", n, "Merge Sort", avgMergeTime, k);
            System.out.printf("%-10d %-20s %-20.3f %-15d%n", n, "Heap Sort", avgHeapTime, k);
        }
        
        System.out.println("=".repeat(70));
        System.out.println("Test complete!");
    }

    public static String generateRandomDNAString(int length) {
        Random rand = new Random();
        char[] dna = {'a', 'c', 'g', 't'};
        StringBuilder sb = new StringBuilder(length);
        
        for (int i = 0; i < length; i++) {
            sb.append(dna[rand.nextInt(4)]);
        }
        
        return sb.toString();
    }
    
    public static double measureMergeSortTime(String text) {
        int n = text.length();
        Integer[] indices = new Integer[n];
        for (int i = 0; i < n; i++) {
            indices[i] = i;
        }
        
        long startTime = System.nanoTime();
        mergeSort(indices, text, 0, n - 1);
        long endTime = System.nanoTime();
        return (endTime - startTime) / 1e6;
    }

    public static double measureHeapSortTime(String text) {
        int n = text.length();
        Integer[] indices = new Integer[n];
        for (int i = 0; i < n; i++) {
            indices[i] = i;
        }
        
        long startTime = System.nanoTime();
        heapSort(indices, text);
        long endTime = System.nanoTime();
        return (endTime - startTime) / 1e6;
    }
}
