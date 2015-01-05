#!/bin/bash

#redirecting to /dev/null suppresses console output
mv $1 /Users/ajoseph/Desktop/lejos_superoptimiser/file1.java > /dev/null 2>/dev/null
mv $2 /Users/ajoseph/Desktop/lejos_superoptimiser/file2.java > /dev/null 2>/dev/null
cd /Users/ajoseph/Desktop/lejos_superoptimiser/
cat LeJOSTestTop.java file2.java LeJOSTestBottom.java > LeJOSTest.java
# for this one, concatenate head and tail of a sample application and run it through the same nxj emulator, except reroute the output to a file, parse it for just a simple number and then echo that back to SATE
/usr/local/lejos_nxj/bin/nxjc LeJOSTest.java > /dev/null 2>/dev/null
/usr/local/lejos_nxj/bin/nxj -u LeJOSTest > /dev/null 2>/dev/null
/usr/local/lejos_nxj/bin/emu-lejosrun LeJOSTest.nxj > output.txt 2>/dev/null
grep drawString output.txt | cut -f1 -d, | cut -f2 -ds
