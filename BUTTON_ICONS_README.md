# Button Icons Implementation

This document describes the implementation of button icons for the Acloud Quarter Web Application.

## Changes Made

1. **Web Icons Implementation**
   - Replaced local PNG images with Material Icons from Google Fonts
   - Added Material Icons library to HTML templates
   - Created styled button containers with icons and text

2. **CSS Updates**
   - Added styles for web icons in `styles.css`
   - Implemented hover effects for the icon buttons
   - Ensured consistent styling across all buttons

3. **HTML Template Updates**
   - Updated `performance.html` with icon buttons for:
     - Test CPU (memory icon)
     - Test Mémoire (storage icon)
     - Test Concurrence (swap_horiz icon)
     - Exécuter tous les tests (playlist_add_check icon)
     - Retour à l'accueil (home icon)
     - Afficher les informations système (info icon)
   
   - Updated `index.html` with icon buttons for:
     - Récupérer le message de bienvenue (message icon)
     - Afficher la version Java (code icon)
     - Générateur de blagues (sentiment_very_satisfied icon)
     - Tests de performance (speed icon)
     - Trouveur de restaurant (restaurant icon)
     - Générateur de signature (email icon)

## Material Icons Used

| Button Text | Icon Name | Description |
|-------------|-----------|-------------|
| Test CPU | memory | CPU/processor icon |
| Test Mémoire | storage | Storage/memory icon |
| Test Concurrence | swap_horiz | Horizontal swap/parallel processing icon |
| Exécuter tous les tests | playlist_add_check | Checklist icon |
| Retour à l'accueil | home | Home icon |
| Afficher les informations système | info | Information icon |
| Récupérer le message de bienvenue | message | Message/chat icon |
| Afficher la version Java | code | Code/programming icon |
| Générateur de blagues | sentiment_very_satisfied | Smiley face icon |
| Tests de performance | speed | Speedometer/performance icon |
| Trouveur de restaurant | restaurant | Restaurant/dining icon |
| Générateur de signature | email | Email icon |

## Styling Details

- **Button Background Color**: Red Poppy (#f8485e)
- **Icon Color**: White (#ffffff)
- **Text Color**: White (#ffffff)
- **Hover Effect**: 
  - Button lifts up slightly (translateY(-5px))
  - Background color changes to Dark Grey (#3c3c3a)
- **Shadow Effect**: 
  - Box shadow for depth (0 4px 8px rgba(0,0,0,0.2))

## Future Improvements

- Add animation effects for button interactions
- Implement responsive sizing for different screen sizes
- Add accessibility features (ARIA attributes)
- Consider adding tooltips for additional information