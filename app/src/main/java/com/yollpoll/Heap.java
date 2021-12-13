package com.yollpoll;

/**
 * Created by spq on 2021/12/6
 * 堆相关
 */
public class Heap {
    /**
     * 建立大根堆
     *
     * @param num
     * @return
     */
    public static int[] buildBigHeap(int... num) {
        int lastIndex = (num.length / 2) - 1;
        for (int i = lastIndex; i >= 0; i--) {
            adjustBigHeap(i, num);
        }
        return num;
    }

    //堆化为大根堆
    public static int[] adjustBigHeap(int i, int... num) {
        if (i >= num.length) {
            return num;
        }
        if (num[i * 2 + 1] > num[i * 2 + 2]) {
            if (num[i * 2 + 1] > num[i]) {
                int temp = num[i];
                num[i] = num[i * 2 + 1];
                num[i * 2 + 1] = temp;
                adjustBigHeap(i * 2 + 1, num);
            }
        } else {
            if (num[i * 2 + 2] > num[i]) {
                int temp = num[i];
                num[i] = num[i * 2 + 2];
                num[i * 2 + 2] = temp;
                adjustBigHeap(i * 2 + 2, num);
            }
        }
        return num;
    }
}
