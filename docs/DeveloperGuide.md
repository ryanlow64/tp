---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# REconnect Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

We would like to humbly acknowledge the following for the success of our project:
* The SE-EDU team for creating and maintaining the AddressBook-Level3 project.
* Our course instructors and teaching assistants whose patient guidance and feedback were instrumental throughout the development journey.
* Our peers and collaborators for their support, constructive code reviews, and insightful discussions.
* The open-source Java and JavaFX communities for providing comprehensive documentation and development tools.
* The developers and maintainers of essential libraries and frameworks used in this project, including Jackson for JSON processing and JUnit for testing.

### **Java Dependencies**

* **JavaFX** - for Graphical User Interface (GUI) rendering
* **Jackson** - for JSON serialization and deserialization
* **JUnit 5** - for JUnit testing
* **Gradle Shadow Plugin** - for creating executable JAR files with dependencies
* **Checkstyle** - for enforcing coding standards

### **Documentation Tools**

* **MarkBind** - for authoring and building the project website
* **PlantUML** - for creating UML diagrams used in the Development Guide

### **Badges**

* **CodeCov** - for providing code coverage badge
* **GitHub Actions** - for providing the Java CI badge

<div style="page-break-before: always;"></div>

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

<div style="page-break-before: always;"></div>

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

<div style="page-break-before: always;"></div>

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2425S2-CS2103T-T12-3/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `ClientListPanel`, `PropertyListPanel` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays the `Client` object residing in the `Model`.

<div style="page-break-before: always;"></div>

### Logic component

