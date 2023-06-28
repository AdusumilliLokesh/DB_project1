# DB_Project-1
Repo for CSCI 4370/6370 Project 1

CSCI X370: Database Management
Summer 2023

Authors : Lokesh Adusumilli (811314401) ; Prudhvi Arun Chekka(811072949) ; Lalithya sajja(811167189) ; Dahun Im (811009835)

The 5 relational operations are implemented in the "Table.java". They are

- Select
- Project
- Union
- Minus
- Join (equi join, theta join, natural join)

note: while running the theta join make please sure that blank space(" ") exists between the operators and the operand. "attribute1 <op> attribute2"
How the project is going to be tested:
1. The testing will start in MovieDB.java which is the entry point for the project that will be used for testing.
2. We have performed unit testing using J-unit to test the individual relational operators.
3. The test cases are written in "JunitTest.java" for all the relational operations.
4. All the test cases are validated with the expected output tables. 
5. To start the unit testing test cases, we must run the "JunitTest.java". (before running the file, please make sure that all the necessary packages are available)
Note: make sure that "JunitTest.java" exists in the source folder.
