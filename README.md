# Vulnerabilities Aggregation and Analysis Software
Implemented as part of Bachelor thesis "Aggregation and Analysis of IT Security Vulnerabilities"  
Technical University of Vienna; INSO; Essential Security; (2017 - 2018).  
Advisor: Florian Fankhauser  
Author: Eugen Gruzdev  
Date: 01.03.2018

# Brief Description and Foundations
Vulnerability Aggregation and Analysis Software is
an application that enables gathering of security advisories
about current software vulnerabilities from the wide variety
of sources followed by their analysis with an aim to identify
similarity between two or more security advisories and finally
save the information about vulnerabilities in a consistent local
database with the possibility to retrieve this information with
help of search by vulnerability or by product.  

Vulnerabilities are in some sense mistakes made during program
implementation or design that are sometimes very hard to find
by developers or testers of a program, but they can be used by
hackers to violate security policies of a program.

Huge amount of vulnerabilities in different software products
are found every day. Some of them are found by people that are
not interested in attacks on systems or programs. These
vulnerabilities are then disclosed to vendors and/or wide public
using so-called security advisories (or also security alerts).
Typically a security alert contains the name of the vulnerability,
its discovery date, level of severity, technical description,
affected products and packages, proof of concepts, possible
solutions and patches (if they are available). Most security
alerts are written by humans in natural language and do not have
mandatory structure, which means that every security alert is written
in different way without adhering some pattern. Fortunately, there
are some publishers of security alerts who strive to use the same
pattern for all published advisories, but they are more the exception
than the rule.

There are three important points that make aggregation and 
analysis of security advisories a computational hard task:
* **Large amount of security advisories** published every day
in wide variety of sources. The aggregation module of the
program should be able to collect advisories from many different
sources, including different source types, for example: emails,
web pages, rss feeds, etc.
* **Poorly structured information in security alerts written
in natural human language** makes the analysis procedure very hard.
The challenging part is to identify semantically similar advisories
from different sources (possibly in different formats) and to
retrieve the information about products and their versions from
semi-structured texts. Some publishers provide multiple vulnerabilities
in the same security alert. This makes the analysis task not easier.
* **Not every aggregated advisory is an advisory.** Some publishers
of security alerts do not always disclose
vulnerabilities information in their announces. Sometimes, and
quite often, they publish also advertisements of security events
or books, congratulate with local or global holidays, etc. Therefore,
there is a need to classify if every received security alert
contains vulnerabilities information or no. 

In the following the program architecture and implementation details
will be presented.

# Architecture Description

The Vulnerability Aggregation and Analysis Software consists of
3 major layers: data layer (called here DAO); business layer
(called here Service); and presentation layer (called here UI
(an abbreviation of User Interface)). The precise technical
description of each layer will be given in this document below.
In this chapter only the intuitive meaning will be provided.

* **Data (DAO) layer** is responsible for every  program-internal
data manipulation: saving, searching, modifying, deleting, etc
of data located in the database. It also provides interfaces
for other layers who want to access or manipulate data in the
database. Any computations occur here.

* **Business (service) layer** provides the program with main
logical modules. It is responsible for:
    * aggregation of security alerts from all the defined
  sources
    * correlation of similar security alerts
    * accessing interfaces of DAO layer for saving results of computations
    * program-internal logic: accessing and reading properties
  file; sending greeting email to new users; etc.
  
* **Presentation (UI) layer** includes all the classes and all
the logic that is needed to present to the user the results of
computations made in service layer and to search concrete data
in the database.

There are also some separate folders that contain important
program parts:
* Entities folder contains all the logical entities the program
needs to operate with, for example database: every table in the
database is represented by entity classes. Objects of these
classes are then entries of the table.
* Main class is the main control point of the whole program.
It sets the rules of uer-program and inter-modules cooperation.
* Resources package contains properties file which specifies 
current (variable) program options.
* pom.xml file contains information about the project and
configuration details used by Maven to build the project.
* LICENSE file defines terms of BSD 2-Clause License.

# Technology Stack

* Language: Java 8;
* Operating System: Windows 10 Home x64;
* Maven 1.6 & 1.8;
* SQLite Database with DB Browser for SQLite. The DB Browser is available as well as standalone
 application extension, and also as Firefox Browser Extension;
* IntelliJ IDEA 2017.2.5 as IDE;
* log4j 1.2.17 as LOGGER;
* javax.mail and com.sun.mail 1.6.0 for accessing email account and reading emails;
* Jsoup for accessing and parsing HTML Web Pages;
* com.sun.syndication and rome 1.7.4 for accessing and parsing RSS-Feeds;
* JDom 1.1 for manipulating XML Data
* Apache OpenNLP (1.8.2) and Stanford NLP Package (3.5.0) for Preprocessing step;
* Apache Lucene (7.0.0) for some tasks in Preprocessing and Classification steps;
* com.github.chen0040 (1.0.3) is open source implementation of Naive Bayes Classifier;
* Bouncy Castle Cryptography (1.59) for encryption of passwords;
* JUnit (4.12) for testing.

## DAO
This package contains all the code needed for manipulation with
the database of all kinds. It defines the sub-package **interfaces**
which contains the interfaces that are visible from other layers.
In the **db** package the code for creation of tables is situated
and in the **SQLiteWorkingPackage** the classes with basic CRUD
operations for each table are situated.

This package defines its own DAOException class. 

## Entities
There are three kinds of entities used in the program:
* **Aggregation Entities** are classes that define the structure
of the aggregated security advisories: Emails; HTMLs and RSS-feeds.
* **dbEntities** are classes used for storing and retrieving the
information in/from the database.
* **Mining Entity** is the special kind of entity that is used
for storing all relevant for data mining information and is actively
used during the text mining.

