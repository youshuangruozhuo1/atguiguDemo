package com.atguigu.lxl;

public class ArraysTest {

    public static void main(String[] args) {
        //这种情况会NPE,因为外层元素的初始化值为:null
        int arr[][][][] = new int[3][][][];
        System.out.println(arr[0][0]);






    }
}
