using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;

namespace PermutationsProblemThreaded
{
    static class Program
    {

        public class CounterMessage
        {
            public Mutex mtx = new();
            public int Counter { get; set; }
        }

        static bool IsPermutation(List<int> array)
        {
            var areAllDistinct = array.Distinct().Count() == array.Count;
            return areAllDistinct;
        }

        private static bool Check(int[] array)
        {
            return array[0] % 2 == 0;
        }

        static void GeneratePermutations(int[] array, int position, List<List<int>> result, int threadNumber, CounterMessage counter)
        {
            if (position == array.Length)
            {
                if (!IsPermutation(array.ToList())) return;
                result.Add(new List<int>(array));
                if (!Check(array)) return;
                counter.mtx.WaitOne();
                counter.Counter += 1;
                counter.mtx.ReleaseMutex();
                return;
            }

            for (var i = 1; i <= array.Length; i++)
            {
                
                if (threadNumber > 1)
                {
                    array[position] = i;
                    Console.WriteLine($"I am thread {Task.CurrentId}");
                    var t1 = new Task(() => { GeneratePermutations(array, position + 1, result, threadNumber - 1, counter);});
                    t1.Start();
                    Task.WaitAll(t1);
                }
                else
                {
                    array[position] = i;
                    GeneratePermutations(array, position + 1, result, threadNumber, counter);
                }
            }

        }

        internal static void PrintArray(this int[] array)
        {
            if (array.Length <= 0) return;
            foreach (var number in array)
            {
                Console.Write($"{number} ");
            }
            Console.WriteLine();
        }

        static void Main(string[] args)
        {
            var arr = new int[4];
            var result = new List<List<int>>();
            var counter = new CounterMessage
            {
                Counter = 0
            };
            GeneratePermutations(arr, 0, result, Environment.ProcessorCount, counter);
            result.ForEach(q => q.ToArray().PrintArray());
            Console.WriteLine($"Counter got to {counter.Counter}");
        }
    }
}