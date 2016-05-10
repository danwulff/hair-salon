# _Hair Salon_

#### _Daniel Wulff's Hair Salon, May 6th, 2016_

#### By _**Daniel Wulff**_

## Description

_This is an example of java use, specifically SQL database use with one-to-many relationships. This is a  website that gives a user an interface for a hair salon, to create hair stylists and add clients to a specific stylist. The website also allows stylists and clients to be deleted and updated as needed._

## Setup/Installation Requirements

* _Be sure to place all provided files/folders as seen in the repository into a single directory_
* _Make sure the version 2.12 or new of gradle is installed on your system (entering "gradle -v" into the command line should give you the version of the installed gradle system)_
* _Use postgreSQL to create an sql database using the following commands:_
* _CREATE DATABASE hair_salon;_
* _CREATE TABLE stylists(id serial PRIMARY KEY, name varchar);_
* _CREATE TABLE clients(id serial PRIMARY KEY, name varchar, stylist_id int);_
* _(if running gradle tests) CREATE DATABASE hair_salon_test FROM TEMPLATE hair_salon;_
* _To view the webpage, change the command line directory to be the root of the project folder._
* _type "gradle run" into the command line._
* _Open a browser and browse to http://localhost:4567/_

## Known Bugs

_No known issues at this time._

## Support and contact details

_If you find any errors or have trouble viewing the website feel free to contact Daniel: danielwulff.ee@gmail.com_

## Technologies Used

_The website is written in java, by using spark as the web framework. Template files were created in html using bootstrap. Additionally unit testing was done using jUnit, and interface testing was done using FluentLenium. In this way, the website was tested thoroughly during development using behavior driven development patterns (with the help of Gradle to keep track of our dependencies)._

### License

Copyright (c) 2016 Daniel Wulff

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
