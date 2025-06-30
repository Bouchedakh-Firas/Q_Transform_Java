# UI Upgrade Implementation

This document describes the UI upgrade implementation for the Acloud Quarter Web Application.

## Changes Made

1. **New Color Scheme**
   - Red Poppy: #f8485e (R 248, G 72, B 94)
   - Dark Grey: #3c3c3a (R 60, G 60, B 58)
   - White: #ffffff (R 255, G 255, B 255)

2. **CSS Refactoring**
   - Created a centralized CSS file at `/src/main/resources/static/css/styles.css`
   - Implemented the new color scheme using CSS variables
   - Removed inline styles from HTML templates

3. **Button Image Implementation**
   - Created a directory for button images at `/src/main/resources/static/images/buttons/`
   - Added placeholder code for image buttons with fallback to text buttons
   - Updated all HTML templates to use image buttons

4. **Template Updates**
   - Updated all HTML templates to use the new CSS file
   - Implemented image buttons across all pages
   - Added fallback styling for when images are not available

## Required Button Images

The following button images need to be created and placed in the `/src/main/resources/static/images/buttons/` directory:

### Home Page Buttons
- welcome_button.png - For retrieving welcome message
- joke_button.png - For accessing joke generator
- performance_button.png - For accessing performance tests
- restaurant_button.png - For accessing restaurant finder
- signature_button.png - For accessing signature generator
- java_info_button.png - For displaying Java version info

### Joke Page Buttons
- joke_button.png - For generating random jokes

### Signature Page Buttons
- signature_button.png - For generating email signatures

### Restaurant Page Buttons
- restaurant_button.png - For finding restaurants
- api_test_button.png - For testing the API

### Performance Page Buttons
- system_info_button.png - For displaying system information
- cpu_test_button.png - For running CPU tests
- memory_test_button.png - For running memory tests
- concurrency_test_button.png - For running concurrency tests
- all_tests_button.png - For running all tests
- home_button.png - For returning to home page

## How to Add Button Images

1. Create PNG images for each button using the specified color scheme
2. Name the images according to the list above
3. Place the images in the `/src/main/resources/static/images/buttons/` directory
4. The application will automatically use the images for the buttons

## Fallback Mechanism

If button images are not available, the application will fall back to text buttons with the Red Poppy background color. This ensures the application remains functional even if the images are not yet available.

## Testing

To test the UI changes:
1. Build and run the application
2. Navigate to each page to verify the new color scheme is applied
3. Check that buttons display correctly (either as images or with the fallback styling)
4. Verify that all functionality continues to work as expected

## Future Improvements

- Add hover effects for button images
- Implement responsive design for better mobile experience
- Add animations for button interactions