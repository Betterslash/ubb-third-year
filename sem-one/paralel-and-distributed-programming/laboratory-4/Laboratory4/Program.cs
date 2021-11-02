using System.Threading;
using Laboratory4.Service;

namespace Laboratory4
{
    internal static class Program
    {
        private static void Main(string[] args)
        {
            SerialCallback.StartSerialExecution();
            Thread.Sleep(5000);
        }
    }
}