package funsets

import org.scalatest.FunSuite


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

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
  // test("adding ints") {
  //   assert(1 + 2 === 3)
  // }


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
    val s4 = singletonSet(4)
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

  test("union contains all elements of each set") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("intersect") {
    new TestSets {
      val t1 = intersect(s1, s2)
      val t2 = intersect(s2, s2)
      assert(!contains(t1, 1), "Intersect 1")
      assert(contains(t2, 2), "Intersect 2")
    }
  }

  test("diff") {
    new TestSets {
      val d1 = union(s1, s2)
      val d2 = union(s2, s3)
      val d3 = diff(d1, d2)
      assert(contains(d3, 1), "Diff 1")
      assert(!contains(d3, 2), "Diff 2")
    }
  }

  test("filter") {
    new TestSets {
      val d1 = union(s1, s2)
      def f(a:Int): Boolean = a > 1
      val d2 = filter(s2, f)
      assert(contains(d2, 2), "Diff 1")
      assert(!contains(d2, 1), "Diff 2")
    }
  }

  test("forall") {
    new TestSets {
      val f1 = union(s1, s2)
      val f2 = union(s3, s4)
      val f3 = union(f1, f2)

      def f(a: Int): Boolean = a < 5

      val d2 = forall(f3, f)
      assert(d2, "Forall 1")
    }
  }

  test("exists") {
      new TestSets {
        val f1 = union(s3, s4)
        val f2 = s2
        val f3 = union(f1, f2)

        def f(a: Int): Boolean = a < 5

        val d2 = exists(f3, f)
        assert(d2, "exists 1")
      }
    }

    test("map") {
      new TestSets {
        val f1 = union(s1, s2)
        val f2 = union(s3, s4)
        val f3 = union(f1, f2)
        def f(a: Int): Int = a + 1

        val m1 = map(f3, f)

        assert(contains(m1, 2), "map 1")
        assert(contains(m1, 5), "map 2")
      }
    }
}
