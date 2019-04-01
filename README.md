Spencer Stock

# Android_Threads

## Introduction

This app will have you take a large encrypted string and decrypt it with a simple cypher. I'll walk you through setting the task up on a separate thread, however, I'll leave the algorithm for decoding the cypher up to you.

## Instructions

### Part 1 - Front End
1. Copy the provided text block [here](https://github.com/LambdaSchool/Android_Threads/blob/master/encoded_content.txt) into your project as a string resource.
2. Build a main layout with 
	a. a large text view inside of a scroll view (set the content to be the string resource you just created)
	b. an edit text field (to input a signed value for the size and direction of the shift)
	c. a button
	d. a progress bar (keep the default progress bar style for now and set the visibility to `gone`).

### Part 2 - Build your Algorithm
1. Create a click listener for your button
2. pull the content from your `TextView` as a string and from the `EditText` field as an int. 
3. Write the algorithm to decrypt the string
> A shift cypher is one where each character is "shifted" to the right a number of times. So a letter 'A' shifted once would be 'B' and 'Z' shifted twice would also be 'B'. To decode this, you have to shift the encrypted character to the left a number of times.
> Remember characters in Java are stored as unicode codes. These can be manipulated like integers. A table showing their values can be found  [here](https://www.rapidtables.com/code/text/unicode-characters.html)
> A few things that may trip you up:
> 	All the uppercase and lowercase letters are found in two separate groups. After incrementing your character, you need to make sure that it has not moved out of the range of those groups, otherwise, you'll get a lot of weird symbols in your decrypted text
> 	Only manipulate the charater if it falls into one of these ranges. Only shift letters.
> 	You can tell how far to shift the characters by looking for common word or phrase structures (cough! URL cough!) and then counting back to get to what you expect. [here](http://rumkin.com/tools/cipher/caesar.php) is an online tool to play with shift cyphers


4. The first and last lines of your listener should display and hide your progressbar respectively
5. Test your algorithm to make sure it works.
> Android will yell at you about doing too much in the main thread so this is where we'll pull it apart into an `AsyncTask`


5. Try and manipulate the UI of your test device while your algorithm is running.
> If the algorithm completes too quickly for you to manipulate the UI, congratulations! For the purposes of this project and the limitations of the content we've covered so far, use String concatenation instead of StringBuilder when assembling the new string.


### Part 3 - Multi-Threading
Now, you've seen how long this process takes and what happens when you run it on the main thread, let's run it on a background thread.
1. Create a new inner class (class inside of another class, your main activity in this case) which extends `AsyncTask`
	a. When extending `AsyncTask`, we pass data types for the type of parameter it accepts, how it reports progress, and how the result is passed
2. Navigate to your "Generate" menu (right-click, alt-insert...)
3. Insert Override methods for onPreExecute, onPostExecute, onProgressUpdate, onCancelled, doInBackground. This will generate methods with the proper signatures for the types you gave in the class signature

Now we'll move the majority of the tasks from the listener to this new class.
4. In `onPreExecute`, perform any tasks you would normally do before the thread spins off (make the progress bar visible, etc.)
    > You will still need to pull and parse the content from the `TextView` in your click listener as you will need it to call the async task

5. In `doInBackground`, perform the time consuming portion of your task. You won't be able to access any views in the UI and you should only use the parameter that you passed in to it. This method should work independently of everything except `onProgressUpdate`, but we'll deal with that later, move your algorithm code here. When you complete your task, return the result, this will be passed to the `onPostExecute`
6. In `onPostExecute`, perform tasks you would do after the thread completes (hide the progress bar and update the text in the field)
	a. This method will accept a parameter which matches what you put in the class signature and returned in `doInBackground`. This value will be what you returned in the `doinBackground` method.
7. At the end of your listener, add a line to construct a new instance of this class, call the execute method on it, passing in the data you need to work on (the string from the textview). Build and test your app. Play with the UI and see that it is now responsive.

8. In `onProgressUpdate`, you can interact with the UI to update the user on your progress. In this case, you can set the progress on the `progressBar`
	a. In your xml layout file, add the line `style="@android:style/Widget.DeviceDefault.Light.ProgressBar.Horizontal"` to the `ProgressBar`, this will convert the progress bar from a spinning circle to a percentage bar
	b. In your listener, before calling the execute method on this class, call `setMax` on the `ProgressBar` to set the maximum value for the progress bar
	> I would set this to be the total number of letters to be shifted.
	
	c. In your algorithm, decide when and how you want to update the user on your progress
	> In this case, I recommend tracking the number of letters which have been shifted and passing it to the `updateProgress` method using `publishProgress`
	
	d. finally in this method, call `progressBar.serProgress(value[0])` to update the progress bar.
	e. Test the app to make sure the progress bar is updating.

9. In `onCancelled` will clean up any thing you need to if the task is cancelled
	a. because this class is an inner class of an `Activity`, that activity can't end until this thread has completed so we'll need to implement a cancel feature in our background thread.
	b. periodically in your thread, check the `isCancelled` method. If it is true, quit the process (return statement), close all open resources (you shouldn't have any, these would be any open files or system tools), and perform other tasks which would have been performed in the `onPostsExecute` method
	c. Override the `onStop` method. Make sure the task data member isn't null and then call `cancel` on it.
	d. set breakpoints or log statements in your `Activity.onStop` and `asyncTask.onCancelled`methods and test the app to make sure the thread is cancelled.
