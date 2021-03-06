/*                     __                                               *\
**     ________ ___   / /  ___     Scala API                            **
**    / __/ __// _ | / /  / _ |    (c) 2003-2010, LAMP/EPFL             **
**  __\ \/ /__/ __ |/ /__/ __ |    http://scala-lang.org/               **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
\*                                                                      */



package scala.collection
package mutable

import generic._

/** This class implements double linked lists where both the head (`elem`),
 *  the tail (`next`) and a reference to the previous node (`prev`) are mutable.
 *
 *  @author Matthias Zenger
 *  @author Martin Odersky
 *  @version 2.8
 *  @since   1
 *
 *  @tparam A     the type of the elements contained in this double linked list.
 *
 *  @define Coll DoubleLinkedList
 *  @define coll double linked list
 *  @define thatinfo the class of the returned collection. In the standard library configuration,
 *    `That` is always `DoubleLinkedList[B]` because an implicit of type `CanBuildFrom[DoubleLinkedList, B, DoubleLinkedList[B]]`
 *    is defined in object `DoubleLinkedList`.
 *  @define $bfinfo an implicit value of class `CanBuildFrom` which determines the
 *    result class `That` from the current representation type `Repr`
 *    and the new element type `B`. This is usually the `canBuildFrom` value
 *    defined in object `DoubleLinkedList`.
 *  @define orderDependent
 *  @define orderDependentFold
 *  @define mayNotTerminateInf
 *  @define willNotTerminateInf
 */
@serializable @SerialVersionUID(-8144992287952814767L)
class DoubleLinkedList[A]() extends LinearSeq[A]
                            with GenericTraversableTemplate[A, DoubleLinkedList]
                            with DoubleLinkedListLike[A, DoubleLinkedList[A]] {
  next = this

  /** Creates a node for the double linked list.
   *
   *  @param elem    the element this node contains.
   *  @param next    the next node in the double linked list.
   */
  def this(elem: A, next: DoubleLinkedList[A]) {
    this()
    if (next != null) {
      this.elem = elem
      this.next = next
    }
  }

  override def companion: GenericCompanion[DoubleLinkedList] = DoubleLinkedList
}

/** $factoryInfo
 *  @define coll double linked list
 *  @define Coll DoubleLinkedList
 */
object DoubleLinkedList extends SeqFactory[DoubleLinkedList] {
  /** $genericCanBuildFrom */
  implicit def canBuildFrom[A]: CanBuildFrom[Coll, A, DoubleLinkedList[A]] = new GenericCanBuildFrom[A]
  def newBuilder[A]: Builder[A, DoubleLinkedList[A]] =
    new Builder[A, DoubleLinkedList[A]] {
      var current: DoubleLinkedList[A] = _
      val emptyList = new DoubleLinkedList[A]()
      if(null == current)
        current = emptyList

      def +=(elem: A): this.type = {
        if (current.nonEmpty)
          current.insert(new DoubleLinkedList(elem, emptyList))
        else
          current = new DoubleLinkedList(elem, emptyList)
        this
      }

      def clear() {
        current = emptyList
      }
      def result() = current
    }
}
