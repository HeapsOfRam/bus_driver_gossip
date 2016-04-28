import busdriver.BusDriver
import scala.io.Source

object Gossip {
    def main(args: Array[String]) = {
        var all_drivers:Array[BusDriver] = Array[BusDriver]()

        for( line <- Source.fromFile("drivers.txt").getLines()) {
            val routes_strings = line.split(" ")
            var routes:Array[Int] = Array[Int]()

            for( r <- routes_strings ){
                routes = routes :+ r.toInt
            }

            all_drivers = all_drivers :+ new BusDriver(all_drivers.length, routes)
        }

        var transfersComplete = false

        for( t <- 0 to (8 * 60) ){
            for( d <- all_drivers ){
                d.moveToNextStop
            }

            for( d <- all_drivers ){
                val driverGroup = d.atStopWith(all_drivers)

                for( g <- driverGroup ){
                    d.exchangeGossipWith(g)
                }
            }

            if(gossipIsFullyShared(all_drivers) && !transfersComplete){
                transfersComplete = true
                println(t)
            }
        }

        if(!transfersComplete){
            println("never")
        }

    }

    def gossipIsFullyShared(DriverList: Array[BusDriver]): Boolean = {
        for( d <- DriverList ){
            if(d.getGossip().length < DriverList.length){
                return false
            }
        }

        return true
    }
}
