
import java.io.*;
import java.util.*;

public class ToyStore {
    private static List<Toy> toys;
    private static List<Toy> prizeToys;

    /**
     * @param ToyStore
     */
    public static void main (String[] ToyStore) {
        toys = new ArrayList<>();
        prizeToys = new ArrayList<>();
    }

    public void addToy(int id, String name, int quantity, double weight) {
        Toy toy = new Toy(id, name, quantity, weight);
        toys.add(toy);
    }

    public void updateWeight(int id, double newWeight) {
        for (Toy toy : toys) {
            if (toy.getId() == id) {
                toy.setWeight(newWeight);
                break;
            }
        }
    }

    public void play() {
        Toy prizeToy = selectPrizeToy();
        if (prizeToy != null) {
            prizeToys.add(prizeToy);
            toys.remove(prizeToy);
            decreaseQuantity(prizeToy);
            savePrizeToyToFile(prizeToy);
        }
    }

    private Toy selectPrizeToy() {
        double totalWeight = 0;
        for (Toy toy : toys) {
            totalWeight += toy.getWeight();
        }

        double randomWeight = Math.random() * totalWeight;
        double weightSum = 0;
        for (Toy toy : toys) {
            weightSum += toy.getWeight();
            if (randomWeight <= weightSum) {
                return toy;
            }
        }

        return null;
    }

    private void decreaseQuantity(Toy toy) {
        toy.setQuantity(toy.getQuantity() - 1);
    }

    private void savePrizeToyToFile(Toy toy) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("prize_toys.txt", true))) {
            writer.println(toy.getId() + "," + toy.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Toy {
    private int id;
    private String name;
    private int quantity;
    private double weight;

    public Toy(int id, String name, int quantity, double weight) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}