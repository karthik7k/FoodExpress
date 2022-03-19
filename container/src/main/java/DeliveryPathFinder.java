import dataaccess.handlers.CustomerDataAccess;
import dataaccess.handlers.RestaurantDataAccess;
import database.CustomerDB;
import database.RestaurantDB;
import entities.CustomerEntity;
import entities.Location;
import entities.Order;
import entities.RestaurantEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

@Component
public class DeliveryPathFinder {

    @Autowired
    private CustomerDB customerDB;
    @Autowired
    private RestaurantDB restaurantDB;

    @Autowired
    private RestaurantDataAccess restaurantDataAccess;
    @Autowired
    private CustomerDataAccess customerDataAccess;

    private static final double SPEED = 0.333;//speed in km/min
    private static final int R = 6371; // Radius of the earth
    private List<Location> minimumDistancePath;
    private double minDistance;

    private void driverProgram(){
        customerDB.getCustomerEntities().add(new CustomerEntity(1,"C1", new Location(3,4)));
        customerDB.getCustomerEntities().add(new CustomerEntity(2,"C2", new Location(2,6)));

        restaurantDB.getRestaurantEntities().add(new RestaurantEntity(3,"R1", new Location(4,0)));
        restaurantDB.getRestaurantEntities().add(new RestaurantEntity(4,"R2", new Location(1,7)));
    }

    public void run(){
        driverProgram();

        List<Order> orders = new ArrayList<>();
        orders.add(new Order(1,3, 0.5));
        orders.add(new Order(2,4, 1));

        Location deliveryPartner = new Location(0,0);

        List<Location> path = new ArrayList<>();
        path.add(deliveryPartner);
        minDistance = Double.MAX_VALUE;

        int nearestOrderReadyForPickupIndex = 0;
        double leastDistance = Double.MAX_VALUE;
        for(Order order : orders){
            double orderDistance = getDistanceWithPreparationTime(deliveryPartner,
                    ((RestaurantEntity)restaurantDataAccess.getData(order.getRestaurantId()).get()).getLocation(), order.getPreparationTime());
            if(orderDistance < leastDistance){
                leastDistance = orderDistance;
                nearestOrderReadyForPickupIndex = orders.indexOf(order);
            }
        }

        orders.get(nearestOrderReadyForPickupIndex).setOrderStatus(Order.Status.ORDER_PICKED);
        path.add(((RestaurantEntity)restaurantDataAccess.getData(orders.get(nearestOrderReadyForPickupIndex).getRestaurantId()).get()).getLocation());


        traverse(path, orders, 1, leastDistance);
        //The final output which should be given to delivery partner is variable "minimumDistancePath"
    }

    //recursive function to iterate through all possible scenarios to get the least distance path
    private double traverse(List<Location> path, List<Order> orders, int stopNumber, double distance) {
        if(stopNumber == orders.size()*2+1){
            if(distance < minDistance){
                minimumDistancePath = path;
                minDistance = distance;
            }
            return distance;
        }

        for(Order order : orders){
            double orderDistance = getDistanceWithPreparationTime(path.get(stopNumber),
                    ((RestaurantEntity)restaurantDataAccess.getData(order.getRestaurantId()).get()).getLocation(), order.getPreparationTime());
            if(orderDistance <= distance && order.getOrderStatus().equals(Order.Status.PREPARING_FOOD)){
                order.setOrderStatus(Order.Status.READY_FOR_DISPATCH);
            }
            if(orderDistance > distance){
                order.setOrderStatus(Order.Status.PREPARING_FOOD);
            }
        }

        double minDistance = Double.MAX_VALUE;

        for(Order order : orders){
            if(order.getOrderStatus().equals(Order.Status.READY_FOR_DISPATCH)){
                path.add(((RestaurantEntity)restaurantDataAccess.getData(order.getRestaurantId()).get()).getLocation());
                order.setOrderStatus(Order.Status.ORDER_PICKED);
                minDistance = Math.min(minDistance, traverse(path, orders, stopNumber+1,
                        distance + getDistance(path.get(stopNumber), path.get(stopNumber+1))));
                path.remove(path.size() - 1);
                order.setOrderStatus(Order.Status.READY_FOR_DISPATCH);
            }
        }

        for(Order order : orders){
            if(order.getOrderStatus().equals(Order.Status.ORDER_PICKED)){
                path.add(((CustomerEntity)customerDataAccess.getData(order.getCustomerId()).get()).getLocation());
                order.setOrderStatus(Order.Status.ORDER_DELIVERED);
                minDistance = Math.min(minDistance, traverse(path, orders, stopNumber+1,
                        distance + getDistanceWithPreparationTime(path.get(stopNumber), path.get(stopNumber+1), order.getPreparationTime())));
                path.remove(path.size() - 1);
                order.setOrderStatus(Order.Status.ORDER_PICKED);
            }
        }

        return minDistance;
    }

    private double getDistanceWithPreparationTime(Location l1, Location l2, double preparationTime){
        return Math.max(getDistance(l1,l2), preparationTime*SPEED);
    }

    //distance according to haversine formula
    private double getDistance(Location l1, Location l2){
        double latitudeDistance = toRad(l2.getLatitude()-l1.getLatitude());
        double longitudeDistance = toRad(l2.getLongitude()-l1.getLongitude());

        double a = Math.sin(latitudeDistance / 2) * Math.sin(latitudeDistance / 2) +
                Math.cos(toRad(l1.getLatitude())) * Math.cos(toRad(l2.getLatitude())) *
                        Math.sin(longitudeDistance / 2) * Math.sin(longitudeDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double distance = R * c;
        return distance;
    }

    private static double toRad(Double value) {
        return value * Math.PI / 180;
    }

}
