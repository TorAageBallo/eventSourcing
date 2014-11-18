eventSourcing
=============

Comparing the implementation a Current State Bank and a Event Sourced Bank for a presentation on Event Sourcing. 

If you run the main() from SimpleEventSourcingMain.class you can first see  in the Current Stae Bank example how we normaly obtain current state when we implement our applications. 

Then we implement the same bank using event sourcing, with one current state projection. 
We then have a look on using aggregate to simulate changes to the current state through for example a web page. 

In the last section we'll have a look at implementing a change to our bank(in this case, allowing credit), and then running our events through our new projection to see that our result now differ. 
