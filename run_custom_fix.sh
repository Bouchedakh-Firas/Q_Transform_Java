#!/bin/bash

# Make the custom fix script executable
chmod +x /workspace/custom_fix.sh

# Run the custom fix script
echo "Running custom fix script..."
/workspace/custom_fix.sh

# Verify the file was fixed
echo "Checking if the file was fixed..."
tail -n 10 /workspace/src/main/java/com/example/app/services/PerformanceTestService.java

# Run Maven build to verify the fix
echo "Running Maven build..."
cd /workspace
mvn clean install