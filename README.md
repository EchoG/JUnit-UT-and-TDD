# About This Repo

This series of exercises and assignments implement the account-related API of a social networking application in Java unsing Junit. The API is provided to the users by the class `SocialNetwork`. The business logic is mostly in the lower level class that represents the information assoicated with a user, or member: `Account`. 

You will modify and extend the classes of this project. The goals are:

1) Practice unit testing.
2) Apply basic unit testing principles.
3) Practie TDD when implementing new functionality.
4) Pracitce TDD when refactoring code. 
5) Appreciate the value of unit tests when developing and changing software. 

# Unit Testing and TDD Exercises

1) Download the repo using the [Download ZIP] link. Don't use the [Clone in Desktop] link. Don't try to push your changes back to this repository.  
2) Import the repo into your Eclipse workspace.   
3) Complete tasks in the order given.

## Unit Testing Warmup Exercises: Refactoring Test Code

Review the Given Java Program and Refactor the Test Code

__L1. Rename poorly named tests.__  
__L2. Find overprotective tests, and simplify and remove any redundancy in them.__  
__L3. Refactor replicated test code out.__  
__L4. Split tests that have more than one reason to fail into multiple tests.__  
__L5. Add extra tests if you think they can reveal faults.__  

## Unit Testing Exercises: Implement New Functionality with New Unit Tests

1) Take the top task from the list of tasks.   
2) Implement the task with unit tests.  
3) Repeat for next task.  
4) Submit to WEB-CAT when finished.  
5) Check your score: achieve 100% test coverage and pass all of instructor's supplied references tests (not visible to you)

__Tips:__  
  Make sure you have all the necessary tests in place before starting the next task.  
  Make sure all tests, both the new ones written by you and those provided with the repo, pass.  
  Run all tests frequently, each time you change production code or finish a new test.  
  Make sure a test doesn’t pass by accident.  

### Tasks for Unit Testing Exercises

Each task is specified by an action and an example of how to carry out the action in the code. 
These variables are used in the examples:
```
sn : SocialNetwork  
me, her : Account  
myUserName : String  
herUserName : String  
```
  
`her` is the originator of a friend request: `her` invokes the `befriend` method of `me` with `her` as a paramater:  
```
her -> befriend(her) -> me
```  

`me` is the target of a friend request: `me` invokes the `accepted` method of `her` with `me` as a parameter to inform `her` that `me` just accepted a friend request from `her`:  
```
me -> accepted(me) -> her
```  

IMPORTANT: Remember how message passing in objects works: if a `source` object  sends a `message()` to a `target` object (which I denote above as `source -> message() -> target`), then the `source` calls the `mesage()` method of the `target` and not the other way around. Thus the `source` object executes the statement `target.message()` in its code. 

__A1. List pending friend requests `me` had sent to others:__  
```
me.whoDidIAskToBefriend() => returns Set<String>             <- implement this method  
```
__A2. Accept all friend requests that are pending a response from `me`:__  
```
sn.acceptAllFriendRequestsTo(me)                             <- implement this method  
``` 
__A3. Reject a friend request. Tell `her` that `me` has just rejected `her` friend request:__  
```
her.rejected(me)                                             <- implement this method  
sn.rejectFriendRequestFrom(herUserName, me)                  <- implement this method  
```
__A4. Reject all friend requests sent to `me`:__  
```
sn.rejectAllFriendRequestsTo(me)                             <- implement this method 
```
__A5. Automatically accept all new friend requests sent to `me`:__  
```
me.autoAccept()                                              <- implement this method 
sn.autoAcceptFriendRequestsTo(me)                            <- implement this method 
```
*From now on, whenever somebody sends me a friend request, my account will automatically accept it.*  

__A6. Unfriend an existing friend. Tell `her` that `me` has just unfriended `her`:__   
```
her.unfriend(me)                                             <- implement this method 
sn.sendUnfriendRequestTo(herUserName, me)                    <- implement this method 
```
__A7. Remove `me` from social network:__  
```
sn.leave(me)                                                 <- implement this method 
```
### STOP HERE! Submit your code. See instructions at the end. 

