# USAA_Exercise
 
Question 1:
Create a data model called Challenge that will hold 3 properties:
- question: The question that will be asked.
- answer:  The answer to the challenge question.
- correct: A boolean that indicates whether the provided answer is correct. 
 
Question 2:
Write a function that accepts a collection of Challenge objects as the input
     - Return true/false if the collection is passing (number of correct responses is > 60.0%).
     - Return nil if there are no challenges in the collection input.
     - Note:  You do not need to check the answers.  Assume that the Challenge has already been
              checked elsewhere in the program.
 
Question 3:
Write tests that verify the correct operation of the passing function from Question 2. As a reminder, you may use any libraries or frameworks you would like

In order to complete this exercise I used some Android Jetpack libraries:
 * Room
 * LiveData
 * Lifecycle
 * Coroutines
 * DataBinding

For Testing I use:
 * Robolectric
 * Hamcrest
 * JUnit4


The project uses MVVM as design pattern. The main activity is just use to display the result from 
the ViewModel, view model uses Coroutine Scope to launch the repository request, avoiding blocking the UI.
Repository contains LocalDataSource and RemoteDataSource. if the local data source is empty we try to 
obtain the info from the API. In this example we are just mocking that API using a local file and
waiting 3 seconds before return.

The method used to decide if the response is valid or not is MainViewModel.isCollectionPassed() this will receive 
a valid arrayListOf<Challenge> and return true/false base in the required validation. For real code I'm using 
an extension function.

The test class is launch for MainViewModel and is just testing the behavior of MainViewModel.isCollectionPassed(),
using dummy data to simulate the response from the server.