
# My Carbon Footprint

## Raise awareness to prevent global warming :earth_americas:

We all know that we are slowly leading ourselves towards global warming, even though we don't intend to.  While we need
big solutions for this problem, the best way to actually combat a problem is to start with the most basic approach. And
this includes reducing our carbon footprint.

### What is carbon footprint? How does it relate to global warming?

A carbon footprint is the total amount of greenhouse gas emissions that are generated as a result of our actions. These
greenhouse gases are the reason that Sun's heat is trapped in the atmosphere causing warmer temperatures to sustain
life. However, we have been producing high carbon footprint lately and this trend contributes to a predicted
2&deg;C increase in global temperature.

### How do I know how much carbon footprint am I generating?
Don't worry, you don't need to stress yourself about killing the planet. You can help Mother Earth be calculating your
carbon footprint through a series of simple questions about the actions you perform in life with the help of **My Carbon
Footprint**.

**My Carbon Footprint** can help you:
- *calculate the carbon footprint* you are generating yearly, and
- *compare your actions* in a specific genre through data collected by other people, and
- *provide suggestions* as to where and how you can improve to reduce your carbon footprint.

### Usage and Impact

This application will be used by environmental activists to aid them in spreading awareness towards the consequences our
actions have on this planet, and even by curious fellow human beings. I believe this problem involves everyone so
everyone should be aware of their carbon footprint and become a part of solution by reporting their data, which can
help us better research and suggest possible ways to avoid global warming. <br />
As more and more people consent to providing their data to this app, we will be in a position to better estimate the
carbon footprint we are generating and help the users reduce their carbon footprint by narrowing their attention to a
specific genre of activities by comparing their data with others. This would tell them where they are unnecessarily 
generating greenhouse emissions.

### User Stories
- As a user, I want to be able to calculate my carbon footprint.
- As a user, I want to be able to contribute my carbon footprint data to a data consisting of other people's footprints
  (if I so choose).
- As a user, I want to be able to manually edit my footprint values (if I so choose).
- As a user, I want to be able to save my footprint data to file (if I so choose).
- As a user, I want to be able to find out the average footprint of other people.
- As a user, I want to be able to compare my consumption of resources visually to the average.
- As a user, I want to be able to find out the carbon footprint I generate in different areas or categories.
- As a user, I want to be able to load my footprint data from file  (if I so choose).
- As a user, I want to be able to remove my contributions from the data of user footprints whenever I want.

## Instructions for Grader
- You can generate the first required action related to adding Xs to a Y by
Calculate My Footprint [Each Footprint (X) is added to FootprintRecord (Y)]
- You can generate the second required action related to adding Xs to a Y by
Calculate My Footprint -> Contribute [Your FootprintRecord (X) to Contributions (Y)].
- You can also delete your record by Delete My Contribution on main menu.
- You can also use the compare buttons to get comparison graph related to Xs and Y by Calculate OR Load from main screen.
- You can locate my visual component on the main menu which is the logo.
- You can locate my second visual component which is a comparison graph
(needs to save some sample values to contributions first to get average data.) by Calculate My Footprint -> Compare
OR Load Footprint -> Compare.
- You can save your footprint record by clicking the save button in Calculate OR Load.
- You can manually edit the footprint values by clicking the edit button in Calculate OR Load.
- You can reload your footprint record by Load My Footprint.

## Phase 4: Task 2
```
Wed Apr 12 14:25:01 PDT 2023
Loaded questions from file to question bank
Wed Apr 12 14:25:06 PDT 2023
Calculated user footprint
Wed Apr 12 14:25:06 PDT 2023
Calculated user footprint
Wed Apr 12 14:25:06 PDT 2023
Calculated user footprint
Wed Apr 12 14:25:06 PDT 2023
Loaded user records from the contributions file
Wed Apr 12 14:25:06 PDT 2023
Calculated average footprints from contributions file
Wed Apr 12 14:25:11 PDT 2023
Exported footprint record to phase4.json
Wed Apr 12 14:25:13 PDT 2023
Added footprint record to contributions file
Wed Apr 12 14:25:29 PDT 2023
Loaded user records from the contributions file
Wed Apr 12 14:25:29 PDT 2023
Calculated average footprints from contributions file
Wed Apr 12 14:25:38 PDT 2023
Loaded user records from the contributions file
Wed Apr 12 14:25:38 PDT 2023
Calculated average footprints from contributions file
Wed Apr 12 14:25:45 PDT 2023
Exported footprint record to phase4v2.json
Wed Apr 12 14:25:46 PDT 2023
Added footprint record to contributions file
Wed Apr 12 14:25:51 PDT 2023
Loaded user records from the contributions file
Wed Apr 12 14:25:51 PDT 2023
Calculated average footprints from contributions file
Wed Apr 12 14:26:07 PDT 2023
Exported footprint record to phase4.json
Wed Apr 12 14:26:08 PDT 2023
Loaded user records from the contributions file
Wed Apr 12 14:26:08 PDT 2023
Calculated average footprints from contributions file
Wed Apr 12 14:26:14 PDT 2023
Loaded user records from the contributions file
Wed Apr 12 14:26:14 PDT 2023
Deleted contribution record with ID: a291a473-3856-4bdd-aed3-05e361397929
Wed Apr 12 14:26:20 PDT 2023
Loaded user records from the contributions file
Wed Apr 12 14:26:20 PDT 2023
Calculated average footprints from contributions file
```

## Phase 4: Task 3
Looking at the 
[UML class diagram](https://github.students.cs.ubc.ca/CPSC210-2022W-T2/project_u9q7n/blob/main/UML_Design_Diagram.png),
I would refactor the four menu classes in the `menu` package in the `ui` package. If I had more time, I would create an
abstract class as these classes has a lot of common behavior and attributes for menus, which can then be inherited by
the individual menu classes. This would avoid duplication in the code and also making it easier to add new menu types in
the future. <br/>
For a much better design and given I had more time, I would refactor some bits of the code which is similar across
multiple classes and does the same thing. I would create an object that would be responsible for carrying out tasks
related to that, so I can segregate that method with the other methods which are related to each other in the class.
I did this for utilities like `Chart Manager` in the `ui` package, however, it would help to do it for classes in the
`data` package, especially saving data. <br/>
Another possible refactoring that I could do is to use the <b>Dependency Inversion Principle</b> to reduce the coupling between
classes. This principle suggests that high-level modules should not depend on low-level modules, but rather both should
depend on abstractions. By using abstractions, I can change the implementation of low-level modules without affecting 
high-level modules, making the code more flexible and maintainable.

