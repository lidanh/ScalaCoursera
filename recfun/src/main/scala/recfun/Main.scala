package recfun
import common._

object Main {
  def main(args: Array[String]) {
    /*println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }*/
    
    /*println("Parentheses Balancing")
    println(balance("".toList))*/
    
    /*println("Counting Change")
    println(countChange(10, List(1,2,5,10,20,50,100,200)))*/
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int =
    if (r == c || c == 0) 1
    else pascal(c - 1, r - 1) + pascal(c, r - 1)

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    def checkBalance(chars: List[Char], total: Int): Boolean =
      if (chars.isEmpty) total == 0
      else if (total < 0) false
      else if (chars.head == ')') checkBalance(chars.tail, total - 1)
      else if (chars.head == '(') checkBalance(chars.tail, total + 1)
      else checkBalance(chars.tail, total)
      
    checkBalance(chars, 0)
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = 
    if (money == 0) 1
    else if (money < 0 || coins.isEmpty) 0
    else countChange(money, coins.tail) + countChange(money - coins.head, coins)
}
