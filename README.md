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

### Simple Service

### Program Service

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

## User Interface (UI)

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

# Summary and Final Notes