#include <iostream>
#include <vector>
#include <thread>
#include <condition_variable> 
#include <list>
#include <mutex>
#include <functional> 

using namespace std;

class ThreadPool{
    condition_variable cv;
    mutex mtx;
    list<function<void()>> work;
    vector<thread> threads;
    bool isFinished = false;
    void run(){
        
        while (!isFinished)
        {
            if(work.empty()){
                unique_lock<mutex> lck(mtx);    
                cv.wait(lck); // -> 
            }
            else{
                unique_lock<mutex> lck(mtx);
                function<void()> vi = work.front();
                work.pop_front();
                vi();
            }
        }     
    }
    public : 
        
        explicit ThreadPool(int n){
            threads.resize(n);
            for (int i = 0; i < n; ++i)
            {
                threads.emplace_back([this]() {run();});
            }
        }

        void enqueue(function<void()> fun){
            unique_lock<mutex> lck(mtx);
            work.push_back(fun);
            cv.notify_one(); 
        }

        void stop(){
            unique_lock<mutex> lck(mtx);
            isFinished = true;
            cv.notify_all();
        }
};

void print(){
    cout<< "Hi there" << endl;
}

int main(){
    ThreadPool* tp = new ThreadPool(5);
    function<void()> fun = []() {print(); }; 
    tp->enqueue(fun);
    tp->stop();
    return 0;
}