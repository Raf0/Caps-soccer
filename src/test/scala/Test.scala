import org.scalatest.FlatSpec
import Elements.Cap

class SetSpec extends FlatSpec {
    val cap = Cap(100, 100, 1)

    "A Cap" should "collide with three caps" in {
        assert(cap.isCollide(120, 120, 10))
        assert(cap.isCollide(140, 100, 0))
        assert(cap.isCollide(100, 150, 10))
    }

    it should "not collide with three caps" in {
        assert(!cap.isCollide(130, 130, 0))
        assert(!cap.isCollide(150, 100, 0))
        assert(!cap.isCollide(200, 200, 60))
    }

    it should "have distance = 100" in {
        assert(cap.distance(200,100) == 100)
    }
}