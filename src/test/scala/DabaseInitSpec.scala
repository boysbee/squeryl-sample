package db

import org.scalatest._
import org.scalatest.matchers.ShouldMatchers
import org.squeryl.Session
import org.squeryl.adapters.H2Adapter
import org.squeryl.SessionFactory
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Schema


class DatabaseInitSpec extends FlatSpec with ShouldMatchers with DatabaseInit with Schema {

	"A DatabaseInit" should "use configureDb for start connection" in {
		configureDb()
	}

	"A DatabaseInit" should "retreive session at inTransaction" in {
		inTransaction {
			val s = Session.currentSession
			assert(null != s)
			val conn = s.connection.asInstanceOf[java.sql.Connection]
			assert(null != conn)
			val stmt = conn.createStatement().asInstanceOf[java.sql.Statement]
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

			sql = "insert into person values(1,'test')"
			try { 
			  stmt.execute(sql)
			} catch {
			  case e: Exception => println("Can't create table")
			}			

			sql = "select firstname from person where id =1"

			try { 
			  val rs = stmt.executeQuery(sql).asInstanceOf[java.sql.ResultSet]
			  if( rs.next()) {
			  	val firstname = rs.getString("firstname")
			  	
			  	println("@@ firstname : " + firstname)
			  	firstname should ("test")
			  }
			  rs.close()
			} catch {
			  case e: Exception => println("Can't create table")
			}

			
			stmt.close()

		}

	}

	"A DatabaseInit " should " work with Schema" in {
		// Setup the Person class to be mapped to the "Person" table in H2
		val persons = table[Person]("Person")

		// Select Person with id=1 from the Person table
		transaction {
			val person = persons.where(c=> c.id === 1).single
			person.firstname should("test")
			println("Person firstname: " + person.firstname)
		}
	}
}


// create Person class which has fields same as table Person
case class Person (id : Long , firstname : String)