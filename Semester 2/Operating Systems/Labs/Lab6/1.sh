
#!/bin/bash

echo "Hello World!"

for A in $@; do
	if [ -d $A ]; then 
		echo "$A is a directory"
	elif [ -f $A ]; then
		echo "$A is a file"
	else
		echo "$A is something"
	fi
done
