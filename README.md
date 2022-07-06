# taco-cloud
(kinda) Full-stack service working as an online taco store;

Java + Spring Boot + PostgreSQL + Thymeleaf
<p/>(kinda full-stack because i didn't spend much time on the visual component of the project)

Service has following functions:
- User registration and authentification [img 1,2]:
  In the begging service asks user to login or registrate (all registration information goes into DB and used later to simplify order confirmation) and then authentificate on the 
website using name and password (without authentification further use of the website is impossible, since the service requires information about a specific user in order to create 
new orders as well as show old orders and a shopping cart);
- Design and shopping cart [img 3,4]:
  In a design section user picks the ingredients that the taco should consist of (list of Ingredients is taken from a DB and displayed using Thymeleaf (all subsequent personal 
information of users and their orders works on the same principle)) and names it (this information goes into DB as an order request that was created but not complete). After 
confirmation of the designed taco service redirects user to shopping cart where he can go to design next taco or proceed to order it. Shopping cart displays all infromation 
about tacos that were added but not yet ordered by user;
- Order creation and past orders [img 5,6]:
  On the order page, user must specify personal information about current order (city, phone number, address, etc.) if user specified this information during registration
it will be automaticly put in the appropriate fields (user can change it). The only informaion that user will have to enter again with each order it information about
credit card. All fields have verification and user can't proceed with order if some information isn't correct. If everything is OK, then a new order is created (saved into DB),
and the order request status is set to COMPLETE (it means that tacos from this request no longer will shown on a shopping cart page). At the end service redirect user to orders
page where shown all the information about 5 past orders (amount of shown orders can be changed in an application.yml file).
    (order doesn't contain his own list of ordered tacos but he has an id of order request that has this list);
- Logout.

Service uses PostgreSQL DB and has following tables:

![tables](https://user-images.githubusercontent.com/90202470/153898593-bc83b59d-a2cf-4abb-bc38-cc40902e6706.jpg)

- User registration and authentification:

![1](https://user-images.githubusercontent.com/90202470/153901161-4a1bac44-84d1-41fb-86a3-98efef1af30d.jpg)
<p align="center">[1]</p>

![2](https://user-images.githubusercontent.com/90202470/153901218-2717fb97-7eea-4bfd-a2e2-70819a2af5b2.jpg)
<p align="center">[2]</p>


- Design and shopping cart:

![3](https://user-images.githubusercontent.com/90202470/153901340-23338569-90f3-4ac3-ad9e-e91227e8e592.jpg)
<p align="center">[3]</p>

![4](https://user-images.githubusercontent.com/90202470/153901347-367a69d4-6840-48dc-b946-4cfc6342e954.jpg)
<p align="center">[4]</p>


- Order creation and past orders:

![5](https://user-images.githubusercontent.com/90202470/153901430-887f545e-5f3d-4abd-a72d-f6b356291f5b.jpg)
<p align="center">[5]</p>

![6](https://user-images.githubusercontent.com/90202470/153901432-8ec61103-723b-4ebf-ab06-c7fbe4ec1a92.jpg)
<p align="center">[6]</p>
