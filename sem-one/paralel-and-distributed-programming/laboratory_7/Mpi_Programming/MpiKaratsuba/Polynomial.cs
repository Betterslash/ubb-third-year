using System;
using System.Text;

namespace MpiKaratsuba
{
    [Serializable]
    public class Polynomial
    {
        public int Degree { get; set; }
        public int[] Coefficients { get; set; }
        public int Size;

        public Polynomial(int s)
        {
            Degree = s;
            Size = s + 1;
            Coefficients = new int[Size];
        }

        public void GenerateRandom()
        {
            Random rnd = new();

            for (int i = 0; i < Size; i++)
            {
                Coefficients[i] = rnd.Next(-10, 10);
                if(i == Size - 1)
                {
                    while(Coefficients[i] == 0)
                    {
                        Coefficients[i] = rnd.Next(-10, 10);
                    }
                }
            }
        }

        public override string ToString()
        {
            var sb = new StringBuilder();

            for(var i = Size - 1; i >= 0; i--)
            {
                switch (Coefficients[i])
                {
                    case 0:
                        continue;
                    case < 0:
                        sb.Append(Coefficients[i]);
                        break;
                    case > 0:
                    {
                        if (i < Size - 1)
                        {
                            sb.Append('+');
                        }
                        sb.Append(Coefficients[i]);
                        break;
                    }
                }


                if (i == 1)
                {
                    sb.Append('*');
                    sb.Append('X');
                }
                else if(i != 0)
                {
                    sb.Append('*');
                    sb.Append("X^");
                    sb.Append(i);
                }
            }

            return sb.ToString();
        }

        internal Polynomial GetLast(int m)
        {
            Polynomial result = new(m - 1);

            for (int i = 0; i < m; i++)
            {
                result.Coefficients[i] = Coefficients[i];
            }

            return result;
            
        }

        internal Polynomial GetFirst(int m)
        {
            var result = new Polynomial(m - 1);
            var k = 0;
            for (var i = Size - m; i < Size; i++)
            {
                result.Coefficients[k] = Coefficients[i];
                k++;
            }

            return result;
        }

        internal Polynomial Sum(Polynomial b)
        {
            var size1 = Size;
            var size2 = b.Size;

            var sizeMax = (size1 > size2) ? size1 : size2;

            var result = new Polynomial(sizeMax - 1);

            for(var i = 0; i < sizeMax; i++)
            {
                var res = 0;
                if(i < size1)
                {
                    res += Coefficients[i];
                }
                if(i < size2)
                {
                    res += b.Coefficients[i];
                }
                result.Coefficients[i] = res;
            }

            return result;
        }

        internal Polynomial AddZerosLeft(int v)
        {
            
            var newCoef = new int[Size + v];

            for(var i = 0; i < Size; i++)
            {
                newCoef[i] = Coefficients[i];
            }
            for (var i = Size; i < Size +v; i++)
            {
                newCoef[i] = 0;
            }

            Coefficients = newCoef;
            Size = Coefficients.Length;

            return this;
        }

        internal Polynomial AddZerosRight(int v)
        {
            var result = new Polynomial(Size + v - 1);

            for (var i = v; i < Size + v; i++)
            {
                result.Coefficients[i] = Coefficients[i - v];
            }

            return result;
        }

        internal Polynomial Difference(Polynomial b)
        {
            var size1 = Size;
            var size2 = b.Size;

            var sizeMax = (size1 > size2) ? size1 : size2;

            var result = new Polynomial(sizeMax - 1);

            for (var i = 0; i < sizeMax; i++)
            {
                var res = 0;
                if (i < size1)
                {
                    res = Coefficients[i];
                }
                if (i < size2)
                {
                    res -= b.Coefficients[i];
                }
                result.Coefficients[i] = res;
            }

            return result;
        }
    }
}