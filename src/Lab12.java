import java.util.Arrays;

public class Lab12 {
    static final int SIZE = 10000000;
    static final int HALF = SIZE / 2;

    public static void firstM(){
        float[] arr = new float[SIZE];
        Arrays.fill(arr, 1);
        long a = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i/5) * Math.cos(0.2f + i/5) * Math.cos(0.4f + i/2));
        }
        System.out.println("время работы первого метода = " + (System.currentTimeMillis() - a));
    }

    public static void secondM(){
        float[] arr = new float[SIZE];
        float[] arr1 = new float[HALF];
        float[] arr2 = new float[HALF];
        Arrays.fill(arr, 1);
        long a = System.currentTimeMillis();
        System.arraycopy(arr, 0, arr1, 0, HALF);
        System.arraycopy(arr, HALF, arr2, 0, HALF);

        Thread run1 = new Thread(new MyClass(arr1, 0));
        Thread run2 = new Thread(new MyClass(arr2, HALF));
        run1.start();
        run2.start();

        try {
            run1.join();
            run2.join();
        }
        catch (InterruptedException ie){
            ie.printStackTrace();
        }

        System.arraycopy(arr1, 0, arr, 0, HALF);
        System.arraycopy(arr2, 0, arr, HALF, HALF);
        System.out.println("время работы второго метода = " + (System.currentTimeMillis() - a));
    }
}

class MyClass extends Thread{
    private float[] array;
    private int half;
    MyClass(float[] array, int half){
        this.array = array;
        this.half = half;
    }
    @Override
    public void run(){
        for (int i = 0; i < array.length; i++) {
            array[i] = (float) (array[i] * Math.sin(0.2f + (i + half) / 5) * Math.cos(0.2f + (i + half) / 5) * Math.cos(0.4f + (i + half) / 2));
        }
    }
}
