# Hotmart Code Challenge – Reactive Version

**Context:**
This project is my solution to a coding challenge from **Hotmart**, now implemented using **Spring WebFlux** to demonstrate a fully reactive, non-blocking architecture. Below is the original challenge description and requirements.

---

## Version

1.2.1

## Context

Imagine you're a developer at a tech company, and your new task is to build part of a high-traffic product marketplace.

So far, you’ve received the description of the following entities:

* [x] Product: ID, name, description, creation date
* [x] Buyer: ID, name
* [x] Seller: ID, name
* [x] Product Category: ID, name
* [x] Sale: ID, seller, buyer, product

When a buyer purchases a product, they can rate it from 0 to 5 (this rating is not yet mapped in the entities).

One of the most important parts of the marketplace being built is the product search feature. Each product will receive a daily ranking score calculated using the following formula:

```
X = Average rating of the product over the last 12 months  
Y = Number of sales / days the product has existed  
Z = Number of news articles related to the product category on the current day
```

Score = X + Y + Z

## The Challenge

Your task is to:

* Create a **CRUD** (Create, Read, Update, Delete) for products.
* Build a **Product API** exposing the CRUD operations (list, find, delete, update, insert).
* Create a service to search for products ordered by ranking, filtered by name and category.

  * The service output must include the fields `currentDate` and `searchTerm`, along with the list of products matching the search. Each returned product must include: { `id`, `name`, `description`, `creationDate`, `score` }
* All services must be **auditable**.

## Notes:

* To get the number of news articles for a given category, you can use the API at [https://newsapi.org/](https://newsapi.org/). Model the entities accordingly to fit the scenario. Also, create initial data for these entities.
* You should query the [https://newsapi.org/docs/endpoints/top-headlines](https://newsapi.org/docs/endpoints/top-headlines) endpoint **4 times a day**, store the data locally, and use the publication date of each article to support the required logic.

## What We Expect from Your Test

* We want to assess your **technical skills**, **analytical thinking** (preferably through short and clear commit messages), and how you **present results**.
* You don’t need to implement everything, but we want a clear sense of how you approach and solve problems.
* Feel free to include/modify/remove entities, attributes, technologies, jobs, or anything else necessary to better address the challenge.

## Specifically, in your project delivery, we would like to see:

* Documentation
* Proper use of libraries
* Code written with clear **semantics** – readability is as important as performance
* **Decoupling** and a clear **hierarchy** among components
* **Automated testing**

## Things That Would Impress Us

* **Fault tolerance** when calling the external News API
* **Caching** with a suitable library to improve query speed
* **Pagination support** in product listing
* **Containerization** using Docker
* Strategy to avoid excessive usage of the news API
* Automated tests beyond unit tests (e.g., integration, E2E)
* Use of a tool for **versioning data loading scripts**
* Good **solution architecture**, design patterns, and object-oriented or reactive programming
* **Security** for endpoints that persist data (e.g., in-memory auth)
* All code written in **English**

## What We Don’t Want to See

* Code that clearly wasn’t written by you
* A project that misses the core objectives
* Poorly readable code
* Lack of documentation or run instructions

## Evaluation Criteria

* Completion of proposed objectives
* Code semantics, structure, readability, maintainability, and scalability
* Project organization
* Architecture, design patterns, and use of WebFlux reactive principles
* Performance, resilience, and error handling
* API documentation
* Entity modeling

## Requirements

* Use **Java** or **Kotlin**
* Do not use tools or databases requiring commercial licenses
* Each table must have **at least 100 records**
