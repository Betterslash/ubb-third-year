using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Exercises
{
    public static class ExercisesOne
    {
        
        private static List<List<int>> Chunks(IReadOnlyList<int> array, int chunksNumber)
        {
            var chunkSize = array.Count / chunksNumber;
            var remainder = array.Count % chunksNumber;
            var result = new List<List<int>>();

            for (var i = 0; i < chunksNumber; i++)
            {
                var chunk = new List<int>();
                for (var j = 0; j < chunkSize; j++)
                {
                    chunk.Add(array[i * chunkSize + j]);
                }
                result.Add(chunk);
            }

            if (remainder <= 0) return result;
            {
                for (var i = 0; i < remainder; i++)
                {
                    result[i].Add(array[chunkSize * chunksNumber + i]);
                }
            }
            return result;
        }

        private static void PrintArray(this IReadOnlyCollection<int> array)
        {
            if (array.Count <= 0) return;
            foreach (var i in array)
            {
                Console.Write($"{i} ");
            }
            
            Console.WriteLine();
        }


        private static int RecursiveDecomposition(int[] array, int low, int high, int threadsNumber)
        {
            if (low + 1 == high) return array[low];
            var middle = (low + high) / 2;
            if (threadsNumber < 2)
            {
                return RecursiveDecomposition(array, low, middle, threadsNumber) +
                       RecursiveDecomposition(array, middle, high, threadsNumber);
            }

            var t1 = Task.Run(() => RecursiveDecomposition(array, low, middle, threadsNumber / 2));
            var s1 = RecursiveDecomposition(array, middle, high, threadsNumber - threadsNumber / 2);
            return s1 + t1.Result;
        }
        
        
        private static void Run()
        {
            var array = new[] { 1, 2, 3, 4, 5, 6, 7 };
            var chunkedArray = Chunks(array, 2);
            chunkedArray.ForEach(e => e.PrintArray());
            var result = RecursiveDecomposition(array, 0, array.Length, 5);
            Console.WriteLine(result);
        }
    }
}