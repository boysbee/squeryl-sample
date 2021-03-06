package db

import com.mchange.v2.c3p0.ComboPooledDataSource
import org.squeryl.adapters.H2Adapter
import org.squeryl.Session
import org.squeryl.SessionFactory
import org.squeryl.Schema
import org.slf4j.LoggerFactory
import org.squeryl.PrimitiveTypeMode._


trait DatabaseInit {
  val logger = LoggerFactory.getLogger(getClass)

  val databaseUsername = "sa"
  val databasePassword = ""
  val databaseConnection = "jdbc:h2:mem:mock"

  var cpds = new ComboPooledDataSource

  def configureDb() {
    cpds.setDriverClass("org.h2.Driver")
    cpds.setJdbcUrl(databaseConnection)
    cpds.setUser(databaseUsername)
    cpds.setPassword(databasePassword)

    cpds.setMinPoolSize(1)
    cpds.setAcquireIncrement(1)
    cpds.setMaxPoolSize(50)

    SessionFactory.concreteFactory = Some(() => connection)

    def connection = {
      logger.info("Creating connection with c3po connection pool")
      Session.create(cpds.getConnection, new H2Adapter)
    }
  }

  def closeDbConnection() {
    logger.info("Closing c3po connection pool")
    cpds.close()
  }	
}

object DB extends DatabaseInit {
  
}
