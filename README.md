eventSourcing
=============

Comparing the implementation a Current State Bank and a Event Sourced Bank for a presentation on Event Sourcing. 

If you step thorugh the the main() from SimpleEventSourcingMain.class in debug you will first see the Current State Bank example. This is the CRUD way how to obtain current state within our application. 

Then we implement the same bank using event sourcing, with one current state projection. 
We then have a look on using aggregate to simulate changes to the current state for a web page. 

In the last section we'll have a look at implementing a change to our bank(in this case allowing credit), and then running our events through our new projection to see that our result now differ. 
