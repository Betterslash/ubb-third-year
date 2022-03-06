using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ColoringProblem
{

    class Node
    {
        public int Color { get; set; }
        public List<Node> Neighbours { get; set; }
    }

    
    
    class Program
    {
        public static bool IsSafe(List<Node> nodes, int[] colors)
        {
            for (int i = 0; i < colors.Length; i++)
            {
                nodes[i].Color = colors[i];
            }

            var ok = true;
            nodes.ForEach(e =>
            {
                var contains = e.Neighbours
                    .Select(t => t.Color)
                    .Contains(e.Color);
                if (contains)
                {
                    ok = false;
                }
            });
            return ok;
        }

        static void Color(int[] possibilities,
            List<int> colors,
            int position,
            List<Node> nodes,
            int begin,
            int end)
        {
            if (possibilities.Length == position)
            {
                if (IsSafe(nodes, possibilities))
                {
                    foreach (var possibility in possibilities)
                    {
                        Console.Write($"{possibility} ");
                    }
                    Console.WriteLine();
                }
            }
            else
            {
                for(int i = 0; i < end; i++)
                {
                    possibilities[position] = colors[i];
                    Color(possibilities, colors, position + 1 , nodes, begin, end);
                }
            }
        }

        static void Main(string[] args)
        {
            var collors = new[] { 1, 2, 3, 4 };
            var node1 = new Node
            {
                Color = -1,
                Neighbours = new List<Node>()
            };
            var node2 = new Node
            {
                Color = -1,
                Neighbours = new List<Node>()
            };
            var node3 = new Node
            {
                Color = -1,
                Neighbours = new List<Node>()
            };
            var node4 = new Node
            {
                Color = -1,
                Neighbours = new List<Node>()
            };
            node1.Neighbours.Add(node2);
            node1.Neighbours.Add(node3);
            node2.Neighbours.Add(node1);
            node3.Neighbours.Add(node4);
            node3.Neighbours.Add(node1);
            node4.Neighbours.Add(node3);
            var graph = new List<Node>
            {
                node1, node2, node3, node4
            };
            var t1 = Task.Run(() =>
            {
                Color(new int[4], collors.ToList(), 0, graph, 0, 1);
            });
            var t2 = Task.Run(() =>
            {
                Color(new int[4], collors.ToList(), 0, graph, 2, 3);
            });
            var tsks = new[] { t1, t2};
            Task.WaitAll(tsks);
        }
    }
}