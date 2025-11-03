# Luo Hongxun - Project Portfolio Page

# Overview
Internity is a Command-Line Interface (CLI) application designed to help users manage their internship applications efficiently.
It is especially useful for Computer Science students who often apply to hundreds of internships and need a simple yet powerful
way to organize their applications.
The app allows users to add, update, delete, find, and list internships, each with detailed
attributes such as company name, role, application deadline, pay, and status. In addition, Internity provides features
like a dashboard overview for quick insights and data persistence between sessions.

## Summary of Contributions

### Code Contributions and Enhancements
[RepoSense link](https://nus-cs2113-ay2526s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2025-09-19T00%3A00%3A00&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=BestBearrr&tabRepo=AY2526S1-CS2113-W14-4%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

#### AddCommand
* Implemented the AddCommand feature, enabling users to record new internship entries with details such as company, role, deadline, and pay.
* Designed the command to integrate seamlessly with the ArgumentParser, ensuring robust validation and structured parsing.
* Added logic in AddCommand.execute() to instantiate a new Internship object and update the global static InternshipList.
* Enhanced usability by integrating Ui.printAddInternship() for consistent success feedback after addition.
* Implemented comprehensive validation for empty fields, negative pay, and exceeded character limits.

#### FindCommand
* Implemented FindCommand, which filters internships by matching user keywords against both company and role fields.
* Designed case-insensitive substring matching for more intuitive search results.
* Integrated with the ArgumentParser to handle and validate keyword input.
* Ensured that filtered results are displayed using the Ui layer in a consistent and readable format.
* Optimised command behavior to handle empty and invalid inputs gracefully using centralized exception handling.

#### ArgumentParser
* Designed and implemented parsing logic for both add and find commands.
* For AddCommand:
    * Developed a structured parsing mechanism that enforces strict input format (company/, role/, deadline/, pay/).
    * Implemented extensive validation and error handling for missing prefixes, field order, and data format.
* For FindCommand:
    * Implemented argument extraction logic to handle single or multi-word search terms.
    * Ensured trimming, normalization, and exception handling for blank or invalid input.
* Enhanced logging for debugging and traceability of command parsing.

#### User Interface
* Added methods to display messages and information from commands.  
* Enhanced Ui class to provide standardised feedback for add and find commands.
* Improved readability and formatting of success and error messages.
* Added logging and structured formatting for output consistency.

#### Internship
* Added a toString method to display the internship to text

Wrote unit tests for AddCommand and FindCommand to ensure code correctness and reliability.

### Project Management
* Coordinated weekly development tasks and ensured alignment with milestone deliverables.
* Managed the GitHub project board, assigning issues and tracking progress across sprints.
* Led feature integration and conflict resolution during merges to maintain a stable main branch.
* Reviewed and approved pull requests to ensure adherence to coding and documentation standards.
* Organised and documented team discussions during meetings, ensuring clear follow-ups and accountability.
* Ensured consistent code quality through periodic refactoring and adherence to the project’s coding style guidelines.

### Documentation
* User Guide:
  * Added documentation for the `add` and `find` features and FAQs
  * Improved clarity to enhance user understanding
* Developer Guide
  * Added implementation details for the `add`, `find` and `help` features
  * Described sequence of operations, logic for parsing arguments, as well as design considerations
  * Added and documented new UML Diagrams to improve the technical clarity of the model layer and features:
    * Model Component Object Diagram 
    * AddCommand Sequence Diagram
    * FindCommand Sequence Diagram
    * HelpCommand Sequence Diagram
  * Updated instructions for manual testing

### Community

#### Contribution to Team
- PRs reviewed (with non-trivial review comments):

[#38](https://github.com/AY2526S1-CS2113-W14-4/tp/pull/38),

 
- Collaborated closely with team members to design and refine command features, ensuring consistency across all implementations.
- Helped maintain consistent coding standards across the team by suggesting improvements to naming conventions and code structure.
- Contributed to sprint planning discussions, helping prioritise tasks and define achievable milestones.
