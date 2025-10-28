# Luke Aidan Tan - Project Portfolio Page

## Overview
Internity is a Command-Line Interface (CLI) application designed to help users manage their internship applications efficiently. 
It is especially useful for Computer Science students who often apply to hundreds of internships and need a simple yet powerful 
way to organize their applications.
The app allows users to add, update, delete, find, and list internships, each with detailed 
attributes such as company name, role, application deadline, pay, and status. In addition, Internity provides features 
like a dashboard overview for quick insights and data persistence between sessions.

### Code Contributions (and Enhancements)
[RepoSense link](https://nus-cs2113-ay2526s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2025-09-19T00%3A00%3A00&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=lukeai-tan&tabRepo=AY2526S1-CS2113-W14-4%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)
#### InternityManager (+ enhancement)
- Developed the main controller class that coordinates between the UI, command parser, and the internship data.
- Handles execution of commands and manages the interaction with the storage and UI layers.
- Extracted the main logic from the original `Internity` class and refactored it into a dedicated `InternityManager` class.
- This enhancement improves code organization by separating application orchestration from the entry point, making
the system more modular and maintainable.

#### Command Parser (`CommandParser`)
- Implemented the logic to parse raw user input into executable commands, handling input validation,
  splitting into command words and arguments, and coordinating with `CommandFactory`.

#### Command Factory (`CommandFactory`) (+ enhancement)
- Developed the factory class that creates the appropriate command objects based on parsed input,
  delegating argument parsing to `ArgumentParser`.
- Abstracted the command creation logic from `CommandParser` into a dedicated `CommandFactory` class.
- This enhancement centralizes the instantiation of commands based on user input, improving separation of concerns and
maintainability.
- Simplifies `CommandParser` by delegating command creation, making it easier to add new commands in the future.

#### Argument Parser (`ArgumentParser`) 
- Designed and implemented `ArgumentParser` to centralize command argument parsing.
- Provides a structured way for commands like `add`, `delete`, `update`, `list` to parse their input arguments.
- Allows future developers to easily add new parsing methods for additional commands, promoting modularity and
code reuse.

#### Argument Parsing Support (`DateFormatter` and `Date`)
- Created `DateFormatter` for parsing user-provided dates and `Date` as a custom comparable date object
  used across internships.

#### Exit, Username, Dashboard Command (`ExitCommand`, `UsernameCommand`, `DashboardCommand`)
- Implemented the `exit` command for users to exit the program.
- Implemented the `username` command to set or display the current username.
- Implemented the `dashboard` command to display a dashboard overview of internships, showing nearest deadlines and status
  breakdowns.

#### Dashboard UI (`DashboardUi`)
- Built the UI layer for the dashboard, including methods to print the total number of internships,
  nearest deadlines and status overview.

### Project management
- Managed the implementation schedule to meet project deadlines.
- Managed releases `v1.0`-`v2.1` on GitHub.

### Enhancements to existing features
- Wrote additional tests for existing features to increase branch coverage from 55% to 72% and line coverage from
70% to 83% (Pull Request [#95](https://github.com/AY2526S1-CS2113-W14-4/tp/pull/95))

### Documentation
- User Guide:
    - Added documentation for the features `exit`, `username`, `dashboard`
- Developer Guide:
    - Added design details of the architecture, `Logic` and `UI` component.
    - Added UML diagrams for:
      - Architecture Diagram
      - Internity Class Diagram
      - User Interaction Sequence Diagram
      - UI Component Diagram
      - Logic Component Class Diagram
      - Logic Component Sequence Diagram
    - Added details of product scope, user stories and non-functional requirements.

### Community
- PRs reviewed (with non-trivial review comments):
[#38](https://github.com/AY2526S1-CS2113-W14-4/tp/pull/38),
[#59](https://github.com/AY2526S1-CS2113-W14-4/tp/pull/59),
[#62](https://github.com/AY2526S1-CS2113-W14-4/tp/pull/62),
[#65](https://github.com/AY2526S1-CS2113-W14-4/tp/pull/65),
[#78](https://github.com/AY2526S1-CS2113-W14-4/tp/pull/78),
[#79](https://github.com/AY2526S1-CS2113-W14-4/tp/pull/79)
