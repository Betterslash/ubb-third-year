using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;

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

        private static void Main()
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
}

/*
 * 1 2 3 4 5
 * 1 -> 1 .. 5
 * 2 -> 1 2, 1 3, .., 4 5
 * 3 -> 1 2 3, .. , .
 * .
 */