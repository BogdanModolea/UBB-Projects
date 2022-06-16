
#!/bin/bash

echo "Hello World!"

for A in $@; do
	if [ -d $A ]; then 
		echo "$A is a directory"
	elif [ -f $A ]; then
		echo "$A is a file"
	elif echo $A | grep -E -q "^[0-9]+$"; then
		echo "$A is a number"
	else
		echo "$A is something"
	fi
done
