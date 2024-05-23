H.Dip. in Computer Science (Software Development) 

Software Design & Data Structures 2023 ASSIGNMENT 

Author: Tanya Costello 

Sentiment Analysis Java API using Virtual Threads


This Java API is designed to process a set of tweets using virtual threads and perform sentiment 
analysis based on a lexicon file. The API determines the overall sentiment of the text file by 
analyzing the tweets using predefined lexicons. The lexicon contains a set of words and an 
associated polarity. The API uses the Score and Sum scoring system (SaS), where the 
value/polarity of the words in the twitter file are calculated by summing up the words scores.


To Run:



• Menu: User is presented with a menu, requested input to begin the analysis process.

• Processing Tweets: The API accepts a single file of tweets from various Twitter/X users. 
 Each file is then computed against a lexicon to calculate the overall sentiment.

• Sentiment Analysis: Utilizes a user chosen lexicon to perform sentiment analysis on the 
 tweets.

• Virtual Threads: The processing of tweets and sentiment analysis is handled using Java's 
 virtual threads for efficient concurrency.


Functionality:



The API supports the use of multiple lexicons and tweets. However, it must be said that the 
functionality of this API is presently based on the use of lexicons that have a polarity in single 
digit integers only, due to logic shortcomings (due to be rectified).


Classes: 



Runner: Contain the main method which acts as an entry point into the application.

MainMenu: Displays the user menu which delegates to each class/method for further execution 

AbstractParser: An abstract class that provides functionality to parse in lexicons and populate the 
appropriate concurrent data structure for use with virtual threads, i.e., concurrent HashMap.

ParserConcrete: Extends AbstractParser. Processes twitter files, populates thread safe data structure.

Goable: An interface that provides a single abstract method.


