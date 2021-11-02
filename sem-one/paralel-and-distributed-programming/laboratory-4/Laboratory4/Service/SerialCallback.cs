using System;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using Laboratory4.Models;
using Laboratory4.Utils;

namespace Laboratory4.Service
{
    public static class SerialCallback
    {
        public static void StartSerialExecution()
        {
            var id = new Random().Next();
            var host = ProgramConstants.Hosts.ElementAt(1).Split("/")[0];
            var hostInformation = Dns.GetHostEntry(host);
            var addresses = hostInformation.AddressList[0];
            var remoteEndpoint = new IPEndPoint(addresses, ProgramConstants.DefaultPort);
            var socket = new Socket(remoteEndpoint.AddressFamily,
                SocketType.Stream,
                ProtocolType.Tcp);
            var state = new CustomState {
                Socket = socket,
                Hostname = host,
                Endpoint = host.Contains("/") ? host[host.IndexOf("/", StringComparison.Ordinal)..] : "/",
                RemoteEndpoint = remoteEndpoint,
                Id = id
            };
            socket.BeginConnect(state.RemoteEndpoint, OnConnect, state);
        }

        private static void OnConnect(IAsyncResult ar)
        {
            var message = (CustomState)ar.AsyncState;
            if (message == null) return;
            message.Socket.EndConnect(ar);
            Console.WriteLine(nameof(OnConnect));
            var header =
                Encoding.ASCII.GetBytes(ProgramConstants.BuildHeaderRequest(message.Endpoint, message.Hostname));
            message.Socket.BeginSend(header, 0, header.Length, 0, OnSend, message);
        }

        private static void OnSend(IAsyncResult ar)
        {
            Console.WriteLine(nameof(OnSend));
            var message = (CustomState)ar.AsyncState;
            message?.Socket.BeginReceive(message.Buffer, 0, CustomState.BufferSize, 0, OnReceive, message);
        }

        private static void OnReceive(IAsyncResult ar)
        {
            var message = (CustomState)ar.AsyncState;
            if (message == null) return;
            try
            {
                var bytes = message.Socket.EndReceive(ar);
                var socket = message.Socket;
                message.ResponseContent.Append(Encoding.ASCII.GetString(message.Buffer, 0, bytes));
                if (!message.ResponseContent.ToString().Contains("\r\n\r\n"))
                {
                    socket.BeginReceive(message.Buffer, 0, CustomState.BufferSize, 0, OnReceive, message);
                }
                else
                {
                    var body = ProgramConstants.GetResponseBody(message.ResponseContent.ToString());
                    var headerLength = ProgramConstants.GetContentLength(message.ResponseContent.ToString());
                    if (body.Length < headerLength)
                    {
                        socket.BeginReceive(message.Buffer, 0, CustomState.BufferSize, 0, OnReceive, message);
                    }
                    else
                    {
                        ResponseBuilder.BuildResponse(message);
                        socket.Shutdown(SocketShutdown.Both);
                        socket.Close();
                    }
                }
            }
            catch (Exception e)
            {
                throw new Exception(e.Message);
            }
        }
    }

    internal static class ResponseBuilder
    {
        public static void BuildResponse(CustomState message)
        {
            Console.WriteLine(nameof(BuildResponse));
            // Write the string array to a new file named "WriteLines.txt".
            using var outputFile = new StreamWriter(Path.Combine(ProgramConstants.ResponsePath, $"{message.Hostname}_{message.Id}.txt"));
            foreach (var line in message.ResponseContent.ToString().Split('\r', '\n'))
                outputFile.WriteLine(line);
        }
    } 
}