using System;
using System.Globalization;
using System.Linq;
using System.Threading;
using MPI;

namespace MpiKaratsuba
{
    public static class Program
    {
        private static Polynomial ComputeFinalResult(Polynomial[] results){
            var result = new Polynomial(results[0].Degree);
            for (var i = 0; i < result.Size; i++)
                foreach (var t in results)
                    result.Coefficients[i] += t.Coefficients[i];

            return result;
        }

        static void MPIMultiplicationMaster(Polynomial polynomial1, Polynomial polynomial2)
        {
            var start = DateTime.Now;

            var n = Communicator.world.Size;
            var end = 0;
            var length = polynomial1.Size / (n - 1);

            for (var i = 1; i < n; i++)
            {
                var begin = end;
                end += length;
                if (i == n - 1)
                    end = polynomial1.Size;

                Communicator.world.Send(polynomial1, i, 0);
                Communicator.world.Send(polynomial2, i, 0);
                Communicator.world.Send(begin, i, 0);
                Communicator.world.Send(end, i, 0);
            }

            var results = new Polynomial[n - 1];

            for (var i = 1; i < n; i++)
                results[i - 1] = Communicator.world.Receive<Polynomial>(i, 0);

            var result = ComputeFinalResult(results);

            double time = (DateTime.Now - start).Milliseconds;
            Console.WriteLine("MPI Multiplication: " + result.ToString() + "\n" + "TIME: " + time.ToString(CultureInfo.InvariantCulture) + " milliseconds");
        }

        static void MPIMultiplicationWorker()
        {
            //Console.WriteLine("Child");
            var polynomial1 = Communicator.world.Receive<Polynomial>(0, 0);
            var polynomial2 = Communicator.world.Receive<Polynomial>(0, 0);

            var begin = Communicator.world.Receive<int>(0, 0);
            var end = Communicator.world.Receive<int>(0, 0);

            var result = PolynomialOperations.MPIMultiply(polynomial1, polynomial2, begin, end);

            Communicator.world.Send(result, 0, 0);
        }

        static void MPIKaratsubaMaster(Polynomial polynomial1, Polynomial polynomial2)
        {
            var start = DateTime.Now;

            var result = new Polynomial(polynomial1.Degree * 2);
            if (Communicator.world.Size == 1)
            {
                result = PolynomialOperations.AsynchronousKaratsubaMultiply(polynomial1, polynomial2);
            }
            else
            {
                Communicator.world.Send<int>(0, 1, 0);
                Communicator.world.Send<int[]>(polynomial1.Coefficients, 1, 0);
                Communicator.world.Send<int[]>(polynomial2.Coefficients, 1, 0);
                Communicator.world.Send<int[]>(
                    Communicator.world.Size == 2
                        ? Array.Empty<int>()
                        : Enumerable.Range(2, Communicator.world.Size - 2).ToArray(), 1, 0);

                var coefs = Communicator.world.Receive<int[]>(1, 0);
                result.Coefficients = coefs;
            }

            double time = (DateTime.Now - start).Milliseconds;
            Console.WriteLine("MPI  Karatsuba: " + result + "\n" + "TIME: " + time.ToString(CultureInfo.InvariantCulture) + " milliseconds");
        }

        static void MPIKaratsubaWorker()
        {
            PolynomialOperations.MPIKaratsubaMultiply();
        }

        private static void Main(string[] args)
        {
            using (new MPI.Environment(ref args))
            {
                if (Communicator.world.Rank == 0)
                {
                    //master process
                    var totalProcessors = Communicator.world.Size - 1;

                    const int firstLength = 7;
                    const int secondLength = 7;
                    var polynomial1 = new Polynomial(firstLength);
                    polynomial1.GenerateRandom();
                    Thread.Sleep(500);
                    var polynomial2 = new Polynomial(secondLength);
                    polynomial2.GenerateRandom();

                    Console.WriteLine("p1 { size = " + polynomial1.Size + " }, degree = " + polynomial1.Degree + "}: \n" + polynomial1);
                    Console.WriteLine("\np2 { size = " + polynomial2.Size + " }, degree = " + polynomial2.Degree + "}:\n" + polynomial2 + "\n");
                    
                    MPIMultiplicationMaster(polynomial1, polynomial2);
                    Console.WriteLine("\n");
                    MPIKaratsubaMaster(polynomial1, polynomial2);
                }
                else
                {
                    //child process
                    MPIMultiplicationWorker();
                    MPIKaratsubaWorker();
                }
            }
        }
    }
}