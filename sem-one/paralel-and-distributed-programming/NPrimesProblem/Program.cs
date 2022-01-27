using System;

namespace NPrimesProblem
{
    internal static class Program
    {
        /*
         * Primes(){
         *  var temp = (maxN - sqrtN) / nrProcesses
         *      var lowBound = sqrtN // 10 
         *      var highBound = lowBound + temp; // 28
         *      Send(lowBound, highBound, i, 0)
         *      var received = Receive(i)
         *      var toBeAdded.AddRange(primes(received))
         *      for(i = 1 to nrProcesses){
         *          lowBound = highBound
         *          highBound = lowBound + temp;
         *          Send(lowBound, highBound, i, 0)
         *          received = Receive(i)
         *          result.AddRange(primes(received))
         *      }
         * }
         * 
         * Master(){
         *      maxN : 100 => sqrtN = 10 => [2, 3, 5, 7]
         *      5 procs
         *      maxN
         *      nrProcesses
         *      primesToSqrtN 
         *      
         *      result.AddRange(primes([for highBound .. maxN]))
         * }
         *
         * Worker(){
         *      var received = Receive(low, high)
         *      var generated = [for low..high]
         *      Send(generated)
         * }
         * 
         */
        private static void Main(string[] args)
        {
            Console.WriteLine("Hello World!");
        }
    }
}