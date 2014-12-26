#!/bin/bash

#redirecting to /dev/null suppresses console output
mv $1 /Users/ajoseph/Desktop/lejos_superoptimiser/LeJOSBenchmark.java
cd /Users/ajoseph/Desktop/lejos_superoptimiser/
/usr/local/lejos_nxj/bin/nxjc LeJOSBenchmark.java > /dev/null
/usr/local/lejos_nxj/bin/nxj -u LeJOSBenchmark > /dev/null
/usr/local/lejos_nxj/bin/emu-lejosrun LeJOSBenchmark.nxj
