// --------------------------------
// projects/collatz/RunCollatz.java
// Copyright (C) 2011
// Glenn P. Downing
// --------------------------------

/*
To run the program:
    % javac -Xlint RunCollatz.java
    % java  -ea    RunCollatz < RunCollatz.in > RunCollatz.out

To document the program:
    % javadoc -d html -private Collatz.java
*/

// -------
// imports
// -------

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import java.util.Scanner;

import java.io.*;
import java.util.Random; //for random inputs

// ----------
// RunCollatz
// ----------

final class RunCollatz {
    private static final int MAX_INPUT = 1000000;
    private static final int MIN_INPUT = 1;
    private static final int NUM_PAIR = 1000;
    
    // -------------
    // genPairs
    // -------------

    public static void genPairs(String fileName) throws IOException { //generate pairs and write the input file
        FileWriter fstream = new FileWriter(fileName);
        BufferedWriter out = new BufferedWriter(fstream);
        
        int i = 0;
        int j = 0;
        for(int k=0;k<NUM_PAIR;k++){ // make sure that i and j are never less than 1
            i = MIN_INPUT + (int)(Math.random() * ((MAX_INPUT - MIN_INPUT) + 1));
            j = MIN_INPUT + (int)(Math.random() * ((MAX_INPUT - MIN_INPUT) + 1));
            assert i > 0;
            assert j > 0;
            out.write(i+" "+j+"\n");
        }   
        out.flush();
    }
    public static void main (String[] args) throws IOException {
        final Scanner r = new Scanner(System.in);
        final Writer  w = new PrintWriter(System.out);
        Collatz.solve(r, w);}}
