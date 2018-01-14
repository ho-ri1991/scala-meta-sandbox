package com.example

import scala.annotation.{StaticAnnotation, compileTimeOnly}
import scala.meta._

@compileTimeOnly("TupleGenerator not expanded")
class TupleGenerator(n: Int) extends StaticAnnotation {
  inline def apply(defn: Any): Any = meta {
    val q"object $name" = defn
    val q"new $_(${num: Term.Arg})" = this

    val tupleDefs = for(i <- 1 to num.toString.toInt) yield {
      val genericParams = (1 to i).map(j => s"A${j}").mkString(", ")
      val members = (1 to i).map(j => s"_${j}: A${j}").mkString(", ")
      s"case class MyTuple${i}[${genericParams}](${members})".parse[Stat].get
    }

    val tupleFactoryDefs = for(i <- 1 to num.toString.toInt) yield {
      val genericParams = (1 to i).map(j => s"A${j}").mkString(", ")
      val argDefs = (1 to i).map(j => s"_${j}: A${j}").mkString(", ")
      val args = (1 to i).map(j => s"_${j}").mkString(", ")
      s"def MyTuple[${genericParams}](${argDefs}) = MyTuple${i}(${args})".parse[Stat].get
    }

    val body = s"case class MyTuple[A](a${num}: A)".parse[Stat].get
    q"""
        object $name {
          ..$tupleDefs
          ..$tupleFactoryDefs
        }
       """
  }
}
