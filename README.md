CoreJava_MatrixMultiplier
=========================

This is one of the tasks for Core Java Course I am attending at the university.

This application must read two matrices from binary files, multiply them and write the result to third file.<br/>
The matrices are very large (approximately 1000x1000).<br/>
There must be two versions of multiplications - single threaded and multithreaded.

Observations:<br/>
While computin single threaded multiplication my processor (Intel i7) was working at ~18%.<br/>
But while computing the multithreaded multiplication - it was at ~99%.<br/>

Average single-threaded multiplication time: 893ms.<br/>
Average multi-threaded multiplication time: 219ms.<br/>
This is the average of 10 attempts.<br/>

Example speeds output after running the main: <br/>
a[i] for i from 1 to the number of threads in your cpu (mine is 8)<br/>
and improvement to the linear multiplication: <br/>

--------------------------------------------------------------<br/>
Sync: 872.00<br/>
Async:<br/>
    a[1] = Speed: 872 Improvement: 1.0000 times<br/>
    a[2] = Speed: 504 Improvement: 1.7302 times<br/>
    a[3] = Speed: 361 Improvement: 2.4155 times<br/>
    a[4] = Speed: 318 Improvement: 2.7421 times<br/>
    a[5] = Speed: 312 Improvement: 2.7949 times<br/>
    a[6] = Speed: 271 Improvement: 3.2177 times<br/>
    a[7] = Speed: 241 Improvement: 3.6183 times<br/>
    a[8] = Speed: 217 Improvement: 4.0184 times<br/>
--------------------------------------------------------------<br/>
