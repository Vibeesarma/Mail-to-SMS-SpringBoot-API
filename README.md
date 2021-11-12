# Mail-to-SMS-SpringBoot-API

Steps: Copy the application.properties file from src/main/resources to project root directory issue maven install command cp src/main/resourcs/application.properties.

Notes : Make sure application.properties file is available on the same folder as the jar

This project get the data from email "INBOX" and convert to SMS using twilo,

#first configure the twilo account

first you want create twilo account,then you get username,password and also twilo phone number.
you want to add these details to application propeties file 

#second configure the mail account 

if you want to access your inbox of your email you want enable "IMAP" in your email, 
  go to the email  Settings --> click  See all settings -->go to Forwording IMAP/POP --> Enable IMAP
   
    note : if you enable double verfication when login the gmail account disble it or go google account service to get app password for use here.

then go to application propeties file and paste your gmail  and password to your imap uri 
note : use instead of @ using %40  like a  
    user@gmail.com -> user%40gmail.com 
    
 if your password also have @ simpole use instead of %40 there,
    
	Note : when you run the application you must disble your antivirus 
  
  

