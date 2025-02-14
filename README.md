> eShop with Spring Boot

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
