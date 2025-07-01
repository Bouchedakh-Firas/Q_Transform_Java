package com.example.app.controllers;

import com.example.app.models.XmlTestData;
import com.example.app.services.XmlProcessingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.JAXBException;

/**
 * REST controller for handling XML processing API requests.
 * This demonstrates using JAXB, which was included in Java 8 but removed in Java 11+.
 */
@RestController
@RequestMapping("/api/xml")
public class XmlController {

    private static final Logger logger = LoggerFactory.getLogger(XmlController.class);
    private final XmlProcessingService xmlProcessingService;

    @Autowired
    public XmlController(XmlProcessingService xmlProcessingService) {
        this.xmlProcessingService = xmlProcessingService;
    }

    /**
     * API endpoint for generating a sample XML.
     * 
     * @return XML string
     */
    @GetMapping(value = "/sample", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> generateSampleXml() {
        logger.info("API request received for generating sample XML");
        
        try {
            // Create sample test data
            XmlTestData testData = xmlProcessingService.createSampleTestData();
            
            // Marshal to XML
            String xml = xmlProcessingService.marshalToXml(testData);
            
            return ResponseEntity.ok(xml);
        } catch (JAXBException e) {
            logger.error("Error generating sample XML", e);
            return ResponseEntity.internalServerError().body("<error>" + e.getMessage() + "</error>");
        }
    }

    /**
     * API endpoint for parsing XML.
     * 
     * @param xml XML string to parse
     * @return Parsed XmlTestData object
     */
    @PostMapping(value = "/parse", consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<XmlTestData> parseXml(@RequestBody String xml) {
        logger.info("API request received for parsing XML");
        
        try {
            // Unmarshal from XML
            XmlTestData testData = xmlProcessingService.unmarshalFromXml(xml);
            
            return ResponseEntity.ok(testData);
        } catch (JAXBException e) {
            logger.error("Error parsing XML", e);
            return ResponseEntity.badRequest().build();
        }
    }
}