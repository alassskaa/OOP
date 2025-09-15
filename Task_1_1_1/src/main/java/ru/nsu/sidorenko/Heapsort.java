package ru.nsu.sidorenko;

/**
 * Класс heapsort, внутри которого реализована пирамидальная сортировка.
 */
public class Heapsort
{
    /**
     * Меняет местами два элемента из массива arr, где эти элементы имеют индексы i и j.
     *
     * @param arr - массив, в котором будут поменяны местами два элемента
     * @param i - индекс первого элемента
     * @param j - индекс второго элемента
     */
    public static void swap(int[] arr, int i, int j)
    {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * Делает из массива двоичное дерево, в котором каждый родитель больше своих сыновей.
     *
     * @param arr - массив, который будет обработан
     * @param n - длина обрабатываемого массива
     * @param i - родитель, который обрабатывается в данный момент. Проверяется, больше ли
     * он своих сыновей. Если да, то ничего не меняется и мы переходим к следующему родителю.
     * Если нет, то он меняется местами со своим сыном, а затем происходит обработка его уже как
     * родителя нового узла.
     */

    public static void heapify(int[] arr, int n, int i)
    {
        int max_id = i, l = i * 2 + 1, r = i * 2 + 2;
        if (l < n && arr[l] > arr[max_id])
        {
            max_id = l;
        }
        if (r < n && arr[r] > arr[max_id])
        {
            max_id = r;
        }

        if (max_id != i)
        {
            swap(arr, i, max_id);
            heapify(arr, n, max_id);
        }
    }

    /**
     * Реализация пирамидальной сортировки по возрастанию.
     *
     * @param arr - сортируемый массив
     */

    public static void heapsort(int[] arr)
    {
        int n = arr.length;
        for (int i = n / 2 - 1; i >= 0; i--)
        {
            heapify(arr, n, i);
        }

        for (int i = n - 1; i > 0; i--)
        {
            swap(arr, 0, i);
            heapify(arr, i, 0);
        }
    }

    /**
     * Метод для тестируемых массивов.
     *
     * @param args - аргументы командной строки
     */

    public static void main(String[] args)
    {

    }
}