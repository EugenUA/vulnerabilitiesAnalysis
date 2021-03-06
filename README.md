# Vulnerabilities Aggregation and Analysis Software
Implemented as part of Bachelor thesis "Aggregation and Analysis of IT Security Vulnerabilities"  
Technical University of Vienna; INSO; Establishing Security; (2017 - 2018).    
Author: Eugen Gruzdev  
Date: 01.03.2018 

Updated: 20.05.2018

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
the logic that is needed to present the results of
computations made in service layer to the user and to search concrete data
in the database.

There are also some separate folders that contain important
program parts:
* Entities folder contains all the logical entities the program
needs to operate with, for example database: every table in the
database is represented by entity classes. Objects of these
classes are then entries of the table.
* vlnAggr class is the main control point of the whole program.
It sets the rules of user-program and inter-modules cooperation.
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
This package contains all the code needed for manipulation of all kinds with
the database. It defines the sub-package **interfaces**
which contain the interfaces that are visible from other layers.
In the **db** package the code for creation of tables is situated
and in the **SQLiteWorkingPackage** the classes with basic CRUD
operations for each table are placed.

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
 * connection of the database manipulation classes with user interface, enabling the user to
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
file reader. It extracts the information from that file. To be able to read html and rss sources
it also reads rss.txt and html.txt files. They contain the links to the respective sources.
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
content of security alerts. For now only the sources specified in htmlSources.txt are accepted. Because
of the fact that different publishers disclose advisories in different ways (i.e.: as links to HTML page with concrete advisory,
as plain text in HTML tags, as text without HTML tags, as PGP-Signed Messages, etc.) each new source must be configured in
**ParseHTML.java** individually.
* **RSS Aggregation Package** contains a class **ParseRSS.java** which is used 
for reading RSS-Feeds using sun.syndication library. This class is responsible for
reading and parsing RSS-Feeds and performs modifications of initial
content of security alerts. Only the RSSs where the information about vulnerabilities is provided in
plain text can now be aggregated. If the RSS-Feed contains links to vulnerabilities description, then some changes
must be made to **ParseRSS.java**
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
    function is implemented. To learn more about the cosine similarity function follow the link:https://en.wikipedia.org/wiki/Cosine_similarity (visited on: 20.05.2018)
    and to learn more about Euclidean distance function follow this link: https://en.wikipedia.org/wiki/Euclidean_distance (visited on: 20.05.2018)
    Attention! Per default the program uses **Cosine Similarity function**, but eventually it can also accept another functions. To change the
    similarity function there is a need to modify line 94 of **Clustering.java** class.
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

* **rss.txt** and **html.txt** are two text files that store current links to the
vulnerabilities sources incoming from RSS and from HTML respectively. 

* **nonspamVector** und **spamVector**. For the classification part we use
the so-called nonspamVector and spamVector. Each vector is a dictionary with
words and their weights. The words in each dictionary are specifically
selected, such that the words in nonspamVector are often observed in
non-spam messages, and the words in spamVector are more often observed in
spam vector. The weight of each word is computed by use of naive
bayes classification algorithm. Link to the original source of spam/nonspam Vectors:
http://openclassroom.stanford.edu/MainFolder/DocumentPage.php?course=MachineLearning&doc=exercises/ex6/ex6.html (visited on: 20.05.2018)
Attention! The spam and nonspam Vectors used in this work are not 100% equal with original files. Vectors
used in this work are modified to fit the topic of this work and to classify security relevant document more precisely.

## Tests
Package Tests include one single test which was used during the
implementation of Text Mining procedure. This testcase shows that
mining step is done successfully for selected advisories.

# How to run a program from console?
To run a program one needs to install maven. The detailed explanation how to do this is available here:
https://maven.apache.org/install.html (visited on: 20.05.2018)

After the successful installation make the following steps:
1. Unzip the archive with the vulnerability aggregation and analysis software and store "vulnerabilitiesAnalysis" package somewhere on your PC;
2. Open your command line terminal;
3. In opened terminal navigate to "vulnerabilitiesAnalysis" package;
4. Type the following line and press Enter: **mvn compile**;
5. Wait until you see **BUILD SUCCESS** words returning after the compilation;
6. Type the following line and press Enter: **mvn exec:java -Dexec.mainClass=vlnAggr**;
7. After some INFOs and/or WARNINGs you'll be able to login into the program.
8. DEFAULT CREDENTIALS: Login: **Eugen**; Password: **1234**;
9. To quit the program, press "q" or "Q";
10. After finishing your work close the terminal.

# How to run a program from IDE?
To run a program from IDE, please notice, that to be able to enter the credentials from IDE console you need to change following lines:
````
//String password = scanner.nextLine();      // <--- if you run in IDE
  char[] pss = console.readPassword();       // <--- if you run in console
  String password = new String(pss);         // <--- if you run in console
````
You need to comment lines 2 and 3 and to uncomment line 1 on the following positions in code:
* In class **WelcomePage.java** lines: 83-85;
* In class **UpdateUserCredentials.java** lines: 90-92; 94-96;
* In class **UserAuthentication.java** lines: 38-40; 42-44;

To run the program in Eclipse/NetBeans/Intellij IDEs you need:
1. Unzip the archive with the vulnerability aggregation and analysis software and store "vulnerabilitiesAnalysis" package somewhere on your PC;
2. Open the IDE;
3. In menu tab **File** select **Open Existing Project**/**Open**;
4. Navigate to the "vulnerabilitiesAnalysis" package;
5. Select this package and confirm your selection; 
6. Make changes to the classes discussed above;
7. Import maven changes, if the IDE does not make it automatically;
8. Run the project's main file called **vlnAggr** using the IDE's run button;
9. To quit the program, press "q" or "Q";
10. After finishing your work close the IDE.

# Summary and Final Notes
To sum up, it has succeeded to write a prototype of software application that is
able to:
* aggregate security advisories from different sources and in different formats;
* convert security advisories to one format
* analyse incoming security advisories for the sake of being identical or similar;
* filter out textual documents that are not devoted to disclosure of vulnerabilities;
* filter out textual documents that are not security advisories
* automatically retrieve form textual data information about products, where vulnerabilities were found;
* save this information to the database in consistent way;
* query the database with intent of searching specific product, vulnerability or to get statistics.

It is also important to speak about limitations that has this software prototype:
* Text analysers maintain security advisories written only in English language;
* Classification step is based on Naive Bayes Classifier which gives the correct results only in 70% of cases;
* Software does not support correlation of already present in the database advisories with new aggregated advisories. However, the description table saves preprocessed short and long descriptions of each security advisory in the database;  
* Aggregation of security alerts incoming from HTML Web Pages is strongly oriented on default links given in properties file. Aggregation from
another HTML sources may need changes in the implementation because of the nature of HTML Web Pages;
* Retrieval of product and brand names is based on deleting of known English words from texts and not on information retrieval algorithms in their pure sense;
* Database and analytics packages do not support non textual data;
* User interface is poor command line interface which in many cases is not optimal for software of such kind;
* Statistics package provides very restricted statistics information and is designed to show that the statistics is possible to compute.