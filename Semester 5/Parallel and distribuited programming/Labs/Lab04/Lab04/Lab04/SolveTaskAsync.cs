using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Sockets;
using System.Threading;
using System.Threading.Tasks;

namespace Lab04;

public class SolveTaskAsync
{
    public SolveTaskAsync(List<string> hosts) {
            List<Task> tasks = new();
            hosts.ForEach(host => tasks.Add(Task.Factory.StartNew(run, host)));
            Task.WaitAll(tasks.ToArray());
            Thread.Sleep(1000);
        }

        // Asynchronous method to be executed for each host
        private async void run(object host) {
            string hostAsString = (string)host;
            
            // Resolving the host name to an IP address
            IPAddress IP = Dns.GetHostEntry(hostAsString.Split('/')[0]).AddressList[0];
            IPEndPoint endPoint = new(IP, 80);
            
            // Creating a TCP socket
            Socket client = new(IP.AddressFamily, SocketType.Stream, ProtocolType.Tcp);
            byte[] buffer = new byte[Common.BUFFER_SIZE];

            // Asynchronously initiating the connection
            await connect(client, endPoint);
            
            // Asynchronously sending the HTTP GET request
            await send(client, endPoint, hostAsString);
            
            // Asynchronously receiving the response
            await receive(client, buffer);
            
            client.Shutdown(SocketShutdown.Both);
            client.Close();

            Common.interpretResponse(buffer);
        }

        // Asynchronous method to initiate the socket connection
        private async Task connect(Socket clientSocket, IPEndPoint endPoint) {
            TaskCompletionSource<int> promise = new();
            clientSocket.BeginConnect(endPoint, (ar) => promise.SetResult(endConnectWrapper(clientSocket, ar)), null);
            await promise.Task;
        }

        // Callback method for the end of the socket connection
        private int endConnectWrapper(Socket clientSocket, IAsyncResult ar) {
            clientSocket.EndConnect(ar);
            return 1;
        }

        // Asynchronous method to send data over the socket
        private async Task send(Socket clientSocket, IPEndPoint endPoint, string host) {
            TaskCompletionSource<int> promise = new();
            byte[] getRequest = Common.getRequestContent(host);
            clientSocket.BeginSend(getRequest, 0, getRequest.Length, 0, (ar) => promise.SetResult(clientSocket.EndSend(ar)), null);
            await promise.Task;
        }

        // Asynchronous method to receive data from the socket
        private async Task receive(Socket clientSocket, byte[] buffer) {
            TaskCompletionSource<int> promise = new();
            clientSocket.BeginReceive(buffer, 0, Common.BUFFER_SIZE, 0, (ar) => promise.SetResult(clientSocket.EndReceive(ar)), null);
            await promise.Task;
        }
}