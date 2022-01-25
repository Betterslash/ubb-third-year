using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace MColoringProblem
{
    public class Node
    {
        private Node(int value, int color, List<Node> neighbours)
        {
            Value = value;
            Color = color;
            Neighbours = neighbours;
        }

        public int Value { get; }
        public int Color { get; set; }
        public List<Node> Neighbours { get; set; }

        public static Node CreateInstance(int value, int color, List<Node> neighbours)
        {
            return new Node(value, color, neighbours);
        }

        public override string ToString()
        {
            return Value +" "+ Color;
        }
    }
    
    public class Graph
    {
        public List<Node> Nodes { get; init; }

        public void Print()
        {
            Nodes.ForEach(e =>
            {
                Console.Write($"Value : {e.Value} with neighbours ");
                e.Neighbours.ForEach(q => Console.Write(q.Value + " "));
                Console.WriteLine();
            });
        }
    }
    
    public static class MColoringSolver
    {
        private static bool IsSafe(Node node, int color)
        {
            var mappedNeighbours = node.Neighbours.Select(e => e.Color);
            var neighbours = mappedNeighbours.ToList();
            if (!neighbours.Any())
            {
                return true;
            }
            return !neighbours.Contains(color);
        }

        private static void Solve(Graph graph, int currentColor, int maxColor, ICollection<int> visited)
        {
            if (graph.Nodes.Count(q => q.Color != -1) == graph.Nodes.Count || currentColor >= maxColor)
            {
                return;
            }
            graph.Nodes.ForEach(e =>
            {
                if (visited.Contains(e.Value)) return;
                if (!IsSafe(e, currentColor))
                {
                    Task.Run(() => Solve(graph, currentColor + 1, maxColor, visited));
                }
                else
                {
                    e.Color = currentColor;
                    visited.Add(e.Value);
                }
            });
        }

        private static Graph GenerateRandomGraph(int nodesNumber)
        {
            var graph = new Graph
            {
                Nodes = new List<Node>()
            };
            for (var i = 0; i < nodesNumber; i++)
            {
                 graph.Nodes.Add(Node.CreateInstance(i + 1, -1, new List<Node>()));
            }

            var random = new Random();
            var nN = random.Next(0, nodesNumber - 1);
            var neighbours = Enumerable.Range(1, nodesNumber).ToList();
            for (var i = 0; i < nodesNumber; i++)
            {
                var copyN = new List<int>(neighbours);
                for (var j = 0; j < nN; j++)
                {
                    var neighbour = random.Next(0, copyN.Count);
                    graph.Nodes.FirstOrDefault(node => node.Value == i)
                        ?.Neighbours.Add(graph.Nodes.FirstOrDefault(node => node.Value == neighbour));

                    copyN.Remove(neighbour);
                }
            }
            return graph;
        }

        public static void Main(string[] args)
        {
            var graph = GenerateRandomGraph(7);

            Solve(graph, 0, 5, new List<int>());
            graph.Nodes
                .ForEach(Console.WriteLine);
            graph.Print();
            Console.WriteLine();
        }
    }
}