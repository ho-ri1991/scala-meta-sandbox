import scala.annotation.{StaticAnnotation, compileTimeOnly}
import scala.collection.immutable.Seq
import scala.meta._

object Main {
//  import MetaFunctions._
  import com.example.TupleGenerator

  @TupleGenerator(10) object Test

  def main(args: Array[String]): Unit = {
    val t = Test.MyTuple2(1, 1.5)
    println(t._2)
    val t3 = Test.MyTuple(1, "aa", false)
    println(t3)
    val t10 = Test.MyTuple(1, false, 1, "a", 1, 1, 1.0, 1, 1, 1)
    println(t10)
  }
}
