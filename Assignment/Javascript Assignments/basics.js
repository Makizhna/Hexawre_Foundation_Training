//Print Hello World
console.log("Hello, World!");

//Variables and Data Types
let name = "Makizhna";
console.log("My name is:", name);

let num1 = 12;
let num2 = 4;

console.log("Sum:", num1 + num2);
console.log("Difference:", num1 - num2);
console.log("Product:", num1 * num2);
console.log("Quotient:", num1 / num2);

//Simple Calculator
let a = 15;
let b = 3;
let operator = "-";        // use +, -, *, /

let result;

switch (operator) {
  case "+":
    result = a + b;
    break;
  case "-":
    result = a - b;
    break;
  case "*":
    result = a * b;
    break;
  case "/":
    result = a / b;
    break;
  default:
    result = "Invalid Operator";
}
console.log(`${a} ${operator} ${b} = ${result}`);


//Odd or Even
let number = 7;

if (number % 2 === 0) {
  console.log(number + " is Even");
} else {
  console.log(number + " is Odd");
}


//Maximum of Three Numbers
let x = 25, y = 17, z = 30;
let max = x;
if (y > max) max = y;
if (z > max) max = z;
console.log(`Maximum of ${x}, ${y}, and ${z} is ${max}`);
