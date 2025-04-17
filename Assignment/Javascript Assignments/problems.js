//Multiplication Table
let number = 5;
console.log(`Multiplication Table for ${number}:`);
for (let i = 1; i <= 10; i++) {
  console.log(`${number} x ${i} = ${number * i}`);
}

//Factorial of a Number
function factorial(n) {
  let fact = 1;
  for (let i = 2; i <= n; i++) {
    fact *= i;
  }
  return fact;
}

let num = 5;
console.log(`Factorial of ${num} is:`, factorial(num));


//Fibonacci Series
function fibonacci(n) {
    let a = 0, b = 1;
    console.log(`First ${n} Fibonacci numbers:`);
    for (let i = 0; i < n; i++) {
      console.log(a);
      let next = a + b;
      a = b;
      b = next;
    }
  }
  
  fibonacci(7); 
  

  //prime number check
  function isPrime(n) {
    if (n <= 1) return false;
    for (let i = 2; i <= Math.sqrt(n); i++) {
      if (n % i === 0) return false;
    }
    return true;
  }
  
  let primeCheck = 29;
  console.log(`${primeCheck} is ${isPrime(primeCheck) ? 'a Prime' : 'Not a Prime'} number.`);


  //sum off digits
  function sumOfDigits(n) {
    let sum = 0;
    while (n > 0) {
      sum += n % 10;
      n = Math.floor(n / 10);
    }
    return sum;
  }
  
  let digitNum = 1234;
  console.log(`Sum of digits of ${digitNum} is:`, sumOfDigits(digitNum));
  
  
  // reverse a string
  function reverseString(str) {
    return str.split("").reverse().join("");
  }
  console.log(reverseString("hello")); 
  
  //Palindrome Checker
  function isPalindrome(str) {
    const reversed = str.split("").reverse().join("");
    return str === reversed;
  }
  
  console.log(isPalindrome("madam"));  
  console.log(isPalindrome("hello"));  


  //Find Largest Number in Array
  function findLargest(arr) {
    return Math.max(...arr);
  }
  console.log(findLargest([10, 20, 99, 5, 34])); 


  //Count Vowels in a String
  function countVowels(str) {
    const vowels = 'aeiouAEIOU';
    let count = 0;
    for (let char of str) {
      if (vowels.includes(char)) count++;
    }
    return count;
  }
  console.log(countVowels("Makizhna")); 


  //Remove Duplicates from Array
  function removeDuplicates(arr) {
    return [...new Set(arr)];
  }
  console.log(removeDuplicates([1, 2, 2, 3, 4, 4, 5])); 
  
  

  