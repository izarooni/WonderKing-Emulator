# WonderKing-Emulator
I am using Java 10 but any version above 8 should work.    
This is just for experience and trying out different coding techniques. Not really sticking to any theme of naming conventions or structures.  
Hoping to make this project super simple and "plug and play" where anybody can download and start a server.  
 
The socket ports used starts at `10001` which is the login server.  
Each channel also has it's own port which is calculated based on the channel ID and server ID.  
Both channel and server ID starts at 0   
```
10002 + (channelID + (serverID * 15))
``` 

## Startup Arguments
Only one argument is mandatory when starting your server.
```$xslt
-Dlog4j.configurationFile=log4j.xml
``` 
This creates a system property for our logging library so errors can be printed to a file leaving less clutter in our console.  
You will not see any logging without the configuration file and this property. 