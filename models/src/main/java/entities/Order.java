package entities;

public class Order {

    private int customerId;
    private int restaurantId;
    private double preparationTime;
    private Status orderStatus;

    public Status getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Status orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(double preparationTime) {
        this.preparationTime = preparationTime;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Order(int customerId, int restaurantId, double preparationTime) {
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.orderStatus = Status.PREPARING_FOOD;
        this.preparationTime = preparationTime;
    }

    public Order() {
    }

    public enum Status{
        PREPARING_FOOD,
        READY_FOR_DISPATCH,
        ORDER_PICKED,
        ORDER_DELIVERED
    }
}
