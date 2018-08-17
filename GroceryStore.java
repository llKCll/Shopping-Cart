import java.io.FileNotFoundException;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class GroceryStore {

    // A menu to find out if user wants to add or remove a product. Returns "ADD", "REMOVE", or "GO BACK".
    protected static String addOrRemMenu() {
        System.out.println("1. ADD");
        System.out.println("2. REMOVE");
        System.out.println("3. GO BACK");
        System.out.println("Enter a command number (1-3) or name of the command.");

        // Loop until a valid input is received.
        while (true) {
            Scanner scan = new Scanner(System.in);
            String input = scan.nextLine();

            // Upper case for easy comparisons.
            String upperInput = upperAll(input);

            // Both the command name or number can be selected.
            if (upperInput.equals("ADD") || input.equals("1")) { return "ADD"; }
            else if (upperInput.equals("REMOVE") || input.equals("2")) { return "REMOVE"; }
            else if (upperInput.equals("GO BACK") || input.equals("3")) { return "GO BACK"; }
            else {
                System.out.println("Invalid command detected. Please try again");
            }
        }
    }

    // Enumerate the store sections.
    protected enum productSection {
        MEATS("meat_section"),
        PRODUCE("produce_section"),
        BEVERAGES("beverages_section");

        private final String section;

        productSection(String s) {
            section = s;
        }

        protected String getSection() { return section; }
    }

    // Load store products from products.txt and categorize them in meats, produce, or beverages.
    protected static void buildStore(ArrayList<Food> meats, ArrayList<Food> produce, ArrayList<Food> beverages, File file) throws FileNotFoundException {
        Scanner input = new Scanner(file);
        productSection section = null;

        while(input.hasNext()) {
            String line = input.nextLine();

            // Take note of a section change and continue looping through products.
            if (line.equals("Meats")) {
                section = productSection.MEATS;
                continue;
            }
            else if (line.equals("Produce")) {
                section = productSection.PRODUCE;
                continue;
            }
            else if (line.equals("Beverages")) {
                section = productSection.BEVERAGES;
                continue;
            }

            // Construct the food item and add it to the appropriate section.
            String[] tokens = line.split("/");

            if (section == productSection.MEATS) {
                meats.add(new Food("0", tokens[0], tokens[1], tokens[2], 0));
            }
            else if (section == productSection.PRODUCE) {
                produce.add(new Food("0", tokens[0], tokens[1], tokens[2], 0));
            }
            else if(section == productSection.BEVERAGES) {
                beverages.add(new Food("0", tokens[0], tokens[1], tokens[2], 0));
            }
        }

        input.close();

        // Assign unique IDs.
        int subid = 1;
        for (Food meat : meats) {
            meat.setFoodId("1" + Integer.toString(subid));
            subid ++;
        }

        subid = 1;
        for (Food veg : produce) {
            veg.setFoodId("2" + Integer.toString(subid));
            subid ++;
        }

        subid = 1;
        for (Food bev : beverages) {
            bev.setFoodId("3" + Integer.toString(subid));
            subid ++;
        }
    }

    // Displays Food products contained in the ArrayList.
    protected static void displayProducts(ArrayList<Food> section) {
        for (Food product : section) {
            System.out.println(product.getFoodId() + " " + product.getFoodName() + " $" + product.getFoodPrice() + " " + product.getFoodUnits());
        }
    }

    // Returns a product chosen by the user that is present in products.
    protected static Food getUserProduct(ArrayList<Food> products) {
        // Keep getting user input until a product is found.
        while (true) {
            System.out.println("Enter a product number or name.");

            // Get user selection.
            Scanner scan = new Scanner(System.in);
            String input = scan.nextLine();
            String upperInput = upperAll(input);

            // Find the product and return it if it exists.
            for (int i = 0; i < products.size(); i++) {
                Food productItem = products.get(i);
                String upperProductName = upperAll(productItem.getFoodName());
                String productId = productItem.getFoodId();

                if (upperInput.equals(upperProductName) || input.equals(productId)) {
                    return productItem;
                }
            }

            System.out.println("Product entered does not exist. Please try again.");
        }
    }

    // Main control of the menus displayed to the shopper.
    protected static void mainMenu(ArrayList<Food> meats, ArrayList<Food> produce, ArrayList<Food> beverages) {
        ShoppingCart sc = new ShoppingCart();

        // Entire menu control.
        while (true) {
            System.out.println("Enter a command number (1-3) or the name of the command.");
            System.out.println("1. STORE");
            System.out.println("2. CART");
            System.out.println("3. EXIT");

            Scanner scan = new Scanner(System.in);
            String mainMenuInput = scan.nextLine();
            String upperMainMenuInput = upperAll(mainMenuInput);

            if (upperMainMenuInput.equals("STORE") || mainMenuInput.equals("1")) {
                // Enter store control.
                while (true) {
                    // Get store section.
                    String sectInput = sectionMenu();

                    if (sectInput.equals("MEATS")) {
                        // Find out if the user wants to add or remove an item.
                        String addOrRem = addOrRemMenu();

                        // Add an item, remove an item, or go back to the main menu.
                        if (addOrRem.equals("ADD")) {
                            displayProducts(meats);
                            Food userProduct = getUserProduct(meats);
                            sc.addToCart(userProduct);
                            System.out.println("Product " + userProduct.getFoodId() + " (" + userProduct.getFoodName() + ") added to cart.");
                        } else if (addOrRem.equals("REMOVE")) {
                            displayProducts(meats);
                            Food userProduct = getUserProduct(meats);
                            sc.remFromCart(userProduct);
                            System.out.println("Product " + userProduct.getFoodId() + " (" + userProduct.getFoodName() + ") removed from cart.");
                        } else if (addOrRem.equals("GO BACK")) {
                            continue;
                        }
                    } else if (sectInput.equals("PRODUCE")) {
                        String addOrRem = addOrRemMenu();

                        if (addOrRem.equals("ADD")) {
                            displayProducts(produce);
                            Food userProduct = getUserProduct(produce);
                            sc.addToCart(userProduct);
                            System.out.println("Product " + userProduct.getFoodId() + " (" + userProduct.getFoodName() + ") added to cart.");
                        } else if (addOrRem.equals("REMOVE")) {
                            displayProducts(produce);
                            Food userProduct = getUserProduct(produce);
                            sc.remFromCart(userProduct);
                            System.out.println("Product " + userProduct.getFoodId() + " (" + userProduct.getFoodName() + ") removed from cart.");
                        } else if (addOrRem.equals("GO BACK")) {
                            continue;
                        }
                    } else if (sectInput.equals("BEVERAGES")) {
                        String addOrRem = addOrRemMenu();

                        if (addOrRem.equals("ADD")) {
                            displayProducts(beverages);
                            Food userProduct = getUserProduct(beverages);
                            sc.addToCart(userProduct);
                            System.out.println("Product " + userProduct.getFoodId() + " (" + userProduct.getFoodName() + ") added to cart.");
                        } else if (addOrRem.equals("REMOVE")) {
                            displayProducts(beverages);
                            Food userProduct = getUserProduct(beverages);
                            sc.remFromCart(userProduct);
                            System.out.println("Product " + userProduct.getFoodId() + " (" + userProduct.getFoodName() + ") removed from cart.");
                        } else if (addOrRem.equals("GO BACK")) {
                            continue;
                        }
                    } else if (sectInput.equals("MAIN MENU")) {
                        break;
                    }
                }
            }
            // Display items in the cart and tally the total.
            else if (upperMainMenuInput.equals("CART") || mainMenuInput.equals("2")) {
                sc.displayCart();
                System.out.println("Total: $" + sc.total());
            }
            // Exit program.
            else if (upperMainMenuInput.equals("EXIT") || mainMenuInput.equals("3")) {
                System.exit(0);
            }
        }
    }

    /*
       Section screen where the user selects Meats, Produce, or Beverages category to shop.
       Input is case insensitive.
       Returns the name(capitalized) or "GO BACK".
     */
    protected static String sectionMenu() {
        // Loop until a valid answer is received.
        while (true) {
            System.out.println("1. MEATS");
            System.out.println("2. PRODUCE");
            System.out.println("3. BEVERAGES");
            System.out.println("4. MAIN MENU");
            System.out.println("Enter a command number (1-4) or the name of the command.");

            // Get user selection.
            Scanner scan = new Scanner(System.in);
            String input = scan.nextLine();

            // Capitalize to make case insensitive.
            String upperInput = upperAll(input);

            if (upperInput.equals("MEATS") || input.equals("1")) { return "MEATS"; }

            else if (upperInput.equals("PRODUCE") || input.equals("2")) { return "PRODUCE"; }

            else if (upperInput.equals("BEVERAGES") || input.equals("3")) { return "BEVERAGES"; }

            else if (upperInput.equals("MAIN MENU") || input.equals("4")) { return "MAIN MENU"; }

            else {
                System.out.println("Invalid command detected. Please try again");
            }
        }
    }

    // Returns a string all upper case. Characters that aren't letters remain unchanged and appended to the new string.
    protected static String upperAll(String astring) {
        String upperString = "";

        for (int i = 0; i < astring.length(); i++) {
            // It's a letter. Capitalize and append.
            if (Character.isLetter(astring.charAt(i))) {
                upperString += Character.toUpperCase(astring.charAt(i));
            }
            // Not a string. Just copy over as it is.
            else {
                upperString += astring.charAt(i);
            }
        }

        return upperString;
    }

    public static void main(String[] args) {
        // Containers that hold the store products.
        ArrayList<Food> meats = new ArrayList<Food>();
        ArrayList<Food> produce = new ArrayList<Food>();
        ArrayList<Food> beverages = new ArrayList<Food>();

        // Attempt to scan the store products file.
        try {
            File file = new File ("./products.txt");
            buildStore(meats, produce, beverages, file);
        }
        catch (FileNotFoundException exception) {
            System.out.println("File not found.");
            System.exit(1);
        }

        // Store interactive interface.
        mainMenu(meats, produce, beverages);
    }
}
