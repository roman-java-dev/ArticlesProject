# 📖 ARTICLES-APP 

![image.jpg](image.jpg)

### ⚡️ Project description
RESTful web app that allows to create and view articles, to register and authenticate user. Supports user and admin roles. 
Article results support pagination. The project is covered by Unit and integration tests. Does not have UI, interaction 
happens through Postman.

### 🎯 Endpoints
The web app provides the following endpoints:

<b> Articles Endpoints: </b>

- <b>POST: /articles</b> - Add article.
- <b>GET: /articles</b> - This endpoint will return all the stored articles. This endpoint can take optional parameters: page and size.
- <b>GET: /articles/statistics</b> - The endpoint should return count of published articles on daily bases for the 7 days. 
This endpoint can only be accessed only by admins.

<b> User Endpoints: </b>

- <b>POST: /users/register</b> - This endpoint will allow to register user.
- <b>POST: /users/authenticate</b> - This endpoint will allow the user to authenticate using username and password. 
On successful login, the API return the JWT access-token.

### 🔥 Getting Started
To get started with the project, follow these steps:
1. Install Postman for sending requests.
2. Run the application.
3. Use this URL in Postman to test the app: http://localhost:8080/ 
4. JSON payload example for user registration.<br>
   {<br>
   "username": "any username",<br>
   "password" : "any password"<br>
   }
5. To authenticate user you can use the previous JSON payload example. After successful authentication, you will 
receive a JWT token. Don't forget to copy it and add it to the Authorization - Bearer Token field.
6. To add an article, you need to enter data according to the following example. Please note that all the fields are
mandatory and the title should not exceed 100 characters. The publishing date should bind to ISO 8601 format.<br>
   {<br>
   "title": "any title",<br>
   "author": "any author",<br>
   "content": "any content",<br>
   "publishingDate": "2024-02-28T00:00:00"<br>
   } 
7. To test the project with end-to-end integration tests you need to start both the server and the tests.