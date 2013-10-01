package db

import org.scalatest._
import org.scalatest.matchers._
import org.squeryl.Schema
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Session
import org.squeryl.SessionFactory

class PersonSpec extends FunSuite with GivenWhenThen with BeforeAndAfter with ShouldMatchers {

	BaseTest.mock
	
	test("It should be add") {
		Given("a new person data")	
		var p = new Person(1,"test")
		When("insert to Person table")
		transaction {
			p = PersonSchema.persons.insert(p)	
		}
		assert(p != null)
		Then("insert success")
		p.personId should be (1)
	}

	test("It should be retreive with an id") {
		Given("an person_id for find data")
		val id = 1
		var p : Person = null
		When("retreive with person_id")
		transaction {
			p = PersonSchema.persons.where(a => a.id === 1).single	
		}
		Then("return data not null")
		assert(p != null)
		p.firstname should be ("test")
	}
	
	test("It should be update") {
		Given("a new first_name")
		var p : Person = new Person(1,"nott")
		When("update new first_name")
		 transaction {
		 	PersonSchema.persons.update(p)
		}
		Then("update success")
		var p1 : Person = null
		transaction {
			p1 = PersonSchema.persons.where(a => a.id === 1).single	
		}
		And("first_name is 'nott'")
		p1.firstname should be ("nott")
	}
}

object BaseTest extends Schema {

	def mock {
		DB.configureDb()

		transaction {
			// val s = Session.currentSession
			// val conn = s.connection.asInstanceOf[java.sql.Connection]
	        val stmt = Session.currentSession.connection.createStatement
			var sql = "drop table person"

			try { 
			    stmt.execute(sql);
			} catch {
			  case e: Exception => println("Table not found, not dropping")
			}

			sql = "create table person (person_id int primary key,first_name varchar(64))"

			try { 
			  stmt.execute(sql)
			} catch {
			  case e: Exception => println("Can't create table")
			}
		}
	}
}
