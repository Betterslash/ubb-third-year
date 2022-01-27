using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace KaratsubaProblem
{
    static class MpiProgram
    {
        static void Run(string[] args)
        {
            Console.WriteLine("Hello World!");
        }
    }

    internal static class ParallelProgram
    {
        private static long Karatsuba(string x, string y)
        {
            if (x.Length == 1 || y.Length == 1)
            {
                return int.Parse(x) * int.Parse(y);
            }
            var n = Math.Max(x.Length, y.Length);
            var power = n / 2;
            var leftX = GetLeft(x); // a
            var rightX = GetRight(x); // b
            var leftY = GetLeft(y); // c
            var rightY = GetRight(y); // d
            var aPlusB = long.Parse(leftX) + long.Parse(rightX);
            var cPlusD = long.Parse(leftY) + long.Parse(rightY);
            var acTask = new Task<long>(() => Karatsuba(leftX, leftY));
            var bdTask = new Task<long>(() => Karatsuba(rightX, rightY));
            var adPlusBcInitialTask = new Task<long>(() => Karatsuba(aPlusB.ToString(), cPlusD.ToString()));
            var tasks = new List<Task<long>> { acTask, bdTask, adPlusBcInitialTask};
            tasks.ForEach(e => e.Start());
            var resulted = Task.WhenAll(tasks).Result;
            return (long)(resulted[0] * Math.Pow(10, power * 2) +
                          (resulted[2] - resulted[0] - resulted[1]) * Math.Pow(10, power) + resulted[1]);
        }

        private static string GetLeft(string str)
        {
            return str.Length % 2 == 0 ? str[..(str.Length / 2)] : str[..(str.Length / 2 + 1)];
        }

        private static string GetRight(string str)
        {
            return str.Length % 2 == 0 ? str[(str.Length / 2)..] : str[(str.Length / 2 + 1)..];
        }

        public static void Run(string[] args)
        {
            const string numberOne = "123";
            const string numberTwo = "20";
            Console.WriteLine(Karatsuba(numberOne, numberTwo));
        }
    }

    internal static class MainClass
    {
        public static void Main(string[] args)
        {
            ParallelProgram.Run(args);
        }
    }
}