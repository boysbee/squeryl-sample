package db

import org.squeryl._
import org.squeryl.KeyedEntity
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.annotations.{Column}

// create Person class which has fields same as table Person
class Person (@Column("person_id") val id : Long = 0, var firstname : String)  extends KeyedEntity[Long] {
	
	def personId = id

}

object PersonSchema extends Schema {

	val persons = table[Person]("Person")

	on(persons)(p => declare(
      p.id is (primaryKey),
      p.firstname is (named("first_name"))
    ))

}