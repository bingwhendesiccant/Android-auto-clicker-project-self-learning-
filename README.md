# AutoClickerDemo

A self-learning Android project for building a customizable auto clicker tool.

## Project Goal

This project is developed as a personal learning project.  
The main goal is to understand how an Android application can manage click-point data, user settings, local storage, and later extend to actual automated tap execution.

## Current Progress

The project has currently completed the following parts:

- Android project initialization
- Basic UI setup
- `ClickPoint` data structure design
- Ratio-based coordinate system
- Click point list management
- Add-point feature with a maximum limit of 10 points
- Display all click point data on screen
- Remove last click point
- Remove click point by ID with ID reordering
- Select click point with previous / next controls
- Edit selected click point values
- Save and load click point settings using SharedPreferences and JSON

## Current Features

- Display basic app interface
- Add click points one by one
- Limit the total number of click points to 10
- Display all click point data on screen
- Store coordinates as ratios instead of fixed pixels
- Convert ratio coordinates to screen pixels for display
- Select a click point using previous / next buttons
- Edit selected click point values
- Remove the last click point
- Remove a click point by ID
- Reorder IDs after deletion
- Save and load click point settings locally

## Data Structure

Each click point currently contains:

- `id`
- `xRatio`
- `yRatio`
- `delay`
- `duration`

The coordinate fields use ratios instead of fixed pixel values.  
This design helps the click points adapt more easily to different screen sizes and resolutions.

## Storage Design

The current version stores click point settings locally using:

- `SharedPreferences`
- JSON string serialization

The app currently supports saving and loading one set of click point settings.

## Development Notes

This project is being developed step by step instead of being completed all at once.  
The current stage focuses on building reliable click-point management and local storage before implementing accessibility service, automated tap execution, overlay controls, and advanced UI.

## Future Plans

Planned next steps include:

- Implement AccessibilityService structure
- Add accessibility permission guidance
- Check whether the accessibility service is enabled
- Implement basic single-tap execution
- Execute saved click points in sequence
- Add overlay control panel in a later stage
- Add draggable point markers in a later stage
- Support more advanced actions such as swipe gestures

## Author

Developed by bingwhendescicant