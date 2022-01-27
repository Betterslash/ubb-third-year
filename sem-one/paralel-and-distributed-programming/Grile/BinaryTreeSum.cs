using System;
using System.Collections.Generic;

namespace BinaryTreeSum{
    
    public static class Node{ 
        public int Value { get; set; }
        public Node Right { get; set; }
        public Node Left { get; set; }
    }

    public static class Program{

        public static Node FromMatrixToTree(int[][] matrix, int heigth, int width){
            var arr = LiniarizeMatrix(matrix, width, heigth);
            if (i < arr.Length){
                Node temp = new Node(arr[i]);
                root = temp;
                root.left = insertLevelOrder(arr,
                                root.left, 2 * i + 1);
                root.right = insertLevelOrder(arr,
                                root.right, 2 * i + 2);
            }
            return root;
        }

        public static List<int> LiniarizeMatrix(int[][] matrix, int heigth, int width){
            var result = new List<int>();
            for(var i = 0; i < heigth; i++){
                result.AddRange(matrix[i]);
            }
            return result;
        }

        public static void Main(){
            
        }

    }
}