import java.util.*;

// Базовый класс для пассажиров
class Passenger {
    private String name;

    public Passenger(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

// Пожарник (специализированный тип пассажира)
class Firefighter extends Passenger {
    public Firefighter(String name) {
        super(name);
    }
}

// Полицейский (специализированный тип пассажира)
class Policeman extends Passenger {
    public Policeman(String name) {
        super(name);
    }
}

// Обычный пассажир
class RegularPassenger extends Passenger {
    public RegularPassenger(String name) {
        super(name);
    }
}

abstract class Vehicle<T extends Passenger> {
    private int maxSeats;
    private List<T> passengers;

    public Vehicle(int maxSeats) {
        this.maxSeats = maxSeats;
        this.passengers = new ArrayList<>();
    }

    public int getMaxSeats() {
        return maxSeats;
    }

    public int getOccupiedSeats() {
        return passengers.size();
    }

    public void boardPassenger(T passenger) throws Exception {
        if (passengers.size() >= maxSeats) {
            throw new Exception("No available seats");
        }
        passengers.add(passenger);
        System.out.println(passenger.getName() + " has boarded the " + this.getClass().getSimpleName());
    }

    public void disembarkPassenger(T passenger) throws Exception {
        if (!passengers.contains(passenger)) {
            throw new Exception("Passenger is not on board");
        }
        passengers.remove(passenger);
        System.out.println(passenger.getName() + " has disembarked from the " + this.getClass().getSimpleName());
    }

    public List<T> getPassengers() {
        return passengers;
    }

    public abstract boolean canBoard(T passenger);
}

// Автобус, который может перевозить любых пассажиров
class Bus extends Vehicle<Passenger> {
    public Bus(int maxSeats) {
        super(maxSeats);
    }

    @Override
    public boolean canBoard(Passenger passenger) {
        return true; // Можем перевозить любых пассажиров
    }
}

// Такси, которое может перевозить любых пассажиров
class Taxi extends Vehicle<Passenger> {
    public Taxi(int maxSeats) {
        super(maxSeats);
    }

    @Override
    public boolean canBoard(Passenger passenger) {
        return true; // Можем перевозить любых пассажиров
    }
}

// Пожарная машина, которая может перевозить только пожарников
class FireTruck extends Vehicle<Firefighter> {
    public FireTruck(int maxSeats) {
        super(maxSeats);
    }

    @Override
    public boolean canBoard(Firefighter passenger) {
        return passenger instanceof Firefighter; // Можем перевозить только пожарников
    }
}

// Полицейская машина, которая может перевозить только полицейских
class PoliceCar extends Vehicle<Policeman> {
    public PoliceCar(int maxSeats) {
        super(maxSeats);
    }

    @Override
    public boolean canBoard(Policeman passenger) {
        return passenger instanceof Policeman; // Можем перевозить только полицейских
    }
}

class Road {
    private List<Vehicle<? extends Passenger>> carsOnRoad = new ArrayList<>();

    // Подсчёт количества людей, которые перевозятся на дороге
    public int getCountOfHumans() {
        int count = 0;
        for (Vehicle<? extends Passenger> vehicle : carsOnRoad) {
            count += vehicle.getPassengers().size();
        }
        return count;
    }

    // Добавление автомобиля на дорогу
    public void addCarToRoad(Vehicle<? extends Passenger> car) {
        carsOnRoad.add(car);
    }
}

public class Laba4 {
    public static void main(String[] args) {
        try {
            // Создаем транспортные средства
            Bus bus = new Bus(2);
            Taxi taxi = new Taxi(2);
            FireTruck fireTruck = new FireTruck(2);
            PoliceCar policeCar = new PoliceCar(2);

            // Создаем пассажиров
            RegularPassenger regular1 = new RegularPassenger("John");
            Firefighter firefighter1 = new Firefighter("Jake");
            Policeman policeman1 = new Policeman("Adam");

            // Посадка пассажиров
            bus.boardPassenger(regular1);
            taxi.boardPassenger(regular1);
            fireTruck.boardPassenger(firefighter1);
            policeCar.boardPassenger(policeman1);

            // Выводим на экран, кто где сидит
            System.out.println("\nPassengers after boarding:");
            System.out.println("Bus: ");
            for (Passenger p : bus.getPassengers()) {
                System.out.println("- " + p.getName());
            }
            System.out.println("Taxi: ");
            for (Passenger p : taxi.getPassengers()) {
                System.out.println("- " + p.getName());
            }
            System.out.println("FireTruck: ");
            for (Firefighter p : fireTruck.getPassengers()) {
                System.out.println("- " + p.getName());
            }
            System.out.println("PoliceCar: ");
            for (Policeman p : policeCar.getPassengers()) {
                System.out.println("- " + p.getName());
            }

            // Проверяем количество людей на дороге
            Road road = new Road();
            road.addCarToRoad(bus);
            road.addCarToRoad(taxi);
            road.addCarToRoad(fireTruck);
            road.addCarToRoad(policeCar);

            System.out.println("\nTotal humans on road: " + road.getCountOfHumans()); // Выведет 4

            // Высадка пассажиров
            bus.disembarkPassenger(regular1);
            taxi.disembarkPassenger(regular1);

            // Выводим на экран, кто остался
            System.out.println("\nPassengers after disembarking:");
            System.out.println("Bus: ");
            for (Passenger p : bus.getPassengers()) {
                System.out.println("- " + p.getName());
            }
            System.out.println("Taxi: ");
            for (Passenger p : taxi.getPassengers()) {
                System.out.println("- " + p.getName());
            }
            System.out.println("FireTruck: ");
            for (Firefighter p : fireTruck.getPassengers()) {
                System.out.println("- " + p.getName());
            }
            System.out.println("PoliceCar: ");
            for (Policeman p : policeCar.getPassengers()) {
                System.out.println("- " + p.getName());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
