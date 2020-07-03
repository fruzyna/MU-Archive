/**
 * Created by mail929 on 9/11/16.
 */
public class Driver {

    public static void main(String[] args) {
        test(100);
        test(1000);
        test(10000);
        test(50000);
        test(100000);
    }

    public static void test(int items)
    {
        for(int j = 0; j < 5; j++)
        {
            double array[] = new double[items];
            for(int i = 0; i < items; i++)
            {
                array[i] = i;
            }

            System.out.println("Prefix Average 1 Test with " + items + " items");
            long start = System.currentTimeMillis();
            PrefixAverage.prefixAverage1(array);
            long finish = System.currentTimeMillis();
            long total = finish - start;
            System.out.println("Total Time: " + total + "ms");
            System.out.println("Time per Item: " + (total/items) + "ms/item" + "\n");

            System.out.println("Prefix Average 2 Test with " + items + " items");
            start = System.currentTimeMillis();
            PrefixAverage.prefixAverage2(array);
            finish = System.currentTimeMillis();
            total = finish - start;
            System.out.println("Total Time: " + total + "ms");
            System.out.println("Time per Item: " + (total/items) + "ms/item" + "\n\n");
        }
    }
}