using System;
using System.Collections.Generic;
using System.Linq;
using MPI;
using Environment = MPI.Environment;

namespace PermutationsMPI
{
    internal static class Program
    {

        public static bool Check(int[] array)
        {
            return array[0] % 2 == 0;
        }

        public static void Slave()
        {
            var received = Communicator.world.Receive<List<List<int>>>(0, 0);
            
            var toBeReturned = 0;
            received.ForEach(e => e.ToArray().PrintArray());
            received.ForEach(e =>
            {
                if (Check(e.ToArray()))
                {
                    toBeReturned += 1;
                }
            });
            Communicator.world.Send(toBeReturned, 0, 0);
        }

        public static void Master()
        {
            Console.WriteLine("Master started");
            var permutations = new List<List<int>>();
            GeneratePermutations(new int[3], 0, 3, permutations);
            Console.WriteLine(permutations.Count);
            var toBeChunked = new List<int>();
           
            for (var i = 0; i < permutations.Count; i++)
            {
                toBeChunked.Add(i);
            }

            var worldSize = Communicator.world.Size - 1;
            Console.WriteLine(worldSize);
            var chunks = toBeChunked.ToArray().Chunks(worldSize);
            chunks.ForEach(e => e.ToArray().PrintArray());
            var finalResult = 0;
            for (var i = 1; i <= worldSize; i++)
            {
                var chunk = new List<List<int>>();
                var start = chunks[i - 1][0];
                var end = chunks[i - 1][^1];
                Console.WriteLine($"Start {start}");
                Console.WriteLine($"End {end}");
                for (var j = start; j < end; j++)
                {
                    chunk.Add(permutations[j]);
                }
                chunk.ForEach(e => e.ToArray().PrintArray());
                Communicator.world.Send(chunk, i, 0);
                var toBeAdded = Communicator.world.Receive<int>(i, 0);
                finalResult += toBeAdded;
            }

            Console.WriteLine($"Final result is {finalResult} ");
        }

        private static void GeneratePermutations(int[] array, int position, int length, List<List<int>> result)
        {
            if (position == length)
            {
                if (array.Distinct().Count() == array.Length)
                {
                    result.Add(new List<int>(array));
                }
            }
            else
            {
                for (var i = 1; i <= length; i++)
                {
                    array[position] = i;
                    GeneratePermutations(array, position + 1, length, result);
                }
            }
        }

        private static List<List<int>> Chunks(this int[] array, int chunksNumber)
        {
            var chunkSize = array.Length / chunksNumber;
            var remainder = array.Length % chunksNumber;
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
        private static void PrintArray(this int[] arr)
        {
            if (arr.Length <= 0) return;
            foreach (var number in arr)
            {
                Console.Write($"{number} ");
            }
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