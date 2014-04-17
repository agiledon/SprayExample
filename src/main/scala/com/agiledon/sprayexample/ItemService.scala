package com.agiledon.sprayexample

import java.util.Date
import akka.actor.Actor
import akka.event.Logging

trait ItemOperations {

  def getById(id: Long) = {
    OneCustomer(new Customer(id, new Date(1000), "item1"))
  }

  def all() =  {
    try{
      ListCustomers(List(new Customer(100, new Date(1000), "item1")))
    } catch{
      case e:Exception => {
        println(e.getMessage())
        List()
      }
    }
  }

  def delete(id: Long) = {
    Success("deleted successfully")
  }

  def create (dueDate: Date, text: String) =  {

    Created("")
  }

  /**
   * modify an existing TodoItem
   *
   * @param item the item to modify
   * @return the modified item
   */
  def update (item: Customer) = {
    getById(item.id)
  }
}

/**
 * Actor to provide the Operations on TodoItems
 */
class ItemActor extends Actor with ItemOperations{
  val log = Logging(context.system, this)
  def receive = {
    case GetCustomer(id) => sender ! getById(id)
    case UpdateCustomer(item) => sender ! update(item)
    case DeleteCustomer(id) => sender ! delete(id)
    case CreateCustomer(dueDate, text) => sender ! create(dueDate, text)
    case AllCustomers => sender ! all()
  }
}
