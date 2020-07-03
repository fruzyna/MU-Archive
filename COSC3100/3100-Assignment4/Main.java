import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class Main
{

	public static void main(String[] args) 
	{
		new Main();
	}
	
	public Main()
	{
		//compare using 100 values 0-100
		compareAlgorithms(100000, 0, 100);
		
		//compare using 1000 values 0-100
		compareAlgorithms(1000000, 0, 100);
	}
	
	/**
	 * Compares brute force and transform and conquer by determining mode
	 * @param values Number of values to test with
	 * @param min Minimum random value
	 * @param max Maximum random value
	 */
	public void compareAlgorithms(int values, int min, int max)
	{
		System.out.println("Sorting " + values + " random values from " + min + " to " + max);
		
		//make a list of x random numbers from y to z
		int[] partAValues = generateRandomInts(values, min, max);
		
		//brute force then transform and conquer
		long bruteForce = bruteForceMode(partAValues);
		long transformAndConquer = transformAndConquerMode(partAValues);
		
		//determine and printout faster alg
		long net = bruteForce - transformAndConquer;
		if(net > 0)
		{
			System.out.println("Transform and Conquer is faster by " + Math.abs(net) + "ms");
		}
		else if(net < 0)
		{
			System.out.println("Brute Force is faster by " + Math.abs(net) + "ms");
		}
		else
		{
			System.out.println("Algorithms took the same amount of time");
		}
	}

	/**
	 * Determines mode of an array using brute force
	 * @param values Values to determine mode of
	 * @return Time to determine mode
	 */
	public long bruteForceMode(int[] values)
	{
		long start = System.currentTimeMillis();
		
		//count number of each value
		Map<Integer, Integer> instances = new HashMap<>();
		for(int i = 0; i < values.length; i++)
		{
			int current = values[i];
			if(instances.containsKey(current))
			{
				instances.put(current, instances.get(current) + 1);
			}
			else
			{
				instances.put(current, 1);
			}
		}
		
		//find highest value counted
		int maxValue = 0;
		int maxKey = 0;
		for(Entry<Integer, Integer> entry : instances.entrySet())
		{
			if(entry.getValue() > maxValue)
			{
				maxValue = entry.getValue();
				maxKey = entry.getKey();
			}
		}
		
		//determine time and printout results
		long time = System.currentTimeMillis() - start;
		System.out.println("Mode (BF:" + values.length + "): " + maxValue + " instances of " + maxKey + " in " + time + "ms");
		return time;
	}
	
	/**
	 * Determines mode of an array using transform and conquer
	 * @param values Values to determine mode of
	 * @return Time to determine mode
	 */
	public long transformAndConquerMode(int[] values)
	{
		long start = System.currentTimeMillis();
		
		//sorts array with merge sort
		int[] sorted = mergeSort(values);
		
		//init variables
		int maxValue = values[0];
		int maxCount = 1;
		int currentCount = 1;
		//determines most common number by counting longest section
		for(int i = 1; i < sorted.length; i++)
		{
			//if this number is the same as the last increase the count
			if(sorted[i] == sorted[i-1])
			{
				currentCount++;
				//if the count is greater than the max update the max
				if(currentCount > maxCount)
				{
					maxValue = sorted[i];
					maxCount = currentCount;
				}
			}
			else
			{
				currentCount = 1;
			}
		}
		
		//determine time and printout results
		long time = System.currentTimeMillis() - start;
		System.out.println("Mode (T&C:" + values.length + "): " + maxCount + " instances of " + maxValue + " in " + time + "ms");
		return time;
	}
	
	/**
	 * Uses merge sort to sort an array
	 * @param array Array to be sorted
	 * @return Sorted array
	 */
	public int[] mergeSort(int[] array)
	{
		//Makes and fills 2 arrays of half the size of the original
		int aSize = array.length/2;
		int[] a = new int[aSize];
		int[] b = new int[array.length - aSize];
		for(int i = 0; i < array.length; i++)
		{
			if(i < aSize)
			{
				a[i] = array[i];
			}
			else
			{
				b[i - aSize] = array[i];
			}
		}
		
		//Continue dividing if each array has multiple items left
		if(a.length > 1)
		{
			a = mergeSort(a);
		}
		if(b.length > 1)
		{
			b = mergeSort(b);
		}
		
		//Merge arrays
		return combineArrays(a, b);
	}
	
	/**
	 * Merges 2 arrays for merge sort
	 * @param a First array
	 * @param b Second array
	 * @return Combined array
	 */
	public int[] combineArrays(int[] a, int[] b)
	{
		int[] array = new int[a.length + b.length];
		
		//Determines smallest next available number in either array and adds to final array
		int aPos = 0;
		int bPos = 0;
		for(int i = 0; i < array.length; i++)
		{
			//If both arrays still have eligible values
			if(aPos < a.length && bPos < b.length)
			{
				//If a is smaller add it
				if(a[aPos] <= b[bPos])
				{
					array[i] = a[aPos];
					aPos++;
				}
				else
				{
					//Otherwise b
					array[i] = b[bPos];
					bPos++;
				}
			}
			else if(aPos < a.length)
			{
				//If only a has values left add a
				array[i] = a[aPos];
				aPos++;
			}
			else
			{
				//If only b has values left add b
				array[i] = b[bPos];
				bPos++;
			}
		}
		return array;
	}
	
	/**
	 * Generates an array of random numbers
	 * @param count Amount to generate
	 * @param min Minimum number
	 * @param max Maximum number
	 * @return Array of random values
	 */
	public int[] generateRandomInts(int count, int min, int max)
	{
		int[] array = new int[count];
		
		//run randomInt() once for every requested value
		for(int i = 0; i < count; i++)
		{
			array[i] = randomInt(min, max);
		}
		
		return array;
	}
	
	/**
	 * Generates one random number between given values
	 * @param min Minimum value
	 * @param max Maximum value
	 * @return Random number
	 */
	public int randomInt(int min, int max)
	{
		Random rand = new Random();
		return min + rand.nextInt(max);
	}

}
