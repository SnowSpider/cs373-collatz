#!/usr/bin/env python

# ------------------------------
# projects/collatz/RunCollatz.py
# Copyright (C) 2011
# Glenn P. Downing
# -------------------------------

"""
To run the program
    % python RunCollatz.py < RunCollatz.in > RunCollatz.out
    % chmod ugo+x RunCollatz.py
    % RunCollatz.py < RunCollatz.in > RunCollatz.out

To document the program
    % pydoc -w Collatz
"""

# -------
# imports
# -------

import sys
import random

from Collatz import collatz_solve

# ----
# main
# ----

def genRandPairs (n) :
    input_file = open("RunCollatz.in", "w")
    for i in range(0, 1000):
        input_file.write(str(random.randrange(1, 1000000))+" "+str(random.randrange(1, 1000000))+"\n")
    input_file.close()

genRandPairs(1000)
collatz_solve(sys.stdin, sys.stdout)
