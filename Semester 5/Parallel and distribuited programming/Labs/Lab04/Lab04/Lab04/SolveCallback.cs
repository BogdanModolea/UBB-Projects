using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Sockets;
using System.Threading;

namespace Lab04;

public class SolveCallback
{
    public SolveCallback(List<string> hosts) => hosts.ForEach(host => {
        start(host);
        Thread.Sleep(500);
    });
    
    
    private void start(string host) {
        // Resolving the host name to an IP address
        IPAddress IP = Dns.GetHostEntry(host.Split('/')[0]).AddressList[0];
        IPEndPoint endPoint = new(IP, 80);
        
        // Creating a TCP socket
        Socket client = new(IP.AddressFamily, SocketType.Stream, ProtocolType.Tcp);
        
        // Initiating a connection asynchronously and providing a callback method
        client.BeginConnect(endPoint, onConnected, new Payload(client, endPoint, host));
    }

    // Callback method for the completion of the socket connection
    private void onConnected(IAsyncResult connectionPayload) {
        Payload payload = (Payload)connectionPayload.AsyncState;
        
        // Generating the HTTP GET request content
        byte[] getRequest = Common.getRequestContent(payload.host);
        
        // Initiating the send operation asynchronously and providing a callback method
        payload.clientSocket.BeginSend(getRequest, 0, getRequest.Length, 0, onSend, payload);
    }

    // Callback method for the completion of the send operation
    private void onSend(IAsyncResult connectionPayload) {
        Payload payload = (Payload)connectionPayload.AsyncState;
        
        // Initiating the receive operation asynchronously and providing a callback method
        payload.clientSocket.BeginReceive(payload.buffer, 0, Common.BUFFER_SIZE, 0, onReceive, payload);
    }

    // Callback method for the completion of the receive operation
    private void onReceive(IAsyncResult connectionPayload) {
        Payload payload = (Payload)connectionPayload.AsyncState;
        
        // Ending the receive operation
        payload.clientSocket.EndReceive(connectionPayload);
        
        payload.clientSocket.Shutdown(SocketShutdown.Both);
        payload.clientSocket.Close();
        Common.interpretResponse(payload.buffer);
    }
}