public class Car implements Comparable<Car> {
    private String plate, model, brand;
    private int year;
    private boolean active; //Is active in the database

    public Car(String plate, String model, String brand, int year, boolean active) {
        plate = validate(plate);
        model = validate(model);
        brand = validate(brand);

        this.plate = plate;
        this.model = model;
        this.brand = brand;
        this.year = year;
        this.active = active;
    }

    public Car() {
        plate = null;
        brand = null;
        model = null;
        year = 0;
        active = false;
    }

    public void setPlate(String plate) {
        plate = validate(plate);
        this.plate = plate;
    }

    public void setModel(String model) {
        model = validate(model);
        this.model = model;
    }

    public void setBrand(String brand) {
        brand = validate(brand);
        this.brand = brand;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getPlate() {
        return plate;
    }

    public String getModel() {
        return model;
    }

    public String getBrand() {
        return brand;
    }

    public int getYear() {
        return year;
    }


    @Override
    public String toString() {
        return "Car{" +
                "plate='" + plate + '\'' +
                ", model='" + model + '\'' +
                ", brand='" + brand + '\'' +
                ", year=" + year +
                ", active=" + active +
                '}';
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    /**
     * Validates a string to make it of 15 characters and lowercase
     *
     * @param value
     * @return
     */
    public static String validate(String value) {
        value = value.toLowerCase();
        if (value.length() < 15) {
            for (int i = value.length(); i < 15; i++) {
                value += " ";
            }

        } else {
            value = value.substring(0, 15);
        }
        return value;
    }


    @Override
    public int compareTo(Car o) {
        return plate.compareTo(o.getPlate());
    }
}
