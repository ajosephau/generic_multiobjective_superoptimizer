#!/bin/bash

# echo "true test completed for file" $1

if grep -q "variableA" "$1"; then
   echo "true test found variableA"
   exit 0
else
   echo "false test failed"
   exit 1
fi