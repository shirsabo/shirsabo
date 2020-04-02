#!/bin/bash
#shir sabo 206481665
chmod 700 ex13.sh
input="$1"
cd "$1"
try=$(ls | sort -V)
for file in $try
do
	#directory
if [[ -d $file ]]; then
	cd "$file"
	try2=$(ls | sort -V)
	for file in $try2
		do
			if [[  "$file" == "$2" ]]; then
				cat $file
			fi
		done
	cd ..
elif [[ "$file" == "$2" ]]; then
	cat "$file"
	echo " "
	#statements
fi
done
