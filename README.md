# Restaurant Ordering App

Welcome to the Zippy Delivery App! This Android application is designed to provide users with a seamless experience in searching for restaurants, placing orders, and viewing recent orders.

## Features

1. **Navigation Drawer**
   - The app includes a Navigation Drawer that displays the user's profile picture along with their name and email.
   - Navigation Drawer buttons:
     - Home: Takes the user to the main screen.
     - Recent Orders: Displays a list of all orders placed in the past.
     - Calendar View: Interactive calendar of their purchases
     - Sign Out: Logs the user out of the app, destroying the current session.

2. **Home Screen**
   - The Home Screen is the central hub of the app, featuring a search icon in the App Bar for convenient restaurant searching.
   - Divided into two fragments (or composables):
      - **Fragment 1 (Horizontal Scrollable Recent Restaurants)**
        - Displays recent restaurants in horizontally scrollable card views.
        - Each card represents a restaurant through which the user has recently ordered.
        - Clicking on a restaurant card navigates the user to the corresponding Restaurant screen.
        - A minimum of 5 pre-added recent restaurants is included for testing purposes.
      - **Fragment 2 (Vertically Scrollable All Restaurants)**
        - Displays all restaurants in vertically scrollable card views.
        - Each card represents a restaurant from the main restaurant collection.
        - Clicking on a restaurant card navigates the user to the corresponding Restaurant screen.
        - A minimum of 8 pre-added restaurants is included for testing purposes.

3. **Restaurant Screen**
   - Each restaurant has its own location, ensuring that it is within 50 miles of the user.
   - The restaurant collection includes the name of the restaurant, its location, and three images stored in Firebase Cloud Storage.
   - 
4. **Checkout Screen**
   - Allows the user to review their order before placing it.
   - If they choose to modify it, they can go back to the restaurant screen and update their order.
   - The user can also input their delivery address and special instructions.
  
5. **Map Screen**
   - Shows how far away the order is from the user on a map.


## Getting Started

To run the app locally, follow these steps:

1. Clone the repository to your local machine.
2. Open the project in Android Studio.
3. Build and run the app on an Android emulator or physical device.

## Dependencies

- [Firebase Firestore](https://firebase.google.com/docs/firestore): Used for storing and retrieving restaurant and order data.
- [Firebase Cloud Storage](https://firebase.google.com/docs/storage): Used for storing restaurant images.
- [Android Navigation Component](https://developer.android.com/guide/navigation): Used for navigation between fragments.

## Demonstration 

## Future Improvements
1. Scrolling images.
  - Glide is internally implemented, but I struggled with syncing the ImageAdapter to the RestaurantScreen
2. Choosing menu items for your order
   - The database is sending the menu items and they display, but I struggled to get the plus and minus buttons to work correctly within the MenuItemAdapter.
   - It is implemented internally with comments, but is not in the final product.


## Contributing

Contributions are welcome! If you find any issues or have suggestions for improvement, please open an issue or create a pull request.

## License

Copyright [2023] [Kenna Edwards]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
