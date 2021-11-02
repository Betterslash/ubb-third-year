using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;

namespace Laboratory4.Models
{
    public class CustomState
    {
        // client socket
        public Socket Socket = null;

        // size of receive buffer
        public const int BufferSize = 512;

        // receive buffer  
        public readonly byte[] Buffer = new byte[BufferSize];

        // received data  
        public readonly StringBuilder ResponseContent = new();

        // client id
        public int Id;

        // server's hostname
        public string Hostname;

        // request path
        public string Endpoint;
        
        // server's ip address
        public IPEndPoint RemoteEndpoint;

        // mutex for "connect" operation
        public ManualResetEvent ConnectDone = new(false);

        // mutex for "send" operation
        public ManualResetEvent SendDone = new(false);

        // mutex for "receive" operation
        public ManualResetEvent ReceiveDone = new(false);
    }
}