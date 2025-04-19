#!/bin/bash
export JAVA_TOOL_OPTIONS="-Dfile.encoding=UTF-8"

# Set CLASSPATH with Windows-style semicolons (works in Git Bash & WSL)
export CLASSPATH="target/classes;evosuite-standalone-runtime-1.2.0.jar;evosuite-tests;target/dependency/junit-4.12.jar;target/dependency/hamcrest-core-1.3.jar"
# Base test directory (use forward slashes)
TEST_DIR="evosuite-tests"

# Compile all Java tests
find "$TEST_DIR" -name "*.java" > sources.txt
javac -Xlint:deprecation -cp "$CLASSPATH" @sources.txt
rm -f sources.txt

# Run each _ESTest class
for test_file in $(find "$TEST_DIR" -name "*_ESTest.java"); do
    # Convert path to fully qualified class name (handles both / and \)
    # fqcn=$(echo "$test_file" | sed "s|^$TEST_DIR/||; s|\.java$||; s|/|.|g; s|\\|.|g")
    fqcn=$(echo "$test_file" | sed -e "s|^$TEST_DIR/||" -e "s|\.java$||" | tr '/\\' '.' )

    
    echo "Running test: $fqcn"
    java -cp "$CLASSPATH" org.junit.runner.JUnitCore "$fqcn"
done