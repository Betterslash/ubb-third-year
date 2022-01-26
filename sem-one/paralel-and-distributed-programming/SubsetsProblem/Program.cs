using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using MPI;
using Environment = MPI.Environment;

namespace SubsetsProblem
{
    internal static class Program
    {
        private static bool IsSubset(IReadOnlyList<int> batch)
        {
            var areAllDistinct = batch.Distinct().Count() == batch.Count;
            var isAscending = true;
            for (var i = 0; i < batch.Count - 1; i++)
            {
                if (batch[i] >= batch[i + 1])
                {
                    isAscending = false;
                }
            }
            return areAllDistinct && isAscending;
        }

        private class Message
        {
            private readonly Mutex _mutex;
            
            public Message()
            {
                _mutex = new Mutex();
                _value = 0;
            }

            private int _value;

            public void Increment()
            {
                _mutex.WaitOne();
                _value += 1;
                _mutex.ReleaseMutex();
            }

            public override string ToString()
            {
                return _value.ToString();
            }
        }

        private static void BackTracking(List<int> availableNumbers, int position, int length, int[] batch, Message countedSets)
        {
            if (position == length && IsSubset(batch))
            {
                if (!CheckSubset(batch)) return;
                countedSets.Increment();
            }
            else if(position < length)
            {
                foreach (var number in availableNumbers)
                {
                    batch[position] = number;
                    BackTracking(availableNumbers, position + 1, length, batch, countedSets);
                }
            }
        }

        private static bool CheckSubset(IEnumerable<int> subset)
        {
            return subset.Sum() > 5;
        }

        private static void Run()
        {
            var countedSets = new Message();
            Console.WriteLine("Read N : ");
            var n = int.Parse(Console.In.ReadLine() ?? string.Empty);
            var threads = new List<Task>();
            var numbers = new List<int>();
            for (var i = 0; i < n; i++)
            {
                numbers.Add(i + 1);
            }
            for (var i = 1; i <= n; i++)
            {
                var i1 = i;
                threads.Add(new Task(() => BackTracking(numbers, 0, i1, new int[i1], countedSets)));
            }
            threads.ForEach(e => e.Start());
            Task.WaitAll(threads.ToArray());
            Console.WriteLine(countedSets);
        }
    }

    internal static class MpiProgram
    {

        private static void BackTracking(List<int> numbers, int position, int length, int[] batch, ref int counter)
        {
            if (position == length && IsSubset(batch))
            {
                if (!CheckCondition(batch)) return;
                counter += 1;
            }else if (position < length)
            {
                foreach (var number in numbers)
                {
                    batch[position] = number;
                    BackTracking(numbers, position + 1, length, batch, ref counter);
                }
            }
        }

        private static bool CheckCondition(IEnumerable<int> batch)
        {
            return batch.Sum() > 5;
        }

        private static bool IsSubset(IReadOnlyList<int> batch)
        {
            var areAllDistinct = batch.Distinct().Count() == batch.Count;
            var areItemsAscending = true;
            for (var i = 0; i < batch.Count - 1; i++)
            {
                if (batch[i] < batch[i + 1]) continue;
                areItemsAscending = false;
                break;
            }
            return areAllDistinct && areItemsAscending;
        }

        private static void SlaveProcess()
        {
            var worldSize = Communicator.world.Size;
            var numbers = GenerateNumbers(worldSize).ToList();
            var result = 0;
            var rank = Communicator.world.Rank;
            BackTracking(numbers, 0, rank + 1, new int[rank + 1], ref result);
            Communicator.world.Send(result, 0, 0);
        }

        private static void MasterProcess()
        {
            try
            {
                var n = Communicator.world.Size;
                var numbers = GenerateNumbers(n).ToList();
                var result = 0;
                BackTracking(numbers, 0, 1, new int[1], ref result);
                for (var i = 1; i < n; i++)
                {
                    result += Communicator.world.Receive<int>(i, 0);
                }
                Console.WriteLine($"There were {result} subsets matching the condition !!");
            }
            catch (Exception e)
            {
                Console.WriteLine($"Error detected reason being : {e.Message}");
            }
        }

        private static IEnumerable<int> GenerateNumbers(int i)
        {
            var result = new int[i];
            for (var j = 0; j < i; j++)
            {
                result[j] = j + 1;
            }
            return result;
        }

        public static void Run(string[] args)
        {
            using (new Environment(ref args))
            {
                if (Communicator.world.Rank == 0)
                {
                    MasterProcess();
                }
                else
                {
                    SlaveProcess();
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