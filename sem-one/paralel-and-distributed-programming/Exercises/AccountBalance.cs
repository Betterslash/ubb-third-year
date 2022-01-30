using System.Threading;

namespace Exercises
{
    public class AccountBalance
    {
        public int Id;
        public int Balance;
        private readonly Mutex _mutex = new();

        public static bool Transfer(AccountBalance from, AccountBalance to, int amount)
        {
            if (from.Id < to.Id)
            {
                to._mutex.WaitOne();
                from._mutex.WaitOne();   
                if (from.Balance < amount) return false;
                {
                    from.Balance -= amount;
                    to.Balance += amount;
                }
                from._mutex.ReleaseMutex();
                to._mutex.ReleaseMutex();
            }

            if (from.Id <= to.Id) return true;
            from._mutex.WaitOne();
            to._mutex.WaitOne();
            if (from.Balance < amount) return false;
            {
                from.Balance -= amount;
                to.Balance += amount;
            }
            to._mutex.ReleaseMutex();
            from._mutex.ReleaseMutex();
            return true;
        }
    }
}