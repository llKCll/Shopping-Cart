import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;

public class ShoppingCart {

    private ArrayList<Food> cart = new ArrayList<Food>();

    // Add a food product to the shopping cart.
    protected void addToCart(Food product) {

        if (inCart(product)) {
            // Product is present. Just increase the quantity.
            product.setFoodQuantity(product.getFoodQuantity() + 1);
        } else {
            // Add the product and set the quantity to 1.
            product.setFoodQuantity(1);
            this.cart.add(product);
        }
    }

    // Print display of Foods in the shopping cart. Additional effect is the cart is sorted by name.
    protected void displayCart() {
        Collections.sort(this.cart);

        for (Food item : this.cart) {
            System.out.println(item.getFoodId() + " " + item.getFoodName() + " x" + item.getFoodQuantity() + " $" + item.getFoodPrice());
        }
    }

    // Return size of the cart.
    protected int getSize() {
        return cart.size();
    }

    // Return true if Food is in cart, false otherwise.
    protected boolean inCart(Food product) {
        for (Food item : this.cart) {
            if (item.getFoodId().equals(product.getFoodId())) {
                return true;
            }
        }

        return false;
    }

    // Remove a Food from cart. Does nothing if it does not exist.
    protected void remFromCart(Food product) {
        for (int i = 0; i < this.cart.size(); i++) {

            // Remove product with matching id.
            if (product.getFoodId().equals(this.cart.get(i).getFoodId())) {
                // Remove the whole product from the shopping cart.
                if (product.getFoodQuantity() == 1) {
                    this.cart.remove(i);
                }
                // Decrement the quantity by 1.
                else if (product.getFoodQuantity() > 1) {
                    product.setFoodQuantity(product.getFoodQuantity() - 1);
                }
            }
        }
    }

    // Calculate and return the total of all Foods in the shopping cart.
    protected BigDecimal total() {
        BigDecimal result = new BigDecimal(0);

        for(Food item : this.cart) {
            result = result.add(item.getFoodPrice());
        }

        return result;
    }
}
