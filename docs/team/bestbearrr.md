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
* Designed the command to function and integrate seamlessly with other classes.
* Implemented comprehensive validation to handle empty fields, negative and non-numeric pay, and exceeding character limits.

#### FindCommand
* Implemented FindCommand, which filters internships by matching user keywords against both company and role fields.
* Designed case-insensitive substring matching for more intuitive search results.

#### ArgumentParser
* Designed and implemented parsing logic for both add and find commands.
  * Developed a structured parsing mechanism for the input formats.
  * Implemented extensive validation and error handling for missing prefixes, field order and data format.
  * Ensured trimming and exception handling for blank or invalid input.
    
#### User Interface
* Added methods to display messages and information from commands.
* Improved readability and formatting of success and error messages.

#### Internship
* Added a toString method to display the internship details to a structured text format

Wrote unit tests for AddCommand and FindCommand to ensure code correctness and reliability.

### Project Management
* Coordinated weekly development tasks and ensured alignment with milestone deliverables.
* Managed the GitHub project board, assigning issues and tracking progress across sprints.
* Reviewed and approved pull requests to ensure adherence to coding standards.
* Ensured consistent code quality through periodic refactoring and adherence to coding style guidelines.

### Documentation
* User Guide:
  * Added documentation for the `add` and `find` features and FAQs
  * Improved clarity to enhance user understanding
* Developer Guide
  * Added implementation details for the `add`, `find` and `help` features
  * Described sequence of operations, parsing logic, and design considerations
  * Added and documented new UML Diagrams to improve the technical clarity of the model layer and features:
    * Object Diagram: Model Component
    * Sequence Diagrams: AddCommand, FindCommand, HelpCommand
  * Updated instructions for manual testing

### Community

#### Contribution to Team
- PRs reviewed (with non-trivial review comments):
[#75](https://github.com/AY2526S1-CS2113-W14-4/tp/pull/75),
[#109](https://github.com/AY2526S1-CS2113-W14-4/tp/pull/109),
[#175](https://github.com/AY2526S1-CS2113-W14-4/tp/pull/175)
- Collaborated closely with team members to design and refine command features
- Helped maintain consistent coding standards across the team