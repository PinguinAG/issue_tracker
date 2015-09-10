Tracker
============

Proof of concept issue tracker developed using Spring Boot, Spring Jpa Data and Thymeleaf.

Dependencies
----------------
This project requires JVM 8 to run and JDK 8 to compile. Gradle is also used, but can be run directly from the 
wrapper. A Tomcat 7 server (7.0.50+) can be used to deploy the war file.

### Things to Note
The Thymeleaf templates (`index.html`, `issues.html` and `plan.html`) were done in a hurry and require the 
application to be deployed as **ROOT**! Otherwise only the REST endpoints will work.

Building and Running
--------------

### Working with Gradle
To build the application you can use the gradle wrapper. This is called using (from inside the project folder):

 * Windows: `gradlew.bat`
 * Unix: `./gradlew`

For a list of all the tasks available the `tasks` task can be called:

 * Windows: `gradlew.bat tasks`
 * Unix: `./gradlew tasks`

From here on all the tasks will be considered to be running on an Unix system.

### IDE Setup

Depending on your environment you must first setup the necessary project files. This can be achieved using the 
appropriate Gradle tasks:

 * IDEA Intellij: `./gradlew idea`
 * Eclipse: `./gradlew eclipse`

To clean these files (for example, when a hard refresh of the project setup is needed), `clean<Task>` must be called.

 * IDEA Intellij: `./gradlew cleanIdea`
 * Eclipse: `./gradlew cleanEclipse`
 
These two commands can be chained:

 * IDEA Intellij: `./gradlew cleanIdea idea`
 * Eclipse: `./gradlew cleanEclipse eclipse`

### Building and Running

Simply running the `assemble` task will in Gradle generating the necessary jar and war files.

**`./gradlew assemble`**

Spring Boot Gradle plugin packages everything in a single runnable **big** jar. To run it, simply calling the jvm will 
suffice (after `assemble` has run).

**`java -jar build/libs/tracker-0.1.0.jar`**

These two commands can be chained:

 * Windows: `gradlew.bat assemble & java -jar build/libs/tracker-0.1.0.jar` 
 * Unix: `./gradlew assemble && java -jar build/libs/tracker-0.1.0.jar`

