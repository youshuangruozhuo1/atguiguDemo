package com.atguigu.lxl;

import java.util.Random;

public class RandomNumberTest {
    public static void main(String[] args) {


        test4();

    }


    public static void test() {
        int[] nums = new int[6];


        for (int i = 0; i < nums.length; i++) {
            nums[i] = (int) (Math.random() * (30 - 24) + 24);
            for (int j = 0; j < i; j++) {
                if (nums[i] == nums[j]) {
                    i--;
                    break;
                }
            }
        }


        for (int num : nums) {
            System.out.println(num);
        }
    }

    public static void test1() {
        String[] arr = new String[]{"EE", "DD", "CC", "BB", "AA"};


        for (int i = 0; i < arr.length / 2; i++) {
            String temp = arr[i];
            arr[i] = arr[arr.length - i - 1];
            arr[arr.length - i - 1] = temp;
        }

        for (int i = 0, j = arr.length - 1; i < j; i++, j--) {
            String temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }

        for (String s : arr) {
            System.out.print(s + "\t");
        }

    }

    public static void test2() {
        String[] arr = new String[]{"EE", "DD", "CC", "BB", "AA"};

        String dest = "BB";
        dest = "QQ";

        boolean flag = false;

        int i = 0;
        for (String s : arr) {
            if (dest.equals(s)) {
                flag = true;
                System.out.println("找到了在:" + i);
                break;
            }
            i++;
        }

        if (!flag) {
            System.out.println("找不到");
        }

    }

    public static void test3() {
        //二分法查找 :
        //前提:所要查找的数组 必须有序
        int[] arr = {-100, -20, -10, -5, 0, 3, 5, 7, 8, 10, 30, 36, 99, 150, 190, 320, 335};
        int dest = 30;

        int start = 0;   //起始 下标
        int end = arr.length - 1;   //末尾下标

        while (start <= end) {
            int index = (start + end) / 2;
            if (dest == arr[index]) {
                System.out.println("找到了在:" + index);
                break;
            } else if (arr[index] > dest) {
                end = index - 1;
            } else if (arr[index] < dest) {
                start = index + 1;
            }
        }

    }

    public static void test4(){
        int [] arr = {43,32,76,-98,-50,0,54,63,58,74,12,368,542};
        System.out.println("排序前:");
        for (int i : arr) {
            System.out.print(i+"\t");
        }
        System.out.println();
        //冒泡排序
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j+1]){
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
        System.out.println("从小到大排序后:");
        for (int i : arr) {
            System.out.print(i+"\t");
        }
        System.out.println();
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] < arr[j+1]){
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
        System.out.println("从大到小排序后:");
        for (int i : arr) {
            System.out.print(i+"\t");
        }


    }
}
