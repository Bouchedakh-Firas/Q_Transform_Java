#!/bin/bash

# Make the fix script executable
chmod +x /workspace/fix_script.sh

# Run the fix script
echo "Running fix script..."
/workspace/fix_script.sh

# Verify the file was fixed
echo "Checking if the file was fixed..."
tail -n 10 /workspace/src/main/java/com/example/app/services/PerformanceTestService.java

# Run Maven build to verify the fix
echo "Running Maven build..."
cd /workspace
mvn clean install