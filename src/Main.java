//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car{
    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    public Car(String id,String model,String brand,int price){
        this.carId=id;
        this.model=model;
        this.brand=brand;
        this.basePricePerDay=price;
        this.isAvailable=true;
    }

    public String getCarId(){
        return carId;
    }

    public String getModel(){
        return model;
    }

    public String getBrand(){
        return brand;
    }

    public double totalprice(int noofdays){
        return basePricePerDay*noofdays;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void rent(){
        isAvailable=false;
    }
    public void returnCar(){
        isAvailable=true;
    }
}
class Customer{
    private String customerId;
    private String name;

    public Customer(String id,String name){
        this.customerId=id;
        this.name=name;
    }
    public String getCustomerId(){
        return customerId;
    }
    public String getName(){
        return name;
    }
}
class Rental{
    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car,Customer customer,int days){
        this.car=car;
        this.customer=customer;
        this.days=days;
    }
    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}
class CarRentalSystem{
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRentalSystem(){
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCars(Car car){
        cars.add(car);
    }
    public void addCustomers(Customer cust){
        customers.add(cust);
    }
    public void addrentcar(Car car,Customer customer,int days){
        if(car.isAvailable()){
            rentals.add(new Rental(car,customer,days));
            car.rent();
        }
        else{
            System.out.println("Car is not available for rent.");
        }
    }

    public void returncar(Car car){
        Rental rentalToRemove=null;
        for(Rental rental:rentals){
            if(rental.getCar()==car){
                rentalToRemove = rental;
                break;
            }
        }
        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);

        } else {
            System.out.println("Car was not rented.");
        }
    }

    public void menu(){
        Scanner scanner=new Scanner(System.in);

        while(true){
            System.out.println("===== Car Rental System =====");
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a Car");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if(choice==1){
                System.out.println("\n== Rent a Car ==\n");
                System.out.print("Enter your name: ");
                String customerName = scanner.nextLine();

                System.out.println("\nAvailable Cars:");
                for(Car car:cars){
                    if(car.isAvailable()){
                        System.out.println("Car ID: "+car.getCarId()+" Car Brand: "+car.getBrand()+" Car Name:"+car.getModel());
                    }
                }
                System.out.print("\nEnter the car ID you want to rent: ");
                String carId = scanner.nextLine();

                System.out.print("Enter the number of days for rental: ");
                int rentalDays = scanner.nextInt();
                scanner.nextLine();

                Customer newCustomer=new Customer("CUS"+(customers.size()+1),customerName);
                addCustomers(newCustomer);

                Car selectedCar=null;
                for(Car car:cars){
                    if(car.getCarId().equals(carId) && car.isAvailable()){
                        selectedCar=car;
                        break;
                    }
                }

                if(selectedCar!=null){
                    double totalprice= selectedCar.totalprice(rentalDays);
                    System.out.println("\n== Rental Information ==\n");
                    System.out.println("Customer ID:"+ newCustomer.getCustomerId());
                    System.out.println("Customer Name: " + newCustomer.getName());
                    System.out.println("Car: " + selectedCar.getBrand() + " " + selectedCar.getModel());
                    System.out.println("Rental Days: " + rentalDays);
                    System.out.printf("Total Price: $%.2f%n",totalprice);

                    System.out.print("\nConfirm rental (Y/N): ");
                    String confirm = scanner.nextLine();

                    if(confirm.equalsIgnoreCase("Y")){
                        addrentcar(selectedCar,newCustomer,rentalDays);
                        System.out.println("\nCar rented successfully.");
                    }
                    else{
                        System.out.println("\nRental canceled.");
                    }
                }
                else{
                    System.out.println("\nInvalid car selection or car not available for rent.");
                }
            }
            else if(choice==2){
                System.out.println("\n== Return a Car ==\n");
                System.out.print("Enter the car ID you want to return: ");
                String carId = scanner.nextLine();

                Car cartoreturn=null;
                for(Car car:cars){
                    if(car.getCarId().equals(carId) && !car.isAvailable()){
                        cartoreturn=car;
                        break;
                    }
                }
                if(cartoreturn!=null){
                    Customer customer = null;
                    for(Rental rental:rentals){
                        customer=rental.getCustomer();
                        break;
                    }
                    if(customer!=null){
                        returncar(cartoreturn);
                        System.out.println("Car returned successfully by " + customer.getName());
                    }
                    else{
                        System.out.println("Car was not rented or rental information is missing.");
                    }
                }
                else {
                    System.out.println("Invalid car ID or car is not rented.");
                }
            }
            else if (choice == 3) {
                break;
            }
            else {
                System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
        System.out.println("\nThank you for using the Car Rental System!");
    }
}
public class Main {
    public static void main(String[] args) {
        CarRentalSystem rentalSystem = new CarRentalSystem();

        Car car1 = new Car("C001", "Toyota", "Camry", 60); // Different base price per day for each car
        Car car2 = new Car("C002", "Honda", "Accord", 70);
        Car car3 = new Car("C003", "Mahindra", "Thar", 150);
        rentalSystem.addCars(car1);
        rentalSystem.addCars(car2);
        rentalSystem.addCars(car3);

        rentalSystem.menu();
    }
}