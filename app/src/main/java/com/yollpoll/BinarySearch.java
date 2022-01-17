package com.yollpoll;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by spq on 2021/12/31
 */
public class BinarySearch {
    public static void main(String[] args) {
        System.out.println("367: " + isPerfectSquare(5));
    }

    /**
     * leetCode367
     *
     * @param num
     * @return
     */
    public static boolean isPerfectSquare(int num) {
        int l = 1, r = num;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (num / mid == mid && num % mid == 0) {
                return true;
            } else if (num / mid > mid) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }

        return (num / l == l) && num % l == 0;
    }
}
