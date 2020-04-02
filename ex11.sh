#!/bin/bash
#shir sabo 206481665
chmod 700 ex11.sh
input=$1
p=0
while IFS= read -r line || [ -n "$line" ]
do
	p=$[$p+1]
	for i in $line; do
		if [[  $i == "$2" ]]; then
			printf "$p "
			echo "$line" | wc -w
		fi
	done
done < "$input"
