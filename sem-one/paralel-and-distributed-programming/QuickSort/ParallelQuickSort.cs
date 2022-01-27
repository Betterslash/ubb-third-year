using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace QuickSort
{
    public static class ParallelQuickSort
    {
        private static void QuickSort(int[] array, int lowIndex, int highIndex)
        {
            if (lowIndex >= highIndex)
            {
                return;
            }
            
            var pivot = array[highIndex];
            var leftPointer = lowIndex;
            var rightPointer = highIndex;

            while (leftPointer < rightPointer)
            {
                while (array[leftPointer] <= pivot && leftPointer < rightPointer)
                {
                    leftPointer++;
                }

                while (array[rightPointer] >= pivot && leftPointer < rightPointer)
                {
                    rightPointer--;
                }

                Swap(array, leftPointer, rightPointer);
            }
            Swap(array, leftPointer, highIndex);
            var t1 = new Task(() => QuickSort(array, lowIndex, leftPointer - 1));
            var t2 = new Task(() => QuickSort(array, leftPointer + 1, highIndex));
            var tasks = new List<Task>{t1, t2};
            tasks.ForEach(e => e.Start());
            Task.WaitAll(tasks.ToArray());
        }

        private static void Swap(int[] array, int leftPointer, int rightPointer)
        {
            (array[leftPointer], array[rightPointer]) = (array[rightPointer], array[leftPointer]);
        }

        private static void Run()
        {
            var array = new int[10];
            var random = new Random();
            for (var i = 0; i < 10; i++)
            {
                array[i] = random.Next(100);
            }
            foreach (var i in array)
            {
                Console.Write(i + " ");
            }
            Console.WriteLine();
            QuickSort(array, 0, array.Length - 1);
            foreach (var number in array)
            {
                Console.Write(number + " ");
            }
            Console.WriteLine();
        }
    }

    public static class MpiQuickSort
    {
        public static void QuickSort(int[] array, int lowIndex, int highIndex)
        {
            if (lowIndex >= highIndex)
            {
                return;
            }

            var pivot = array[highIndex];
            var leftPointer = lowIndex;
            var rightPointer = highIndex;

            while (leftPointer < rightPointer)
            {
                while (array[leftPointer] <= pivot && leftPointer < rightPointer )
                {
                    leftPointer++;
                }

                while (array[rightPointer] >= pivot && leftPointer < rightPointer)
                {
                    rightPointer--;
                }

                Swap(array, leftPointer, rightPointer);
            }
            Swap(array, leftPointer, highIndex);
            QuickSort(array, lowIndex, leftPointer - 1);
            QuickSort(array, leftPointer + 1, highIndex);
        }

        private static void Swap(int[] array, int leftPointer, int rightPointer)
        {
            (array[leftPointer], array[rightPointer]) = (array[rightPointer], array[leftPointer]);
        }
    }
}