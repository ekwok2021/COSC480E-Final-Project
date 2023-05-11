# COSC480E-Final-Project
## Insta Cart Ripoff

### Statement
The purpose of this app is to allow users to easily create and purchase items for meals that meet their calorie and various nutrient goals. As of now we plan to have several scenes that will help the user accomplish this goal. The first and main tab will be allow the user to shop for items by category and allow users to add these item to a cart, while displaying a progress bar below for the user to track how close to their goals they are. We will additionally have a tab that displays the users cart and tracker bars, as well as the option to checkout. The next tab will be a scene that allows the user to set their own custom goals. The next tab will show an order history of the user, allowing the user to reorder previous orders. This tab will be also include a calendar where the user can see previous orders and scheduled deliveries. The final tab will be a review tab that will allow the user to se how great of reviews the app has gotten!


### Technical Outline
For this project we used a backend database that is a csv file that contains a list of items, each with a name, a price, category, calorie count, amount of protein, amount of carbs, amount of fat, and an image. We made two new classes that are to be used to represent each item. Each item is created by referencing the database. The other class is used to store the items that have been purchased, this essentially acting as the shopping cart. It includes an arraylist, as well as helper functions, that stores each item that has been added to the list and various ways to interact with the list. There is also a file that functions as a file handler which is used to save the future and past orders. This file is automatically be called on the startup of the app so all data is automatically loaded. The data that will be saved is stored in a HashMap with a date as the key, and an order as a value. Along with the order ArrayList and the HashMap, there is an ObservableList that functions to display the data in several TableViews.


### Bibliography
### Books
- The Elements of Design (NC997, 2008)
- Designing Information (P93, 2012)
- Information is Beautiful(P93.5, 2009)
### Websites
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

### Objectives
- Images for each Item
    - Have an image attached to an item that is able to be added to the shopping cart in order for the user to be able to see what they are selecting.
    - *We were able to accomplish this by saving .png files for each item's image and including the image name in the csv file row for each item, allowing us to easily display each item as a button* 
- Grid Display
    - Instead of a bland list of items like a basic table view, we want to implement a grid like construction that is able to be scrolled through. This would allow for the images of items to be more clearly seen, and allow more room for other information.
    - *We were able to implement a grid like construction using a scroll pane with a grid pane, with v boxes inside each panes and the image button and item name within each v box.*
- Backend Database
    - implement a backend database of items that contains all the information of each item so that the user does not have to input their own items.
    - *We were able to implement a backend database of items using a .csv file with a line for each food item and their attributes.*
- Tabs
    - Implement a way to switch the scene to another scene that has a different function an puropose. For example, switching from the shoppin screen to the tracker and checkout screens. This could be implemented with a tab pane, but we would want to switch the style up so the tabs at the top do not look like browser tabs. This could also be done using buttons to change the scene/redirect the user to the proper screen.
    - *We were able to implement it by using a tab pane to implement all the tabs.*
- Animation
    - implement an animation that plays when a user adds something to the shopping cart to provide feedback that an item has been added to the cart.
    - *We were able to implement the button animation using the rotate transitions library.*
- Pop up shopping cart
    - Instead of the shopping cart taking up its own tab or part of the screen at all times, the shopping cart, when clicked on, will drop down or pop up the list of items in the cart and include a checkout button. 
    - *We were able to implemented a shipping cart using a titled lane.*
- Calendar
    - Implement a Calendar that will allow the user to plan orders/meals ahead of time, as well as show previous orders/meals. This will allow users to keep track of their progress, reorder meals from the past, and plan orders/meals for the future.
    - *We were able to implement a calendar using a date picker*
- Progress bar
    - Implement a tracker, most likely a progress bar with  sections for each food group, that will allow the user to keep track of what food they have already added to the cart, and what food they still need to add in order to meet their goals. Would most likely be simlar to the apple storage bar, with the bar showing the breakdown of each element that contributes to it. For example, how much is protein, how much is fat, how much is carbs, etc.
    - *We implemented the progress bar using the progress bar package and connecting them to the global tracking variables we implemented.*
- Filtered Search Bar
    - Using the search bar, update the displayed Items grid that meets the name crieteria of whatever is entered into the search bar. Use a similar logic also for if something is selected within the Food Categories menu. With the search bar also ensure that capitalization errors are ignored, although further error checking might be difficult.
    - *We implemented the search bar to the presentation by doing a simple look up on a list variable containing the items.
    
### Planning Images
<img height = "200" alt="Screenshot 2023-05-04 at 10 51 14 AM" src="https://user-images.githubusercontent.com/78102462/236245817-78aacf15-ddd2-4f74-9bb7-0ca7fa53adc9.png">
<img height = "200" alt="Screenshot 2023-05-04 at 10 51 23 AM" src="https://user-images.githubusercontent.com/78102462/236245764-8a5c9b4e-f815-466b-8c98-f91cfd964c2d.png">
<img height = "200" alt="Screenshot 2023-05-04 at 10 51 30 AM" src="https://user-images.githubusercontent.com/78102462/236245727-f1879cb9-d91d-4826-ac92-1d0fca5dc613.png">

## Final Product

### Code
#### Data Structures
- Item: Items are an object that has values for name, category, price, amount, calories, protein, fat, and carbs.
- Order: Orders are a class that holds Items in an ArrayList.
- HashMap: Orders are held in a hashmap with a LocalDate as a key and an Order as a value. This is helpful for being able to view previous   meals, and plan future ones.
- ObservableLists: The current order is stored in several observable lists in order to be displayed in multiple table views.

