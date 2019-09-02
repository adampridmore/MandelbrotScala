import SequenceHelper._
import org.scalatest.{Matchers, WordSpec}

class SequenceHelperSpec extends WordSpec with Matchers {
  "Unfold" should {
    "can generate a sequence of numbers" in {
      unfold(0)(i => Some(i.toString, i + 1))
        .take(3) shouldBe Seq("0", "1", "2")
    }
  }

  "Unfold" should {
    "can generate a large sequence of numbers" in {
      unfold(0)(i => Some(i, i + 1))
        .drop(1000000)
        .take(3) shouldBe Seq(1000000,1000001, 1000002)
    }
  }
}
