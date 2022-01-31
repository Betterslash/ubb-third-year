using System;
using System.Collections.Generic;
using System.Linq;
using MPI;

namespace MatrixPower
{

    internal class Message
    {
        public int[][] Matrix { get; set; }
        public int[] Factors { get; set; }
    }

    internal static class ParallelProgram
    {
        private static List<int[]> Chunks(this int[] array, int chunksNumber)
        {
            var result = new List<List<int>>();
            var chunkSize = array.Length / chunksNumber;
            var intEnd = chunkSize * chunksNumber;
            var rest = array.Length - intEnd;
            for (var i = 0; i < intEnd; i++)
            {
                var chunk = new int[chunkSize];
                for (var j = 0; j < chunkSize; j++)
                {
                    chunk[j] = array[i + j];
                }
                result.Add(chunk.ToList());   
            }

            if (rest <= 0) return result.Select(e => e.ToArray()).ToList();
            {
                for (var i = 0; i < rest; i++)
                {
                    result[i].Add(array[intEnd + i]);
                }
            }

            return result.Select(e => e.ToArray()).ToList();
        }

        internal static int[][] Multiply(this int[][] matrix, int[][] secondMatrix)
        {

            var result = new int[matrix.Length][];
            for (var i = 0; i < matrix.Length; i++)
            {
                result[i] = new int[matrix.Length];
            }
            
            for (var i = 0; i < matrix.Length; i++)
            {
                for (var j = 0; j < matrix.Length; j++)
                {
                    var element = matrix.Select((_, k) => matrix[i][k] * secondMatrix[k][j]).Sum();
                    result[i][j] = element;
                }
            }
            return result;
        }

        internal static int[][] Pow(this int[][] matrix, int pow)
        {
            var result = matrix;

            for (int i = 1; i < pow; i++)
            {
                result = result.Multiply(matrix);
            }
            return result;
        }

        private static void PrintArray(this int[] array)
        {
            for (var i = 0; i < array.Length; i++)
            {
                if (array[i] != 0)
                {
                    Console.WriteLine("Position : " + i +" Number : " +  array[i] + " ");
                }
            }
            Console.WriteLine();
        }

        private static int[] GetPrimesArray(this int[] array)
        {
            var result = new int[array.Length];
            for (var i = 0; i < array.Length; i++)
            {
                if (array[i] != 0)
                {
                    result[i] = array[i] * i;
                }
            }

            return result.Where(e => e != 0).ToArray();
        }
        
        internal static int[] ComputePrimeFactors(this int number)
        {
            var result = new int[number];
            for (var j = 0; j < number; j++)
            {
                result[j] = 0;
            }
            
            var n = number;
            while (n % 2 == 0)
            {
                result[2]++;
                n /= 2;
            }
 
            for (var i = 3; i <= Math.Sqrt(n); i+= 2)
            {
                while (n % i == 0)
                {
                    result[i]++;
                    n /= i;
                }
            }
 
            if (n > 2)
                result[n]++;
            
            return result;
        }

        internal static void Master(int[][] matrix, int power)
        {
            var chunks = power.ComputePrimeFactors()
                .GetPrimesArray()
                .Chunks(Communicator.world.Size - 1);
            var results = new List<int[][]>();
            for (int i = 0; i < chunks.Count; i++)
            {
                Communicator.world.Send(new Message
                {
                    Matrix = matrix,
                    Factors = chunks[i]
                    
                }, i + 1, 0);
                var result = Communicator.world.Receive<int[][]>(i + 1, 0);
                results.Add(result);
            }

            var response = new int[matrix.Length][];
            for (int i = 0; i < response.Length; i++)
            {
                response[i] = new int[matrix.Length];
            }
            
            for (int i = 0; i < results.Count; i++)
            {
                response = results[i].    
            }
        }

        internal static void Main(string[] args)
        {
            2108.ComputePrimeFactors()
                .GetPrimesArray()
                .Chunks(2)
                .ForEach(e => e.ToArray().PrintArray());
        }
    }
}