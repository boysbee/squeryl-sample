package db

import org.scalatest._
import org.scalatest.matchers._
import org.squeryl.Schema
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Session
import org.squeryl.SessionFactory

class PersonSpec extends FunSuite with GivenWhenThen with BeforeAndAfter with ShouldMatchers {

	BaseTest.mock
	
	test("it should be add") {
		Given("an id")
		val id = 1
		And("firstname is 'test'")
		val firstname = "test"

		When("insert to table Person")
		var p : Person = null
		transaction {
			p = PersonSchema.add(new Person(1,"test"))
		}

		Then("return firstname is 'test'")
		// println("firstname : %s".format(p.firstname))
		p.firstname should be ("test")
		
	}

	test("it should be retreive with an id") {
		Given("an id")
		val id = 1
		When("find by id")
		var p = PersonSchema.findById(id)
		Then("return object person not null")
		assert(p != null)
		p.firstname should be ("test")
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

			sql = "create table person (id int primary key,firstname varchar(64))"

			try { 
			  stmt.execute(sql)
			} catch {
			  case e: Exception => println("Can't create table")
			}
		}
	}
}
