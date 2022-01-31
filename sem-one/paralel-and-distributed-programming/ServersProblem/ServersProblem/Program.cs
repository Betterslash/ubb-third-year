using System;
using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;
using MPI;
using Environment = MPI.Environment;

namespace ServersProblem
{
    [Serializable]
    internal class Event
    {
        public string Log { get; set; }

        public override string ToString()
        {
            return Log;
        }
    }

    internal static class Program
    {
        internal static bool IsExitMessage(Event e)
        {
            return e.Log == "Exit";
        }

        internal static void Master()
        {
            var eventsNum = 0;
            var history = new List<Event>();
            var worldSize = Communicator.world.Size - 1;
            while (true)
            {
                for (var i = 1; i <= worldSize; i++)
                {
                    var received = Communicator.world.Receive<Event>(i, 0);
                    history.Add(received);
                }

                if (eventsNum >= 30)
                {
                    history.Add(new Event{Log = "Exit"});
                    Communicator.world.Broadcast(ref history, 0);
                    break;
                }

                Communicator.world.Broadcast(ref history, 0);
                eventsNum++;
            }
        }

        internal static void Server()
        {
            Task.Run(() =>
            {
                var eventTime = 0;
                while (true)
                {
                    Communicator.world.Send(new Event
                    {
                        Log = $"Event from {Communicator.world.Rank} at time {eventTime}"
                    }, 0, 0);
                    eventTime++;
                    Thread.Sleep(100);
                }
            });
            var running = true;
            var history = new List<Event>();
            while (running)
            {
                Communicator.world.Broadcast(ref history, 0);
                if (IsExitMessage(history[^1]))
                {
                    running = false;
                }
            }

            history.ForEach(e => Console.WriteLine($"{e} Process {Communicator.world.Rank}"));
        }

        private static void Main(string[] args)
        {
            using (new Environment(ref args))
            {
                if (Communicator.world.Rank == 0)
                {
                    Master();
                }
                else
                {
                    Server();
                }
            }
        }
    }
}