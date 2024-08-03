#!/bin/bash

opt=
for option in "$@"
do
  opt=""$opt" -D"$option""
done
echo $opt
exec java -Denv=${RUNTIME_ENV:-dev} $opt -jar *.jar