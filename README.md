# Structure
The application allows the end users to interact with the system in a user-friendly way. There are four main screens that the user can navigate between using a well-known navigation bar at the bottom of the screen with icons that are commonly associated with each page. The different pages are all implemented using fragments which sit inside a single activity.

# Features
## Home Screen
On the home screen, the user is able to see their weekly carbon usage compared to the average user as a circular bar. They also have a face emoji displayed on the screen to reflect how they are doing (thumbs up for low carbon usage or a sad face for high carbon usage).

## Goals Screen
On the goals screen, the user gets a more detailed breakdown of how they compare to the different percentiles which are displayed as different bars on the screen. This gives the user measurable goals and progress for decreasing their carbon consumption.

## Stats Screen
On the stats screen, the user is able to see a history of their transactions over the last seven days. For each transaction, they can see the timestamp, vendor name, monetary cost  and carbon cost. They are also able to edit the carbon cost through a button which brings up a new fragment as a pop-up where they can enter the new value. Offsets are also listed in the list of transactions with negative carbon cost.

## Offset Screen
On the offset screen, the user is able to see the offsetting options available. Each offset has information on who the company is, a short description and the price per kilogram of carbon offsets. The user can click a button to choose how much carbon they would like to offset. This is done by bringing up a new fragment.
