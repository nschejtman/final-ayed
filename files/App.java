import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class App {

    private Scanner sc;
    private BST bst;

    /**
     * Relates the number of the option provided by the menu with the corresponding methods.
     */
    public App() {
        generateIndex();
        sc = new Scanner(System.in);
        int option = menu();
        while (option < 10) {
            switch (option) {
                case 1:
                    add();
                    break;
                case 2:
                    remove();
                    break;
                case 3:
                    edit();
                    break;
                case 4:
                    list();
                    break;
                case 5:
                    search();
                    break;
                case 6:
                    size();
                    break;
                case 7:
                    zip();
                    break;
                case 8:
                    generateIndex();
                    System.out.println("Successful!");

                    break;
                case 9:
                    printOrdered();
                    break;
            }
            option = menu();
        }
    }

    /**
     * Displays a number of options from which the user selects one by writing the number of the desired option. Then
     * returns the value of the desired option.
     *
     * @return
     */
    public int menu() {
        System.out.println("1: Add");
        System.out.println("2: Remove");
        System.out.println("3: Edit");
        System.out.println("4: List");
        System.out.println("5: Search");
        System.out.println("6: Size");
        System.out.println("7: Zip");
        System.out.println("8: Generate index");
        System.out.println("9: Print ordered");
        System.out.println("10: Exit");
        System.out.println("Option: ");
        String opc = sc.nextLine();
        int option = Integer.parseInt(opc);
        return option;
    }

    /**
     * Adds a product from input to the database.
     */
    public void add() {
        String plate, brand, model;
        int year;
        boolean active = true;

        try {
            DataBase database = new DataBase("CarDataBase");
            database.end();
            System.out.println("Plate: (write 0 to exit)");
            plate = sc.nextLine();
            while (!plate.equals("0")) {
                System.out.println("Brand: ");
                brand = sc.nextLine();
                System.out.println("Model: ");
                model = sc.nextLine();
                System.out.println("Year: ");
                year = Integer.parseInt(sc.nextLine());
                boolean valid = false;
                while (!valid) {
                    System.out.println("Active: (yes/no)");
                    String dummy = sc.nextLine().toLowerCase();
                    if (dummy.equals("yes")) {
                        active = true;
                        valid = true;
                    } else if (dummy.equals("no")) {
                        active = false;
                        valid = true;
                    } else valid = false;
                }


                database.write(new Car(plate, model, brand, year, active));
                System.out.println("Plate: (write 0 to exit)");
                plate = sc.nextLine();
            }
            database.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Removes a car from the database.
     */
    public void remove() {

        try {
            DataBase dataBase = new DataBase("CarDataBase");
            System.out.println("Plate: ");
            String plate = sc.nextLine();
            if (dataBase.remove(plate)) System.out.println("Remove was successful");
            else System.out.println("Invalid plate");
            dataBase.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Given a license plate, finds the car and edits it.
     */
    public void edit() {
        try {
            DataBase dataBase = new DataBase("CarDataBase");
            System.out.println("Plate: ");
            String plate = Car.validate(sc.nextLine());
            Car car = dataBase.find(plate);
            if (car.getPlate() != null) {
                System.out.println(car.toString());
                dataBase.move(-dataBase.getRegSize()); //Goes to the position before the car registry

                boolean active = car.isActive();

                System.out.println("Plate: ");
                car.setPlate(sc.nextLine());

                System.out.println("Brand: ");
                car.setBrand(sc.nextLine());

                System.out.println("Model: ");
                car.setModel(sc.nextLine());
                System.out.println("Year: ");
                car.setYear(Integer.parseInt(sc.nextLine()));
                boolean valid = false;
                while (!valid) {
                    System.out.println("Active: (yes/no)");
                    String dummy = sc.nextLine().toLowerCase();
                    if (dummy.equals("yes")) {
                        active = true;
                        valid = true;
                    } else if (dummy.equals("no")) {
                        active = false;
                        valid = true;
                    } else valid = false;
                }
                car.setActive(active);


                dataBase.write(car);
            } else {
                System.out.println("Invalid plate");
            }
            dataBase.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Lists all the cars in the database
     */
    public void list() {
        Car car;
        System.out.println("Brand: (type 'all' to list all cars)");
        String brand = Car.validate(sc.nextLine());
        try {
            DataBase dataBase = new DataBase("CarDataBase");
            dataBase.start();
            long qty = dataBase.regQty();
            if (brand.equals(Car.validate("all"))) {
                for (long i = 0; i < qty; i++) {
                    car = dataBase.read();
                    if (car.isActive()) System.out.println(car.toString());
                }
            } else {
                for (long i = 0; i < qty; i++) {
                    car = dataBase.read();
                    if (car.isActive() && car.getBrand().equals(brand)) System.out.println(car.toString());
                }
            }
            dataBase.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Prints the size of the database.
     */
    public void size() {
        try {
            DataBase dataBase = new DataBase("CarDataBase");
            System.out.println("Size of the database: " + dataBase.size());
            dataBase.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Searches a car by its plate and prints it.
     */
    public void search() {
        Car car;
        try {

            DataBase dataBase = new DataBase("CarDataBase");
            System.out.println("Plate: ");
            String plate = sc.nextLine();
            car = dataBase.find(plate);
            if (car.getPlate() == null)
                System.out.println("Not found!");
            else
                System.out.println(car.toString());


            dataBase.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Removes all the non active cars from the database.
     */
    public void zip() {
        try {
            DataBase database = new DataBase("CarDataBase");
            DataBase temp = new DataBase("temp");
            Car car;
            long qty = database.regQty();
            for (long i = 0; i < qty; i++) {
                car = database.read();
                if (car.isActive()) temp.write(car);
                System.out.println("loading...");

            }
            database.delete();
            temp.renameTo("CarDataBase");
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }

    public void generateIndex(){
        bst = new BST<>();
        Car car;
        DataBase dataBase = null;
        try {
            dataBase = new DataBase("CarDataBase");
            for (int i = 0; i < dataBase.regQty(); i++) {
                car = dataBase.read();
                if (car.isActive()) {
                    bst.insert(car);
                }

            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void printOrdered(){
        generateIndex();
        System.out.println(bst.toString());
    }


    public static void main(String[] args) {
        new App();
    }


}


