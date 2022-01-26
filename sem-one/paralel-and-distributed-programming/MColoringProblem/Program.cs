/*using System;
using System.Collections.Generic;
using System.Linq;

namespace MColoringProblem
{
    internal static class Program
    {
        internal enum GraphColor
        {
            Red,
            Green,
            Blue,
            Orange,
            Purple
        }

        internal class Node
        {
            public Guid Id { get; }

            private Node(Guid id, GraphColor color, IList<Node> children)
            {
                Id = id;
                Color = color;
                Children = children;
            }

            public static Node CreateInstance(Guid id, GraphColor color, IList<Node> children)
            {
                return new Node(id, color, children);
            }

            public GraphColor Color { get; }
            public IList<Node> Children { get; set; }
        }

        private static class Solver
        {
            public static void GenerateChildren(Node node, int nodesNumber)
            {
                if (nodesNumber == 0)
                {
                    return;
                }

                var colors = Enum.GetValues(typeof(GraphColor))
                    .Cast<GraphColor>()
                    .ToList();
                var possibleColors = colors.Where(e => e != node.Color)
                    .ToList();

                possibleColors.ForEach(e =>
                {
                    var nextNode = Node.CreateInstance(Guid.NewGuid(), e, new List<Node>());
                    node.Children.Add(nextNode);
                    GenerateChildren(nextNode, nodesNumber - 1);
                });
            }
        }

        private static void PrettyPrint(Node node, string tabs)
        {
            node.Children
                .ToList()
                .ForEach(q =>
                {
                    Console.WriteLine($"{tabs} Id: {q.Id} | Color: {q.Color}");
                    if (q.Children.Count == 0) return;
                    foreach (var objChild in q.Children)
                    {
                        PrettyPrint(objChild, tabs + "\t");
                    }
                });
        }
        
        private static void Main(string[] args)
        {
            var nodeOne = Node.CreateInstance(Guid.NewGuid(), GraphColor.Blue, new List<Node>());
            var nodeTwo = Node.CreateInstance(Guid.NewGuid(), GraphColor.Blue, new List<Node>());
            var nodeThree = Node.CreateInstance(Guid.NewGuid(), GraphColor.Blue, new List<Node>());
            var nodeFour = Node.CreateInstance(Guid.NewGuid(), GraphColor.Blue, new List<Node>());
            nodeOne.Children = new List<Node>{nodeTwo, nodeThree, nodeFour};
            nodeTwo.Children = new List<Node>{nodeOne, nodeThree, nodeFour};
            nodeThree.Children = new List<Node>{nodeTwo, nodeOne, nodeFour};
            nodeFour.Children = new List<Node>{nodeTwo, nodeThree, nodeOne};
            var adjencyMatrix = new[]
            {
                new []{0, 2, 3, 4},
                new []{2, 0, 1, 3},
                new []{3, 1, 0, 2},
                new []{4, 3, 2, 0}
            };
            var root = Node.CreateInstance(Guid.NewGuid(), GraphColor.Red, new List<Node>());
            Solver.GenerateChildren(nodeOne, 4);
            Console.WriteLine($"Id: {root.Id} | Color: {root.Color}");
            PrettyPrint(root, "");
        }
    }
}*/