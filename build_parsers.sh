#!/usr/bin/env bash

ANTLR_JAR=`pwd`/"common/include/antlr-4.7-complete.jar"
OUTPUT_BASE_DIR=`pwd`/"common/languages"
OUTPUT_DIR=$OUTPUT_BASE_DIR/"generated"
GRAMMARS_DIR=$OUTPUT_BASE_DIR/"grammars"
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