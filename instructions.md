# Introduction

The goal of this exercise is to give you the opportunity to build a very small scale web application. You should not invest more than 4-6 hours into this exercise.

We do not expect you to fully complete this project. We have all done this ourself, and so far nobody has "finished" the task in the provided 4-6 hours. We are mostly interested in a discussion base, seeing how you set up a project, what technical choices you make and what areas you decide to focus on.

This project is set up for you to focus on frontend development. The backend is already completely developed.

If you get completely stuck somewhere, feel free to ask us about clarification.

# Launching the test environment

please read instructions on how to run the backend at https://github.com/PinguinAG/issue_tracker/blob/master/backend/README.md
this also contains description for the API endpoints you can use to get data for the UI.

# Technical requirements for the application

* The application should work.
* You are free to choose technologies or frameworks as you see fit.

# Functional requirements for the application:

The goal is to develop a very simple issue tracker for a small team of developers. The requirements of the team are extremely basic:

    There should be a list of developers. No administration interface for this list is necessary, but it should be fairly easy to add or remove developers. Developers just have a name as their only attribute.
    The application should be able to handle two different kinds of issues: Stories and Bugs
        Both Types have an issue-ID that should be unique, as well as a title, a description a creation date and an assigned developer
        Stories also have an estimated point value, as well as a status that can switch between "New", "Estimated" and "Completed"
        Bugs have a priority ("Critical", "Major" or "Minor") as well as a status ("New", "Verified" or "Resolved")
    A simple CRU-Interface for Issues exists.
        Stories and Bugs can be displayed in a list ordered by creation date. It is possible to display either all issues or only open issues (not "Completed" or "Resolved")
        Stories and Bugs can be created.
        A single issue can be edited.
        Deleting issues should not be possible.
    The team wants some help with planning their stories. The goal is to have a simple interface that displays what stories will be worked on in which week.
        The team has learned that on average one developer can complete 10 story points a week
        A page should exist that distributes the uncompleted stories that have an estimated point value as optimally over the next weeks as possible.
        The goal is to complete all the stories in the minimum amount of weeks.
        It is not necessary to find a truly optimal solution, but the solution should not have any glaring flaws.
        The total amount of story points in a week should not exceed <developer count> * 10. It is not important to consider which developer a task is assigned to or individual developer capacities.
        No other constraints on the ordering of the stories exists.
        Bugs are completely ignored in this view and calculation.
        As long as no new stories are created, the distribution should remain the same. If the list of stories changes, reordering the plan is acceptable.


# What you should send to us:

* The source code of your project.
* A short explanation on how we can build and run your project.
* How to access the application (the URL)
* A short description on the technical choices you made and why you made them. If you had problems completing the project, a short description of the problems you faced and how you would continue to resolve them if you had more time.
