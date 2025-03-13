# Kosta Interpreter
Kosta is an interpreter of the high level programming language Kosta. The primary purpose of the application is to learn 
how interpreters work under the hood. Kosta will be maintained and improved by me and other contributors who are
willing to contribute in future.

# Description
At this stage of the development we have REPL(Read, Eval, Print, Loop) which reads user input, evaluates, prints the 
evaluation and starts this cycle over and over again. We pass a set of instructions as a string to our REPL. Then we 
construct lexer with provided instructions as string and with this lexer we construct parser object. We use the lexer in 
order to tokenize input string and the parser uses this lexer object to construct AST (Abstract Syntax Tree). After 
obtaining AST the evaluator will evaluate it and actually show the results.
We've used Java because of its simplicity, platform independence, and garbage collector, so we will not have to worry 
about complex things and focus more on improving interpreter. The parser uses [pratt parsing](https://matklad.github.io/2020/04/13/simple-but-powerful-pratt-parsing.html)
technique because its simplicity and how ease of it is to extend operator precedence rules. 

# How to use
1. Have at least JDK 17 or above. 
2. Clone this repository. 
3. Run ```./mvnw exec:java``` in kosta-interpreter.

# Kosta language
### Syntax
_C-style with curly braces ({}) and semicolons (;) to terminate statements._

Example:

```java
var x = 10;
if (x > 5) { pout("yes"); };
```

_Data Types:_ 

* Integers: `5, -3`
* Booleans: `true, false`
* Strings: `Hello, Kosta!`
* Arrays: `[1, "two", true]`
* Hashes (dictionaries): `{"name": "Kosta", "age": 23}`
* Functions: `First-class citizens.`
* null: `Represents absence of a value.`


_Variables_

Declared with var: 
```java
var name = "Monkey";
var pi = 3;
```

_Functions_

Defined with fn, support closures and higher-order functions:

```java
var add = fn(a, b) { return a + b; };
var greet = fn(name) { pout("Hello, " + name); };

// Higher-order function example:
var adder = fn(x) { fn(y) { x + y }; };
var addTwo = adder(2);
addTwo(3); // Returns 5
```

_Control Flow_

if/else as expressions (returning values):

```java
var result = if (x > 5) { "yes" } else { "no" };
```

_Operators_

* Arithmetic: `+, -, *, /`
* Comparison: `==, !=, <, >`


_Built-in Functions_

* `len(array|string)`: Returns length.
* `pout(value)`: Prints to output.
* `head(array), tail(array), append(array, value)`: Array utilities.


_Data Structure_

1. Arrays: Indexed with [n] (zero-based).

```java
var arr = [1, 2, 3];
arr[0]; // 1
```

2. Hashes: Accessed via keys.

```java
var person = {"name": "Alice"};
person["name"]; // "Alice"
```


### Example:
```java
// Define a factorial function
var factorial = fn(n) {
    if (n == 0) {
        return 1;
    } else {
        return n * factorial(n - 1);
    }
};

// Use a higher-order function
var apply = fn(f, x) { f(x); };
apply(factorial, 5); // Returns 120

// Create a hash and array
var data = {"numbers": [1, 2, 3], "name": "Data"};
pout(data["name"]); // Prints "Data"
```

# Contributors
<a href="https://github.com/OWNER/REPO/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=KonstantineVashalomidze/kosta-interpreter" />
</a>

