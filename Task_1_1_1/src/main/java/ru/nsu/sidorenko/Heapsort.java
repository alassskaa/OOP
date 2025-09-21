package ru.nsu.sidorenko;

/**
 * Класс heapsort, внутри которого реализована пирамидальная сортировка.
 */
public final class Heapsort {

    /**
     * Делает из массива двоичное дерево, в котором каждый родитель больше своих сыновей.
     *
     * @param arr - массив, который будет обработан
     * @param n - длина обрабатываемого массива
     * @param i - родитель, который обрабатывается в данный момент.
     */

    public static void heapify(int[] arr, int n, int i) {
        int maxid = i;
        int l = i * 2 + 1;
        int r = i * 2 + 2;
        if (l < n && arr[l] > arr[maxid]) {
            maxid = l;
        }
        if (r < n && arr[r] > arr[maxid]) {
            maxid = r;
        }

        if (maxid != i) {
            swap(arr, i, maxid);
            heapify(arr, n, maxid);
        }
    }

    /**
     * Реализация пирамидальной сортировки по возрастанию.
     *
     * @param arr - сортируемый массив
     */

    public static void heapsort(int[] arr) {
        int n = arr.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        for (int i = n - 1; i > 0; i--) {
            swap(arr, 0, i);
            heapify(arr, i, 0);
        }
    }


    /**
     * Меняет местами два элемента из массива arr, где эти элементы имеют индексы i и j.
     *
     * @param arr - массив, в котором будут поменяны местами два элемента
     * @param i - индекс первого элемента
     * @param j - индекс второго элемента
     */

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private Heapsort() {
        throw new UnsupportedOperationException("This class can not be instantiated");
    }
}
