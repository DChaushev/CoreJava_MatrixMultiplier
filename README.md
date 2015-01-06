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
This is the average of 10 attempts.
