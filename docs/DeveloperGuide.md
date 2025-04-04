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

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

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

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

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

Here are the partial class diagrams in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserStructure.puml" width="600" height="500"/>
<puml src="diagrams/CommandStructure.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddClientCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
  * All `XYZCommandParser` classes (e.g., `AddClientCommandParser`, `DeleteClientCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="800" height="600"/>


The `Model` component,

* stores the data stored in REconnect i.e., all `Client` objects (which are contained in a `UniqueClientList` object).
* stores the currently 'selected' `Client` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Client>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

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

As you can see, its implemented in such a way that if any of the command classes do not implement the `addCommandWord()` method correctly, 
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

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

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

*(More to be added)*

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

**Client**: Anyone who intends to buy, sell or invest in real estate that interacts with an agent regarding their prospective real estate transactions.

**Contact Details**: Information including name, phone number, email, and/or other personal details of a client.

**Command-Line Interface (CLI)**: A text-based user interface that allows users to interact with the system by typing commands.

**Connective Prefix**: A prefix that can be combined with logical connectives (AND, OR) to form compound arguments in commands.

**Deal**: A property transaction record that tracks the relationship between a property, its seller (property owner), a buyer, the agreed price, and the current status of the transaction.

**Deal Status**: The current status of a property transaction which falls into three categories: Pending, Closed, or Open.

**Event**: A scheduled activity or appointment related to real estate transactions.

**Event Type**: The category of an event, such as client meetings, workshops, conferences, or any other relevant activities.

**Property Listing**: A collection of information about a property available for sale.

**Property Viewing**: A scheduled meeting between the real estate agent and a client to inspect a property.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

