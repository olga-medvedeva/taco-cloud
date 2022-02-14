# taco-cloud
(kinda) Full-stack service working as an online taco store;

Java + Spring Boot + PostgreSQL + Thymeleaf
(kinda full-stack because i didn't spend much time on the visual component of the project)

Service has following functions:
- User registration and authentification [img]:
  In the begging service asks user to login or registrate (all registration information goes into DB and used later to simplify order confirmation) and then authentificate on the 
website using name and password (without authentification further use of the website is impossible, since the service requires information about a specific user in order to create 
new orders as well as show old orders and a shopping cart);
- Design and shopping cart []:
  In a design section user picks the ingredients that the taco should consist of (list of Ingredients is taken from a DB and displayed using Thymeleaf (all subsequent personal 
information of users and their orders works on the same principle)) and names it (this information goes into DB as an order request that was created but not complete). After 
confirmation of the designed taco service redirects user to shopping cart where he can go to design next taco or proceed to order it. Shopping cart displays all infromation 
about tacos that were added but not yet ordered by user;
- Order creation and past orders []:
  On the order page, user must specify personal information about current order (city, phone number, address, etc.) if user specified this information during registration
it will be automaticly put in the appropriate fields (user can change it). The only informaion that user will have to enter again with each order it information about
credit card. All fields have verification and user can't proceed with order if some information isn't correct. If everything is OK, then a new order is created (saved into DB),
and the order request status is set to COMPLETE (it means that tacos from this request no longer will shown on a shopping cart page). At the end service redirect user to orders
page where shown all the information about 5 past orders (amount of shown orders can be changed in an application.yml file);
- Logout.

Service with PostgreSQL DB and has following tables:
![1]()
