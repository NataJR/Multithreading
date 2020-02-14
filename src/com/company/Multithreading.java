package com.company;

import com.sun.tools.javac.Main;

import java.util.Arrays;

public class Multithreading {

    static final int size = 4;
    static final int h = size / 2;


    public static void main(String[] args) {


        float[] arr = new float[size];

        fillArray(arr);
        long time = System.currentTimeMillis();
        System.out.println("method1 before " + Arrays.toString(arr));
        method1(arr);

        System.out.println("execution time " + (System.currentTimeMillis() - time));

        System.out.println("method1 after " + Arrays.toString(arr));


        fillArray(arr);
        System.out.println();
        System.out.println("method2 before " + Arrays.toString(arr));
        time = System.currentTimeMillis();
        method2(arr);

        System.out.println("execution time " + (System.currentTimeMillis() - time));
        System.out.println("method2 after " + Arrays.toString(arr));
    }


    private static void fillArray(float a[]) {
        for (int i = 0; i < a.length; i++)
            a[i] = 1;
    }


    public static synchronized void method1(float a[]) {

        for (int i = 0; i < a.length; i++)
            a[i] = (float) (a[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


        private static synchronized void method2a(float a[], int g){

            for (int i = 0; i < a.length; i++) {
                a[i] = (float) (a[i] * Math.sin(0.2f + (i + h) / 5) * Math.cos(0.2f + (i + h) / 5)
                        * Math.cos(0.4f + (i + h) / 2));

            }
            try {
                Thread.sleep(1 / 100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
       
    //очевидно, что я что-то сделала не так, но я не понимаю и не вижу разницу при использовании Thread.sleep
    
    
    private static void method2(float a[]){

            float[] a1 = new float[h];
            float[] a2 = new float[h];
            System.arraycopy(a, 0, a1, 0, h);
            System.arraycopy(a, h, a2, 0, h);

            System.out.println("a1 before " + Arrays.toString(a1));
            System.out.println("a2 before " + Arrays.toString(a2));

            new Thread(() -> method2a(a1, 0)).start();
            new Thread(() -> method2a(a2, h)).start();


            System.out.println("a1 after " + Arrays.toString(a1));
            System.out.println("a2 after " + Arrays.toString(a2));

            System.arraycopy(a1, 0, a, 0, h);
            System.arraycopy(a2, 0, a, h, h);
        }

        private static boolean compareArrays ( float a1[], float a2[]){
            for (int i = 0; i < a1.length; i++) {
                if (a1[i] != a2[i])
                    return false;
            }
            return true;
        }

    }


