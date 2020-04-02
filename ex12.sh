#!/bin/bash
#shir sabo 206481665
chmod 700 ex12.sh
input="$1"
cd "$1"
counter=1
try=$(ls | sort -V)
for file in $try
do
if [[ "$file" == *.txt ]]
then
	printf "$counter "
	counter=$((counter+1))
	echo -n "$file "
	echo "is a file"
fi
done 
try2=$(ls | sort -V)
for file in $try2
do
if [[ -d $file ]]; then
	printf "$counter "
	counter=$((counter+1))
 	echo -n "$file "
 	echo "is a directory"
fi
done
