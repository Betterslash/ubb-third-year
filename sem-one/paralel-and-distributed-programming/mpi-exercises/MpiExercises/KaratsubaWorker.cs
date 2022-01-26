using MPI;

namespace MpiExercises
{
    public class KaratsubaWorker
    {
        public void work()
        {
            var number = Communicator.world.Receive<string>(0, 0);
            
        }
    }
}