## TDD Exercises (TDD Lab - will be done in class)

The following exercises are part of the TDD Lab. Do not try them yet. You'll tackle them in the class. 

You will refactor the application to implement a new interface: `ISocialNetwork`  (supplied). You will accomplish this by applying TDD. The task will require changing both production and test code. You may add new production and test classes as you see fit. Remember: Always try to lead by tests (add or modify existing tests). Then follow compilation errors, warnings, and IDE suggestions to correct these problems. Then follow through by changing production code to complete the loop. 

__B1. Make `SocialNetwork` implement the given interface.__  

__B2. Implement the `public Account login(member Account)` method.__  

Change behavior of the API so that after joining the social network, a user must login to with her `Account` handle to be able to access the remaining public interface of `SocialNetwork` (except `join`). If the user can login, the method returns a valid account handle back. 

This protocol will ensure that no other public method other than `login` needs a parameter of type `Account`. Every instance of `SocialNetwork` can have only one user logged in at a time. You will notice that this refactoring will considerably simplify the understandability of the API: once a user is logged in, all operations of `SocialNetwork` are issued by the logged-in member (user) to make friends, list members, test membership of others, accept friend requests, etc. 

__B3. Implement the `public void boolean hasMember(String userName)` method.__  

This method returns true if a member with user name `userName` has previously joined the social network.

__B4. Implement the `public void acceptFriendRequestsExplicitly()` method.__  

This method cancels autoacceptance for future friend requests, and causes the logged-in member having to explicity accept any future friend requests.

__B5. Implement the `public void block(String userName)` method.__  

This method blocks the logged-in member from being visible to the member with user name `userName`. The blocked member can't list or befriend the logged-in member. 

__B6. Implement the `public void unblock(String userName)` method.__  

This method unblocks an existing member (with user name `userName`) that was blocked by the logged-in user. The logged-in user will become visible to the previously blocked member and the two can find and befriend each other once again. __   

__B7. Implement the `public Collection<String> recommendFriends()` method.__  

This method returns a list of members who are friends with at least two friends of the logged-in user and who are not already a friend of the logged-in user.   

__B8. Make sure blocking a member terminates all friendship-related relationships between two members.__  

If  the two are already friends or have pending friend requests for each other, these relatioships are cancelled if one member blocks the other. 

__B9. Fill out the one-page lab report in `LabReport.txt.`__ 

The lab report should not exceed a page. The lab report template is in the `extras` folder. 

Now submit your code and lab report to WEB-CAT.  Submit a copy of your lab report (no formatting required) to Blackboard. 

All your unit tests should pass, hopefully.    

Good luck!
	
## How to Submit Your Solution to WEB-CAT

1) Export your code to a JAR file.  
_Select the Java project to export from Package Explorer._  
_Select File->Export._  
_Uncheck all files that begin with a "." (.gitignore, .project, .classpath, ...)._  
_Select the subfolders `src`, `test`, and `lab` in the project folder._
_Check only "Export Java source files and resources. Don't need class files and others._  
_Don't select "Compress the contents of jar file."_
_Name your JAR file using this convention: `SN-YourAndrewID.jar.` Hit Next._  
_Check "Create source folder structure" and hit Finish._  
2) Go to: [https://web-cat.cs.vt.edu](https://web-cat.cs.vt.edu).    
3) Select "Carnegie Mellon University Silicon Valley" from pull down menu.  
4) Login to your WEB-CAT account.  
5) Check under “Assignments Accepting Submissions in Your Courses”.   
6) Submit your JAR file for: "Social Network: Unit Testing Assignment".     
7) Wait for the results. The system will auto-grade your assignment. 
_Your tests will be run. Failing test may lower your grade._  
_Your code may be checked against reference correctness or refernce tests supplied by the instructor._ 
_Failing correctness tests may lower your score: you'll be able to see which tests fail._  
_The coverage of your code will be analyzed._  
_Low code coverage may lower your score: you'll be able to see which parts of the code are not covered._  
8) Inspect your score and results.  
9) If you're not satisfied by your score, fix your code, write more unit tests, refactor, and resubmit. You are allowed as many submissions as you like. 

Good luck!





