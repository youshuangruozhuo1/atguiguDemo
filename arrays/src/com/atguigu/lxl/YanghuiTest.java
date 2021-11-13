package com.atguigu.lxl;

public class YanghuiTest {

    public static void main(String[] args) {
        //声明并初始化二维数组
        int[][] yangHui = new int[10][];

        //赋值
        for (int i = 0; i < yangHui.length; i++) {
            //从小到大 每个数组长度是上一个数组长度 +1
            yangHui[i] = new int[i+1];

            yangHui[i][0] = yangHui[i][i] = 1;


            for (int j = 1 ; j <yangHui[i].length - 1;j++){
                yangHui[i][j] = yangHui[i-1][j-1] + yangHui[i-1][j];
            }

        }

        for (int i = 0; i < yangHui.length; i++) {
            for (int j = 0; j < yangHui[i].length; j++) {
                System.out.print(yangHui[i][j]+" ");
            }
            System.out.println();
        }


    }
}
