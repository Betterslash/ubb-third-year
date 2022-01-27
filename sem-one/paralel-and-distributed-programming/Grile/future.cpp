// template <typename T>
// class Future
// {
//     list<function<void(T)>> continuations;
//     T val;
//     bool hasValue;
//     mutex mtx;

// public:
//     Future() : hasValue(false) {}
//     void set(T v)
//     {
//         val = v;
//         hasValue = true;
//         for (function<void(T)> &f : continuations)
//         {
//             unique_lock<mutex> lck(mtx);
//             f(v);n
//         }
//         continuations.clear();
//     }
//     void addContinuation(function<void(T)> f)
//     {
//         unique_lock<mutex> lck(mtx);
//         if (hasValue)
//         {
//             f(val);
//         }
//         else
//         {
//             continuations.push_back(f);
//         }
//     }
// };

int scalarProduct(vector<int> const &a,
                  vector<int> const &b,
                  int nrThreads)
{
    // 6 / 2 = 3 rest 0
    // [1, 2, 3, 4, 5, 6]
    // [1, 2, 3, 4, 5, 6]
    int result = 0;
    vector<thread> threads;
    threads.reserve(nrThreads);
    int begin = 0;
    for (int i = 0; i < nrThreads; ++i)
    {
        int end = begin + a.size() / nrThreads;
        //end = begin + (a.size()+nrThreads-1)/nrThreads;
        threads.emplace_back([&a, &b, begin, end, &result]() {
            int part = 0;
            for (int i = begin; i < end; ++i)
            {
                part += a[i] * b[i];
                result += part;
            });
            begin = end;
        }
    }
    for(int i=0 ; i<nrThreads ; ++i) {
                result += threads[i].get();
    }
    return result;
}