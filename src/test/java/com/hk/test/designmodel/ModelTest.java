package com.hk.test.designmodel;

import java.util.Scanner;

/**
 * @author kai.hu
 * @date 2020/10/27 10:43
 */
public class ModelTest {
    private static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) {
        while (true) {
            System.out.print("输入一个数：");
            int b = scan.nextInt();
            aaa(b);
        }
    }

    public static void aaa(int a) {
        int result = 1;

        while (a > 0) {
            int temp = a%10;
            a /= 10;
            if (temp != 0)
            result *= temp;
        }
        System.out.println(result);
        System.out.println("是否继续（n/N停止）：");
        String next = scan.next();
        if (next.equalsIgnoreCase("N")) {
            return;
        }
        aaa(result);
    }
}
