#!/bin/bash
#shir sabo 206481665
chmod 700 ex14.sh
input=$2
counterFile=0
counterInput=0
flag=0;
flag1=0;
p=0
i=-1;
succeed=0;
re=^-?[0-9]+$
for WordInInput in $1; do
	i=$((i+1))
done
while IFS= read -r line
do
	flag=0
	counterFile=0
	counterInput=0
	succeed=0;
		for wordInFile in $line; do
			if [[ "$flag1" -eq 1 ]]; then
				counterFile=$(($counterFile+1))
				if [[ "$counterInput" -gt i ]]; then
					if  [[ $wordInFile =~ $re ]]; then
						succeed=1;
						break;
					else
						succeed=0
						break;
					fi
				fi
				flag1=0;
			fi
			if [[ "$counterFile" -gt i ]]; then
				if  [[ $wordInFile =~ $re ]]; then
					succeed=1;
					break;
				fi
			fi
			if [[ "$flag" -eq -1 ]]; then
				break
			fi 
			counterInput=0
			for WordInInput in $1; do
				if [[ "$counterFile" -ne "$counterInput" ]]; then
					counterInput=$(($counterInput+1))
					continue
				fi
				if [[ "$wordInFile" == "$WordInInput" ]]; then
						flag1=1
						break;
				elif [[ "$wordInFile" != "$WordInInput" ]]; then
						flag=-1
						break;
				fi
			done	
		done
		if [[ "$succeed" -eq 1 ]]; then
			if [[ "$counterInput" -eq i ]]; then
				echo "$line"
				p=$[$p + wordInFile]
			fi
		fi
done < "$input"
printf "Total balance: "
echo "$p"
