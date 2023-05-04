# COSC480E-Final-Project
##### Insta Cart Ripoff

##### Statement
The purpose of this app is to allow users to easily create and purchase items for meals that meet their calorie and various nutrient goals. As of now we plan to have several scenes that will help the user accomplish this goal. The first and main tab will be allow the user to shop for items by category and allow users to add these item to a cart, while displaying a progress bar below for the user to track how close to their goals they are. We will additionally have a tab that displays the users cart and tracker bars, as well as the option to checkout. The next tab will be a scene that allows the user to set their own custom goals. The next tab will show an order history of the user, allowing the user to reorder previous orders. This tab will be also include a calendar where the user can see previous orders and scheduled deliveries. 


##### Technical Outline
For this project we plan on using a backend database that will likely either be a csv or a dat file. This will be used to store the name of the item, its calorie count as well as a link to the an image of the item to be used in the grid display of lesser importance as well is the addition of the prices of each item. We will also be making two new classes that are to be used to represent each item. Each item will be created by referencing the database. The other class will be used to store the items that have been purchased, this essentially acting as the shopping cart. It will include some sort of list that stores each item that has been added to the list and it will also have the total amount of calories within the cart. The GridPane itself will have another ObservableList that will be a list that has all the items on display. Since we also intend to filter out the grid based upon search criteria we may use more specifically a FilteredList such that the grid only displays matching items. 


##### Bibliography
##### Books
- The Elements of Design (NC997, 2008)
- Designing Information (P93, 2012)
- Information is Beautiful(P93.5, 2009)
##### Websites
* [CSS]https://www.w3.org/wiki/CSS/Properties/color/keywords
* [TableViewCSS]https://edencoding.com/style-tableview-javafx/
* [TableView]http://www.java2s.com/Tutorials/Java/JavaFX/0650__JavaFX_TableView.htm 
* [Animation]https://www.tutorialspoint.com/javafx/javafx_animations.htm 
* [GridPane]https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/GridPane.html
* [EdtingTableView]https://github.com/JavaCodeJunkie/javafx/blob/master/TableViewDemo-2/src/application/Main.java
* [ColorPallete]https://colorhunt.co/palette/f9f7f7dbe2ef3f72af112d4e 
* [TabSwitching]https://stackoverflow.com/questions/20955633how-to-switch-through-tabs-programmatically-in-javafx
* [Datasets]https://www.kaggle.com/datasets 
* [Icons]https://stackoverflow.com/questions/10121991/javafx-application-icon 
* [ProgressBar]https://docs.oracle.com/javafx/2/ui_controls/progress.htm 
* [DatePicker]https://docs.oracle.com/javase/8/javafx/user-interface-tutorial/date-picker.html  

##### Objectives
- Images for each Item
    - Have an image attached to an item that is able to be added to the shopping cart in order for the user to be able to see what they are selecting.
- Grid Display
    - Instead of a bland list of items like a basic table view, we want to implement a grid like construction that is able to be scrolled through. This would allow for the images of items to be more clearly seen, and allow more room for other information.
- Backend Database
    - implement a backend database of items that contains all the information of each item so that the user does not have to input their own items.
- Tabs
    - Implement a way to switch the scene to another scene that has a different function an puropose. For example, switching from the shoppin screen to the tracker and checkout screens. This could be implemented with a tab pane, but we would want to switch the style up so the tabs at the top do not look like browser tabs. This could also be done using buttons to change the scene/redirect the user to the proper screen.
- Animation
    - implement an animation that plays when a user adds something to the shopping cart to provide feedback that an item has been added to the cart.
- Pop up shopping cart
    - Instead of the shopping cart taking up its own tab or part of the screen at all times, the shopping cart, when clicked on, will drop down or pop up the list of items in the cart and include a checkout button. 
- Calendar
    - Implement a Calendar that will allow the user to plan orders/meals ahead of time, as well as show previous orders/meals. This will allow users to keep track of their progress, reorder meals from the past, and plan orders/meals for the future.
- Progress bar
    - Implement a tracker, most likely a progress bar with  sections for each food group, that will allow the user to keep track of what food they have already added to the cart, and what food they still need to add in order to meet their goals. Would most likely be simlar to the apple storage bar, with the bar showing the breakdown of each element that contributes to it. For example, how much is protein, how much is fat, how much is carbs, etc.
    
- Filtered Search Bar
    - Using the search bar, update the displayed Items grid that meets the name crieteria of whatever is entered into the search bar. Use a similar logic also for if something is selected within the Food Categories menu. With the search bar also ensure that capitalization errors are ignored, although further error checking might be difficult.
#### Planning Images
<img width="602" alt="Screenshot 2023-05-04 at 10 51 14 AM" src="https://user-images.githubusercontent.com/78102462/236245817-78aacf15-ddd2-4f74-9bb7-0ca7fa53adc9.png">

<img width="602" alt="Screenshot 2023-05-04 at 10 51 23 AM" src="https://user-images.githubusercontent.com/78102462/236245764-8a5c9b4e-f815-466b-8c98-f91cfd964c2d.png">


<img width="602" alt="Screenshot 2023-05-04 at 10 51 30 AM" src="https://user-images.githubusercontent.com/78102462/236245727-f1879cb9-d91d-4826-ac92-1d0fca5dc613.png">

#### Final Product

##### Features

#### Images
