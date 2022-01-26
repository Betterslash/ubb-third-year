using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using MPI;
using Environment = MPI.Environment;

namespace MpiServersProblem
{
    internal static class Program
    {

        [Serializable]
        public class Event
        {
            public string Message { get; set; }
        }

        private static void MasterProcess()
        {
            var worldSize = Communicator.world.Size;
            var history = new List<Event>();
            for (var i = 1; i < worldSize; i++)
            {
                var receivedEvent = Communicator.world.Receive<Event>(i, 0); 
                history.Add(receivedEvent);
            }
            history.Select(q => q.Message).ToList().ForEach(Console.WriteLine);
        }

        private static void Main(string[] args)
        {
            using (new Environment(ref args))
            {
                if (Communicator.world.Rank == 0)
                {
                    MasterProcess();
                }
                else
                {
                    WorkerProcess();
                }
            }
        }


        private static string RandomString(int length)
        {
            var random = new Random();
            const string chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            return new string(Enumerable.Repeat(chars, length)
                .Select(s => s[random.Next(s.Length)]).ToArray());
        }
        
        //servers
        private static void WorkerProcess()
        {
            
            var i = 0;
            while (i != 3)
            {
                var random = new Random();
                var sentEvent = new Event
                {
                    Message = RandomString(random.Next(100))
                };
                Communicator.world.Send(sentEvent, 0, 0);
                Thread.Sleep(1000);
                i++;
            }
        }
    }
}