#!/bin/bash

# Make the fix_file.sh script executable
chmod +x /workspace/fix_file.sh

# Run the fix_file.sh script
echo "Running fix_file.sh script..."
/workspace/fix_file.sh

# Verify the file was fixed
echo "Checking if the file was fixed..."
tail -n 10 /workspace/src/main/java/com/example/app/services/PerformanceTestService.java

# Run Maven build to verify the fix
echo "Running Maven build..."
cd /workspace
mvn clean install