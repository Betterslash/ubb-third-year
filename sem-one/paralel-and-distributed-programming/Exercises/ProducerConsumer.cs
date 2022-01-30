using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;

namespace Exercises
{
    public static class ProducerConsumer
    {
        private static readonly List<int> ToBeAdded = new();
        private static readonly Mutex Mutex = new();
        private static int _sum;
        private static bool _isClosed;
        public static void StartBackgroundTask()
        {
            Task.Run(() =>
            {
                lock (Mutex)
                {
                    while (!_isClosed)
                    {
                        while (ToBeAdded.Count == 0 && !_isClosed)
                        {
                            Monitor.Wait(Mutex);
                        }

                        if (ToBeAdded.Count <= 0) continue;
                        var lastElement = ToBeAdded[0];
                        ToBeAdded.RemoveAt(0);
                        _sum += lastElement;
                    }
                }        
            });
        }
        public static void Enqueue(int element)
        {
            lock (Mutex)
            {
                ToBeAdded.Add(element);
                Monitor.Pulse(Mutex);
            }
        }
        public static int GetResult()
        {
            lock (Mutex)
            {
                Monitor.PulseAll(Mutex);
                _isClosed = true;
            }
            return _sum;
        }
    }
}