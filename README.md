H.Dip. in Computer Science (Software Development) 

Software Design & Data Structures 2023 ASSIGNMENT 

Author: Tanya Costello 



Sentiment Analysis Java Application using Virtual Threads

This Java application processes a set of tweets using virtual threads and performs sentiment analysis based on a lexicon file. The application determines the overall sentiment of provided tweets (100-twitter-users) by analyzing them against supplied lexicons (files called afinn.txt, mpqa.txt, bingliu.txt). The lexicon contains a set of words and their associated polarity. The application uses the Score and Sum (SaS) scoring system, where the sentiment value of the tweets is calculated by summing up the scores of individual words.

Features

•	Menu-Driven Interface: User-friendly menu to start the analysis process.

•	Processing Tweets: Accepts a file of tweets from various Twitter/X users and computes their overall sentiment.

•	Sentiment Analysis: Utilizes 'user-chosen' lexicon to perform sentiment analysis.

•	Virtual Threads: Uses Java's virtual threads for efficient concurrent processing.


Functionality

The application supports multiple lexicons and tweets. Currently, it works with lexicons where the polarity values are single-digit integers, due to logic limitations that will be addressed in future updates.


Classes

Runner:

•	Contains the main method to execute the program.

•	Initiates the application by displaying the main menu and initializing the required components to start the text processing.

MainMenu:

• Displays the main menu options to the console and solicits user input, delegates tasks to respective classes/methods for execution

AbstractParser:

•	An abstract class that provides functionality to parse lexicons and populate the appropriate concurrent data structure (i.e., ConcurrentHashMap) for use with virtual threads.

ParserConcrete:

•	Extends AbstractParser and processes Twitter files, populating thread-safe data structures.

Goable:

•	An interface that provides a single abstract method.


Getting Started



Prerequisites

•	Java 17 or higher

•	IDE with support for Java (e.g., Eclipse, IntelliJ IDEA)

Running the Application:
1.	Clone the repository:
git clone https://github.com/Tanyahoo/virtual.git

2.	Navigate to the project directory:
cd virtual

3.	Compile: javac -d bin src/ie/atu/sw/*.java
	
4.	Run the application: java -cp ./bin ie.atu.sw.Runner 
	


Usage:

1.	Start the application. You will be presented with a menu.
 
2.	Input the path to the lexicon(s) you wish to use (choose between the provided files called afinn.txt, mpqa.txt, bingliu.txt)
	
3.	Input the path to the file containing tweets (100-twitter-users).
	
4.	The application will process the tweets and display the overall sentiment based on the chosen lexicon.
   

Acknowledgments:

Thank you to my University lecturer JH for providing invaluable help and feedback.


