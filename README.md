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
then the rule.

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


# Technology Stack

# Architecture Description

## DAO

## Entities

### Aggregation Entities

### DatabaseEntities

### Mining Entities

## Service

### Simple Service

### Program Service

### AggregateAlerts

### Text Mining

## User Interface (UI)

## Resources

## Tests

# Summary and Final Notes