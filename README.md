# AutoClickerDemo

A self-learning Android project for building a customizable auto clicker tool.

## Project Goal

This project is developed as a personal learning project.  
The main goal is to understand how an Android application can manage click-point data, store user-defined settings locally, request accessibility permission, and execute automated tap gestures through Android's AccessibilityService.

This project focuses on step-by-step development, from data management to actual automated tap execution.

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
- AccessibilityService setup
- Accessibility permission shortcut
- Accessibility permission status check
- Single tap execution test
- Sequential click point execution
- Repeat count control for click sequences
- Stop control during sequence execution
- Basic prevention of repeated sequence execution

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
- Open Android accessibility settings from the app
- Check whether the accessibility service is enabled
- Execute a single tap at the selected click point
- Execute all click points sequentially
- Set repeat count for the whole click sequence
- Stop an active click sequence

## Data Structure

Each click point currently contains:

- `id`
- `xRatio`
- `yRatio`
- `delay`
- `duration`

The coordinate fields use ratios instead of fixed pixel values.  
This design helps the click points adapt more easily to different screen sizes and resolutions.

During execution, the ratio-based coordinates are converted into actual screen pixel coordinates according to the current display size.

## Storage Design

The current version stores click point settings locally using:

- `SharedPreferences`
- JSON string serialization

The app currently supports saving and loading one set of click point settings.

## Execution Design

The current version uses Android AccessibilityService to execute tap gestures.

The execution flow is:

1. User creates or loads click points.
2. The app checks whether accessibility permission is enabled.
3. Ratio-based coordinates are converted into screen pixel coordinates.
4. The AccessibilityService dispatches tap gestures.
5. The app can execute all click points in sequence.
6. The user can set the repeat count for the whole sequence.
7. The user can stop the sequence while it is running.

This version focuses on a simple and stable MVP instead of advanced overlay controls.

## Development Notes

This project is being developed step by step instead of being completed all at once.

The first stage focused on click-point data management, including adding, editing, deleting, displaying, and saving click points.

The second stage focused on Android system integration, including AccessibilityService setup, permission checking, single tap execution, and sequential click execution.

The current version is already able to demonstrate the core workflow of a basic auto clicker:

```text
Create click points
→ Save / load settings
→ Enable accessibility permission
→ Execute tap gestures
→ Run the click sequence with repeat and stop controls

Overlay controls and draggable point markers are intentionally postponed to later stages because they require additional WindowManager and overlay permission handling.
```
## Future Plans

Planned next steps include:

1. Improve UI layout and usability
2. Add a clear-all click points feature
3. Add better input validation and user feedback
4. Support multiple saved scripts
5. Add overlay control panel in a later stage
6. Add draggable point markers in a later stage
7. Support swipe gestures
8. Improve handling of screen rotation and different display environments

Author
Developed by bingwhendescicant
