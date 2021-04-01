package com.yollpoll;

/**
 * Created by spq on 2021/3/12
 */
public class Search {
    public static void main(String[] args) {
        int[] array=new int[]{1,3,5,6,7,32,42,64,88};
        System.out.println(BinarySearch(array,7)+"/二分查找");
    }

    /**
     * 二分查找
     */
    public static int BinarySearch(int[] nums,int target){
        if(nums.length==0)
            return -1;
        int left=0;
        int right=nums.length-1;
        while(left<right){
            int mid=left+(right-left)/2;
            if(nums[mid]<target){
                left=mid+1;
            }else if(nums[mid]>target){
                right=mid-1;
            }else {
                return  mid;
            }
        }
        if(nums[left]==target){
            return left;
        }else {
            return -1;
        }
    }
}
