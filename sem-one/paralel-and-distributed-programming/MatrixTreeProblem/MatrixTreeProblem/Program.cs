using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace MatrixTreeProblem
{
    public class Tree
    {
        private Node _root;
 
        // Tree Node
        public class Node
        {
            public readonly int Data;
            public Node Left, Right;
            public Node(int data)
            {
                Data = data;
                Left = null;
                Right = null;
            }
        }
 
        // Function to insert nodes in level order
        private static Node InsertLevelOrder(IReadOnlyList<int> arr,
            Node root, int i)
        {
            // Base case for recursion
            if (i >= arr.Count) return root;
            var temp = new Node(arr[i]);
            root = temp;
 
            // insert left child
            root.Left = InsertLevelOrder(arr,
                root.Left, 2 * i + 1);
 
            // insert right child
            root.Right = InsertLevelOrder(arr,
                root.Right, 2 * i + 2);
            return root;
        }
 
        // Function to print tree
        // nodes in InOrder fashion
        private static void InOrder(Node root)
        {
            if (root == null) return;
            InOrder(root.Left);
            Console.Write(root.Data + " ");
            InOrder(root.Right);
        }

        public static int TreeSum(Node node, int depth)
        {
            if (node == null)
            {
                return 0;
            }
            Console.WriteLine($"I'm Task {Task.CurrentId}");
            Console.WriteLine($"Depth is {depth}");
            if (depth > 7)
            {
                
                return node.Data + TreeSum(node.Right, depth) + TreeSum(node.Left, depth);
            }
            var leftTask = new Task<int>(() => TreeSum(node.Left, depth + 2));
            var rightTask = new Task<int>(() => TreeSum(node.Right, depth + 2));
            var tasks = new List<Task<int>> { leftTask, rightTask };
            tasks.ForEach(e => e.Start());
            var results = Task.WhenAll(tasks).Result;
            return node.Data + results[0] + results[1];

        }

        // Driver code
        public static void Main()
        {
            var t2 = new Tree();
            int []arr = { 1, 2, 3, 4, 5, 6, 6, 6, 6, 10, 15,1, 2, 3, 4, 5, 6, 6, 6, 6, 10, 15 };
            t2._root = InsertLevelOrder(arr, t2._root, 0);
            Console.WriteLine(TreeSum(t2._root, 0));
            Console.WriteLine(arr.Sum());
        }
    }
}