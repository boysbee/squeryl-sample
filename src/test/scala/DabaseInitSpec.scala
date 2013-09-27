package db

import db._
import org.scalatest._
import org.scalatest.matchers.ShouldMatchers


class DatabaseInitSpec extends FreeSpec with BeforeAndAfter with ShouldMatchers with DatabaseInit {
	
	"A DatabaseInit" - {
		
		"should connect to database" in {
			configureDb()
		}

		// "should close connection database" in {
		// 	closeDbConnection()
		// }
		
	}

	
}

