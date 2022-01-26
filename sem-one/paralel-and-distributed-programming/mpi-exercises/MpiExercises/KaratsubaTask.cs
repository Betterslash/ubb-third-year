using System;

namespace MpiExercises
{
    public static class KaratsubaTask
    {
        public static long  multiplyKaratsuba(string x, string y)
        {
            if (x.Length == 1 || y.Length == 1)
            {
                return int.Parse(x) * int.Parse(y);
            }

            var n = Math.Max(x.Length, y.Length);
            var power = n / 2;
            var leftX = getLeft(x);
            var rightX = getRight(x);
            var leftY = getLeft(y);
            var rightY = getRight(y);
            var ac = multiplyKaratsuba(leftX, leftY);
            var bd = multiplyKaratsuba(rightX, rightY);
            var adPlusBc =
                multiplyKaratsuba((int.Parse(leftX) + int.Parse(rightX)).ToString(), (int.Parse(leftY) + int.Parse(rightY)).ToString()) - ac -
                bd;
            return (long)(ac * Math.Pow(10, power * 2) + adPlusBc * Math.Pow(10, power) + bd);
        }

        private static string getRight(string s)
        {
            return s.Length % 2 == 0 ? s[(s.Length / 2)..] : s[(s.Length / 2 + 1)..];
        }

        private static string getLeft(string s)
        {
            return s.Length % 2 == 0 ? s[..(s.Length / 2)] : s[..(s.Length / 2 + 1)];
        }
    }
}