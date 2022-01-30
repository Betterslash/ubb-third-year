using System;

namespace Exercises
{
    internal static class Program
    {
        public static void Main(string[] args)
        {
            ProducerConsumer.StartBackgroundTask();
            for (var i = 0; i < 5; i++)
            {
                ProducerConsumer.Enqueue(i);
            }

            var result = ProducerConsumer.GetResult();
           
            Console.WriteLine(result);
        }
    }
}