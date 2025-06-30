    /**
     * Multiply two matrices of the specified size and return the sum of all elements in the result.
     * 
     * @param size Size of the matrices (size x size)
     * @return Sum of all elements in the resulting matrix
     */
    private long multiplyMatrices(int size) {
        // Create two random matrices
        int[][] matrix1 = new int[size][size];
        int[][] matrix2 = new int[size][size];
        int[][] result = new int[size][size];
        
        Random random = new Random();
        
        // Fill matrices with random values
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix1[i][j] = random.nextInt(10);
                matrix2[i][j] = random.nextInt(10);
            }
        }
        
        // Multiply matrices
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }
        
        // Calculate sum of all elements in the result
        long sum = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sum += result[i][j];
            }
        }
        
        return sum;
    }
    
    /**
     * Perform various string operations and return the total length of all strings created.
     * 
     * @param iterations Number of iterations for string operations
     * @return Total length of all strings created
     */
    private int performStringOperations(int iterations) {
        int totalLength = 0;
        
        // String concatenation
        String result = "";
        for (int i = 0; i < iterations / 10; i++) {
            result += "a";
            if (i % 100 == 0) {
                totalLength += result.length();
                result = "";
            }
        }
        
        // StringBuilder operations
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < iterations; i++) {
            sb.append("b");
            if (i % 1000 == 0) {
                totalLength += sb.length();
                sb.setLength(0);
            }
        }
        
        // String formatting
        for (int i = 0; i < iterations / 100; i++) {
            String formatted = String.format("Number: %d, Hex: %08X", i, i);
            totalLength += formatted.length();
        }
        
        return totalLength;
    }
}