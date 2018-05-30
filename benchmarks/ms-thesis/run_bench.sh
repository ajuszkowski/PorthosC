#!/bin/bash

LOG=result.log

echo "[" > $LOG

for i in `seq $1 4 $2 `;
do
	echo "Test i=$i"
	./bench.sh "-i ../litmus_C11/Dekker.c -b $i -t tso" >> $LOG
	echo ", " >> $LOG
done
echo "{}" >> $LOG
echo "]" >> $LOG
