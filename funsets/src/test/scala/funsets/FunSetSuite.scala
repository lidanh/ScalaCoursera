package funsets

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import junit.extensions.TestSetup

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {


  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  test("string take") {
    val message = "hello, world"
    assert(message.take(5) == "hello")
  }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  test("adding ints") {
    assert(1 + 2 === 3)
  }

  
  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }
  
  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   * 
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   * 
   *   val s1 = singletonSet(1)
   * 
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   * 
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   * 
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s4 = (x: Int) => x > 5
    val s5 = (x: Int) => x < 7
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   * 
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {
    
    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3". 
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }
  
  test("s1 intersects s2 is null") {
    new TestSets {
      val s = intersect(s1, s2)
      assert(!contains(s, 1))
      assert(!contains(s, 2))
    }
  }
  
  test("s1 diff s2") {
    new TestSets {
      val s = diff(s1, s2)
      assert(contains(s, 1))
      assert(!contains(s, 2))
    }
  }
  
  test("filter") {
    new TestSets {
      val s_notContains = filter(s1, x => x < 1)
      assert(!contains(s_notContains, 1))
      
      val s_contains = filter(s1, x => x < 2)
      assert(contains(s_contains, 1))
    }
  }
  
  test("filter of {1,3,4,5,7,1000} for _ < 5") {
    new TestSets {
      val orig_set = union(union(union(union(union(singletonSet(1), singletonSet(3)), singletonSet(4)), singletonSet(5)), singletonSet(7)), singletonSet(1000))
      val s = filter(orig_set, x => x < 5)
      assert(contains(s, 1))
      assert(contains(s, 3))
      assert(contains(s, 4))
      assert(!contains(s, 5))
      assert(!contains(s, 7))
      assert(!contains(s, 1000))
    }
  }
  
  test("forall elements in set [s1 U s2 U s3]") {
    new TestSets {
      val s = union(union(s1, s2), s3)
      assert(forall(s, x => x < 4))
      assert(!forall(s, x => x < 3))
    }
  }
  
  test("(x < 2) exists in s1 U s2 U s3 but (x < 1) not") {
    new TestSets {
      val s = union(union(s1, s2), s3)
      assert(exists(s, x => x < 2))
      assert(!exists(s, x => x < 1))
    }
  }
}