#### Noteworthy Libraries
- DatePicker
- Rotate/Scale Transition
- Duration
- Progress Bar
- Scroll Pane
- Titled Pane

#### Project Architecture
- The root of the project is a TabPane. 
    - The Home Tab is laid out on a GridPane. The GridPane contains many different HBox's and VBox's that contain various widgets. Most           importantly though, there is a ScrollPane that holds a GridPane that functions as the Item Browser. Each box of the gridpane contains       a VBox with an image as a button.
    - The CheckOut, progress, and calendar tabs are laid out on a VBox. Each VBox then has a variety of other VBox's and Hbox's that group       together widgets.


### Features
- Home:
    - Search Bar: The home tab has a search bar at the top that allows the user to search for items. These items will then be shown to the       user through the shopping interface.
    - Shopping Interface: The shopping interface is a ScrollPane that contains a GridPane. This GridPane contains images that are buttons         that, when clicked, open more information about each item. The interface is able to be scrolled, and has two columns.
    - Progress Bars: The progress bars show the user's progress towards acheiving their goals. They are updated when the user adds or             removes an item from the cart.
    - Cart: The cart is a TitledPane that can retract and expand to hide and show information. The TitledPane contains a table view which         functions as the cart. The table view contains the item name, the price, and the amount of each item. Items are able to be deleted by       selecting them in the table view, and then pressing the delete key. The amount can be changed by double clicking on the amount,             changing the amount, and then pressing enter. It can also be changed by adding the same item to the cart. This will add the new             amount to the previous amount. There is also a checkout button that takes the user to the checkout tab.
    - Information Group: The information group appears when the user clicks on an item. In this group, the all of the item's information is       displayed, along with an image, and an add to cart interface. The user can input the amount and click the button to add the item to         the cart. There is also a hide button which will hide all this information. 
- CheckOut:
    - Table View: The main feature of the checkout tab is the table view. This table view functions the same as the one on the home screen,       and contains all of the items currently in the cart.
    - Progress Bars: These progress bars function the same as the ones on the home view, but they are a little bigger and easier to tell         what the user's progress is.
    - Date Picker/Checkout: The date picker allows the user to pick the date they would like to order for, and then click the checkout           button to confirm the order. This clears the cart, and adds it to the HashMap of all orders made.
- Goal Setter:
    - The goal setter tab allows the user to set the goals for fat, protein, and carbs. These goals will update and change the way the           progres bars fill up.
- Calendar: 
    - Date Picker/See Order: The date picker allows the user to choose a date they would like to view the order for. The user can see             future orders and previous orders, allowing them to reorder carts that they have already ordered through the checkout button. 
    - Table View: This table view shows the cart for the date the date picker is set to. This table view is not able to be edited, so if the user would like to edit it, they will have to click checkout and to it in the checkout tab. 
    - Save: The Calendar is stored in a hashmap with a LocalDate as a key and an Order as the value. That hashmap can be saved to a dat           file. The contents of the dat file will be automatically put back into the hashmap upon loading the app. This allows the user to be         able to save future orders, or look at previous orders, even after closing the app.
- Reviews: 
    - The reviews tab shows all the glowing reviews the app has gotten. 
- General Features:
    - Many buttons in the application play an animation when clicked. Some of these include the add to cart button and the save button. The       buttons will spin to provide feedback to the user that they have actually clicked the button.

### Challenges
- Scroll Pane
    - We faced some challenges when implementing the scroll pane. Particularly it was difficult getting the items within the grid within the scroll pane to expand with the expansion of the window.

- Titled Pane
    - We faced some challenges with the titled pane and its size. At first, the titled pane would only display a very short box and was incredibly difficult to use. We were since able to resolve this issue and make the cart easy to view.

- Progress Bar
    - We faced some issues with having the progress bars in both the checkout and the main home tab update simultaneously at the same time when adding or removing something to the cart. We were able to resolve this issue and the progress bars on both page update and are identical to one another and accurately display the information.

- Calendar
    - We faced some issues when trying to save and load dates into the calendar with the save dates sometimes not being equal. The calendar feature now works without issue and you are able to save and load using a .dat file.

### Future Work
- Adding Multiple Storefronts
    - Currently there is only one store to choose from. We could implement an option to choose food items from multiple stores and create orders from multiple stores at once while still tracking ones progress.
   
- Preset Diets or Goals
    - In the goal setter tab we could implement an option for users to pick a pre created diet given the written description.

- Implement as an Actual App
    - In the future we would want to make this app genuinely usable and be able to load real stores and create real orders.

### Images
<img height = "200" alt="image" src="https://github.com/ekwok2021/COSC480E-Final-Project/assets/78102462/f4ef3783-b498-40be-86df-6c09b1781cf1">
<img height = "200" alt="image" src="https://github.com/ekwok2021/COSC480E-Final-Project/assets/78102462/b3353922-e422-4824-8760-a948862e06ae">
<img height = "200" alt="image" src="https://github.com/ekwok2021/COSC480E-Final-Project/assets/78102462/d9d482ba-00ec-4946-915e-9e8b618f2ce2">
<img height = "200" alt="image" src="https://github.com/ekwok2021/COSC480E-Final-Project/assets/78102462/5e4bba1a-46f2-4574-b65d-9c88ff4bdb02">
<img height = "200" alt="image" src="https://github.com/ekwok2021/COSC480E-Final-Project/assets/78102462/15d4eb14-56d2-4730-b3cf-45b8ef4a1672">

