# <font color = "#4C4C6D"><u> RECIPE PROJECT </u> </font>

- This is a backend application, used for the publishment of unique food Recipes.
- Users have to use register the system after that they'll get an activation code which enables them to activate their accounts.
- After the activation process they'll get their token and be able to update their accounts, like the recipes published by the people who got the Role "Admin"
- and if there is a new recipe published which got the same category with the users' favorite, they'll recieve mail about the new added recipe.
- Only people who got the role Admin will have the authorization about simple CRUD methods on recipes. User's can only add recipes to their favorite and use search and filter functions.


## <font color = "#1B9C85"><u>Used Programing Languages </u> </font>
- Java, Spring Boot

## <font color="#1B9C85"> <u> Used Technologies: </u> </font>
- OpenFeign, RabbitMQ, Redis, Zipkin, OpenAPI(SwaggerUI)

## <font color="#1B9C85"><u> Used DataBases: </u></font>
- PostgresSQL,MongoDB

## <font color="#1B9C85"><u>Used Services ve Links: </u></font>
* [Auth Service](http://localhost:9090/swagger-ui/index.html)
* [Comment Service](http://localhost:9060/swagger-ui/index.html)
* [Recipe Service](http://localhost:9070/swagger-ui/index.html)
* [UserProfile Service](http://localhost:9080/swagger-ui/index.html)
* [Mail Service](http://localhost:9085/swagger-ui/index.html)
* [Config Server](http://localhost:8888/swagger-ui/index.html)

## <font color = "#FFE194"><u> Auth Service</u></font>
- Auth Service, is the service where users will register and login to the system.
-   After the registration process user's will get their token and use API Urls in order to take action on the website according to their roles.
</br>

<font color ="E8F6EF"><u>Register</u></font></br>
![Register](README_pics/Ekran Resmi 2023-05-29 19.02.28.png) </br></br>
<font color ="E8F6EF"><u>Login</u></font></br>
![Login](README_pics/Ekran Resmi 2023-05-29 19.06.17.png) </br></br>
<font color ="E8F6EF"><u>Forgot Password</u></font></br>
![Forgot Password](README_pics/Ekran Resmi 2023-05-29 19.09.18.png)
<font color ="E8F6EF"><u>Update Password</u></font></br>
![User Change Password](README_pics/Ekran Resmi 2023-05-29 19.15.52.png)</br></br>

## <font color = "#FFE194"><u>User Service</u></font>
- User Service is where Users can update their user profile information,
  delete and inactivate their account and add the recipes they liked to their favorite list.
  In order to take notifications about the newly added recipes which are in the same categories as user favorite recipes.
</br>

<font color ="E8F6EF"><u>Update Users General Information's</u></font></br>
![User Update](README_pics/Ekran Resmi 2023-05-29 19.12.11.png)</br></br>
<font color ="E8F6EF"><u>Add Favorite Recipes</u></font></br>
![User Save Favorite Category](README_pics/Ekran Resmi 2023-05-29 19.25.20.png)</br></br>
<font color ="E8F6EF"><u> Un Add Favorite Recipes</u></font></br>
![User Save Favorite Category](README_pics/Ekran Resmi 2023-05-29 19.27.52.png)</br></br>

## <font color = "#FFE194"><u> Comment Service </u></font>
- Comment Service is the service where User actions will take place such as commenting a recipe and also score the
  recipes based on users' opinion.
</br>

<font color ="E8F6EF"><u>Create Comment and Point</u></font></br>
![Comment Add](README_pics/Ekran Resmi 2023-05-29 19.31.46.png)</br></br>
<font color ="E8F6EF"><u>Update Comment and Point</u></font></br>
![Comment Add](README_pics/Ekran Resmi 2023-05-29 19.32.58.png)</br></br>
<font color ="E8F6EF"><u>Delete Comment and Point</u></font></br>
![Comment Add](README_pics/Ekran Resmi 2023-05-29 19.36.41.png)</br></br>

## <font color = "#FFE194"><u> Recipe Service </u></font>
- Recipe Service is the service where users can look for the recipes.
  Search with pre-determined searches according to the ingredient names,
  categories and food names. Also, responsible who got the role "ADMIN" can create,
  update and delete new forms of categories and recipes.
  </br>

<font color ="E8F6EF"><u>Create Recipe</u></font></br>
![Comment Add](README_pics/Ekran Resmi 2023-05-29 19.44.08.png)</br></br>
<font color ="E8F6EF"><u>Update Recipe</u></font></br>
![Comment Add](README_pics/Ekran Resmi 2023-05-29 19.45.07.png)</br></br>
<font color ="E8F6EF"><u>Delete Recipe</u></font></br>
![Comment Add](README_pics/Ekran Resmi 2023-05-29 19.46.03.png)</br></br>

