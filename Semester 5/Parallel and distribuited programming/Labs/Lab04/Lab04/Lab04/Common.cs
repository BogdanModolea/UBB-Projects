using System;
using System.Text;

namespace Lab04;

public class Common
{
    public static int BUFFER_SIZE = 1024;
    
    
    // Method to generate a simple HTTP GET request as a byte array
    public static byte[] getRequestContent(string host)
    {
        // Extracting the request endpoint from the provided host
        string requestEndpoint = host.Contains("/") ? host.Substring(host.IndexOf("/")) : "/";
        // Constructing the GET request string
        string getRequestAsString = $"GET {requestEndpoint} HTTP/1.1\r\nHost: {host.Split('/')[0]}\r\nContent-Length: 0\r\n\r\n";
        
        return Encoding.ASCII.GetBytes(getRequestAsString);
    }

    // Method to interpret and display information from a byte array representing an HTTP response
    public static void interpretResponse(byte[] response)
    {
        string content = Encoding.ASCII.GetString(response, 0, BUFFER_SIZE);
        string[] splitContent = content.Split('\r', '\n');

        StringBuilder headerBuilder = new StringBuilder();
        
        
        foreach (string line in splitContent)
        {
            if (line.Length == 0)
            {
                continue;
            }

            // Processing Content-Length header to display the length of the content
            if (line.StartsWith("Content-Length:"))
            {
                headerBuilder.AppendLine($"Length: {line.Split(':')[1]} bytes");
            }
            else
            {
                headerBuilder.AppendLine(line);
                if (line.StartsWith("Content-Type:"))
                {
                    break;
                }
            }
        }

        string header = headerBuilder.ToString();
        Console.WriteLine(header + "\n\n\n");
    }

}