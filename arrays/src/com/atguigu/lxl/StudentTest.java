package com.atguigu.lxl;

public class StudentTest {


    public static void main(String[] args) {
        Student[] students = new Student[20];

        for (int i = 0; i < students.length; i++) {
            students[i] = new Student();
            students[i].number = i + 1;
            students[i].state = (int) (Math.random() * (6 - 1 + 1) + 1);
            students[i].score = (int) (Math.random() * (100 - 0 + 1));
        }

        for (Student student : students) {
            System.out.println(student.toString());
        }


        System.out.println("===========================");

        //问题1 : 打印出三年级学生信息
        for (Student student : students) {
            if (student.state == 3) {
                System.out.println(student.toString());
            }
        }


        //问题2: 按成绩 冒泡排序
        for (int i = 0; i < students.length - 1; i++) {
            for (int j = 0; j < students.length - i - 1; j++) {
                if (students[j].score < students[j + 1].score) {
                    Student temp = students[j];
                    students[j] = students[j + 1];
                    students[j + 1] = temp;
                }
            }
        }


        System.out.println("============排序后===============");
        for (Student student : students) {
            System.out.println(student.toString());
        }


    }
}


class Student {
    int number; //学号
    int state;  //年纪
    int score;  //成绩

    @Override
    public String toString() {
        return "Student{" +
                "number=" + number +
                ", state=" + state +
                ", score=" + score +
                '}';
    }
}