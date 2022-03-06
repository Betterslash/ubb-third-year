using System;
using System.Collections.Generic;
using System.Linq;
using MPI;
using Environment = MPI.Environment;

namespace MatrixSum
{
    internal static class Program
    {
        [Serializable]
        class Message
        {
            public List<List<int>> A;
            public List<List<int>> B;
        }

        [Serializable]
        class Message2
        {
            public List<int> indexes;
            public List<int> values;
        }

        static List<List<int>> Primes(List<List<int>> a, List<List<int>> b, int nrProcs)
        {
            List<List<int>> result = new List<List<int>>();
            var message = new Message
            {
                A = a,
                B = b
            };
            Communicator.world.Broadcast(ref message, 0);
            
            int oWo = a.Count * a[0].Count / nrProcs;

            for (int i = 1; i <= nrProcs; i++)
            {
                var lmao = Communicator.world.Receive<Message2>(i, 0);
                foreach (var lmao2 in lmao.indexes)
                {
                    
                    var x = lmao2 / oWo;
                    var y = lmao2 % oWo;
                    result[x][y] = message.A[x][y] + message.B[x][y];
                }
            }
            result.ForEach(e =>
            {
                e.ForEach(q => Console.Write(q + " "));
                Console.WriteLine();
            });
            return null;
        }

        static void Worker()
        {
            var myId = Communicator.world.Rank;
            var nrProcs = Communicator.world.Size - 1;
            var message = new Message();
            Communicator.world.Broadcast(ref message, 0);
            var height = message.A.Count;
            var width = message.A[0].Count;
            int oWo = height * width / nrProcs;
            var startIndex = oWo * (myId - 1);
            var endIndex = oWo * myId;
            if (endIndex > height * width)
            {
                endIndex = height * width;
            }

            var result = new List<int>();
            var indexList = new List<int>();
            for (int i = startIndex; i < endIndex; i++)
            {
                indexList.Add(i);
                var x = i / oWo;
                var y = i % oWo;
                result.Add(message.A[x][y] + message.B[x][y]);
            }

            var message2 = new Message2 { indexes = indexList, values = result };
            Communicator.world.Send(message2, 0, 0);
        }

        private static void Main(string[] args)
        {
            using (new Environment(ref args))
            {
                if (Communicator.world.Rank == 0)
                {
                    var a = new[]
                    {
                        new []{1, 2, 3, 4}.ToList(),
                        new []{1, 2, 3, 4}.ToList(),
                        new []{1, 2, 3, 4}.ToList(),
                        new []{1, 2, 3, 4}.ToList(),
                    }.ToList();
                    var b = new[]
                    {
                        new []{1, 2, 3, 4}.ToList(),
                        new []{1, 2, 3, 4}.ToList(),
                        new []{1, 2, 3, 4}.ToList(),
                        new []{1, 2, 3, 4}.ToList()
                    }.ToList();
                    Primes(a, b, Communicator.world.Size - 1);
                }
            }
        }
    }
}