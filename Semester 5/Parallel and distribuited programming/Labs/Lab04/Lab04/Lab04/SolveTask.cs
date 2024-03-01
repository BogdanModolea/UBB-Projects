using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Sockets;
using System.Threading.Tasks;

namespace Lab04;

public class SolveTask
{
    public SolveTask(List<string> hosts)
    {
        List<Task> tasks = new();
        hosts.ForEach(host => tasks.Add(Task.Factory.StartNew(run, host)));
        Task.WaitAll(tasks.ToArray());
    }

    private void run(object host)
    {
        string hostAsString = (string)host;
        
        // Resolving the host name to an IP address
        IPAddress IP = Dns.GetHostEntry(hostAsString.Split('/')[0]).AddressList[0];
        IPEndPoint endPoint = new(IP, 80);
        
        // Creating a TCP socket
        Socket client = new(IP.AddressFamily, SocketType.Stream, ProtocolType.Tcp);
        byte[] buffer = new byte[Common.BUFFER_SIZE];

        // Connecting to the server asynchronously
        connect(client, endPoint).Wait();
        
        // Sending the HTTP GET request asynchronously
        send(client, hostAsString).Wait();
        
        // Receiving the response asynchronously
        receive(client, buffer).Wait();
        
        client.Shutdown(SocketShutdown.Both);
        client.Close();

        Common.interpretResponse(buffer);
    }

    // Asynchronously initiate the socket connection
    private Task connect(Socket clientSocket, IPEndPoint endPoint)
    {
        TaskCompletionSource<int> promise = new();
        clientSocket.BeginConnect(endPoint,
            (ar) => promise.SetResult(endConnectWrapper(clientSocket, ar)), null);
        return promise.Task;
    }

    // Callback method for the end of the socket connection
    private int endConnectWrapper(Socket clientSocket, IAsyncResult ar)
    {
        clientSocket.EndConnect(ar);
        return 1;
    }

    // Asynchronously send data over the socket
    private Task send(Socket clientSocket, string host)
    {
        TaskCompletionSource<int> promise = new();
        byte[] getRequest = Common.getRequestContent(host);
        clientSocket.BeginSend(getRequest, 0, getRequest.Length, 0,
            (ar) => promise.SetResult(clientSocket.EndSend(ar)), null);
        return promise.Task;
    }

    // Asynchronously receive data from the socket
    private Task receive(Socket clientSocket, byte[] buffer)
    {
        TaskCompletionSource<int> promise = new();
        clientSocket.BeginReceive(buffer, 0, Common.BUFFER_SIZE, 0,
            (ar) => promise.SetResult(clientSocket.EndReceive(ar)), null);
        return promise.Task;
    }
}
