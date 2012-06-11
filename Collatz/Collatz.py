#!/usr/bin/env python

# ---------------------------
# projects/collatz/Collatz.py
# Copyright (C) 2011
# Glenn P. Downing
# ---------------------------

SIZE = 1000000 #the size of the cache.
cache_eval = [0] * SIZE #this array stores the maximum cycle lengths up to each index.
cache_cycLen = [0] * SIZE #this array stores all the cycle lengths.

# ------------
# collatz_read
# ------------

def collatz_read (r, a) :
    """
    reads two ints into a[0] and a[1]
    r is a  reader
    a is an array on int
    return true if that succeeds, false otherwise
    """
    s = r.readline()
    if s == "" :
        return False
    l = s.split()
    a[0] = int(l[0])
    a[1] = int(l[1])
    assert a[0] > 0
    assert a[1] > 0
    return True

# ------------
# collatz_eval
# ------------

def collatz_eval (i, j) :
    """
    i is the beginning of the range, inclusive
    j is the end       of the range, inclusive
    return the max cycle length in the range [i, j]
    """
    assert i > 0
    assert j > 0
    # <your code>
    assert i < SIZE #i and j must be between 1 and a million, inclusive.
    assert j < SIZE
    v = 1
    
    temp = 0;
    if i > j: #swap i and j to make sure that i <= j
        temp = j
        j = i
        i = temp
    assert i <= j
    temp = 0;
    
    di = i<<1; #di stands for deux i
    if di < j:
        for k in range(j, di, -1): #optimization mentioned in Quiz #3
            if cache_eval[k] > 0:
                temp = k
                break
    
    if temp > 0: #divide-and-conquer
        a = collatz_eval(i, (temp>>1))
        b = cache_eval[temp] #if i is less than k/2, ignore everything below k/2
        c = collatz_eval(temp, j)
        if a > b:
            v = a
        else:
            v = b
        if v <= c:
            v = c
        #v = ((a > b) ? a : b)
        #v = ((v > c) ? v : c)
    else:
        if i < (j>>1):
            if cache_eval[j] > 0:
                return cache_eval[j]
            i = j>>1
        for k in range(i, j):
            temp = collatz_getCycLen(k)
            if temp > v:
                v = temp #v temporarily stores the maximum value
    assert v > 0;
    cache_eval[j] = v;
    assert v > 0
    return v

# -----------------
# collatz_getCycLen
# -----------------

def collatz_getCycLen (n) :
    """
    n is the initial value for the algorithm
    return the cycle length of n
    """
    #assert(n > 0);
    if n <= 1: #base case. halt at one
        return 1
    elif n % 2 == 1: #n is odd
        if n < SIZE:
            if cache_cycLen[n] > 0:
                return cache_cycLen[n] #the cache already has the answer
            else:
                cache_cycLen[n] = 2 + collatz_getCycLen( n + (n>>1) + 1 ) #shortcut shown in Quiz #2. Increment the counter by 2, since two cycles are executed in one recursion
                return cache_cycLen[n]
        else: 
            return 2 + collatz_getCycLen( n + (n>>1) + 1 ) 
    else: #n is even
        if n < SIZE:
            if cache_cycLen[n] > 0: 
                return cache_cycLen[n] #the cache already has the answer
            else:
                cache_cycLen[n] = 1 + collatz_getCycLen( n>>1 ) # n/2.
                return cache_cycLen[n] 
        else:
            return 1 + collatz_getCycLen( n>>1 )

# -------------
# collatz_print
# -------------

def collatz_print (w, i, j, v) :
    """
    prints the values of i, j, and v
    w is a writer
    i is the beginning of the range, inclusive
    j is the end       of the range, inclusive
    v is the max cycle length
    """
    w.write(str(i) + " " + str(j) + " " + str(v) + "\n")

# -------------
# collatz_solve
# -------------

def collatz_solve (r, w) :
    """
    read, eval, print loop
    r is a reader
    w is a writer
    """
    a = [0, 0]
    while collatz_read(r, a) :
        v = collatz_eval(a[0], a[1])
        collatz_print(w, a[0], a[1], v)
