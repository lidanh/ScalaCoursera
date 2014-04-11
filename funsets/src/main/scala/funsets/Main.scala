package funsets

object Main extends App {
  import FunSets._
  println(contains(singletonSet(1), 1))
  
  val orig_set: Set = union(union(union(union(union(singletonSet(1), singletonSet(3)), singletonSet(4)), singletonSet(5)), singletonSet(7)), singletonSet(1000))
  printSet(orig_set)
  val s = map(orig_set, x => x - 1)
  printSet(s)
}
