// -----------------------------
// projects/collatz/Collatz.java
// Copyright (C) 2011
// Glenn P. Downing
// -----------------------------

import java.io.IOException;
import java.io.Writer;

import java.util.Scanner;

public final class Collatz {
    private static final int SIZE = 1000000; // the size of the cache.
    private static int[] cache_eval = new int[SIZE]; // this array stores the maximum cycle lengths up to each index.
    private static int[] cache_cycLen = new int[SIZE];  // this array stores all the cycle lengths.
    
    // ----
    // read
    // ----

    /**
     * reads two ints into a[0] and a[1]
     * @param r a  java.util.Scanner
     * @param a an array of int
     * @return true if that succeeds, false otherwise
     */
    public static boolean read (Scanner r, int[] a) {
        if (!r.hasNextInt())
            return false;
        a[0] = r.nextInt();
        a[1] = r.nextInt();
        assert a[0] > 0;
        assert a[1] > 0;
        return true;}

    // ----
    // eval
    // ----

    /**
     * @param i the beginning of the range, inclusive
     * @param j the end       of the range, inclusive
     * @return the max cycle length in the range [i, j]
     */
    public static int eval (int i, int j) {
        assert i > 0;
        assert j > 0;
        // <your code>
        assert i < SIZE;
        assert j < SIZE; // i and j must be between 1 and a million, inclusive.
        int v = 1;
        
        int temp = 0;
        if(i > j){ // swap i and j to make sure that i <= j
            temp = j;
            j = i;
            i = temp;
        }
        assert i <= j;
        
        temp = 0;
        /*
        for(int k = SIZE-1; k >= 0; k--){ //find the known cycle length of the greatest number
            if(cache_eval[k] > 0){ //the cycle length of k is stored in cache
                if(j>=k && i<=(k>>1)){ //k is between 2i and j, inclusive
                     temp = k;
                     break;
                }
            }
        }
        */
        //optimized loop: 
        int di = i<<1; //di stands for double i
        if(di < j){
            for(int k=j; k>=di; k--){
                if(cache_eval[k] > 0){
                    temp = k;
                    break;
                }
            }
        }
        
        if(temp > 0){ //divide-and-conquer
            int a = eval(i, temp>>1); 
            int b = cache_eval[temp]; //if i is less than k/2, ignore everything below k/2
            int c = eval(temp, j); 
            v = ((a > b) ? a : b);
            v = ((v > c) ? v : c);
        }
        else{
            if(i < (j>>1)){
                if(cache_eval[j] > 0){
                    return cache_eval[j];
                }
                i = j>>1;
            }
            for(int k = i; k <= j; k++){
                temp = getCycLen(k);
                if(temp > v){
                    v = temp; //v temporarily stores the maximum value
                }
            } 
        }
        
        assert v > 0;
        cache_eval[j] = v;
        return v;}

    // ---------
    // getCycLen
    // ---------
    
    /**
    * recursively computes the cycle length for input n
    * @param n the initial value for the algorithm
    * @return the cycle length of n
    */
    public static int getCycLen (int n){ 
        //assert(n > 0);
        if(n <= 1){ //base case. halt at one
            return 1; 
        }
        else if(n % 2 == 1){ //n is odd
            if(n < SIZE){
                if(cache_cycLen[n] > 0) return cache_cycLen[n]; //the cache already has the answer
                cache_cycLen[n] = 2 + getCycLen( n + (n>>1) + 1 ); //shortcut shown in Quiz #2. Increment the counter by 2, since two cycles are executed in one recursion
                return cache_cycLen[n]; 
            }
            return 2 + getCycLen( n + (n>>1) + 1 ); 
        }
        else{ //n is even
            if(n < SIZE){
                if(cache_cycLen[n] > 0) return cache_cycLen[n]; //the cache already has the answer
                cache_cycLen[n] = 1 + getCycLen( n>>1 ); // n/2.
                return cache_cycLen[n]; 
            }
            return 1 + getCycLen( n>>1 );
        }
    }

    // -----
    // print
    // -----

    /**
     * prints the values of i, j, and v
     * @param w a java.io.Writer
     * @param i the beginning of the range, inclusive
     * @param j the end       of the range, inclusive
     * @param v the max cycle length
     */
    public static void print (Writer w, int i, int j, int v) throws IOException {
        assert i > 0;
        assert j > 0;
        assert v > 0;
        w.write(i + " " + j + " " + v + "\n");
        w.flush();}

    // -----
    // solve
    // -----

    /**
     * @param r a java.util.Scanner
     * @param w a java.io.Writer
     */
    public static void solve (Scanner r, Writer w) throws IOException {
        final int[] a = {0, 0};
        while (Collatz.read(r, a)) {
            final int v = Collatz.eval(a[0], a[1]);
            Collatz.print(w, a[0], a[1], v);}}}