**API** : [`Logic.java`](https://github.com/AY2425S2-CS2103T-T12-3/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete_client 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete_client 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteClientCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteClientCommandParser`) and uses it to parse the command.
2. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeletClienteCommand`) which is executed by the `LogicManager`.
3. The command can communicate with the `Model` when it is executed (e.g. to delete a client).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
4. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here is the class diagram in `Logic` (omitted from the class diagram above) that is used for parsing a user command:

<puml src="diagrams/ParserStructure.puml" width="600" height="500"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddClientCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddClientCommand`) which the `AddressBookParser` returns back as a `Command` object.
  * All `XYZCommandParser` classes (e.g., `AddClientCommandParser`, `DeleteClientCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/AY2425S2-CS2103T-T12-3/tp/blob/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="800" height="600"/>


The `Model` component,

* stores the data stored in REconnect i.e., all `Client` objects (which are contained in a `UniqueClientList` object).
* stores the currently 'selected' `Client` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Client>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user's preferences. This is exposed to the outside as a `ReadOnlyUserPref` object.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components).


### Storage component

**API** : [`Storage.java`](https://github.com/AY2425S2-CS2103T-T12-3/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

<div style="page-break-before: always;"></div>

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### **Feature 1: Autocompletion of command names and prefixes**

This is done in 2 parts mainly:
- The `CommandBox` class is responsible for listening to changes to the user input and displaying the autocomplete suggestions based on that.
- The `LogicManager` class is responsible for providing the list of possible commands and prefixes to the `CommandBox`.

The `CommandBox` class listens to the user input and checks if the input matches any of the commands or prefixes. If it does, it displays the suggestions in a dropdown list. 
The user can then select one of the suggestions to autocomplete their command.

It has access to the command word to prefix mappings in the main `Command` class. However, since it's troublesome to main a hardcoded list of command words in the `Command` class, 
each concrete subclass of `Command` is responsible for adding its own command word and prefixes. This is done by implementing the `public static void addCommandWord()` method in 
each subclass.

Notice that the method is declared as static and public. This is because it is the `LogicManager` class that calls this method for all concrete command classes. It uses the Reflection 
API to find all concrete command classes and calls the `addCommandWord()` method on each of them during runtime before the Ui is loaded.

This is the implementation for running the `addCommandWord()` method in the `LogicManager` class:

```java
private void initialiseCommandWords() {
    Reflections reflections = new Reflections("seedu.address.logic.commands");
    Set<Class<? extends Command>> commandClasses = reflections.getSubTypesOf(Command.class);
    for (Class<? extends Command> commandClass : commandClasses) {
        boolean isAbstract = Modifier.isAbstract(commandClass.getModifiers());
        if (!isAbstract) {
            try {
                Method addCommandWord = commandClass.getDeclaredMethod("addCommandWord");
                boolean isStatic = Modifier.isStatic(addCommandWord.getModifiers());
                boolean isPublic = Modifier.isPublic(addCommandWord.getModifiers());
                if (isStatic && isPublic) {
                    addCommandWord.invoke(null);
                } else {
                    throw new RuntimeException(commandClass.getSimpleName()
                        + " does not have a public static addCommandWord method");
                }
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(commandClass.getSimpleName()
                    + " does not have a public static addCommandWord method", e);
            } catch (Exception e) {
                if (e instanceof RuntimeException) {
                    throw (RuntimeException) e;
                }
                logger.warning("Error invoking addCommandWord in " + commandClass.getSimpleName() + ": " + e);
            }
        }
    }
}
```

As you can see, it's implemented in such a way that if any of the command classes do not implement the `addCommandWord()` method correctly, 
the app will throw a runtime exception and crash. This is to ensure that the command words are always in sync with the command classes.

### **Feature 2: Enabling the conjunction (AND) and disjunction (OR) of filtering parameters for find commands**

The find commands in the app allow users to search for clients, properties, deals and events using multiple parameters.
Instead of using either the conjunction or disjunction as the default for combining the parameters, we give the user the option to choose between the two.
That is to say, the user can choose to use either the conjunction or disjunction to combine the parameters but they must use 
the same type for all parameters except the first.

To enable this feature, we use a special `ConnectivePrefix` class to add this functionality. Connective prefixes are special prefixes 
that can be used to combine multiple parameters in a command. If a prefix can be connective (i.e. used in a find command), it can take 3 forms: 
its regular form (e.g. `after/`), its connective AND form (e.g. `AND_after/`) and its connective OR form (e.g. `OR_after/`).

The processing of the connective prefixes is done in the `abstract FindCommandParser` class.
Here is one such method that checks if the prefixes used in any of the find commands are valid:

```java
protected static void checkPrefixesUsedAreValid(List<Prefix> prefixesUsed) throws ParseException {
    List<Prefix> validPrefixes = prefixesUsed.stream()
        .filter(prefix -> !prefix.getPrefix().isEmpty())
        .toList();

    if (validPrefixes.isEmpty()) {
        throw new ParseException("No valid prefixes used");
    }

    Prefix firstPrefix = validPrefixes.get(0);
    if (firstPrefix.isConnective()) {
        throw new ParseException("First prefix used cannot be connective");
    }

    boolean allAnd = true;
    boolean allOr = true;
    for (int i = 1; i < validPrefixes.size(); i++) {
        Prefix currentPrefix = validPrefixes.get(i);
        if (!currentPrefix.isConnective()) {
            throw new ParseException("Subsequent prefixes used after the first must be connective");
        }
        if (currentPrefix.isAndPrefix()) {
            allOr = false;
        } else if (currentPrefix.isOrPrefix()) {
            allAnd = false;
        }
    }

    if (!allAnd && !allOr) {
        throw new ParseException("Cannot mix AND and OR connective prefixes");
    }
}
 ```

--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](https://ay2425s2-cs2103t-t12-3.github.io/tp/Documentation.html)
* [Testing guide](https://ay2425s2-cs2103t-t12-3.github.io/tp/Testing.html)
* [Logging guide](https://ay2425s2-cs2103t-t12-3.github.io/tp/Logging.html)
* [Configuration guide](https://ay2425s2-cs2103t-t12-3.github.io/tp/Configuration.html)
* [DevOps guide](https://ay2425s2-cs2103t-t12-3.github.io/tp/DevOps.html)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**: Real Estate agents who 
* need to connect clients (e.g. buyers, sellers, investors) with the best opportunities based on market demands and property locations.
* has a need to manage a significant number of contacts
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**:

Our product provides the user with easier real estate networking by having an organised contact log that can be managed faster than a typical mouse/GUI driven app. Its intuitive design organises industry contacts and scheduling. Less time would be spent on such administrative tasks, and more could be spent on closing deals and strengthening their network.

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                    | I want to …​                                                      | So that I can…​                                                               |
|----------|--------------------------------------------|-------------------------------------------------------------------|-------------------------------------------------------------------------------|
| `* * *`  | real estate agent                          | add property details                                              | offer more property listings to attract potential buyers                      |
| `* * *`  | real estate agent                          | remove property details                                           | keep my available listings relevant                                           |
| `* * `   | real estate agent                          | edit property details                                             | update incorrect or outdated information                                      |
| `* * *`  | real estate agent                          | store all the property details in one place                       | showcase the list of properties that I have to potential buyers               |
| `* * *`  | real estate agent                          | add client contact details                                        | easily follow up on leads and maintain relationships                          |
| `* * *`  | real estate agent                          | remove outdated client contact details                            | keep my records relevant and clutter-free                                     |
| `* * `   | real estate agent                          | edit client contact details                                       | ensure that phone numbers, emails and other details remain accurate           |
| `* * *`  | real estate agent                          | store all my client contacts in one place                         | easily access and manage them                                                 |
| `* * *`  | real estate agent                          | add scheduled meetings with clients                               | efficiently manage my daily appointments                                      |
| `* * *`  | real estate agent                          | delete scheduled meetings                                         | free up time for other appointments                                           |
| `* * `   | real estate agent                          | edit scheduled meetings                                           | adjust timing when clients request changes                                    |
| `* * *`  | real estate agent                          | be able to track my scheduled meetings                            | plan my time effectively                                                      |
| `* * `   | real estate agent                          | update the status of property deals (e.g., pending, closed, open) | stay updated on ongoing transactions and plan my next actions                 |
| `*`      | real estate agent                          | filter properties based on location                               | find properties that match location preferences of clients quickly            |
| `*`      | real estate agent                          | filter properties based on price                                  | find properties that match clients' budgets quickly                           |
| `* *`    | busy real estate agent                     | sort my client meetings in chronological order                    | prioritize the upcoming meetings first                                        |
| `* * *`  | real estate agent                          | search clients by their names                                     | view their contact details easily                                             ||

*(More to be added)*

### Use cases

(For all use cases below, the **System** is the `REconnect` and the **Actor** is the `user`, unless specified otherwise)

**Use case: Add Client Contact Details**

**Actor:** User (Real Estate Agent)\
**Preconditions:** The system is running.

**MSS**

1. User requests to add a new contact and provides contact details.
2. System validates input.
3. System stores contact.
4. System informs user the outcome of the addition.

**Use case ends.**

**Extensions**

2a. System detects missing or incorrect fields.\
&nbsp;&nbsp;&nbsp;&nbsp;2a1. System prompts the user to complete them.\
&nbsp;&nbsp;&nbsp;&nbsp;2a2. User enters new data.\
&nbsp;&nbsp;&nbsp;&nbsp;Steps 2a1-2a2 are repeated until the data entered is correct.\
&nbsp;&nbsp;&nbsp;&nbsp;**Use case resumes at step 3.**

3a. System fails to store the contact details.\
&nbsp;&nbsp;&nbsp;&nbsp;3a1. System displays an error message.\
&nbsp;&nbsp;&nbsp;&nbsp;**Use case ends.**

---

**Use case: Remove Client Contact Details**

**Actor:** User (Real Estate Agent)\
**Preconditions:** The system is running.

**MSS**

1. User selects the contact to be removed.
2. System requests user for confirmation.
3. System removes the contact.
4. System informs user the outcome of the deletion.

**Use case ends.**

**Extensions**

3a. System fails to delete the contact.\
&nbsp;&nbsp;&nbsp;&nbsp;3a1. System displays an error message.\
&nbsp;&nbsp;&nbsp;&nbsp;**Use case ends.**

---

**Use case: Edit Client Contact Details**

**Actor:** User (Real Estate Agent)\
**Preconditions:** The system is running.

**MSS**

1. User requests to edit an existing contact and provides contact details.
2. System validates input.
3. System updates contact.
4. System informs user the outcome of the edition.

**Use case ends.**

**Extensions**

2a. System detects missing or incorrect fields.\
&nbsp;&nbsp;&nbsp;&nbsp;2a1. System prompts the user to complete them.\
&nbsp;&nbsp;&nbsp;&nbsp;2a2. User enters new data.\
&nbsp;&nbsp;&nbsp;&nbsp;Steps 2a1-2a2 are repeated until the data entered is correct.\
&nbsp;&nbsp;&nbsp;&nbsp;**Use case resumes at step 3.**

3a. System fails to update the contact details.\
&nbsp;&nbsp;&nbsp;&nbsp;3a1. System displays an error message.\
&nbsp;&nbsp;&nbsp;&nbsp;**Use case ends.**

---

**Use case: Find Client Contact**

**Actor:** User (Real Estate Agent)\
**Preconditions:** The system is running.

**MSS**

1. User requests to find an existing contact and provides criteria for name, phone, email, and/or address.
2. System validates input.
3. System returns a list of relevant contacts.

**Use case ends.**

**Extensions**

2a. System detects missing or incorrect fields.\
&nbsp;&nbsp;&nbsp;&nbsp;2a1. System prompts the user to complete them.\
&nbsp;&nbsp;&nbsp;&nbsp;2a2. User enters new data.\
&nbsp;&nbsp;&nbsp;&nbsp;Steps 2a1-2a2 are repeated until the data entered is correct.\
&nbsp;&nbsp;&nbsp;&nbsp;**Use case resumes at step 3.**

3a. System fails to find the clients with the input criteria.\
&nbsp;&nbsp;&nbsp;&nbsp;3a1. System displays an empty list.\
&nbsp;&nbsp;&nbsp;&nbsp;**Use case ends.**

---

**Use case: Add Property Listing Details**

**Actor:** User (Real Estate Agent)\
**Preconditions:** The system is running.

**MSS**

1. User requests to add a new property listing and provides property listing details.
2. System validates input.
3. System stores property listing.
4. System informs user the outcome of the addition.

**Use case ends.**

**Extensions**

2a. System detects missing or incorrect fields.\
&nbsp;&nbsp;&nbsp;&nbsp;2a1. System prompts the user to complete them.\
&nbsp;&nbsp;&nbsp;&nbsp;2a2. User enters new data.\
&nbsp;&nbsp;&nbsp;&nbsp;Steps 2a1-2a2 are repeated until the data entered is correct.\
&nbsp;&nbsp;&nbsp;&nbsp;**Use case resumes at step 3.**

3a. System fails to store the property listing details.\
&nbsp;&nbsp;&nbsp;&nbsp;3a1. System displays an error message.\
&nbsp;&nbsp;&nbsp;&nbsp;**Use case ends.**

---

**Use case: Remove Property Listing Details**

**Actor:** User (Real Estate Agent)\
**Preconditions:** The system is running.

**MSS**

1. User selects the property listing to be removed.
2. System requests user for confirmation.
3. System removes the listing.
4. System informs user the outcome of the deletion.

**Use case ends.**

**Extensions**

3a. System fails to delete the property listing.\
&nbsp;&nbsp;&nbsp;&nbsp;3a1. System displays an error message.\
&nbsp;&nbsp;&nbsp;&nbsp;**Use case ends.**

---

**Use case: Edit Property Listing Details**

**Actor:** User (Real Estate Agent)\
**Preconditions:** The system is running.

**MSS**

1. User requests to edit an existing property and provides property listing details.
2. System validates input.
3. System updates property listing.
4. System informs user the outcome of the edition.

**Use case ends.**

**Extensions**

2a. System detects missing or incorrect fields.\
&nbsp;&nbsp;&nbsp;&nbsp;2a1. System prompts the user to complete them.\
&nbsp;&nbsp;&nbsp;&nbsp;2a2. User enters new data.\
&nbsp;&nbsp;&nbsp;&nbsp;Steps 2a1-2a2 are repeated until the data entered is correct.\
&nbsp;&nbsp;&nbsp;&nbsp;**Use case resumes at step 3.**

3a. System fails to update the property details.\
&nbsp;&nbsp;&nbsp;&nbsp;3a1. System displays an error message.\
&nbsp;&nbsp;&nbsp;&nbsp;**Use case ends.**

---

**Use case: Find Property Listing**

**Actor:** User (Real Estate Agent)\
**Preconditions:** The system is running.

**MSS**

1. User requests to find an existing property listing and provides criteria for name keywords, address, price, size, and/or owner.
2. System validates input.
3. System returns a list of relevant property listings.

**Use case ends.**

**Extensions**

2a. System detects missing or incorrect fields.\
&nbsp;&nbsp;&nbsp;&nbsp;2a1. System prompts the user to complete them.\
&nbsp;&nbsp;&nbsp;&nbsp;2a2. User enters new data.\
&nbsp;&nbsp;&nbsp;&nbsp;Steps 2a1-2a2 are repeated until the data entered is correct.\
&nbsp;&nbsp;&nbsp;&nbsp;**Use case resumes at step 3.**

3a. System fails to find the property listings with the input criteria.\
&nbsp;&nbsp;&nbsp;&nbsp;3a1. System displays an empty list.\
&nbsp;&nbsp;&nbsp;&nbsp;**Use case ends.**

---

**Use case: Add Deal**

**Actor:** User (Real Estate Agent)\
**Preconditions:** The system is running and has at least one property and one client.

**MSS**

1. User requests to add a new deal by providing property ID, buyer ID, price, and optional status.
2. System validates the input and checks if the property is available for a deal.
3. System automatically sets the seller based on the property owner.
4. System creates and stores the new deal.
5. System informs user the outcome of the addition.

**Use case ends.**

**Extensions**

2a. System detects invalid property ID or buyer ID.\
&nbsp;&nbsp;&nbsp;&nbsp;2a1. System displays an error message.\
&nbsp;&nbsp;&nbsp;&nbsp;**Use case ends.**

2b. Property is already involved in another deal.\
&nbsp;&nbsp;&nbsp;&nbsp;2b1. System displays an error message.\
&nbsp;&nbsp;&nbsp;&nbsp;**Use case ends.**

2c. Buyer and seller are the same person.\
&nbsp;&nbsp;&nbsp;&nbsp;2c1. System displays an error message.\
&nbsp;&nbsp;&nbsp;&nbsp;**Use case ends.**

2d. Price format is invalid.\
&nbsp;&nbsp;&nbsp;&nbsp;2d1. System displays an error message.\
&nbsp;&nbsp;&nbsp;&nbsp;**Use case ends.**

---

**Use case: Update Deal**

**Actor:** User (Real Estate Agent)\
**Preconditions:** The system is running and has at least one deal.

**MSS**

1. User requests to update an existing deal by providing the deal index and the fields to update.
2. System validates the input.
3. System updates the deal with the new information.
4. System informs user the outcome of the update.

**Use case ends.**

**Extensions**

2a. System detects invalid deal index.\
&nbsp;&nbsp;&nbsp;&nbsp;2a1. System displays an error message.\
&nbsp;&nbsp;&nbsp;&nbsp;**Use case ends.**

2b. User attempts to manually update the seller.\
&nbsp;&nbsp;&nbsp;&nbsp;2b1. System displays an error message.\
&nbsp;&nbsp;&nbsp;&nbsp;**Use case ends.**

2c. Updated deal would violate data constraints.\
&nbsp;&nbsp;&nbsp;&nbsp;2c1. System displays an error message.\
&nbsp;&nbsp;&nbsp;&nbsp;**Use case ends.**

---

**Use case: List Deals**

**Actor:** User (Real Estate Agent)\
**Preconditions:** The system is running.

**MSS**

1. User requests to list all deals.
2. System displays all deals in the address book.

**Use case ends.**

---

**Use case: Find Deal**

**Actor:** User (Real Estate Agent)\
**Preconditions:** The system is running.

**MSS**

1. User requests to find deals by providing search criteria (property name, buyer name, seller name, status, price above, or price below).
2. System displays a list of deals matching the criteria.

**Use case ends.**

**Extensions**

1a. No deals match the search criteria.\
&nbsp;&nbsp;&nbsp;&nbsp;1a1. System displays an empty list.\
&nbsp;&nbsp;&nbsp;&nbsp;**Use case ends.**

---

**Use case: Add Event to Schedule**

**Actor:** User (Real Estate Agent)\
**Preconditions:** The system is running.

**MSS**

1. User requests to add a new event and provides event details.
2. System validates input.
3. System checks for clashes in schedule.
4. System adds the event to the schedule.
5. System informs user the outcome of the addition.

**Use case ends.**

**Extensions**

2a. System detects missing or incorrect fields.\
&nbsp;&nbsp;&nbsp;&nbsp;2a1. System prompts the user to complete them.\
&nbsp;&nbsp;&nbsp;&nbsp;2a2. User enters new data.\
&nbsp;&nbsp;&nbsp;&nbsp;Steps 2a1-2a2 are repeated until the data entered is correct.\
&nbsp;&nbsp;&nbsp;&nbsp;**Use case resumes at step 3.**

3a. Meeting clashes with the existing schedule.\
&nbsp;&nbsp;&nbsp;&nbsp;3a1. System prompts the user to choose a different date or time.\
&nbsp;&nbsp;&nbsp;&nbsp;3a2. User enters new data.\
&nbsp;&nbsp;&nbsp;&nbsp;**Use case resumes at step 2.**

4a. System fails to store the event details.\
&nbsp;&nbsp;&nbsp;&nbsp;4a1. System displays an error message.\
&nbsp;&nbsp;&nbsp;&nbsp;**Use case ends.**

---

**Use case: Edit Event Details**

**Actor:** User (Real Estate Agent)\
**Preconditions:** The system is running.

**MSS**

1. User requests to edit an event and provides event details.
2. System validates input.
3. System checks for clashes in schedule.
4. System adds the event to the schedule.
5. System informs user the outcome of the edition.

**Use case ends.**

**Extensions**

2a. System detects missing or incorrect fields.\
&nbsp;&nbsp;&nbsp;&nbsp;2a1. System prompts the user to complete them.\
&nbsp;&nbsp;&nbsp;&nbsp;2a2. User enters new data.\
&nbsp;&nbsp;&nbsp;&nbsp;Steps 2a1-2a2 are repeated until the data entered is correct.\
&nbsp;&nbsp;&nbsp;&nbsp;**Use case resumes at step 3.**

3a. Meeting clashes with the existing schedule.\
&nbsp;&nbsp;&nbsp;&nbsp;3a1. System prompts the user to choose a different date or time.\
&nbsp;&nbsp;&nbsp;&nbsp;3a2. User enters new data.\
&nbsp;&nbsp;&nbsp;&nbsp;**Use case resumes at step 2.**

4a. System fails to update the event details.\
&nbsp;&nbsp;&nbsp;&nbsp;3a1. System displays an error message.\
&nbsp;&nbsp;&nbsp;&nbsp;**Use case ends.**

---

**Use case: Delete Event**

**Actor:** User (Real Estate Agent)\
**Preconditions:** The system is running.

**MSS**

1. User selects the event to be deleted.
2. System requests user for confirmation.
3. System deletes the event.
4. System informs user the outcome of the deletion.

**Use case ends.**

**Extensions**

3a. System fails to delete the event.\
&nbsp;&nbsp;&nbsp;&nbsp;4a1. System displays an error message.\
&nbsp;&nbsp;&nbsp;&nbsp;**Use case ends.**

---

**Use case: Find Event**

**Actor:** User (Real Estate Agent)\
**Preconditions:** The system is running.

**MSS**

1. User requests to find an existing event and provides criteria for event type, date and time, client name, and/or property name.
2. System validates input.
3. System returns a list of relevant events.

**Use case ends.**

**Extensions**

2a. System detects missing or incorrect fields.\
&nbsp;&nbsp;&nbsp;&nbsp;2a1. System prompts the user to complete them.\
&nbsp;&nbsp;&nbsp;&nbsp;2a2. User enters new data.\
&nbsp;&nbsp;&nbsp;&nbsp;Steps 2a1-2a2 are repeated until the data entered is correct.\
&nbsp;&nbsp;&nbsp;&nbsp;**Use case resumes at step 3.**

3a. System fails to find the events with the input criteria.\
&nbsp;&nbsp;&nbsp;&nbsp;3a1. System displays an empty list.\
&nbsp;&nbsp;&nbsp;&nbsp;**Use case ends.**

---

**Use case: List Events**

**Actor:** User (Real Estate Agent)\
**Preconditions:** The system is running.

**MSS**

1. User requests to list all events.
2. System displays all deals in the address book.

**Use case ends.**

### Non-Functional Requirements

1. The system must process all user commands within 2 seconds.
2. The system must be able to run on different operating systems like Linux, Windows and MacOS.
3. The system should be able to support up to 10000 contact details, property listings and meeting schedules each without a noticeable degradation in performance.
4. The system must not lose or corrupt contact data under normal conditions.
5. The program will not send automated tasks like periodic reminders.
6. The system should be intuitive so that a new user can quickly learn how to use it.

*(More to be added)*

### Glossary

**Address Book**: A digital storage system that manages client contacts and related information.

**Agent**: A licensed professional responsible for managing client contacts and facilitate real estate transactions.

**AND Connective**: A logical operator used in find commands (prefixed as `AND_`) that requires all specified conditions to be true for a match to be returned.

**Buyer**: A client who purchases a property in a deal transaction.

**Case-insensitive**: A property of string comparison where uppercase and lowercase letters are treated as equivalent (e.g., "Villa" matches "villa", "VILLA", etc.).

**Client**: Anyone who intends to buy, sell or invest in real estate that interacts with an agent regarding their prospective real estate transactions.

**Command**: A text instruction entered by the user to perform a specific action in the application (e.g., `add_deal`, `find_client`).

**Command-Line Interface (CLI)**: A text-based user interface that allows users to interact with the system by typing commands.

**Connective Prefix**: A prefix that can be combined with logical connectives (AND, OR) to form compound arguments in commands.

**Contact Details**: Information including name, phone number, email, and/or other personal details of a client.

**Deal**: A property transaction record that tracks the relationship between a property, its seller (property owner), a buyer, the agreed price, and the current status of the transaction.

**Deal Status**: The current status of a property transaction which falls into three categories: Pending, Closed, or Open.

**Event**: A scheduled activity or appointment related to real estate transactions.

**Event Type**: The category of an event, such as client meetings, workshops, conferences, or any other relevant activities.

**Graphical User Interface (GUI)**: A visual way of interacting with a computer using items such as windows, icons, and menus, operated by a mouse or touchscreen.

**Index**: A number used to refer to a specific item in a displayed list. In REconnect, indexes are 1-based (start from 1) and shown to the left of each item in the list.

**JAR file**: A Java Archive file (with .jar extension) that packages multiple Java class files and resources into a single file for distribution.

**OR Connective**: A logical operator used in find commands (prefixed as `OR_`) that requires at least one of the specified conditions to be true for a match to be returned.

**Parameter**: A piece of information provided to a command, typically in the format `prefix/VALUE`, such as `price/2000`.

**Positive Integer**: A whole number greater than zero (1, 2, 3, etc.).

**Prefix**: A keyword followed by a forward slash (/) that indicates the type of information being provided in a command, such as `pid/` for property ID.

**Property ID (pid)**: A reference number used to identify a specific property in the property list.

**Property Listing**: A collection of information about a property available for sale.

**Property Viewing**: A scheduled meeting between the real estate agent and a client to inspect a property.

**Result Display**: The area in the GUI that shows the outcome of executing a command, including success messages and error messages.

**Seller**: The owner of a property who is selling it in a deal transaction.

**Test Case**: A specific set of conditions, inputs, and expected outcomes used to verify that a particular feature works correctly.

**Terminal**: A text-based interface program that allows users to interact with the operating system by entering commands.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Planned Enhancements**

1. Allow for other valid phone number formats (e.g., `+65 8287 2001`, `65-8287-2001`, etc.) to be accepted.

2. Improve validation for emails for clients.
   - Currently, the regex used for email validation is not comprehensive and can be improved to cover more edge cases.
   - E.g. alex@55.533 is considered valid, but it should not be.
   - E.g. alex@emailcom is considered valid, but it should not be.

3. Improve validation for addresses for clients and properties.
   - Currently, any non-empty string is considered valid.
   - E.g. 1234 is considered valid, but it should not be.
   - E.g. @%%@% is considered valid, but it should not be.

4. Add support for more complex date and time formats in the event scheduling feature.
   - Currently, only `dd-MM-yyyy HHmm` format are supported.
   - E.g. `dd/MM/yyyy HH:mm` is not supported.

5. Allow substring matching for client and property names in the find commands.
   - Currently, only keyword matching is supported.
   - E.g. `find_client name_keywords/Joh` will not match `John Doe`, but it's better if it does.

6. Allow optional parameters when adding events.
   - Currently, parameters such as `cid` and `pid` are compulsory. This may not make sense for some event types like `workshop` or `others`.
   - Some parameters can be made optional for certain event types.

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   2. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

2. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   2. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

### Client management

1. Adding a client
    
   1. Test case: `add_client name/John Doe phone/82872001`<br>
      Expected: A new client is added with the name "John Doe", phone number "82872001", email and address as "(blank)".
      A success message is shown in the result display.

   2. Test case: `add_client name/John S/O Doe phone/82872001`<br>
      Expected: A new client is added with the name "John S/O Doe", phone number "82872001", email and address as "(blank)".
      A success message is shown in the result display.

   3. Test case: `add_client name/John S\O Doe phone/82872001`<br>
      Expected: A new client is added with the name "John S\O Doe", phone number "82872001", email and address as "(blank)".
      A success message is shown in the result display.

   4. Test case: `add_client name/John Mo phone/72872001`<br>
      Expected: An error message is shown in the result display indicating that the phone number is invalid.

   5. Test case: `add_client name/John Mo phone/828720019`<br>
      Expected: An error message is shown in the result display indicating that the phone number is invalid.

   6. Test case: `add_client name/John Doe phone/98765432 email/johnd@example.com`<br>
      Expected: A new client is added with the name "John Doe", phone number "98765432", email "johnd@example.com" and address as "(blank)".
      A success message is shown in the result display.

   7. Test case: `add_client name/John Moe phone/98765432 email/johnd_9+0-0.0@example.com`<br>
      Expected: A new client is added with the name "John Moe", phone number "98765432", email "johnd_9+0-0.0@example.com" and address as "(blank)".
      A success message is shown in the result display. 

   8. Test case: `add_client name/John Moe phone/98765432 email/johnd_+-.0@example.com`<br>
      Expected: An error message is shown in the result display indicating that the email is invalid.

   9. Test case: `add_client name/Jo Doe phone/98765432 addr/311, Clementi Ave 2, #02-25`<br>
      Expected: A new client is added with the name "Jo Doe", phone number "98765432", email "(blank)" and address as "311, Clementi Ave 2, #02-25".
      A success message is shown in the result display.
   
   10. Test case: `add_client name/Joe Doe phone/98765432 email/johnd@example.com addr/311, Clementi Ave 2, #02-25`<br>
       Expected: A new client is added with the name "Joe Doe", phone number "98765432", email "johnd@example.com" and address as "311, Clementi Ave 2, #02-25".
       A success message is shown in the result display.
   
2. Editing a client

   1. Prerequisites: There must be at least one client in the client list. Use the `list_clients` command to verify this and note its index.

   2. Test case: `edit_client 1 name/John Doe`<br>
      Expected: The client at index 1 is updated with the name "John Doe". A success message is shown in the result display.

   3. Test case: `edit_client 1 phone/82872001`<br>
      Expected: The client at index 1 is updated with the phone number "82872001". A success message is shown in the result display.

   4. Test case: `edit_client 1 email/johnd@example.com`<br>
      Expected: The client at index 1 is updated with the email "johnd@example.com". A success message is shown in the result display.

   5. Test case: `edit_client 1 addr/311, Clementi Ave 2, #02-25`<br>
      Expected: The client at index 1 is updated with the address "311, Clementi Ave 2, #02-25". A success message is shown in the result display.

   6. Test case: `edit_client 1 name/John Doe phone/82872001 email/johnd@example.com addr/311, Clementi Ave 2, #02-25`<br>
      Expected: The client at index 1 is updated with the name "John Doe", phone number "82872001", email "johnd@example.com", and address "311, Clementi Ave 2, #02-25". A success message is shown in the result display.

   7. Test case: `edit_client 1 phone/72872001`<br>
      Expected: An error message is shown in the result display indicating that the phone number is invalid.

   8. Test case: `edit_client 1 email/johnd_+-.0@example.com`<br>
      Expected: An error message is shown in the result display indicating that the email is invalid.

   9. Test case: `edit_client 999 name/John Doe`<br>
      Expected: An error message is shown in the result display indicating that the client index is invalid (out of the range of the current client list).

   10. Test case: `edit_client 1`<br>
       Expected: An error message is shown in the result display indicating that no fields to edit were provided.

3. Finding a client

    1. Prerequisites: There must be multiple clients with different names, phone numbers, emails, and addresses.
       To properly test all cases, ensure you have:
        * At least one client with "Alice" in their name (e.g., "Alice Tan")
        * At least one client with "Bob" in their name (e.g., "Bob Lee")
        * At least one client with the phone number "12345678"
        * At least one client with the email "alice@example.com"
        * At least one client with the address "123, Clementi Ave 3"

    2. Test case: `find_client name_keywords/Alice`<br>
       Expected: Displays clients with names containing "Alice" if present.

    3. Test case: `find_client phone/1234`<br>
       Expected: Displays clients with the phone number "12345678".

    4. Test case: `find_client email/alice`<br>
       Expected: Displays clients with the email "alice@example.com".

    5. Test case: `find_client address/Clementi`<br>
       Expected: Displays clients with the address "123, Clementi Ave 3".

    6. Test case: `find_client keywords/Alice AND_phone/5678`<br>
       Expected: Displays clients with names containing "Alice" and phone number "12345678".

    7. Test case: `find_client keywords/Bob OR_email/xample.com`<br>
       Expected: Displays clients with names containing "Bob" or email "alice@example.com".

    8. Test case: `find_client nonexistent/keyword`<br>
       Expected: Invalid command format. Error message shown.

    9. Test case: `find_client name_keywords/NonexistentName`<br>
       Expected: No clients found. Result display shows "0 clients listed!".

    10. Test case: `find_client name_keywords/Alice AND_phone/99999999`<br>
        Expected: No clients found. Result display shows "0 clients listed!".

4. Deleting a client

    1. Prerequisites: There must be at least one client in the client list. Use the `list_clients` command to verify this and note its index.

    2. Test case: `delete_client 1`<br>
       Expected: The client at index 1 is deleted. A success message is shown in the result display.
    
    3. Test case: `delete_client 999`<br>
       Expected: An error message is shown in the result display indicating that the client index is invalid.

5. Listing clients

    1. Prerequisites: The client list has been filtered using `find_client`.

    2. Test case: `list_clients`<br>
       Expected: All clients are displayed, showing the complete, unfiltered list.

    3. Test case: `list_clients extra_argument`<br>
       Expected: Invalid command format. Error message shown.

### Deal management

1. Adding a deal

   1. Prerequisites: There must be at least one property and one client in the respective lists.
      Use the `list_properties` and `list_clients` commands to verify this and note their indexes.
      **Important**: Since deals cannot be deleted, you will need a different property for each add_deal test. 
      Consider adding multiple properties before testing using the `add_property` command.

   2. Test case: `add_deal pid/1 buyer/1 price/2000 status/OPEN`<br>
      Expected: A new deal is added with the specified property, buyer, and status. The property owner is automatically set as the seller.
      A success message is shown in the result display.

   3. Test case: `add_deal pid/1 buyer/1 price/2000`<br>
      Expected: A new deal is added with default status (PENDING). Success message is shown.

   4. Test case: `add_deal pid/999 buyer/1 price/2000`<br>
      Expected: An error message is shown indicating that the property ID is invalid (out of the range of the current property list).

   5. Test case: `add_deal pid/1 buyer/999 price/2000`<br>
      Expected: An error message is shown indicating that the buyer ID provided is invalid (out of the range of the current client list).

   6. Test case: `add_deal pid/1 buyer/1 price/1`<br>
      Expected: An error message is shown indicating that price must be between 3 and 6 digits.

   7. Other incorrect add_deal commands to test:
      * `add_deal pid/a buyer/1 price/2000`: Non-integer property ID
      * `add_deal pid/1 buyer/b price/2000`: Non-integer buyer ID
      * `add_deal pid/1 buyer/1 price/2000 status/invalid`: Invalid status (not OPEN, PENDING, or CLOSED)
      * `add_deal pid/1 buyer/1 price/2000000`: Price exceeds 6 digits
      * `add_deal`: Missing all required fields<br>
      Expected: Similar to previous test cases. Error messages specific to the invalid input are shown.

2. Updating a deal

   1. Prerequisites: There must be at least one deal in the deal list. Use the `list_deals` command to verify this and note its index.

   2. Test case: `update_deal 1 status/CLOSED`<br>
      Expected: The status of the first deal is updated to CLOSED. Success message is shown.

   3. Test case: `update_deal 1 price/3000`<br>
      Expected: The price of the first deal is updated to $3000k. Success message is shown.

   4. Test case: `update_deal 1 buyer/2`<br>
      Expected: The buyer of the first deal is updated to the client at index 2 (if it exists). Success message is shown.

   5. Test case: `update_deal 0 status/CLOSED`<br>
      Expected: An error message is shown indicating that index must be a positive integer.

   6. Test case: `update_deal 999 status/CLOSED`<br>
      Expected: An error message is shown indicating that the index is invalid.

   7. Other incorrect update_deal commands to test:
      * `update_deal 1 pid/999`: Invalid property ID
      * `update_deal 1 buyer/999`: Invalid buyer ID
      * `update_deal 1 status/invalid`: Invalid status
      * `update_deal 1`: Missing all optional fields<br>
      Expected: Similar to previous test cases. Error messages specific to the invalid input are shown.

3. Finding a deal

   1. Prerequisites: There must be multiple deals with different properties, buyers, sellers, prices, and statuses.
      To properly test all cases, ensure you have:
      * At least one property with "Villa" in its name (e.g., "Maple Villa")
      * At least one property with "Ocean" in its name (e.g., "Ocean View")
      * At least one deal with status "CLOSED" and one with "PENDING"
      * At least one deal with a buyer named "John" and a seller named "Mary"
      * Deals with various prices (e.g., above $2000k, below $5000k)

   2. Test case: `find_deal prop/Villa`<br>
      Expected: Displays deals with properties containing "Villa" in their names if present.

   3. Test case: `find_deal status/CLOSED`<br>
      Expected: Displays all deals with status CLOSED.

   4. Test case: `find_deal buyer/John AND_seller/Mary`<br>
      Expected: Displays deals with buyers containing "John" and sellers containing "Mary".

   5. Test case: `find_deal prop/Ocean OR_status/PENDING`<br>
      Expected: Displays deals with properties containing "Ocean" or with PENDING status.

   6. Test case: `find_deal price_>/2000`<br>
      Expected: Displays deals with prices above $2000k.

   7. Test case: `find_deal price_</5000`<br>
      Expected: Displays deals with prices below $5000k.

   8. Test case: `find_deal nonexistent/keyword`<br>
      Expected: Invalid command format. Error message shown.

   9. Test case: `find_deal prop/NonexistentProperty`<br>
      Expected: No deals found. Result display shows "0 deals listed!".

   10. Test case: `find_deal prop/Villa AND_price_>/10000000`<br>
       Expected: An error message is shown indicating that price must be between 3 and 6 digits.

4. Listing deals

   1. Prerequisites: The deal list has been filtered using `find_deal`.

   2. Test case: `list_deals`<br>
      Expected: All deals are displayed, showing the complete, unfiltered list.

   3. Test case: `list_deals extra_argument`<br>
      Expected: Invalid command format. Error message shown.

### Event management

1. Adding an event

  1. Prerequisites: There must be at least one client and one property in the respective lists.
    Use the `list_clients` and `list_properties` commands to verify this and note their indexes.
   
  2. Test case: `add_event at/30-04-2025 1700 etype/meeting cid/2 pid/1 note/Property viewing at 123 Clementi Ave 2`<br>
     Expected: A new event is added with the specified state date and time, event type, client, property, and note.
     A success message is shown in the result display.
   
  3. Test case: `add_event at/30-04-2024 1700 etype/meeting cid/2 pid/1 note/N/A`<br>
     Expected: An error message is shown indicating that the event is scheduled too far back in the past.

  4. Test case: `add_event at/30-04-2025 1700 etype/meeting cid/3 pid/3 note/Some other meeting`<br>
     Expected: An error message is shown indicating that the event conflicts with an existing event.

  4. Test case: `add_event at/30-04-2025 1900 etype/some type cid/2 pid/1 note/N/A`<br>
     Expected: An error message is shown indicating that the event type is invalid, and the accepted event types are listed.

  5. Test case: `add_event at/30-04-2025 1900 etype/meeting cid/999 pid/1 note/N/A`<br>
     Expected: An error message is shown indicating that the client ID provided is invalid (out of the range of the current client list).

  6. Test case: `add_event at/30-04-2025 1900 etype/meeting cid/2 pid/999 note/N/A`<br>
     Expected: An error message is shown indicating that the property ID provided is invalid (out of the range of the current property list).

2. Editing an event

  1. Prerequisites: There must be at least one event in the event list. Use the `list_events` command to verify this and note its index.

  2. Test case: `edit_event 1 at/30-04-2025 1200 note/Changed to 12pm`<br>
     Expected: The date and time of the first event is updated to 30-04-2025 at 1200. A success message is shown.
   
  3. Test case: `edit_event 1 etype/others`<br>
     Expected: The type of the first event is updated to "Others". A success message is shown.
   
  4. Test case: `edit_event 1 cid/1`<br>
     Expected: The client of the first event is updated to the client at index 1. A success message is shown.
   
  5. Test case: `edit_event 1 note/Coffee with client`<br>
     Expected: The note of the first event is updated as specified. A success message is shown.
   
  6. Test case: `edit_event 999 at/30-04-2025 1200`<br>
      Expected: An error message is shown indicating that the index provided is invalid (out of the range of the current event list).

  7. Other incorrect `edit_event` commands to test:
      * `edit_event 1 at/30-04-2024 1700`: Past date and time
      * `edit_event 1 cid/999`: Invalid client ID
      * `edit_event 1 etype/invalid`: Invalid event type
      * `edit_event 1`: Missing all optional fields<br>
        Expected: Similar to previous test cases. Error messages specific to the invalid input are shown.

3. Finding an event

  1. Prerequisites: There must be multiple events with different dates and times, types, clients, and properties.
     To properly test all cases, ensure you have:
     * At least one event before 01-03-2025 0000 and after 30-04-2025 1700
     * At least one event with event type "Meeting"
     * At least one event with a client whose name contains "Alice" (e.g., "Alice Tan")
     * At least one event with a property whose name contains "Villa" (e.g., "Maple Villa")

  2. Test case: `find_event before/30-04-2025 1700`<br>
     Expected: Displays events before 30-04-2025 1700.

  3. Test case: `find_event after/01-03-2025 0000`<br>
     Expected: Displays events after 01-03-2025 0000.

  4. Test case: `find_event after/01-03-2025 0000 AND_before/30-04-2025 1700`<br>
     Expected: Displays events after 01-03-2025 0000 and before 30-04-2025 1700.

  5. Test case: `find_event etype/meeting`<br>
     Expected: Displays events with the type "Meeting".

  6. Test case: `find_event with/Alice`<br>
     Expected: Displays events associated with clients with names containing "Alice".

  7. Test case: `find_event about/Villa`<br>
     Expected: Displays events associated with properties with names containing "Villa".

  8. Test case: `find_event after/01-03-2025 0000 AND_before/30-04-2025 1700`<br>
     Expected: Displays events before 30-04-2025 1700.

  9. Test case: `find_client nonexistent/keyword`<br>
     Expected: An error message is shown indicating that the format is invalid.


4. Deleting an event

  1. Prerequisites: There must be at least one event in the event list. Use the `list_event` command to verify this and note its index.

  2. Test case: `delete_event 1`<br>
     Expected: The event at index 1 is deleted. A success message is shown.

  3. Test case: `delete_event 999`<br>
     Expected: An error message is shown in the result display indicating that the event index is invalid.

  4. Test case: `delete_event 1 1 1`<br>
     Expected: An error message is shown indicating that the event index is invalid.

5. Listing events
    1. Prerequisites: The event list has been filtered using `find_event`.

    2. Test case: `list_events`<br>
       Expected: All events are displayed, showing the complete, unfiltered list.

    3. Test case: `list_events extra_argument`<br>
       Expected: Invalid command format. Error message shown.
