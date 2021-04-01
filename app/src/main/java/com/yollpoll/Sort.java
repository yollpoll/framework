package com.yollpoll;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by spq on 2021/3/9
 */
public class Sort {
    public static void main(String[] args) {
        int[] test = new int[]{1, 3, 4, 5, 12, 64, 2, 0};
        fastSort(0, test.length - 1, test);
        System.out.println(Arrays.toString(test) + "/快速排序");

        int[] test2 = new int[]{1, 3, 4, 5, 12, 64, 2, 0};
        mergeSort(test2);
        System.out.println(Arrays.toString(test2) + "/归并排序");

        int[] array = new int[]{1, 3, 4, 5, 12, 64, 2, 0};
        int[] bubbleSort = bubbleSort(Arrays.copyOf(array, array.length));
        System.out.println(Arrays.toString(bubbleSort) + "/冒泡排序");

        System.out.println(Arrays.toString(selectionSort(Arrays.copyOf(array, array.length))) + "/选择排序");

        System.out.println(Arrays.toString(insertionSort(Arrays.copyOf(array, array.length))) + "/插入排序");

        System.out.println(Arrays.toString(countSort(Arrays.copyOf(array, array.length))) + "/计数排序");
    }

    //--------------------------------快速排序--------------------------------/

    /**
     * 快速排序
     * 快速排序的思想是找到每个数对应的index，取一个数组的首个数字，找到这个数字在这个数组中的index
     * 再以index为分割，分别找到左子数列第一个数的index和右子数列的第一个数的index
     * 分为两个部分，一个是递归，一个是getIndex。
     * 在递归中，left，right，array作为入参，左边界、右边界、数组。找到index以后,把index两边的数组继续处理
     *
     * @param left
     * @param right
     * @param array
     */
    private static void fastSort(int left, int right, int[] array) {
        if (left < right) {
            int site = getIndex(left, right, array);

            fastSort(left, site - 1, array);
            fastSort(site + 1, right, array);
        }
    }


    /**
     * getIndex中，首先将第一个数存为temp。然后比较array[right]和temp，如果array[right]>temp，则说明array[right]
     * 相对temp应在的位置没问题，right--,通过while循环一口气-到第一个小于temp的数，这个数此时位置错误，赋值给array[left]
     * 同时，开始left的循环检查，与right相似，left++，出现错误值就打断内层while，将值赋给另一边。
     * 当left==right,退出外层while，此时left==right，此时，将array[left]=temp赋值，并返回left
     *
     * @param left
     * @param right
     * @param array
     * @return
     */
    private static int getIndex(int left, int right, int[] array) {
        int temp = array[left];
        while (left < right) {


            while (left < right && array[right] > temp) {
                right--;
            }
            array[left] = array[right];

            while (left < right && array[left] < temp) {
                left++;
            }

            array[right] = array[left];
        }

        array[left] = temp;
        return left;
    }

    //--------------------------------归并排序--------------------------------/

    /**
     * 归并排序需要用到一个临时数组temp,思想是将一个数组分割，当分割到最小单元（一个数）以后再一次合并
     * 合并可以理解为合并两个有序数组。
     *
     * @param array
     */
    private static void mergeSort(int[] array) {
        int[] temp = new int[array.length];
        mergeSort(0, temp.length - 1, array, temp);
    }

    /**
     * 递归方法，找到left和right的中间点，把这个mid作为分界线，分别递归左边和右边
     * 然后合并merge
     *
     * @param left
     * @param right
     * @param array
     * @param temp
     */
    private static void mergeSort(int left, int right, int[] array, int[] temp) {
        if (left < right) {
            int mid = left + (right - left) / 2;

            mergeSort(left, mid, array, temp);
            mergeSort(mid + 1, right, array, temp);
            merge(left, mid, right, array, temp);
        }
    }

    /**
     * 可以理解成合并 left-mid，mid+1-right两个数组
     * 设置三个指针，i,j,k, i:左边数组指针，j:右边数组指针,k:temp数组中的指针
     * 当i<=mid同时j<=right，此时两个数组都没有遍历完，比较两个数组当前指针位置的值，
     * 小的加到temp数组中
     * 当其中一个遍历完成以后，如果另外一个没有遍历完，说明另外一个一定都比当前temp中的大，逐步加到temp中
     * 将temp中的数，从array[left]开始依次赋值
     *
     * @param left
     * @param mid
     * @param right
     * @param array
     * @param temp
     */
    private static void merge(int left, int mid, int right, int[] array, int[] temp) {
        int i = left;
        int j = mid + 1;
        int k = 0;
        while (i <= mid && j <= right) {
            if (array[i] <= array[j]) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
            }
        }
        while (i <= mid) {
            temp[k++] = array[i++];
        }
        while (j <= right) {
            temp[k++] = array[j++];
        }
        k = 0;
        while (left <= right) {
            array[left++] = temp[k++];
        }
    }

    //--------------------------------冒泡排序--------------------------------/

    /**
     * 每次比较相邻的两个数，前面的大就交换顺序，直到一轮将最大的沉到最后
     * 循环比较，每次将最大的沉到最后，取得结果
     *
     * @param array
     * @return
     */
    public static int[] bubbleSort(int[] array) {
        for (int i = array.length - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                if (array[j] > array[j + 1]) {
                    array[j] = array[j] + array[j + 1];
                    array[j + 1] = array[j] - array[j + 1];
                    array[j] = array[j] - array[j + 1];
                }
            }
        }
        return array;
    }

    //--------------------------------选择排序--------------------------------/

    /**
     * 选择排序,每一次循环选择一个数和最后一个值比较，大就交换。
     * 一次循环选出一个最大值
     *
     * @return
     */
    public static int[] selectionSort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = i; j < array.length; j++) {
                if (array[j] < array[i]) {
                    array[i] = array[i] + array[j];
                    array[j] = array[i] - array[j];
                    array[i] = array[i] - array[j];
                }
            }
        }
        return array;
    }
    //--------------------------------插入排序--------------------------------/

    /**
     * 插入排序，将一个数依次和一个有序队列比较。从尾（头）开始，如果比当前的大，就交换一下位置直到遇到比当前小的停止
     *
     * @param array
     * @return
     */
    public static int[] insertionSort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = i; j > 0; j--) {
                if (array[j - 1] > array[j]) {
                    array[j - 1] = array[j - 1] + array[j];
                    array[j] = array[j - 1] - array[j];
                    array[j - 1] = array[j - 1] - array[j];
                } else {
                    break;
                }
            }
        }
        return array;
    }
    //--------------------------------计数排序--------------------------------/

    public static int[] countSort(int[] array) {
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for (int i = 0; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
            if (array[i] < min) {
                min = array[i];
            }
        }
        int[] temp = new int[max + 1];
        for (int i = 0; i < max + 1; i++) {
            temp[i] = 0;
        }
        for (int i = 0; i < array.length; i++) {
            int count = temp[array[i]];
            count++;
            temp[array[i]] = count;
        }
        int cur = 0;
        for (int i = 0; i < temp.length; i++) {
            while (temp[i] > 0) {
                array[cur] = i;
                temp[i]--;
                cur++;
            }
        }
        return array;
    }
}

