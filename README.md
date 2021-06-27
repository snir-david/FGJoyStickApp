 
# Android FlightGear Joystick App  
### Preview from the App
![enter image description here](https://github.com/snir-david/JoyStickAndroid/blob/master/pictures/PreviewApp.png?raw=true)
Our App is a controller for FlightGear Simulator. After open FG (make sure to enter the right settings), the user can connect to the FG server and start to fly the plane!
## Directory hierarchy  
**Model** - this class is responsible for connecting to the server and send data to the simulator.\
**ViewModel**- this class is responsible for getting data from View (using data binding) and process it for the Model class.\
**View** - this class is responsible for creating views, getting data, and manipulate the view's component.  

## Installation and Running application  
Before running our app you need to download and install the FlightGear application - you can find [download link here](https://www.flightgear.org/download/). For more information about FlightGear, you can find [here](https://www.flightgear.org/)
![enter image description here](https://github.com/snir-david/JoyStickAndroid/blob/master/pictures/jetblue1.0.jpg?raw=true)
 - **For Developers** - install a proper IDE for the Android Application (we used Intellji but you can use any other software). You will need to install a few programs and modules-   
 - [ ] **Android SDK** - install android SDK (usually install with IDE or when trying to open an android project in the IDE). Code was wrriten to API 27 but should work for lower API's too. 
 - [ ] **Virtual Phone (using AVD)** -  install an emulator to emulate android phones on the computer. Also can be done using IDE. 
 - [ ] **FlightGear Settings** -  make sure to put this line in flightgear setting so you can connect to the server from the app - 
 >     --telnet=socket,in, 10,127.0.0.1,6400 ,tcp
this line means the FG opens a server on the localhost (127.0.0.1) it runs on port 6400.
you can find server IP address using the command - 

> ipconfig

Find the server IPv4 address - it should look something like this - 
![enter image description here](https://github.com/snir-david/JoyStickAndroid/blob/master/pictures/ipconfig.png?raw=true)
set up the port to be the same port as FG settings.
## Documentation  

Here you can find a Link to UML contains partial information of the central classes. UML represents the various connections between the classes and the most important information found in each class. UML can be found [here](https://lucid.app/lucidchart/3ddfc71b-ecd1-4bf7-ac04-9e92169fd6d4/view?page=0_0#).   
If you are a developer you can find full documentation of functions, variables, and more in the code.  

## Video  

Here you can find a link to our demo video - [link](https://youtu.be/Ox2uRGX0ASA).  
