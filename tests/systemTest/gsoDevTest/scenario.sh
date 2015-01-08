#!/bin/bash

echo `tests/systemTest/gsoDevTest/scenario1.sh $2` "|" `tests/systemTest/gsoDevTest/scenario2.sh $1 $2`