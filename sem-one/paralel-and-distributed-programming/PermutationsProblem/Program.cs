using System;
using System.Collections.Generic;
using System.Linq;
using MPI;

namespace PermutationsProblem
{
    internal static class Program
    {
        private static void PrintArray(this int[] arr)
        {
            foreach (var t in arr)
            {
                Console.Write(t + " ");
            } 

            Console.WriteLine();
        }

        static void Backtracking(int[] arr, int size, int position, List<int[]> result)
        {
            if (position == size) 
            {
                result.Add((int[])arr.Clone());
                return;
            }

            for (var i = 0; i <= 9; i++)
            {
                arr[position] = i;
                Backtracking(arr, size, position + 1, result);
            }
        }

        private static bool CheckPermutation(int[] arr)
        {
            return arr.Sum() > 10;
        }

        public static List<int[]> Master()
        {
            var workersNumber = Communicator.world.Size - 1;
            var partitionSize = 9 / workersNumber;
            var remainder = partitionSize % workersNumber;
            var tasks = new List<int[]>();
            for (int i = 0; i < workersNumber; i++)
            {
                var batch = new int[partitionSize];
                for (int j = 0; j < partitionSize; j++)
                {
                    batch[i * workersNumber + j] = 0;
                }
                tasks.Add(batch);
            }

            if (remainder <= 0) return tasks;
            {
                for (int i = 0; i < remainder; i++)
                {
                    tasks[i]
                }
            }
        }

        static void Main(string[] args)
        {
            var array = new int[2];
            var res = new List<int[]>();
            Backtracking(array, 2, 0, res);
            res.ForEach(e => e.PrintArray());
        }
    }
}