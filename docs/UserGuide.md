# Internity User Guide

## Introduction

Internity is a CLI based app for managing internship applications. Internity can help you manage and keep track 
of your internship applications more efficiently, perfect for Computer Science students who need to 
manage hundreds of applications. This guide explains how to install and use Internity to the fullest.

# Table of Contents
- [Quick Start](#quick-start)
- [Features](#features)
  - [Adding an application : `add`](#adding-an-application-add)
  - [Deleting an application : `delete`](#deleting-an-application-delete)
  - [Updating an application : `update`](#updating-an-application-update)
  - [Viewing all applications: `list`](#listing-all-applications-list)
  - [Finding by keyword : `find`](#finding-by-keyword)
  - [Setting/Changing username : `username`](#settingchanging-username-username)
  - [Displaying dashboard : `dashboard`](#displaying-dashboard-dashboard)
  - [Help : `help`](#help)
  - [Exiting the program : `exit`](#exit-internity-exit)
- [FAQ](#faq)
- [Command Summary](#command-summary)

---

## Quick Start

1. **Install Java 17.** Confirm you have Java 17 installed by running `java -version` in your terminal.  
   If you do not have Java 17 installed, download it from [here](https://www.oracle.com/java/technologies/downloads/#java17).
2. **Download Internity.** Download the latest version of `Internity.jar` from [Github](https://github.com/AY2526S1-CS2113-W14-4/tp/releases), onto anywhere on your computer.
3. **Launch the terminal** Open a terminal, cd into the folder where you saved `Internity.jar`.
4. **Run the program.** Run the command:  
   ```java -jar Internity.jar```
5. **Start using Internity!** You can now start adding, deleting, updating and viewing your internship applications.  
   You may refer to the Features below for details of each command.

Tip: Type `help` to view a list of available commands at any time.

---

## Features

<div style="background-color: #002b5c; color: #cce6ff; padding: 15px; border-radius: 8px; border-left: 5px solid #3399ff;">
<h4>Notes about the command format</h4>
<ul>
<li>Words in <strong>UPPER_CASE</strong> are placeholders that must be supplied by you.<br>
Example: <code>delete INDEX</code> → Please input <code>delete 1</code></li>
<li>If using a PDF version, be careful when copying commands that span multiple lines as spaces surrounding line-breaks may be omitted.</li>
</ul>
</div>

### Adding an application: `add`

Add a new internship application with company, role, deadline and pay amount

Format

```
add company/COMPANY_NAME role/ROLE_NAME deadline/DEADLINE pay/PAY_AMOUNT
```

Example:

```
add company/Google role/Software Engineer Intern deadline/17-09-2025 pay/100000
```

This command adds an internship application at Google for the role of Software Engineer Intern with a deadline of 17
September 2025, and an annual salary of $100000.

<div style="background-color: #331c16; color: #c3b091; padding: 15px; border-radius: 8px; border-left: 5px solid #966919;">
<h4>Notes</h4>
<ul>
<li>The parameters should be entered in the specified order i.e. company, role, deadline, followed by pay.</li>
<li>No duplicate parameter type, so only exactly one of each parameter type.</li>
<li>By default, when an internship is added, the status is set to Pending. Use the update command to change the status.</li>
<li>Field character limits: <code>COMPANY</code> ≤ 15 characters, <code>ROLE</code> ≤ 30 characters.</li>
<li><code>DEADLINE</code> must be in <code>dd-MM-yyyy</code> format.</li>
<li><code>PAY_AMOUNT</code> must be a non-negative integer that fits within Java's 32-bit signed integer range (maximum 2,147,483,647).</li>
</ul>
</div>

---

### Deleting an application: `delete`

Delete an existing internship application from the internship list

Format:

```
delete INDEX
```

Example:

```
delete 2
```

This command deletes the internship application at index 2 from the list.

---

### Updating an application: `update`

Use this command to update any field (company, role, deadline, pay, status) of an internship application.  


<div style="background-color: #331c16; color: #c3b091; padding: 15px; border-radius: 8px; border-left: 5px solid #966919;">
<h4><strong>Valid <code>STATUS</code> values</strong></h4>
<ul>
<li><code>Pending</code> - You’re considering the internship but haven’t applied yet.</li>
<li><code>Interested</code> - You’ve found the internship and might apply.</li>
<li><code>Applied</code> - You’ve submitted your application.</li>
<li><code>Interviewing</code> - You’re currently in the interview process.</li>
<li><code>Offer</code> - You’ve received an internship offer.</li>
<li><code>Accepted</code> - You’ve accepted the offer.</li>
<li><code>Rejected</code> - The application was unsuccessful or withdrawn.</li>
</ul>
</div>

Format:

```
update INDEX field1/VALUE1 field2/VALUE2 ...
```

Example:

```
update 1 status/Accepted
update 2 company/Microsoft status/Interviewing
```

The 1st command updates the status of the internship application at index 1 to "Accepted".

The 2nd command updates the company of the internship application at index 2 to "Microsoft" and its status to "Interviewing".

<div style="background-color: #331c16; color: #c3b091; padding: 15px; border-radius: 8px; border-left: 5px solid #966919;">
<h4>Notes</h4>
<ul>
<li>For v1.0, only the <code>status</code> field can be updated. Updating other fields (company, role, deadline, pay) will be added in
future iterations.</li>
<li>All field values must adhere to the constraints specified in the <code>add</code> feature.</li>
</ul>
</div>

---

### Listing all applications: `list`

Use this command to view all internship applications.  
By default, the list shows applications in the **order they were added**.
You can optionally sort them by deadline in ascending or descending order.

Format:

```
list
list sort/ORDER
```

Example:

```
list
list sort/asc
list sort/desc
```

- `list` → shows all applications in the order they were added
- `list sort/asc` → sorts applications by deadline ascending
- `list sort/desc` → sorts applications by deadline descending

---

### Finding by keyword: `find`

Search for internship applications by keyword. The search is case-insensitive.

Format:

```
find KEYWORD
```

Example:

```
find Software Engineer
```

This command lists all internship applications that contain the keyword "Software Engineer" in either its company or
role details.

---

### Setting/Changing username: `username`

Use this command to set or change the username for the Internity application.

Format:

```
username USERNAME
```

Example:

```
username Yoshikage Kira
```

---

### Displaying dashboard: `dashboard`

Use this command to display a dashboard showing the current user's information, total internships, nearest deadline,
and a breakdown of internships by status.

Format:

```
dashboard
```

---

### Help: `help`

Use this command to display a help message with a list of available commands.

Format:

```
help
```

### Exit Internity: `exit`

Use this command to quit the program. Your data is saved.

Format:

```
exit
```

---

## FAQ

* **Q: How do I transfer my data to another computer?**
<br> A: Make a copy of the `data/` folder of the home directory that runs `Internity.jar` to the new computer. Ensure that
  the folder structure remains intact.


* **Q: I have encountered an invalid command error. What do I do?** 
  <br> A: Refer to the [Features](#features) section for details on using a command.


* **Q: Does Internity work without Internet?**
  <br> A: Yes, Internity does not require an active internet connection, allowing you to manage your internships
  seamlessly!


* **Q: My application unexpectedly closed. Will my previous entries be saved?**
  <br> A: Yes, Internity automatically saves your data after each command, ensuring that your entries are preserved even in the event of an unexpected closure.


* **Q: How can I reset my data to start fresh?**
  <br> A: Close the app and delete or rename the `data/internships.txt` file. On next start, the app will create a new empty data file. Be careful: this deletes all saved internships.


---

## Command Summary

| **Action**              | **Command** | **Format**                                                                                                                                                  | **Example**                                                                       |
|-------------------------|-------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------|
| **Add Application**     | `add`       | `add company/COMPANY_NAME role/ROLE_NAME deadline/DEADLINE pay/PAY_AMOUNT`                                                                                  | `add company/Google role/Software Engineer Intern deadline/17-09-2025 pay/100000` |
| **Delete Application**  | `delete`    | `delete INDEX`                                                                                                                                              | `delete 2`                                                                        |
| **Update Application**  | `update`    | `update INDEX FIELD/VALUE`                                                                                                                                  | `update 1 status/Interviewing`                                                    |
| **List Applications**   | `list`      | `list` → list all applications in the order they were added <br> `list sort/ORDER` → sort applications by deadline ascending (`asc`) or descending (`desc`) | `list` <br> `list sort/asc` <br> `list sort/desc`                                 |
| **Find Application**    | `find`      | `find KEYWORD`                                                                                                                                              | `find Software Engineer`                                                          |
| **Set/Change username** | `username`  | `username USERNAME`                                                                                                                                         | `username Yoshikage Kira`                                                         |
| **Display Dashboard**   | `dashboard` | `dashboard`                                                                                                                                                 | `dashboard`                                                                       |
| **Help**                | `help`      | `help`                                                                                                                                                      | `help`                                                                            |
| **Exit Internity**      | `exit`      | `exit`                                                                                                                                                      | `exit`                                                                            |


