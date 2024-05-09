# Book Management Software

## Purpose

The software provides users with a centralized platform for managing their reading materials, library, and book-related experiences. By utilizing an object-oriented design, it aims to give readers an organized way to track their books and connect with others. The software allows users to store details about each book, share reviews, and customize their libraries.

## Organization 

This software utilizes object-oriented programming principles to effectively store and organize book-related data. The core classes model key entity types - the `Book` class represents individual books with attributes like title and author. User reviews are encapsulated in the `Review` class, capturing rating and comment details. A user's personal experience with each book is modeled by the extended `ProfileBook` class, which tracks reading progress, time spent, and the user's custom review.

To enable data storage and retrieval, interface classes are defined to interact with the underlying databases. `GeneralDbPage` allows accessing the general book catalog. `PersonalDbPage` handles the user's own book profile data. User authentication and account functionality is provided through the `RegisterPage` and `LoginPage` classes.

The user interacts with these classes through an intuitive GUI. The bookshelf display leverages the `Book` class to present core book details. Search functionality permits finding books via title or author attributes. The profile view accesses `ProfileBook` data to show personalized reading statistics.

## Instructions
1. When entering the LoginPage, there is two options available: to login with already existing account or create one using "registration".
2. After entering, there is two libraries available, General and Personal.
3. CRUD operations are available for Personal Database and for General Database, only if the user entered as Admin.
4. For General Database, there is a sorting available to make the browsing more comfortable.Sorting is available for Title and Author. In addition to sorting, there is also a Search bar available for user;User can find the desired book by entering either Title or Author of the book.
5. For Personal Database, 


