using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace HamiltonianCycle
{

    public class Node
    {
        public Node(int value, List<Node> neighbours)
        {
            Value = value;
            Neighbours = neighbours;
        }

        public int Value { get; set; }
        public List<Node> Neighbours { get; set; }
        
    }


    public static class Program
    {
        public static void BackTracking(Node startNode, List<Node> nodes, List<int> visited, List<Node> allNodes)
        {
            if (visited.Count == allNodes.Count && CanGoBack(startNode, visited[^1]))
            {
                visited.ForEach(e => Console.Write(e + " "));
                Console.WriteLine();
            }
            else
            {
                foreach (var node in nodes)
                {
                    if (!IsInVisited(node, visited))
                    {
                        var last = visited[^1];
                        var lastVisited = allNodes.Find(e => e.Value == last);
                        if(lastVisited != null && lastVisited.Neighbours.Select(q => q.Value).Contains(node.Value))
                        {
                            visited.ForEach(e => Console.Write(e + " "));
                            Console.WriteLine();
                            visited.Add(node.Value);
                            BackTracking(startNode, node.Neighbours, new List<int>(visited), allNodes);
                        }
                    }
                }
            }
            
        }

        private static bool CanGoBack(Node startNode, int i)
        {
            return startNode.Neighbours.Select(e => e.Value).Contains(i);
        }

        private static bool IsInVisited(Node node, List<int> visited)
        {
            return visited.Contains(node.Value);
        }

        public static void Main(string[] args)
        {
            var n1 = new Node(1, new List<Node>());
            var n2 = new Node(2, new List<Node>());
            var n3 = new Node(3, new List<Node>());
            var n4 = new Node(4, new List<Node>());
            var n5 = new Node(5, new List<Node>());
            var n6 = new Node(6, new List<Node>());

            n1.Neighbours.AddRange(new List<Node> { n2, n3, n6 });
            n2.Neighbours.AddRange(new List<Node> { n1, n5, n6, n3 });
            n3.Neighbours.AddRange(new List<Node> { n1, n2, n4 });
            n4.Neighbours.AddRange(new List<Node> { n3, n5 });
            n5.Neighbours.AddRange(new List<Node> { n2, n4, n6 });
            n6.Neighbours.AddRange(new List<Node> { n1, n2, n5 });
            // 1 -> 3 -> 2 -> 5 -> 4 -> 
            var nodes = new List<Node> { n1, n2, n3, n4, n5, n6 };

            var tasks = new List<Task>();
            BackTracking(n1, n1.Neighbours, new List<int>{n1.Value}, nodes);
        }
    }
}