## Service

Is the core package of this software application. Service package contains all the
classes needed for:
 * aggregation of security advisories from all the sources;
 * connection of the database manipulation classes with user interface, enabling the user
   run queries on the database;
 * normal run of the program, for example: stemming of training data; reading properties file etc.
 * text mining with intent to find and correlate similar advisories and to delete documents which do not represent advisories;
 
In next sub-chapters all these sub-packages will be discussed in details.

### Simple Service
Contains an interface **Service.java** which is visible from the
UI package and the implementation of this interface **SimpleService.java**
which has a task to bind UI command incoming from user and the database.
This class is also used for storing received and mined security advisories
into the database.

### Program Service
Contains three packages with one class in each package:
* Package **ReadPropertiesFile** contains the implementation of config.properties
file reader. It extracts the information from that file.
* Package **SendGreetingEmail** contains a class that sends an email to every new
registered user.
* Package **StemmingOfTrainingData** contains functions and methods that are needed
to conduct stemming procedure of training data for Naive Bayes Classifier. 

### AggregateAlerts

It is a package that is used in the program for aggregating
security alerts from all the sources. It has the following
structure:
* **Email Aggregation Package** contains a class **FetchingIncomingEmails.java**
which uses javax.mail.* imports to gain access to the google email
account with credentials that are specified in the config.properties file.
This class is responsible for fetching incoming emails and for
writing this  emails into a special format (defined in aggregationEntities package).
* **HTML Aggregation Package** contains a class **ParseHTML.java** which is used
for reading web pages using JSOUP library. This class is responsible for
reading document in HTML format and for preliminary modifications of the initial 
content of security alerts.
* **RSS Aggregation Package** contains a class **ParseRSS.java** which is used 
for reading RSS-Feeds using sun.syndication library. This class is responsible for
reading and parsing RSS-Feeds and performs modifications of intitial
content of security alerts.
* **AggregationMainEntrance.java** is the main control point
of the aggregation procedure. This class defines lists of
incoming alerts from all the sources and calls each package
from above to perform aggregation.

For HTML and RSS parts in general holds: if the security advisory source is new
(which means, that it is not already present in the database), then all the available security
alerts will be aggregated; otherwise, if the source in already known (which means, that it is
already present in the database), then only new security advisories will be aggregated. Under new
we understand all the security alerts, that were published since last successful try to
aggregate them.

### Text Mining
Contains two subpackages:
* Package **Correlation** which stores all subpackages and classes needed for
text mining. It contains:
    * **Preprocessing** package: the Preprocessing class that is situated in this package conducts 
    the preprocessing step of the incoming textual data. This step includes: tokenization, removal of emails,
    links and urls, removal of non-letter characters, folding of input to the lower case, removal of stop words, 
    and stemming.
    * **Classification** package: includes two classes: Classification.java and TfIdf.java. The first class creates
    a vector space model from incoming preprocessed documents and conducts the classification step based on
    Naive Bayes Classification algorithm with score indexes computed via TfIdf method.
    * **SimilarityFunctions** package: consists of two classes: CosineSimilarity.java where the cosine similarity (or
    so-called distance) function is implemented; the second class is EuclideanDistance.java where the Euclidean distance
    function is implemented. To learn more about the cosine similarity function follow the link:https://en.wikipedia.org/wiki/Cosine_similarity
    and to learn more about Euclidean distance function follow this link: https://en.wikipedia.org/wiki/Euclidean_distance
    * **Clustering** package: contains the Clustering.java class. The main purpose of this class is to conduct clustering
    using the algorithm described in details in bachelor thesis accompanying this software implementation. This class
    uses results from previous steps and similarity functions defined in previous package.
* Package **SaveVulnerabilities** has a class named SaveVulnerabilitiesToDB.java. The main task of this class is to construct entities
that can be than stored in the database, containing vulnerabilities information considering the clustering results. These entities are: Vulnerability, Description and Product.
The second important function of this class is to obtain products from advisories. Today the solution is simply delete known English words and leave
the words that are not common. These not common words are with probability > 70% specific brands or program names.

## User Interface (UI)
User Interface is organized in 4 packages and one separate class:
* Class **WelcomePage.java** defines first words that user see on the screen. It gives user the opportunity to register or
to login if the user is already registered.
* Package **Authentication_UpdateCredentials** contains two classes:
    * Class **UserAuthentication.java** defines rules for registration and gives the user opportunities to input registration credentials.
    * Class **UpdateUserCredentials.java** defines rules for updating user credentials if the user is already registered.
* Package **UserCabinet** is main point for user to manipulate with the program. This cabinet is only reachable if the user is successfully logged in.
From user cabinet the user can update his/her login credentials, delete his/her account, search in the vulnerabilities database by product names, vulnerabilities details, dates. The user
can also see primitive statistics of vulnerabilities by their sources: EMAIL, RSS, HTML.
* Package **Statistics** has the class with the same name which is intended to run the aggregation query in the database to collect information about
number of vulnerabilities from 3 different source types.

## Resources
The package resources contains three important files:
* **config.properties** is important file that contains the properties
of the program, such as: email account credentials; links to rss-feeds
and html web pages where the advisories come from.

* **nonspamVector** und **spamVector**. For the classification part we use
the so-called nonspamVector and spamVector. Each vector is a dictionary with
words and their weights. The words in each dictionary are specifically
selected, such that the words in nonspamVector are often observed in
non-spam messages, and the words in spamVector are more often observed in
spam vector. The weight of each word is computed by use of naive
bayes classification algorithm.

## Tests
Package Tests include one single test which was used during the
implementation of Text Mining procedure. This testcase shows that
mining step is done successfully for selected advisories.

# Summary and Final Notes
