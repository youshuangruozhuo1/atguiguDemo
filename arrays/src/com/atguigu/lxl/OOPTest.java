package com.atguigu.lxl;

import java.io.PrintStream;

/**
 * 一.Java面向对象的三条主线
 *    1.Java类及类的成员
 *    2.面向对象的三大特征 :
 *    3.其他关键字
 */
public class OOPTest {


    public static void main(String[] args) {
        int a = 10;
        int b = 10;

//        method(a,b);

        method2(a,b);


        System.out.println("a="+a);
        System.out.println("b="+b);




        int[] arr = new int[]{0,1,2,3};
        System.out.println(arr);            //地址值
        char[] arr1 = new char[]{'1','2','3'};
        System.out.println(arr1);           //123

    }

    public static void  method(int a ,int b){
        a*=10;
        b*=20;
        System.out.println("a="+a);
        System.out.println("b="+b);
        System.exit(0);
    }
    public static void  method2(int a ,int b){
        PrintStream ps = new PrintStream(System.out){
            @Override
            public void println(String x) {
                if ("a=10".equals(x)){
                    x = "a=100";
                }
                if ("b=10".equals(x)){
                    x = "b=200";
                }
                super.println(x);
            }
        };
        System.setOut(ps);
    }


}
