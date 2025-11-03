# Benjamin Chek - Project Portfolio Page

## Overview
Internity is a Command-Line Interface (CLI) application designed to help users manage their internship applications efficiently.
It is especially useful for Computer Science students who often apply to hundreds of internships and need a simple yet powerful
way to organize their applications.
Internity allows users to add, update, delete, find, and list internships, each with detailed
attributes such as company name, role, application deadline, pay, and status.
Users can also add a username to personalize their app.
In addition, Internity provides features
like a dashboard overview for quick insights and data persistence between sessions.

### Code Contributions and Enhancements
[RepoSense link](https://nus-cs2113-ay2526s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2025-09-19T00%3A00%3A00&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=BenyAlbatross&tabRepo=AY2526S1-CS2113-W14-4%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

#### Storage System (`Storage`)
- Designed and implemented the complete persistence layer for Internity, enabling data to be saved and loaded between sessions.
- Developed robust file I/O operations using BufferedReader and PrintWriter for reading and writing internship data.
- Implemented comprehensive format validation logic to protect against invalid user edits.
- Added graceful error handling that skips corrupted lines with detailed warning messages, ensuring the application
  remains stable even with malformed data files.
- (Enhancement) Enhanced storage safety by implementing saving to a temporary file before atomically overwriting the original data file, minimizing the risk of data loss during write operations.

#### Delete Command (`DeleteCommand`)
- Implemented the `delete` command that allows users to remove internship entries by index.
- Added comprehensive logging to aid in debugging.
- Implemented assertions to ensure data integrity (e.g., size cannot be negative after deletion).

### Project management
- Maintained the issue tracker by regularly reviewing and informing teammates of relevant issues.
- Actively participated in team meetings and sprint planning.
- Helped coordinate implementation timelines for storage-related features.

### Documentation
- User Guide:
  - Added documentation on the `delete` feature.
  - Added FAQs on data persistence and file corruption handling.
  - Contributed to documentation clarity and formatting.
- Developer Guide:
  - Created and maintained UML diagrams for:
    - Storage Class Diagram
    - Storage Load Sequence Diagram
    - Storage Save Sequence Diagram
  - Documented storage file format and validation rules.
  - Added implementation details for data persistence.

### Community
- PRs reviewed (with non-trivial review comments):
  [#82](https://github.com/AY2526S1-CS2113-W14-4/tp/pull/82), [#86](https://github.com/AY2526S1-CS2113-W14-4/tp/pull/86), [#198](https://github.com/AY2526S1-CS2113-W14-4/tp/pull/198). [See other PRs reviewed here](https://github.com/AY2526S1-CS2113-W14-4/tp/pulls?q=is%3Apr+reviewed-by%3ABenyAlbatross+is%3Aclosed+).
- Helped maintain code quality standards.
- Added manual testing details for the `delete` feature.
- Submitted 15 bugs during the PE-D, ranging from documentation bugs to feature bugs.