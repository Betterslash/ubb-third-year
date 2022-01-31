using System;
using System.Collections.Generic;
using MPI;
using Environment = MPI.Environment;

namespace MatrixAdditionMpi
{
    [Serializable]
    class Message
    {
        public int Start;
        public int End;
    }

    static class Program
    {
        static List<List<int>> Chunks(int[] array, int chunkNumber)
        {
            var chunkSize = array.Length / chunkNumber;
            var remainder = array.Length % chunkNumber;
            var result = new List<List<int>>();
            for (int i = 0; i < chunkNumber; i++)
            {
                var chunk = new List<int>();
                for (int j = 0; j < chunkSize; j++)
                {
                    chunk.Add(array[chunkSize * i + j]);
                }

                result.Add(chunk);
            }

            if (remainder <= 0) return result;
            {
                result[^1].AddRange(array[(chunkSize * chunkNumber)..(chunkSize * chunkNumber + remainder)]);
            }
            return result;
        }

        private static void PrintArray(this int[] array)
        {
            if (array.Length <= 0) return;
            foreach (var t in array)
            {
                Console.Write(t + " ");
            }

            Console.WriteLine();
        }

        private static int[][] ComputeLine(int[][] matrixOne, int[][] matrixTwo, int start, int end)
        {
            var heigth = matrixOne.Length;
            var width = matrixOne[0].Length;
            var resultedLine = new int[heigth][];
            for (int i = 0; i < width; i++)
            {
                resultedLine[i] = new int[width];
            }
            for (int i = start; i <= end; i++)
            {
                for (int j = 0; j < matrixOne.Length; j++)
                {
                    resultedLine[i][j] = matrixOne[i][j] + matrixTwo[i][j];
                }
            }

            return resultedLine;
        }

        private static void PrintMatrix(this int[][] matrix)
        {
            foreach (var r in matrix)
            {
                r.PrintArray();
            }

            Console.WriteLine();
        }


        static void Main(string[] args)
        {
            using (new Environment(ref args))
            {
                if (Communicator.world.Rank == 0)
                {
                    var m1 = new[]
                    {
                        new []{1, 2, 3, 4},
                        new []{1, 2, 3, 4},
                        new []{1, 2, 3, 4}
                    };
                    var m2 = new[]
                    {
                        new []{2, 3 ,4, 5},
                        new []{2, 3 ,4, 5},
                        new []{2, 3 ,4, 5}
                    };
                    Master(m1, m2);
                }
                else
                {
                    var m1 = new[]
                    {
                        new []{1, 2, 3, 4},
                        new []{1, 2, 3, 4},
                        new []{1, 2, 3, 4}
                    };
                    var m2 = new[]
                    {
                        new []{2, 3 ,4, 5},
                        new []{2, 3 ,4, 5},
                        new []{2, 3 ,4, 5}
                    };
                    Worker(m1, m2);
                }
            }

        }

        static void Master(int[][] m1, int[][] m2)
        {
            var worldSize = Communicator.world.Size - 1;
            var length = m1.Length;
            var toBeChunked = new int[length];
            for (int i = 0; i < length; i++)
            {
                toBeChunked[i] = i;
            }

            var chunnks = Chunks(toBeChunked, worldSize);
            chunnks.ForEach(e => e.ToArray().PrintArray());
            var results = new List<int[][]>();
            for (int i = 1; i <= worldSize; i++)
            {
                Communicator.world.Send(new Message
                {
                    Start = chunnks[i - 1][0],
                    End = chunnks[i - 1][^1]
                }, i, 0);
                var res = Communicator.world.Receive<int[][]>(i, 0);
                results.Add(res);
            }
            results.ForEach(e => e.PrintMatrix());
        }

        static void Worker(int[][] m1, int[][] m2)
        {
            var rec = Communicator.world.Receive<Message>(0, 0);
            Console.WriteLine($"Received {rec.Start} {rec.End}");
            var res = ComputeLine(m1, m2, rec.Start, rec.End);
            Communicator.world.Send(res, 0, 0);
        }
    }
}