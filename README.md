# AutoClickerDemo

A self-learning Android project for building a customizable auto clicker tool.

## Project Goal
This project is developed as a personal learning project.  
The main goal is to understand how an Android application can manage click-point data, user settings, and later extend to actual automated tap execution.

## Current Progress
The project has currently completed the following parts:

- Android project initialization
- Basic UI setup
- `ClickPoint` data structure design
- Click point list management
- Add-point feature with a maximum limit of 10 points

## Current Features
- Display basic app interface
- Add click points one by one
- Limit the total number of click points to 10
- Store click point data temporarily in memory during runtime

## Data Structure
Each click point currently contains:

- `id`
- `x`
- `y`
- `delay`
- `duration`

These fields are used to represent the basic information needed for future automated tap execution.

## Development Notes
This project is being developed step by step instead of being completed all at once.  
The current stage focuses on building the core data logic first, before implementing persistent storage, accessibility service, and advanced UI.

## Future Plans
Planned next steps include:

- Display all click point data on screen
- Edit and remove click points
- Save user-defined click point configurations
- Implement actual automated tap execution
- Support more advanced actions such as swipe gestures

## Author
Developed by bingwhendescicant