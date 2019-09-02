import org.scalatest.FunSuite

class MyTest extends FunSuite{
  test("myTest"){
    lazy val ones: Stream[Int] = Stream.cons(1, ones)

    println(ones.take(5).toList)
  }
}
