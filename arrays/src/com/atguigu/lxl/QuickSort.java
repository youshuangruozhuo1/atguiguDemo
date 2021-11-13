package com.atguigu.lxl;

import java.util.Arrays;

public class QuickSort {

    private static void swap(int[] data,int i , int j){
        int temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    private static void subSort(int[] data , int start , int end){
        if (start < end){
            int base = data[start];
            int low = start;
            int high = end + 1;
            while (true){
                while (low < end && data[++low] - base <= 0)
                    ;
                while (high > start && data[--high] - base >= 0)
                    ;
                if (low < high){
                    swap(data,low,high);
                }else {
                    break;
                }
            }

            swap(data,start,high);

            subSort(data, start,high - 1);
            subSort(data, high + 1,end);
        }
    }


    public static void main(String[] args) {
        int [] arr = {43,32,76,-98,-50,0,54,63,58,74,12,368,542};
        System.out.println("排序前:"+ Arrays.toString(arr));

        subSort(arr,0,arr.length - 1);

        System.out.println("排序后:"+ Arrays.toString(arr));

    }

}
