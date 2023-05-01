# dogtag-app

Dustin Gardner
CS-389
4/28/2023

Dog Tag Buddies 
Web Application Project

The project was created as an example of an application a non-profit, called Dog Tab Buddies could utilize. The non-profit helps train the veteran and the dog how to become a service dog. The web application was developed to support training logs that each veteran normally enters manually on paper.  The application makes entering training for the dog quicker and more effectively.  This creates an environment for the trainer to likely to record their training immediately and correctly.

The web application has initially two options for the user which is login and register.  Register is the first step if the user does not exist in the database.  Once the information is correctly entered and saved into the database the user can log in.  The login page allows the user to enter their previously saved login information that is in the database or the new registration information that was save. 

When the user logs in for the first time, they are directed to the profile setup page. This page has the user enter more detailed name and phone number to be associated with their user account. After the user enters acceptable information, it is saved in the database under the UserProfile model.  Once the user profile is saved, they are directed to the training log page where they can start entering information about a given training. If the user already has a profile setup, they will be directed to the training log page as soon as they login.

The training log page is the main purpose of the application and is why it was built.  The veteran can enter a date, location, training, and comments. When the appropriate information has been entered the training logs will be saved and are displayed immediately for them to view.  The user can enter additional training events or choose to log out effectively ended the session for the application. All the information being saved to the database is persistence and will be retained for when the user logs back in.

## Setting up the required database: MariaDB Server

### Install:
MariaDB is the current database being used in the application but this can be changed with minor changes to the dependency and in the application.properties.

MariaDB can be downloaded for mac using homebrew.  This instructions for this are here: https://mariadb.com/kb/en/installing-mariadb-on-macos-using-homebrew/
Note for mac I had to start the server every time I started my computer. This is done with mysql.server start in the command prompt. The program will not start if the server is not running.

MariaDB can be downloaded for Windows and Linux which includes a GUI interface.  Download here: https://mariadb.org/download/?t=mariadb&p=mariadb&r=11.1.0 and select the OS.

### Setting up:
The GUI allows easy setup for the database:
These are the current settings but can be changed in the code to match the database being setup:
url://localhost:3306/dogtag
username=user
password=Password1
Command Line Setup:
If not started: mysql.server start
Log in to mariadb with root:
 mysql -u root
Creating the database:

mysql> CREATE DATABASE dogtag;

Create User:

mysql> CREATE USER 'user'@'localhost' IDENTIFIED BY 'Password1';

mysql> CREATE USER 'user'@'%'  IDENTIFIED BY 'Password1';

mysql> GRANT ALL PRIVILEGES ON dogtag.* TO 'user'@'localhost' WITH GRANT OPTION;

mysql> GRANT ALL PRIVILEGES ON dogtag.* TO 'user'@'%'  WITH GRANT OPTION;

Exit:

mysql> exit

GitHub application cloning:

 	git clone https://github.com/dustgard/dogtag-app


## Starting the application Command Prompt:

To start the application a command prompt needs to be open in the root directory for the program and will look like this where you saved the project.

 

 

 

	Note: If test units wish to be ran: replace the above code with ./gradlew test

 
 
	Note: Do not close window until the application needs to be stopped.

### Starting the application Command Prompt:

Open the IDE and before running the application allow it to build and make sure that the selected Class is the DogtagAppApplication:


 

Then press the play button:

 


 

### Stopping Application:

	Command Prompt:
	Close the command prompt window and agree to terminate.

	IDE:
	Click the red square stop button.

## Checking DATABASE:

<img width="471" alt="Screen Shot 2023-05-01 at 6 18 34 AM" src="https://user-images.githubusercontent.com/77804262/235452099-3a05800e-f63d-406d-9b40-7da944881400.png">

