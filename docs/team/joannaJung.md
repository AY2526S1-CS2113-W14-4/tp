# Joanna Jung - Project Portfolio Page

## Overview
- Major: Computer Science
- School: Northwestern University

## Code Contributions (and Enhancements)
[RepoSense link](https://nus-cs2113-ay2526s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2025-09-19T00%3A00%3A00&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=Joannaj00&tabRepo=AY2526S1-CS2113-W14-4%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

### `UpdateCommand`
- Implemented the `UpdateCommand` class, which allows users to modify one or more fields of an existing internship entry without re-entering the entire record.  
- Supported updates to any combination of fields: `company/`, `role/`, `deadline/`, `pay/`, and `status/`.  
- Integrated validation through the `ArgumentParser` to handle parsing, 1-based to 0-based index conversion, and input format checking.  
- Ensured error handling through `InternityException` for invalid formats, indices, or missing fields.  
- Added static calls to `InternshipList.updateX(...)` for each non-null field
- Added **unit tests** to verify that the command correctly updates internships, throws appropriate exceptions for invalid input, and integrates properly with `ArgumentParser` and `InternshipList`.

### `ListCommand`
- Refactored the existing `ListCommand` and `InternshipList` interaction to resolve the issue where the internship list could not revert to its original order after sorting.
- Modified the `InternshipList.sortInternships()` method to create a separate `ArrayList` copy instead of sorting the internal list directly, ensuring sorting is temporary and non-persistent.
- Updated `listAll()` to iterate through this temporary view when the user specifies list sort/asc or list sort/desc, leaving the original list unchanged.
- Verified that the default `list` command still displays internships in the order they were added.
- Added unit tests confirming that sorting only affects the current view, and that subsequent `list` commands correctly revert to the original unsorted order.

### Documentation
- **Developer Guide:**
  - Worked on the section for the `UpdateCommand`, covering:
    - Command functionality, step-by-step execution flow, and field validation logic  
    - Example command formats and detailed error-handling explanations  
    - UML diagrams:
      - `Update Command Sequence Diagram`
      - `Update Feature Class Diagram`
  - Conducted **manual testing** to confirm that command behavior matched the described documentation scenarios.  
  - Updated the section for the `ListCommand` to match the updated implementation for sorting.

### Community
Reviewed PRs
- https://github.com/AY2526S1-CS2113-W14-4/tp/pull/9
- https://github.com/AY2526S1-CS2113-W14-4/tp/pull/75
- https://github.com/AY2526S1-CS2113-W14-4/tp/pull/118
- https://github.com/AY2526S1-CS2113-W14-4/tp/pull/119 