You can also run it by simply calling the `bootRun` task, but this requires modifying the `resources/main/application
.properties` file:

    # DataSource 
    spring.datasource.url=jdbc:hsqldb:file:\${java.io.tmpdir}/${dbSchema}`
to

    # DataSource
    spring.datasource.url=jdbc:hsqldb:file:\${java.io.tmpdir}/tracker

since `bootRun` does not read resources files from the resource output folder.

By default the server starts up at `localhost:8080/`.

Accessing the application
--------------

The entirety of the tracker is built around REST api endpoints, but there are a few html files that can be accessed. 
These are populated using Thymeleaf and use JQuery to call the underlying REST api.

 * `/index.html` - which displays the list of developers. A new developer can be added here (and developers can also 
 be removed)
 * `/issues.html` - which displays the lis of issues. New issue can be created, and old ones can be edited.
 * `/plan.html` - which displays the current project plan.

### API Endpoints

<table>
    <tr>
        <th>URL</th>
        <th>ACTION</th>
        <th>METHOD</th>
        <th>REQUEST BODY EXAMPLE</th>
        <th>RESPONSE EXAMPLE</th>
    </tr>
    <tr>
        <td>/developers/</td>
        <td>Retrieve All</td>
        <td>GET</td>
        <td>-</td>
        <td>
            <pre>
{
    'developers':[
        {'id': 0, 'name': 'First Developer'},
        {'id': 1, 'name': 'Second Developer'},
        ...
        {'id': 10000, 'name': 'Last Developer'}
        ]
}</pre>
        </td>
    </tr>
    <tr>
        <td>/developer/{id}</td>
        <td>Retrieve One</td>
        <td>GET</td>
        <td>-</td>
        <td>
            <pre>
{
    'id': 0,
    'name': 'First Developer'
}</pre>
        </td>
    </tr>
    <tr>
        <td>/developer/</td>
        <td>Create One</td>
        <td>POST</td>
        <td>
            <pre>
{
    'name':'New Developer'
}</pre>
        </td>
        <td>
            <pre>
{
    'id': 1000,
    'name': 'New Developer'
}</pre>
        </td>
    </tr>
    <tr>
        <td>/developer/{id}</td>
        <td>Remove One</td>
        <td>DELETE</td>
        <td>-</td>
        <td>-</td>
    </tr>
    <tr>
        <td>/issues/</td>
        <td>Retrieve All</td>
        <td>GET</td>
        <td>-</td>
        <td>
            <pre>
{
    'issues':[
        {
            'id': 0,
            'issueType': 'STORY',
            'title': 'First Story',
            'description': 'First Story Description',
            'status': 'ESTIMATED',
            'estimate': 8,
            'createdDate': [2015,7,26],
            'assignedDeveloper': {'id':0, 'name':'First Developer'}
        },
        {
            'id': 1,
            'issueType': 'BUG',
            'title': 'First Bug',
            'description': 'First Bug Description',
            'status': 'VERIFIED',
            'priority': 'CRITICAL',
            'createdDate': [2015,7,26],
            'assignedDeveloper': {'id':0, 'name':'First Developer'}
        },
        ...
        ]
}</pre>
        </td>
    </tr>
    <tr>
        <td>/issues/open/</td>
        <td>Retrieve All</td>
        <td>GET</td>
        <td>-</td>
        <td>Same as /issues/ but excluding COMPLETED or CLOSED issues.</td>
    </tr>
    <tr>
        <td>/story/{id}</td>
        <td>Retrieve One</td>
        <td>GET</td>
        <td>-</td>
        <td>
            <pre>
{
    'issueType': 'STORY',
    'title': 'First Story',
    'description': 'First Story Description 2',
    'status': 'ESTIMATED',
    'estimate': 8,
    'createdDate': [2015,7,26],
    'assignedDeveloper': {'id':0, 'name':'First Developer'}
}</pre>
        </td>
    </tr>
    <tr>
        <td>/story/</td>
        <td>Create One</td>
        <td>POST</td>
        <td>
            <pre>
{
    'title': 'First Story',
    'description': 'First Story Description',
    'status': 'ESTIMATED',
    'estimate': 8,
    'assignedDeveloperId': 0
}</pre>
        </td>
        <td>
            <pre>
{
    'id': 0,
    'issueType': 'STORY',
    'title': 'First Story',
    'description': 'First Story Description',
    'status': 'ESTIMATED',
    'estimate': 8,
    'createdDate': [2015,7,26],
    'assignedDeveloper': {'id':0, 'name':'First Developer'}
}</pre>
        </td>
    </tr>
    <tr>
        <td>/story/{id}</td>
        <td>Update One</td>
        <td>POST</td>
        <td>
            <pre>
{
    'title': 'First Story',
    'description': 'First Story Description 2',
    'status': 'ESTIMATED',
    'estimate': 8,
    'assignedDeveloperId': 2
}</pre>
        </td>
        <td>
            <pre>
{
    'id': 0,
    'issueType': 'STORY',
    'title': 'First Story',
    'description': 'First Story Description 2',
    'status': 'ESTIMATED',
    'estimate': 8,
    'createdDate': [2015,7,26],
    'assignedDeveloper': {'id':2, 'name':'Third Developer'}
}</pre>
        </td>
    </tr>
    <tr>
        <td>/bug/{id}</td>
        <td>Retrieve One</td>
        <td>GET</td>
        <td>-</td>
        <td>
            <pre>
{
    'id': 1,
    'issueType': 'BUG',
    'title': 'First Bug',
    'description': 'First Bug Description',
    'status': 'VERIFIED',
    'priority': 'CRITICAL',
    'createdDate': [2015,7,26],
    'assignedDeveloper': {'id':0, 'name':'First Developer'}
}</pre>
        </td>
    </tr>
    <tr>
        <td>/bug/</td>
        <td>Create One</td>
        <td>POST</td>
        <td>
            <pre>
{
    'title': 'First Bug',
    'description': 'First Bug Description',
    'status': 'VERIFIED',
    'priority': 'CRITICAL',
    'assignedDeveloperId': 0
}</pre>
        </td>
        <td>
            <pre>
{
    'id': 1,
    'issueType': 'BUG',
    'title': 'First Bug',
    'description': 'First Bug Description',
    'status': 'VERIFIED',
    'priority': 'CRITICAL',
    'createdDate': [2015,7,26],
    'assignedDeveloper': {'id':0, 'name':'First Developer'}
}</pre>
        </td>
    </tr>
    <tr>
        <td>/bug/{id}</td>
        <td>Update One</td>
        <td>POST</td>
        <td>
            <pre>
{
    'title': 'First Bug',
    'description': 'First Bug Description',
    'status': 'VERIFIED',
    'priority': 'MAJOR',
    'assignedDeveloperId': 2
}</pre>
        </td>
        <td>
            <pre>
{
    'id': 1,
    'issueType': 'BUG',
    'title': 'First Bug',
    'description': 'First Bug Description',
    'status': 'VERIFIED',
    'priority': 'MAJOR',
    'createdDate': [2015,7,26],
    'assignedDeveloper': {'id':2, 'name':'Third Developer'}
}</pre>
        </td>
    </tr>
   <tr>
        <td>/plan/</td>
        <td>Retrieve All</td>
        <td>GET</td>
        <td>-</td>
        <td>
            <pre>
{
    'plan':[
        {
            'stories':[
                {
                    'id': 0,
                    'issueType': 'STORY',
                    'title': 'First Story',
                    'description': 'First Story Description',
                    'status': 'ESTIMATED',
                    'estimate': 8,
                    'createdDate': [2015,7,26],
                    'assignedDeveloper': {'id':0, 'name':'First Developer'}
                },
                ...
                ]
         },
         ...
    ]
}</pre>
        </td>
    </tr>
</table>
