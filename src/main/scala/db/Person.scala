package db

import org.squeryl._
import org.squeryl.PrimitiveTypeMode._

// create Person class which has fields same as table Person
case class Person (id : Long , firstname : String)

object PersonSchema extends Schema {

	val persons = table[Person]("Person")


	def add(o : Person) : Person = {
		val p = persons.insert(o)
		return p
	}

	def findById(id : Long ) : Person = {
		var person : Person = null
		// Select Person with id=1 from the Person table
		transaction {
			person = persons.where(c=> c.id === 1).single
		}

		return person
	}


}