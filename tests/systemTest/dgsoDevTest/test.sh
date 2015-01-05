#!/bin/bash

# Deterministic test to see if the program outputted matches expected values:

if grep -q "variableA" "$1"; then
   echo "true test found variableA"
   exit 0
else
   echo "false test failed"
   exit 1
fi