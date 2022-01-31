using System;
using System.Collections.Generic;
using System.Linq;
using MPI;
using Environment = MPI.Environment;

namespace VectorsConvolution
{

    [Serializable]
    internal class Message
    {
        public int[] VectorOne { get; set; }
        public int[] VectorTwo { get; set; }
        
        public int[] Positions { get; set; }
    }

    internal static class Parallel
    {
        private const int Mod = 998244;

        private static void PrintArray(this int[] array)
        {
            foreach (var i in array)
            {
                Console.Write(i + " ");
            }
            Console.WriteLine();
        }

        private static List<int[]> Chunks(int[] arrayToBePartitioned, int chunksNumber)
        {
            
            var result = new List<List<int>>();
            var chunkSize = arrayToBePartitioned.Length / chunksNumber;
            var rest = arrayToBePartitioned.Length - chunkSize * chunksNumber;
            for (int i = 0; i < chunksNumber; i++)
            {
                var chunk = new List<int>();
                for (int j = 0; j < chunkSize; j++)
                {
                    chunk.Add(arrayToBePartitioned[i * chunkSize + j]);    
                }
                result.Add(chunk);
            }

            if (rest <= 0) return result.Select(e => e.ToArray()).ToList();
            {
                for (int i = 0; i < rest; i++)
                {
                    result[^1].Add(arrayToBePartitioned[chunkSize * chunksNumber + i]);
                }
            }
            result[^1].Sort();
            return result.Select(e => e.ToArray()).ToList();
        }

        public static int[] ApplyConvolution(int[] a, int[] b)
        {
            int n = a.Length, m = b.Length;
 
            // Stores the final array
            var c = new int[n + m - 1];
 
            // Traverse the two given arrays
            for (var i = 0; i < n; ++i) {
                for (var j = 0; j < m; ++j) {
                    // Update the convolution array
                    Console.WriteLine($"Position {i + j} Value {(a[i] * b[j])} ");
                    c[i + j] += (a[i] * b[j]) % Mod;
                }
                Console.WriteLine();
            }

            return c;
        }

        internal static void Master()
        {
            int[] a = { 1, 2, 3, 4 };
            int[] b = { 5, 6, 7, 8, 9 };
            //[0, 1, ,2 ,3 ,4 ,5 6, 7] / 3 = 2 r 2
            var arr = new int[a.Length];
            for (var i = 0; i < arr.Length; i++)
            {
                arr[i] = i;
            }
            
            var chunks = Chunks(arr, Communicator.world.Size - 1);
            chunks.ForEach(e =>
            {
                e.PrintArray();
            });
            for (int i = 0; i < chunks.Count; i++)
            {
                
                Communicator.world.Send(new Message
                {
                    VectorOne = a,
                    VectorTwo = b,
                    Positions = chunks[i]
                }, i + 1, 0);
                var received = Communicator.world.Receive<List<int>>(i + 1, 0);
                received.ToArray().PrintArray();
            }
        }

        internal static void Slave()
        {
            var received = Communicator.world.Receive<Message>(0, 0);
            var a = received.VectorOne;
            var b = received.VectorTwo;
            int n = a.Length, m = b.Length;
 
            // Stores the final array
            var c = new int[n + m - 1];
            Console.WriteLine($"I am process {Communicator.world.Rank}");

            // Traverse the two given arrays
            for (var i = received.Positions[0]; i <= received.Positions[^1]; ++i) {
                for (var j = 0; j < m; ++j) {
                    // Update the convolution array
                    Console.WriteLine($"Position {i + j} Value {(a[i] * b[j])} ");
                    c[i + j] += (a[i] * b[j]) % Mod;
                }
                Console.WriteLine();
            }
            Communicator.world.Send(received.Positions.ToList(), 0, 0);
        }

        internal static void Main(string[] args)
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
/*
 *
 *          int[] a = { 1, 2, 3, 4 }; 4 => 2 => [0, 1] [2, 3]
 *          for el[0] 
            int[] b = { 5, 6, 7, 8, 9 };
 *
 * 
 */



























////