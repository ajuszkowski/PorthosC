#!/usr/bin/env bash

ANTLR_JAR=`pwd`/"lib/antlr-4.7-complete.jar"
BASE_DIR=`pwd`/"src/main/java/mousquetaires"
OUTPUT_DIR=$BASE_DIR/"languages/generated"
GRAMMARS_DIR=$BASE_DIR/"languages/grammars"
OUTPUT_PACKAGE="mousquetaires.languages.generated"

current_dir=`pwd`
cd $GRAMMARS_DIR
for g in *.g4; do
    command="java -jar $ANTLR_JAR $g -visitor -listener -package $OUTPUT_PACKAGE -o $OUTPUT_DIR"
    echo "Running $command..."
    eval $command
done
cd $current_dir
echo "Finished."