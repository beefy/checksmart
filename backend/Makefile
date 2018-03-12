# by convention the first makefile target
# compile, run the problems, then clean up
all: compile run

# compile all of the files
# and execute them
run:
	java -cp ./build/classes main/Server

# compile all of the files
# and give them execute permissions
compile:
	javac -d ./build/classes ./src/main/*.java

clean:
	rm ./build/classes/main/*
