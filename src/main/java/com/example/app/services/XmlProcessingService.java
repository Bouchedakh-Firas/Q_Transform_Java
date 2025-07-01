package com.example.app.services;

import com.example.app.models.PerformanceTestResult;
import com.example.app.models.XmlTestData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * Service for XML processing using JAXB.
 * JAXB was included in Java 8 but removed in Java 11+.
 */
@Service
public class XmlProcessingService {
    
    private static final Logger logger = LoggerFactory.getLogger(XmlProcessingService.class);
    private static final int TEST_ITERATIONS = 1000;
    private static final int LARGE_TEST_DATA_ITEMS = 100;
    
    /**
     * Marshal a Java object to XML string.
     * 
     * @param testData The XmlTestData object to marshal
     * @return XML string representation of the object
     * @throws JAXBException If an error occurs during marshalling
     */
    public String marshalToXml(XmlTestData testData) throws JAXBException {
        logger.info("Marshalling XmlTestData to XML");
        
        JAXBContext context = JAXBContext.newInstance(XmlTestData.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        
        StringWriter writer = new StringWriter();
        marshaller.marshal(testData, writer);
        
        return writer.toString();
    }
    
    /**
     * Unmarshal XML string to a Java object.
     * 
     * @param xml The XML string to unmarshal
     * @return XmlTestData object
     * @throws JAXBException If an error occurs during unmarshalling
     */
    public XmlTestData unmarshalFromXml(String xml) throws JAXBException {
        logger.info("Unmarshalling XML to XmlTestData");
        
        JAXBContext context = JAXBContext.newInstance(XmlTestData.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        
        StringReader reader = new StringReader(xml);
        return (XmlTestData) unmarshaller.unmarshal(reader);
    }
    
    /**
     * Create a sample XmlTestData object for testing.
     * 
     * @return Sample XmlTestData object
     */
    public XmlTestData createSampleTestData() {
        String id = UUID.randomUUID().toString();
        String name = "Test Data " + id.substring(0, 8);
        String description = "Sample test data for XML processing";
        String creationDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date());
        
        XmlTestData testData = new XmlTestData(id, name, description, creationDate);
        
        // Add properties
        testData.addProperty("version", "1.0");
        testData.addProperty("author", "System");
        testData.addProperty("environment", "Test");
        
        // Add items
        testData.addItem("item-1", "First Item", 10);
        testData.addItem("item-2", "Second Item", 20);
        testData.addItem("item-3", "Third Item", 30);
        
        return testData;
    }
    
    /**
     * Create a large XmlTestData object for performance testing.
     * 
     * @return Large XmlTestData object
     */
    public XmlTestData createLargeTestData() {
        String id = UUID.randomUUID().toString();
        String name = "Large Test Data";
        String description = "Large test data for XML processing performance testing";
        String creationDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date());
        
        XmlTestData testData = new XmlTestData(id, name, description, creationDate);
        
        // Add many properties
        for (int i = 0; i < 20; i++) {
            testData.addProperty("property-" + i, "value-" + i);
        }
        
        // Add many items
        Random random = new Random();
        for (int i = 0; i < LARGE_TEST_DATA_ITEMS; i++) {
            testData.addItem(
                "item-" + i,
                "Item " + i + " Description",
                random.nextInt(100) + 1
            );
        }
        
        return testData;
    }
    
    /**
     * Run an XML processing performance test.
     * 
     * @return PerformanceTestResult with test metrics
     */
    public PerformanceTestResult runXmlTest() {
        logger.info("Starting XML processing performance test");
        
        // Get initial memory usage
        Runtime runtime = Runtime.getRuntime();
        long startMemory = runtime.totalMemory() - runtime.freeMemory();
        
        // Start timing
        long startTime = System.currentTimeMillis();
        
        try {
            // Create test data
            XmlTestData largeTestData = createLargeTestData();
            
            // Marshal and unmarshal multiple times
            int successfulOperations = 0;
            for (int i = 0; i < TEST_ITERATIONS; i++) {
                // Marshal to XML
                String xml = marshalToXml(largeTestData);
                
                // Unmarshal back to object
                XmlTestData unmarshalledData = unmarshalFromXml(xml);
                
                // Verify data integrity
                if (unmarshalledData.getId().equals(largeTestData.getId())) {
                    successfulOperations++;
                }
            }
            
            // End timing
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            
            // Get final memory usage
            long endMemory = runtime.totalMemory() - runtime.freeMemory();
            long memoryUsed = endMemory - startMemory;
            
            // Get CPU count
            int cpuCount = runtime.availableProcessors();
            
            // Create result
            PerformanceTestResult result = new PerformanceTestResult(
                "XML",
                executionTime,
                memoryUsed,
                cpuCount,
                1, // Single thread for XML test
                "Processed " + TEST_ITERATIONS + " XML operations with " + LARGE_TEST_DATA_ITEMS + 
                " items each, successful operations: " + successfulOperations
            );
            
            logger.info("XML test completed in {} ms", executionTime);
            return result;
            
        } catch (JAXBException e) {
            logger.error("Error during XML test", e);
            
            // End timing
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            
            // Create error result
            return new PerformanceTestResult(
                "XML",
                executionTime,
                0,
                runtime.availableProcessors(),
                1,
                "Error during test: " + e.getMessage()
            );
        }
    }
}