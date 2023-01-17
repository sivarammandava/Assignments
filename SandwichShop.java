//MyPostalCustomer.java
package javaapplication39;
//Import the needed files
import java.util.*;
import java.util.concurrent.Semaphore;
//Class for postal customer
public class MyPostalCustomer implements Runnable
{
//Declare the needed variables
public int NoOfCust;
public int myPostCustWrk;
public int allotPostalWorker;
//Constructor that initializes the NoOfCust
MyPostalCustomer(int NoOfCust)
{
//Assign the NoOfCust to this NoOfCust
this.NoOfCust = NoOfCust;
}
//Running the postal customer thread
@Override
public void run()
{
//Call createPostalCustomer() to create a customer
createPostalCustomer();
//Wait the semaphore maximumVolume
wait(MyPostalOffice.maximumVolume);
//Call enterPostalCustomer() to enter into the postal office
enterPostalCustomer();
//Wait the semaphore NoOfDeskWorkers
wait(MyPostalOffice.NoOfDeskWorkers);
//Wait the semaphore myMutex
wait(MyPostalOffice.myMutex);
//call addPostalCustomer() to add the postal customer
addPostalCustomer(NoOfCust);
//Now signal the NoOfReadyCust
signal(MyPostalOffice.NoOfReadyCust);
//Also signal the myMutex
signal(MyPostalOffice.myMutex);
//Now wait the semaPost
wait(MyPostalOffice.semaPost);
//call postalCustomerRequest() to ask the request
postalCustomerRequest();
//Signal the postal worker thread
signal(MyPostalOffice.semaPost2);
//now add the current postal customer to the completed list
wait(MyPostalOffice.semaCompleted[NoOfCust]);
//call completeWork() to complete the work
completeWork();
//call leavePostOffice()
leavePostalOffice();
//Signal the NoOfLeaveDeskWorkers
signal(MyPostalOffice.NoOfLeaveDeskWorkers);
//Also signal the maximumVolume
signal(MyPostalOffice.maximumVolume);
//Now increment the myCnt by 1
MyPostalOffice.myCnt++;
}
//Method that displays the leaving postal customer
void leavePostalOffice()
{
//Display the leaving postal customer
System.out.println("Customer " + NoOfCust + " leaves post office");
}
//Method displays the completed work of the postal customer
void completeWork()
{
//Display the completed work
System.out.println("Customer " + NoOfCust + " completed " + AssignWorksCustomer(myPostCustWrk));
}
//Return the task
private String AssignWorksCustomer(int myPostCustWrk)
{
//Create a retMsg
String retMsg = new String();
//Switch
switch (myPostCustWrk)
{
//case 1 for buying stamps
case 1:
//set the retMsg to buying stamps
retMsg = "buying stamps";
//Exit
break;
//case 2 for mailing a letter
case 2:
//set the retMsg to mailing a letter
retMsg = "mailing a letter";
//Exit
break;
//case 3 for mailing a package
case 3:
//set the retMsg to mailing a package
retMsg = "mailing a package";
//Exit
break;
}
//return the retMsg
return retMsg;
}
/*Print customer requesting a postal worker for some work and adding the work in the worker's customerQue*/
void postalCustomerRequest() {
System.out.println("Customer " + NoOfCust + " asks postal worker " + allotPostalWorker + " to " + myPostCustWrkname(myPostCustWrk));
MyPostalOffice.myWorkerObject[allotPostalWorker].myPostWrkQue.add(myPostCustWrk);
}
/*To print the correct output*/
private String myPostCustWrkname(int myPostCustWrk) {
String message = new String();
switch (myPostCustWrk) {
case 1:
message = "buy stamps";
break;
case 2:
message = "mail a letter";
break;
case 3:
message = "mail a package";
break;
}
return message;
}
//method adds the customer to the queue
void addPostalCustomer(int myPostCustId)
{
//Add the myPostCustId to customerQue
MyPostalOffice.customerQue.add(myPostCustId);
}
//Method creates a postal customer
void createPostalCustomer()
{
//Display message
System.out.println("Customer " + NoOfCust + " created");
//Create random
Random kk = new Random();
//create ll
int ll = 1;
//create hh
int hh = 4;
//Get random work
this.myPostCustWrk = kk.nextInt(hh - ll) + ll;
}
//method acquires the given mySemaphore
void wait(Semaphore mySemaphore)
{
//try
try {
//Acquire
mySemaphore.acquire();
}
//catch()
catch (Exception exxp) {
}
}
//Method prints the entering message
void enterPostalCustomer()
{
//Display the message
System.out.println("Customer " + NoOfCust + " enters shop");
}
//Method releases the mySemaphore
void signal(Semaphore mySemaphore)
{
//Release mySemaphore
mySemaphore.release();
}
}
//MyPostalWorker.java
package javaapplication39;
//import from library
import java.util.*;
import java.util.concurrent.Semaphore;
public class MyPostalWorker implements Runnable {
//Create variables
public int myPostWorkNum;
public static Queue<Integer> myPostWrkQue = new LinkedList<>();
private int myNxtPostCust;
//constructor
MyPostalWorker(int myPostWorkNum) {
//Set the myPostWorkNum
this.myPostWorkNum = myPostWorkNum;
}
//Running the worker
@Override
public void run()
{
//Create a postal worker
createPostalWorker();
//Infinite loop
while (true)
{
//wait the NoOfReadyCust
wait(MyPostalOffice.NoOfReadyCust);
//wait the mymutex
wait(MyPostalOffice.myMutex);
//Call releasePostCustomer()
releasePostCustomer();
//Signal the myMutex
signal(MyPostalOffice.myMutex);
//Signal the semaPost
signal(MyPostalOffice.semaPost);
//wait the semaPost2
wait(MyPostalOffice.semaPost2);
//call servicePostalCustomer()
servicePostalCustomer();
//Signal the semaCompleted
signal(MyPostalOffice.semaCompleted[myNxtPostCust]);
//wait the NoOfLeaveDeskWorkers
wait(MyPostalOffice.NoOfLeaveDeskWorkers);
//Signal the NoOfDeskWorkers
signal(MyPostalOffice.NoOfDeskWorkers);
}
}
//Method releases the customer from the queue
void releasePostCustomer()
{
//remove postal customer
myNxtPostCust = MyPostalOffice.customerQue.remove();
//print the postal customer
System.out.println("Postal worker " + myPostWorkNum + " serving Customer " + myNxtPostCust);
//Assign the work
MyPostalOffice.myCustomerObject[myNxtPostCust].allotPostalWorker = myPostWorkNum;
}
//Method prints the created postal worker
void createPostalWorker()
{
//display postal worker
System.out.println("Postal Worker " + myPostWorkNum + " created");
}
//signal the given mySemaphore
void signal(Semaphore mySemaphore) {
//release the mySemaphore
mySemaphore.release();
}
//method service the postal customer
void servicePostalCustomer()
{
//create postCustomerTask
int postCustomerTask;
postCustomerTask = myPostWrkQue.remove();
switch (postCustomerTask) {
//for postal worker 1
case 1:
try {
Thread.sleep(1000);                
} catch (Exception exxp) {
Thread.currentThread().interrupt();
}
System.out.println("Postal worker " + myPostWorkNum + " Completed serving customer " + myNxtPostCust);
break;
//for postal worker 2
case 2:
try {
Thread.sleep(1500);                
} catch (Exception exxp) {
Thread.currentThread().interrupt();
}
System.out.println("Postal worker " + myPostWorkNum + " Completed serving customer " + myNxtPostCust);
break;
//For postal worker 3
case 3:
wait(MyPostalOffice.postalScale);
System.out.println("Scales in use by postal worker " + myPostWorkNum);
//try
try {
Thread.sleep(2000);                
} catch (Exception exxp) {
Thread.currentThread().interrupt();
}
System.out.println("Scales released by postal worker " + myPostWorkNum);
signal(MyPostalOffice.postalScale);
System.out.println("Postal worker " + myPostWorkNum + " semaCompleted serving customer " + myNxtPostCust);
break;
}
}
//method acquires the mySemaphore
void wait(Semaphore mySemaphore)
{
//try
try {
//Acquire
mySemaphore.acquire();
}
//catch
catch (Exception exxp) {
}
}
}
//MyPostalOffice.java
package javaapplication39;
//Import the needed files
import java.util.*;
import java.util.concurrent.Semaphore;
//Create a class to denote the postal office
public class MyPostalOffice {
//Semaphores needed for the postal office
//Create a semaphore maximumVolume to indicate the maximum allowed customer for the service
public static Semaphore maximumVolume = new Semaphore(10, true);
//Create a semaphore to denote the ready customers
public static Semaphore NoOfReadyCust = new Semaphore(0, true);
//Create a semaphore to denote the postal workers
public static Semaphore NoOfDeskWorkers = new Semaphore(3, true);
//Create a semaphore to denote the mutex
public static Semaphore myMutex = new Semaphore(1, true);
//Create a semaphore to denote the coordinates
public static Semaphore semaPost = new Semaphore(0, true);
//Create a semaphore to denote the leave postal workers
public static Semaphore NoOfLeaveDeskWorkers = new Semaphore(0, true);
//Create a semaphore to denote the coordinates
public static Semaphore semaPost2 = new Semaphore(0, true);
//Create a semaphore for the scale
public static Semaphore postalScale = new Semaphore(1, true);
//Declare myCnt
public static int myCnt;
//Declare the customer queue
public static Queue<Integer> customerQue = new LinkedList<>();
//Declare the number of postal customers
public static final int NoOfCust = 50;
//Declare the number of postal workers
public static final int NoOfWorkers = 3;
//Create a semaphore to denote the completed customers
public static Semaphore[] semaCompleted = new Semaphore[NoOfCust];
//loop to initialize the semphore denoting the completed customers
static {
//Use loop to initialize
for (int kk = 0; kk < NoOfCust; kk++) {
//Allocate memory
semaCompleted[kk] = new Semaphore(0, true);
}
}
//Create a postal customer
public static MyPostalCustomer[] myCustomerObject = new MyPostalCustomer[NoOfCust];
//Create a postal worker
public static MyPostalWorker[] myWorkerObject = new MyPostalWorker[NoOfWorkers];
//Create a thread for the postal customer
public static Thread[] tempThread1 = new Thread[NoOfCust];
//Create a thread for the postal worker
public static Thread[] tempThread2 = new Thread[NoOfWorkers];
}
//SimulationDriver.java
package javaapplication39;
/*Driver program that simulates the post office*/
public class SimulationDriver {
//main() method
public static void main(String[] args) {
//Create a thread for the post office
MyPostalOffice pstOffice = new MyPostalOffice();
//Use loop to create objects
for (int kk = 0; kk < MyPostalOffice.NoOfCust; kk++)
{
//Create the customer
MyPostalOffice.myCustomerObject[kk] = new MyPostalCustomer(kk);
//Create the thread 1
MyPostalOffice.tempThread1[kk] = new Thread(MyPostalOffice.myCustomerObject[kk]);
//Start the thread 1
MyPostalOffice.tempThread1[kk].start();
}
//Loop to create the postal workers thread
for (int kk = 0; kk < MyPostalOffice.NoOfWorkers; kk++)
{
//Create postal worker object
MyPostalOffice.myWorkerObject[kk] = new MyPostalWorker(kk);
//create the thread 2
MyPostalOffice.tempThread2[kk] = new Thread(MyPostalOffice.myWorkerObject[kk]);
//Start the thread 2
MyPostalOffice.tempThread2[kk].start();
}
//Use loop to create postal customers
for (int kk = 0; kk < MyPostalOffice.NoOfCust; kk++)
{
//Try block
try
{
//Join the current customer
MyPostalOffice.tempThread1[kk].join();
//Display the joined customer
System.out.println("Joined customer " + kk);
//End of try
}
//Catch the exception
catch (Exception exxp) {
}
}
//Exit from the execution
System.exit(0);
}
}