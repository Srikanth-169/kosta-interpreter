# Table of Contents
1. [Introduction](#introduction)
2. [Overview](#overview)
3. [Kosta-language](#kosta-language)
   - [Variables and Data types](#1-variables-and-data-types)
   - [Expressions and Operators](#2-expressions-and-operators)
   - [Functions](#3-functions)
   - [Control Flow](#4-control-flow)
   - [Arrays and Hashmaps](#5-arrays-and-hashmaps)
   - [Scopes and Closures](#6-scopes-and-closures)
   - [Function Literals and Immediate Invocation](#7-function-literals-and-immediate-invocation)
4. [Setup and installation](#setup-and-installation)
5. [Code structure](#code-structure)
   - [repl](#repl)
   - [Lexer](#lexer)
   - [Parser](#parser)
   - [AST (Abstract Syntax Tree)](#ast-abstract-syntax-tree)
   - [Environment](#environment)
   - [Evaluator](#evaluator)
6. [Examples](#examples)
7. [References](#references)

---

## Introduction
This project is a Java implementation of a Monkey-like interpreter, inspired by [Writing An Interpreter In Go](https://interpreterbook.com/). This interpreter supports variable bindings, integers, booleans, characters(*coming soon*), arrays(*coming soon*), hash structures(*coming soon*), arithmetic and boolean operations like +, -, *, /, &, |, built-in functions(*coming soon*), first-class and higher order functions, closures.

---

## Overview
*(overview content.)*

---

## Kosta-language

#### 1. **Variables and Data types:**
* *Null*: A special value representing nothing.
* *Integers*: Whole numbers.
* *Booleans*: true or false.
* *Arrays*: Ordered collections of values.
* *Hashmaps*: Key-value pairs for storing structured data.

```javascript
var n = null; // Null value
var integer = 1; // Integer
var boolean = false; // Boolean
var array = [1, 2, 3, 4, 5]; // Array
var hashmap = { 'a': 1, 'b': false, 'c': 'd', 'e': [1, 2] }; // Hashmap
```

#### 2. **Expressions and Operators**
- *Arithmetic Operators*: `+`, `-`, `*`, `/`
- *Comparison Operators*: `==`, `!=`, `>`, `<`
- *Logical Operators*: `&` (AND), `|` (OR), `!` (NOT)
- *Prefix Operators*: `-` (negation), `!` (logical NOT)
- *Grouping*: Use parentheses `()` to influence evaluation order.

```javascript
var expression1 = 10 * (20 / 2); // Evaluates to 100
var expression2 = !true & false | true != 5 > 7 - 1; // Evaluates to true
var expression3 = 5 + 1 * 2 - 3 / 1 == 30; // Evaluates to false
var expression4 = -5; // Prefix operator
var expression5 = !true; // Prefix operator
var expression6 = (5 > 7 == 5 < 7) != false; // Grouping and comparison
```

#### 3. **Functions**
_Functions are first-class citizens in the language. They can_:
- Be defined using the `fn` keyword.
- Accept parameters and return values.
- Support recursion and higher-order functions.
- Be passed as arguments or returned from other functions.

```javascript
var add = fn(a, b) { return a + b; }; // Function definition
add(1, 2); // Function call

var factorial = fn(n) { // Recursive function
  if (n == 0) { 1; } 
  else { n * factorial(n - 1); }
};

var higherOrderFunction = fn(f, x) { return f(f(x)); }; // Higher-order function
var invert = fn(b) { !b; };
higherOrderFunction(invert, true); // Evaluates to true
```

#### 4. **Control Flow**
_Language supports conditional expressions using `if-else`. These expressions can be used anywhere a value is expected_.

```javascript
var ifExpression = if (!a == b) { true; } else { false; }; // If-else expression
var onlyIfExpression = if (true) { false; }; // Only if expression is also valid
```

#### 5. **Arrays and Hashmaps**
_Arrays and hashmaps, with the ability to access and modify elements using dot notation_.

```javascript
var array = [1, 2, 3, 4, 5];
var firstElement = array.0; // Accessing array elements
array.1 = 4; // Replace 2 with 4 in array


var hashmap = { 'a': 1, 'b': false, 'c': 'd', 'e': [1, 2] };
var valueA = hashmap.'a'; // Accessing hashmap elements
hashmap.'a' = 'b'; // Update 'a'
hashmap.'h' = 5; // Put new key value pair
```

#### 6. **Scopes and Closures**
_Language supports lexical scoping, meaning functions capture variables from their surrounding environment_.

```javascript
var i = 5;
var iSeeYou = fn() { return i + 1; }; // Captures 'i' from outer scope

var outerFunction = fn() { 
  var a = true; 
  return fn() { a | b; }; // Captures 'a' from outer scope
};
```

#### 7. **Function Literals and Immediate Invocation**
_Functions can be defined and invoked immediately which are called function literals_.

```javascript
fn(a, b) { a + b; }(1, 3) + 6; // Evaluates to 10
fn(x) { return factorial(x); }(3); // Evaluates to 6
```

---

## Setup and installation
*(setup and installation.)*

---

## Code structure

### repl
*(REPL.)*

### Lexer
*(Lexer content.)*

### Parser
*(Parser.)*

### AST (Abstract Syntax Tree)
*(AST.)*

### Environment
*(Environment.)*

### Evaluator
*(Evaluator.)*

---

## Examples
*(examples.)*

---

## References
*(references.)*

---

