# Operating-System-Concepts
My codes to illustrate what I learn from dinosaur book (Operating System Concepts)

## Process synchronization
Package [com.tamco.synchronization](com.tamco.synchronization)
is some illustration about process synchronization. 
Peterson solution is a software solution to synchronization
problem. But it is complex and not useful in modern computer
architecture. Fortunately most of architectures now provide
special instructions such as [test_and_set](https://en.wikipedia.org/wiki/Test-and-set)
, [compare_and_swap](https://en.wikipedia.org/wiki/Compare-and-swap)
that are un-interruptable (meaning atomically). We
can use them to solve synchronization problem.