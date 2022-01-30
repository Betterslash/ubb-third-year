using System;
using System.Threading.Tasks;

namespace Exercises
{
    internal static class Program
    {
        public static void Main(string[] args)
        {
            var acc1 = new AccountBalance
            {
                Id = 1,
                Balance = 200
            };
            var acc2 = new AccountBalance
            {
                Id = 2,
                Balance = 200
            };
            var t1 = new Task(() =>
            {
                AccountBalance.Transfer(acc1, acc2, 12);
            });
            var t2 = new Task(() =>
            {
                AccountBalance.Transfer(acc2, acc1, 13);
            });
            t1.Start();
            t2.Start();
            Task.WaitAll(t1, t2);
            Console.WriteLine(acc1.Balance);
            Console.WriteLine(acc2.Balance);
        }
    }
}