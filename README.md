## eShop 

### Module 1

<details> 
<summary>Reflection 1</summary>
In short, clean code refers to a program that is easy to develop and adaptable to new features. 
To achieve clean code, we need the motivation to continuously refine the code by following certain best practices.
Below are the clean code principles applied in Exercise 1:

1. Meaningful Names

This means using clear and descriptive variable names so that the code is self-explanatory
(it explains itself without requiring additional comments).

Example:
```java
public Product findProductById(String productId) {
    ...
}
```

This method clearly indicates that it searches for a product by its ID.

2. Functions

For that each function should only perform a single responsibility effectively.

Example:

```java
public Iterator<Product> findAll() {
    ...
}
```
This method retrieves all products without performing any other unrelated operations.
Some other examples like deleting, editing, and creating have each own seperate function and not mixed into one.

3. Comments

Writing good comments does not necessarily make a codebase good. Most of my code does not include comments because it is already self-explanatory.

4. Objects and Data Structures

The details of data structures should not be overly exposed. Instead, data should be abstracted or privated properly.

Example Implementation:
Located in the repository directory, the ProductRepository.java class hides the details of data management. 
Specifically, in the productData list is where all the created product object is stored as of implementing the data structure.

5. Error Handling

Some best practices for error handling include:

a. Using try-catch-finally blocks when necessary.

b. Throwing clear and specific exceptions.

c. Avoiding returning or passing null.

d. and many more

In my program, there are still areas that can be improved. For instance:
```java
public Product findProductById(String productId) {
    return productData.stream().filter(product -> product.getProductId().equals(productId)).findFirst().orElse(null);
}
```  
The findProductById method returns null, which is not considered a best practice.

</details>

<details> 
<summary>Reflection 2</summary>
Unit testing should cover positive and even negative scenarios because it is essential for software development. Achieving 100% of code coverage doesnt mean that my code has no errors or bugs,
since the tests might not cover all possible real world inputs and the code might be covered but not properly validated. So a single method should ideally have multiple test cases covering these different aspects.
Moreover it makes it faster to verify if a method since it doesnt have to be done manually. Overall, adding unit testing and passing all of it makes me believe that my code is more secure rather that not having unit tests.


Creating another functional test suite to verify the number of items in the product list would risk introducing unnecessary code duplication. 
Repeating setup logic and instance variables across multiple test classes increases maintenance effort. A more efficient way would be to extract shared setup logic into a base test class and have individual test suites inherit from it.
Maintaining duplicate code can lead to inconsistencies if one test is updated while the others remain unchanged. Refactoring test cases to use reusable utility methods for common actions like creating and navigating would improve the unit test.
In conclusion, reducing redundancy and centralizing shared logic is what i would implement for a cleaner code.

</details> 

### Module 2

<details> 
<summary>Reflection</summary>

From the beginning, based on ScoreBoard, my test coverage was not even close to 90% so i had to change and improve all the test, mostly using mock. After creating unit test and fixing some of the classed from main, the code was 100% successful. During the CI process i ran into failures after failures. This was proven by GitHub Actions. The issue was that the HTML templates were not being detected correctly. So i had to fix a typo due to case-sensitive naming such as “productList” should’ve been “ProductList” in ProductController.java.



For the implementation of CI/CD workflows, i used module 2 for the reference. I used OSSF scorecard and PMD. Both PMD and OSSF scorecard allows me to check and detect my code incase there are issues needed to be solved. By checking GitHub Actions for test from pushes with ci.yml for Continunois Integration. As for CD, Continuous Deployment, I used Koyeb to deploy the program. I connected Koyeb with GitHub repo using Koyeb’s API access token. Everytime there is a push to my GitHub repo, Koyeb deploys the webservice build for the eshop.

</details>

### Module 3

<details>
<summary>Reflection</summary>

**A. principles applied to the project**

I implemented all the SOLID Principles:
1. S stands for Single Resposibility Principle(SRP): 
   This principle tells us that a class should only have one responsibility or does one thing. Previously, i forgot to add HomePage class, hence i added it here as its own class based on SRP. During before-solid tutorial, the CarController class was an extension of ProductController but this violates the SRP Principles since it only handles car and not products. So what i did was to make its own class called CarController.

2. O stands for Open Closed Principles (OCP):
   An open or closed principle means that software entities such as the parent classes are open only for extension, meanwhile it can't be modified / closed for modifying. For CarRepository, i added a InterfaceRepository class that allows me to create a new repo for upcoming changes without changing previous codes.

3. L stands for Liskov Substitution Principle (LSP):
   This principle states that a subclass can change its superclass without disruption the functionality of the program. Quite similar to the previous implementation, i made CarController its own class when previously it was a subclass of ProductController. This is due to CarController not being able to replace ProductController’s functions, vice versa.

4. I stands for Interface Segregation Principle (ISP):
   It means that an Interface needs its own task to do rather than needing to use all of the methods in a class. For example, the interface of CarService. Hence splitting InterfaceRepository to WriteRepository and ReadRepository for its own tasks.

5. D stands for Dependency Inversions Principle (DIP):
   In general, the principle states that high level modules should not depend on low level modules because both should depend on abstractions. I modified `private CarServiceImpl carservice;` into `private CarService carservice;` because a module relies on a class/interface that is abstract instead of a concrete one based on DIP.


**B. The advantages of applying SOLID principle**

1. The first thing is that the code is now readable due to each method having its own tasks, for example, the controller directory and its classes.

2. InterfaceRepository with its ReadRepository and WriteRepository, allowing code flexibility for not needing a modification when a new repository is made.

3. Since each class and interface has a single responsibility, future modifications and bug fixes are easier to implement without affecting other parts of the code.


C. **The diadvantages of NOT applying SOLID principle**

1. If CarController remained a subclass of ProductController then any change in ProductController could affect CarController. Hence making it harder to maintain and debug.

2. Without ISP, CarService might be forced to implement unnecessary methods. This may lead to redundant or empty method implementations.

3. Using CarServiceImpl directly instead of CarService or not implementing DIP makes it difficult to switch to a new implementation.

</details>