using System;

namespace MpiExercises
{
    internal static class Program
    {
        private static void Main(string[] args)
        {
            var numberOne = "12233";
            var numberTwo = "12345";
            var result = KaratsubaTask.multiplyKaratsuba(numberOne, numberTwo);
            Console.WriteLine(result);
        }

    }
}
