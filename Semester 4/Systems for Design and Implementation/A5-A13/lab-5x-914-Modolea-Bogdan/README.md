# Lab 5x requirements

A new assignment requirement will appear here every week, so check back regularly. Commit and push everything to your **Lab-5x** repository.

----
----

## Lab 13 assignment

**Points**: 0.5

**Deadline**: Week 14

**Last change deadline and penalties**: Week 14

You will need to:
- Make your application as user friendly as possible: any unclear features or names will result in a lower grade.
- Make your code as readable and efficient as possible: any "code smells" will result in a lower grade.
- Write documentation for how to get your app running. Also mention any tech stack choices you made, any tradeoffs and anything else that might be relevant. Anything relevant that is missing from the documentation will result in a lower grade.
- Aim for production-quality levels of UX, code quality and documentation clarity.

----
----

## Lab 12 assignment

**Points**: 0.5

**Deadline**: Week 13

**Last change deadline and penalties**: Week 14, -0.3 points / week delayed

You will need to:
- Come up with a reasonable way to integrate a machine learning model into your application. It can be trained on any data (your application's data or an external data set) and there should be a functionality in your application that makes use of your model's predictions. You can use any model you want. You will be graded based on how realistic your choices of training data and functionality are.

----
----

## Lab 11 assignment

**Points**: 0.5, +0.3 bonus

**Deadline**: Week 12

**Last chance deadline and penalties**: Week 14, -0.2 points / week delayed

You will need to:
- Make one or more graphs containing stress test (spike test) results: it should be clear from your graph(s) how the number of users affects the CPU usage and the request response times. Use JMeter. Start with few users: `~2` constant ones and `~10` for the spikes and gradually move up until you hit `100%` CPU usage. You should make requests to all the (public) endpoints of your API. You only need to do `GET` requests.
- Implement a chat page on your web app using websockets. Every visitor should be able to see messages from all other visitors. Let your visitors choose a nickname. You do not need persistence for the messages. You can either store them in memory or discard them after sending them to all the currently connected clients.

For the bonus:
- The best performing app in each lab receives the bonus. This is available only on week 12 and only if there are at least 2 people with this lab accepted.

----
----

## Lab 10 assignment

**Points**: 0.5, +1 bonus

**Deadline**: Week 11

**Last chance deadline and penalties**: Week 13, -0.2 points / week delayed

You will need to:
- Dockerize your application. You should have setups both for local development and production deployment. Make a new VM, install docker and docker-compose on it and deploy the project like that.
- Write two E2E tests for your application.

The following points are available as bonuses:
- +0.5: setup a Kubernetes Cluster on your Cloud Provider (for example: https://cloud.google.com/kubernetes-engine) and use your app with it. Use JMeter with the Ultimate Thread Group plugin and show how Kubernetes scales resources to accomodate requests from JMeter. Correlate the JMeter graphs with the Kubernetes graphs.
- +0.5: make your frontend responsive. It should seamlessly adapt to any screen size, with no scrollbars and with proper component sizes and layout.

----
----

## Lab 9 assignment

**Points**: 0.5

**Deadline**: Week 10

**Last chance deadline and penalties**: Week 12, -0.2 points / week delayed

You will need to implement the following user roles:
- Anonymous user (no login): can only **read** everything, cannot add, cannot edit;
- Logged in user, **regular** role: can add entities and can edit the entities they added;
- Logged in user, **moderator** role: can add entities and can edit all entities;
- Logged in user, **admin** role: 
    - can add entities and can edit all entities, has access to a page where they can edit user roles for everyone including other admins;
    - has access to a page from where they can **bulk delete** data and from where they can **run the data generation scripts** you wrote for the previous assignments. If you find it easier, you can refactor them such that the data is generated directly from your code and not from the SQL scripts, but this is not mandatory.

You can make your first admin user by directly editing the database.

Permissions need to be checked on both backend and frontend. We will check by making API requests from Postman using users with the wrong roles.

If you haven't already, make sure that you do not store user passwords in plain text. They should at least by hashed with SHA-256.

----
----

## Lab 8 assignment

**Points**: 0.5

**Deadline**: Week 9

**Last chance deadline and penalties**: Week 11, -0.2 points / week delayed

You will need to:
- Implement register and login with username, password and JWT tokens. The username should be unique. The password should have some validation rules to ensure that the password is strong.  
    - Your `User` model must contain any fields needed for login (probably just username and password and whatever else your framework has built in). You should also have a `UserProfile` model with `5` fields with at least 3 validation rules. For example: bio, location, birthday, gender, marital status.  
    - If you already have a `User` model and you use it for this, add one more entity to your app.
    - All of your entities should be directly or indirectly associated with the user that created them. Add `10 000` (ten thousand) random users and randomly associate the existing entities with these users. For testing purposes, have the same password for each of these users. Each entity **must** have an associated user, but not all users must have associated entities. The data insertion part must be implemented in an SQL script. You might want to work on a backup database before running the script on the production database.
    - The `/api/register` endpoint should generate a confirmation code that is valid for `10` minutes. The user must request `/api/register/confirm/<confirmation code>` to activate their account.
- Everything must be validated everywhere possible, including IDs. Implement the `happy case - with data`, `happy case - without data` and `error case` scenarios for all endpoints.
- For all routes that show all entities, also show the username of the user that added the entity. Clicking on the username should take you to the user's profile page.
    - The profile page should contain the user profile info and statistics regarding how many of each entity the user has added.
- You must start using feature branches for all functionalities. Your feature branches should be named according to the feature that you're implementing. Use Pull Requests and merge your branches into a `development` branch when you are done with your work. You can delete the feature branches after that. Have netlify deploy from `development`. You can read more about this here: https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/proposing-changes-to-your-work-with-pull-requests/about-pull-requests
    
----
----

## Lab 7 assignment

**Points**: 0.4

**Deadline**: Week 8

**Last chance deadline and penalties**: Week 10, -0.1 points / week delayed

For this and all future assignments, unless otherwise specified, we are no longer treating the backend and the frontend separately when talking about features: implementing most features will require you to work on both the backend and the frontend, even if this is not explicitly stated.

You will need to:
- Implement all functionalities on the frontend. Implement pagination on the backend and navigation through it on the frontend. You are **not allowed** to **only** use built-in pagination classes and pagination libraries. You must implement the pagination class or functionality yourself either on the frontend or on the backend. Do it in a way that allows for changes. For example, you may use built-in classes on the backend and no built-in classes on the frontend. Your live coding task during the lab will involve the pagination aspect. Recall that you should have at least the following from previous assignments and live tasks:  
    - 3 entities.
    - 1 intermediate entity for a many to many relation, whose CRUDs can be handled either through dedicated routes or through the other entities' routes.
    - 3 validation rules.
    - 1 filter by a numerical field.
    - 2 statistical reports.
- Make sure that your functionalities have efficient implementations. This may require adding indexes to your database, implementing autocomplete and fixing things on the backend. There shouldn't be a noticeable slowdown when interacting with any page.
- Each show all page should display, for each row, an aggregated value on a related entity (for example, the number of students enrolled in the course for the show all courses page). This should still be efficient and not affect page load times too much.
- Duplicate your validation logic on the frontend and add `2` more validation rules, also duplicated on the backend and the frontend. 
- Error messages should show up on the frontend near the corresponding textboxes or using Toasters.
- If you haven't already, add a CSS Components Library to your frontend: Material UI, Bulma, Bootstrap, Tailwind or something similar.
- Secure and improve your server by:
    - Installing `nginx` and a dedicated application server for your backend. The `nginx` reverse proxy server should communicate with your application server and the application server should not be directly exposed to the internet. The application server can be Gunicorn, Apache, Tomcat or something else. For .NET projects, you can just use IIS or the default deployment scheme. If you do not do it this way then you must have a very compelling reason that you can explain with your tech stack's documentation as support.
    - Installing an SSL certificate using something like `certbot` or `acmesh` and `freedns`. Since your VM IP will change if you shut down your VM, I recommend starting and configuring it well in advance of your lab, to account for any possible delays with the DNS propagation. Everything should now use `https`. Make sure you update your frontend accordingly.
    - Making both `nginx` and your application server services that start when your VM starts and that you control with commands such as `sudo systemctl start nginx`, `sudo systemctl start your_app_server`. You might be asked to show this by restarting your VM.



----
----

## Lab 6 assignment

**Points**: 0.3, 0.3 Bonus

**Deadline**: Week 7

**Last chance deadline and penalties**: Week 9, -0.1 points / week delayed

This assignment, as well as all future ones, must be completed on your deployed applications.

You will need to:
- Use an actual database server: I recommend PostgreSQL (and MS SQL for .NET projects), but MySQL or NoSQL databases are also accepted. No embedded databases are accepted. Please **do not** attempt this assignment with SQLite, embedded H2 or anything similar as it will not be accepted.
- Populate your database with at least `1 000 000` (one million) records for each entity. Add `10 000 000` (ten million) records for the entity acting as an intermediary in the many to many relation. This should be done through SQL scripts (one or more files) that you execute outside of your application. The data should be realistic looking: don't add complete gibberish, don't add exclusively the same value for any of the attributes, don't add sequential data where it doesn't make sense etc. It's ok if there is some repetition in the data, outside of unique fields. You can use a service such as https://www.onlinedatagenerator.com/ to help you out, but you might need some manual fine-tuning. The script should execute in a reasonable amount of time. Some ideas and suggestions below:  
  - The data doesn't need to be 100% realistic. You can have a few unique values that you randomly repeat across the many records and that you concatenate things like "\_1", "\_2" etc. to. If they don't need to be unique values, you don't even have to concatenate anything to them. For non-unique attributes, you actually **want** some amount of repetition.  
  - You can generate random values for things such as age, year etc.  
  - The SQL script can have logic such as loops in it.  
  - Although the end result needs to be at least one SQL script, you can use an external program (such as a Python script) that generates the SQL file by writing stuff in it. It might be easier to have things like lists that store base values in Python / other languages compared to pure SQL. Consider using the `Faker` library for this: https://faker.readthedocs.io/en/master/. 
  - You might want to batch the inserts to make things faster: `INSERT INTO tbl_name (a, b, c) VALUES (1, 2, 3), (4, 5, 6), (7, 8, 9);`. Batches of size `1000` generally work pretty well.
  - For the many to many intermediate table, make sure that you don't repeat the same pair of related IDs (unless this makes sense in your particular case).  
- Start using migrations if you haven't already: rerunning the application or changing something in your data models should **not** drop and recreate the database.
- This amount of data will likely break many things in your application, especially the read all functionalities. You **don't have to** fix this **yet**, but **0.3 points** are available as a bonus if you attempt to optimize and fix things by week 7. You will present this assignment by running queries and by showing your SQL files and any scripts that generated them.

Just like the previous assignments, this one will also have a live coding part during the lab. Try to come up with a robust solution that you can easily adapt to any requested changes or enhancements.


----
----

## Lab 5 assignment

**Points**: 0.3

**Deadline**: Week 6

**Last chance deadline and penalties**: Week 8, -0.1 points / week delayed

You will need to:
- Add a frontend to your REST API. It should be a Single Page Application (SPA). You can choose any frontend framework or library that lets you create SPAs: React, Angular, Vue etc. It should be something that is under active development.
- Add Swagger to your REST API. It should be publicly accessible.
- Implement at least one CRUD and one filter / statististical report on the frontend.
- Users should be able to navigate the interface without manually editing the URL. The interface should also be intuitive and self-explanatory: if you need to explain it to your lab teacher, it's not good enough.
- Deploy the frontend either on https://www.netlify.com/ (or equivalent) or on your virtual machine. If you choose netlify or equivalent, it should deploy automatically when you commit to github.
- Try to make it look nice and user friendly by adding a UI Components Library like Material UI, Bulma, or even something like Bootstrap, Tailwind etc. This is optional for now, but it will be required later, and you will save time in the long run if you start doing it from the beginning.
