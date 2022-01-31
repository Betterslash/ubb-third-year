using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using MPI;
using Environment = MPI.Environment;

namespace MergeSort
{
    internal static class ParallelProgram
    {

        private static readonly Mutex Mutex = new();
        private static void PrintArray(this IEnumerable<int> array)
        { 
            Console.Write("Array : ");
            foreach (var element in array)
            {
                Console.Write($"{element} ");
            }
            Console.WriteLine();
        }

        private static void MergeSort(int[] array, int left, int right)
        {
            if (left >= right) return;
            var middle = (left + right) / 2;
            var t1 = new Task(() =>
            {
                MergeSort(array, left, middle);
            });
            var t2 = new Task(() => {
                MergeSort(array, middle + 1, right);
            });
            var threads = new[] { t1, t2 };
            foreach (var thread in threads)
            {
                thread.Start();
            }
            Task.WaitAll(threads);
            Merge(array, left, middle, right);
        }

        private static void Merge(int[] array, int left, int middle, int right)
        {
            Mutex.WaitOne();
            var n1 = middle - left + 1;
            var n2 = right - middle;
            var leftArray = new int[n1];
            var rightArray = new int[n2];

            for (var l = 0; l < n1; l++)
            {
                leftArray[l] = array[left + l];
            }

            for (var l = 0; l < n2; l++)
            {
                rightArray[l] = array[middle + 1 + l];
            }
            
            var i = 0;
            var j = 0;
            var k = left;
            
            while (i < n1 && j < n2) {
                if (leftArray[i] <= rightArray[j]) {
                    array[k] = leftArray[i];
                    i++;
                } else {
                    array[k] = rightArray[j];
                    j++;
                }
                k++;
            }
            while (i < n1) {
                array[k] = leftArray[i];
                i++;
                k++;
            }

            while (j < n2) {
                array[k] = rightArray[j];
                j++;
                k++;
            }
            Mutex.ReleaseMutex();
        }

        private static void Run()
        {
            var array = new[] { 8, 19, 24, 13, 1, 20, 78, 234, 11 };
            MergeSort(array, 0, array.Length - 1);
            array.PrintArray();
        }
    }

    internal static class MpiProgram
    {

        private static void PrintArray(this IEnumerable<int> array)
        { 
            Console.Write("Array : ");
            foreach (var element in array)
            {
                Console.Write($"{element} ");
            }
            Console.WriteLine();
        }
        
        public static void MergeSort(int[] array, int left, int right)
        {
            if(left >= right) return;
            var middle = (left + right) / 2;
            MergeSort(array, left, middle);
            MergeSort(array, middle + 1, right);
            Merge(array, left, middle, right);
        }

        private static void Merge(IList<int> array, int left, int middle, int right)
        {
            var n1 = middle - left + 1;
            var n2 = right - middle;
            var leftArray = new int[n1];
            var rightArray = new int[n2];

            for (var l = 0; l < n1; l++)
            {
                leftArray[l] = array[left + l];
            }

            for (var l = 0; l < n2; l++)
            {
                rightArray[l] = array[middle + 1 + l];
            }
            
            var i = 0;
            var j = 0;
            var k = left;
            
            while (i < n1 && j < n2) {
                if (leftArray[i] <= rightArray[j]) {
                    array[k] = leftArray[i];
                    i++;
                } else {
                    array[k] = rightArray[j];
                    j++;
                }
                k++;
            }
            while (i < n1) {
                array[k] = leftArray[i];
                i++;
                k++;
            }

            while (j < n2) {
                array[k] = rightArray[j];
                j++;
                k++;
            }
        }

        private static List<List<int>> Chunks(this IReadOnlyList<int> array, int chunkNumber)
        {
            var elementsPerPartition = array.Count / chunkNumber;
            var result = new List<List<int>>();
            for (var i = 0; i < chunkNumber; i++)
            {
                var batch = new int[elementsPerPartition];
                for (var j = 0; j < elementsPerPartition; j++)
                {
                    batch[j] = array[i * elementsPerPartition + j];
                }
                result.Add(batch.ToList());
            }

            var partitionDivisionResult = elementsPerPartition * chunkNumber;
            var rest = array.Count - partitionDivisionResult;
            if (rest <= 0) return result;
            {
                for (var i = 0; i < rest; i++)
                {
                    result[i].Add(array[partitionDivisionResult + i]);
                }
            }
            return result;
        }

        public static void Master(int[] array)
        {
            var chunks = array.Chunks(Communicator.world.Size - 1);
            var result = new List<int>();
            for (var i = 0; i < chunks.Count; i++)
            {
                Communicator.world.Send(chunks[i], i + 1, 0);
                Console.WriteLine($"Sent to {i + 1}");
                var received = Communicator.world.Receive<List<int>>(i + 1, 0);
                result.AddRange(received);
            }

            ApplyMergeSort(result.ToArray()).PrintArray();
            
        }

        public static void Worker()
        {
            var received = Communicator.world.Receive<List<int>>(0, 0);
            Console.WriteLine($"Worker {Communicator.world.Rank}");
            received.PrintArray();
            var result = ApplyMergeSort(received.ToArray());
            result.PrintArray();
            Communicator.world.Send(result, 0, 0);
        }

        public static List<int> ApplyMergeSort(int[] array)
        {
            MergeSort(array, 0, array.Length -1);
            return array.ToList();
        }

        public static void Run(string[] args)
        {
            using (new Environment(ref args))
            {
                if (Communicator.world.Rank == 0)
                {
                    Master(new[]{ 8, 19, 24, 13, 1, 20, 78, 234, 11 });
                }
                else
                {
                    Worker();
                }
            }
        }
    }

    internal static class Runner
    {
        public static void Main(string[] args)
        {
            MpiProgram.Run(args);
        }
    }
}


































