using System.Collections.Generic;

namespace Lab04
{
    internal class Program
    {
        public static void Main(string[] args)
        {
            List<string> links = new()
            {
                "www.cs.ubbcluj.ro/~rlupsa/",
                "www.pbinfo.ro/",
            };
            
            // new SolveCallback(links);
            // new SolveTask(links);
            new SolveTaskAsync(links);
        }
    }
}