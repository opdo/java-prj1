package model;

public class Tester {
    public static void main(String[] args) {
        // Test my customer class
        Customer customer1 = new Customer("Vinh", "Pham Ngoc", "vinh@test.com");
        System.out.println(customer1);

        // Wrong email format
        Customer customer2 = new Customer("Vinh", "Pham Ngoc", "wrongemail");
        System.out.println(customer2);
    }
}
