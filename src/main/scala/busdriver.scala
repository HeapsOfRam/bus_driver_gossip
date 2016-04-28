package busdriver

class BusDriver(driverId: Int, route_in: Array[Int]) {
    val route:Array[Int] = route_in
    var gossipList:Array[Int] = Array(driverId)
    var stop_number = 0

    def moveToNextStop() {
        stop_number = stop_number + 1
    }

    def currentStop(): Int = {
        return route(stop_number % route.length)
    }

    def getGossip(): Array[Int] = {
        return gossipList
    }

    def isAtSameStopAs(SecondDriver: BusDriver): Boolean = {
        return currentStop == SecondDriver.currentStop
    }

    def atStopWith(AnyDriver: Array[BusDriver]): Array[BusDriver] = {
        var driverGroup:Array[BusDriver] = Array[BusDriver]()

        for( d <- AnyDriver ){
            if(this.isAtSameStopAs(d)){
                driverGroup = driverGroup :+ d
            }
        }

        return driverGroup
    }

    def exchangeGossipWith(SecondDriver: BusDriver) {
        gossipList = gossipList ++ SecondDriver.getGossip()
        gossipList = gossipList.distinct
    }
}
