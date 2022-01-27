using System;
using System.Collections.Generic;
using System.Linq;
using MPI;
using Environment = MPI.Environment;

namespace QuickSortProblem
{
    internal static class Program
    {
        private static int[] ApplyQuicksort(int[] array)
        {
            QuickSort(array, 0, array.Length - 1);
            return array;
        }

        private static void QuickSort(IList<int> array, int lowIndex, int highIndex)
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
            QuickSort(array, lowIndex, leftPointer - 1);
            QuickSort(array, leftPointer + 1, highIndex);
        }

        private static void Swap(IList<int> array, int leftPointer, int rightPointer)
        {
            (array[leftPointer], array[rightPointer]) = (array[rightPointer], array[leftPointer]);
        }

        private static void Slave()
        {
            var received = Communicator.world.Receive<List<int>>(0, 0);
            Console.WriteLine($"Received process : {Communicator.world.Rank}");
            var resulted = ApplyQuicksort(received.ToArray());
            foreach (var i in resulted)
            {
                Console.Write(i  + " ");
            }
            Console.WriteLine();
            Communicator.world.Send(resulted.ToList(), 0, 0);
        }

        private static void Master()
        {
            var array = new[] { 1, 10, 23, 9, 3, 75, 2 };
            var worldSize = Communicator.world.Size;
            var arrays = new List<int[]>();
            var howMuch = array.Length / (worldSize - 1);
            var batch = new List<int>();
            foreach (var t in array)
            {
                if (batch.Count == howMuch)
                {
                    arrays.Add(batch.ToArray());
                    batch = new List<int>();
                }
                batch.Add(t); 
            }

            if (arrays.Count == 0)
            {
                arrays.Add(batch.ToArray());
            }
            else
            {
                var last = arrays.Last();
                var newLast = last.Concat(batch);
                arrays[^1] = newLast.ToArray();
            }

            var finalArray = new List<int>();
            for (var i = 1; i < worldSize; i++)
            {
                Communicator.world.Send(arrays[i - 1].ToList(), i, 0);
                var received = Communicator.world.Receive<List<int>>(i, 0);
                finalArray.AddRange(received);
            }
            
            var result = ApplyQuicksort(finalArray.ToArray());
            result.ToList()
                .ForEach(e => Console.Write(e + " "));
            Console.WriteLine();
        }

        private static void Main(string[] args)
        {
            using (new Environment(ref args))
            {
                if (Communicator.world.Rank == 0)
                {
                    Master();   
                }
                else
                {
                    Slave();
                }
            }

        }
    }
}