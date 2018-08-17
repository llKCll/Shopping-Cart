import java.math.BigDecimal;

public class Food implements Comparable<Food> {

    private String id;
    private String name;
    private BigDecimal price;
    private String units;
    private int quantity;

    protected Food(String foodid, String foodname, String foodprice, String foodunits, int n) {
        this.id = foodid;
        this.name = foodname;
        this.price = new BigDecimal(foodprice);
        this.units = foodunits;
        this.quantity = n;
    }

    protected String getFoodId() { return id; }

    protected String getFoodName() { return name; }

    protected BigDecimal getFoodPrice() {
        return price;
    }

    protected String getFoodUnits() {
        return units;
    }

    protected int getFoodQuantity() {
        return quantity;
    }

    protected void setFoodId(String foodid) {
        id = foodid;
    }

    protected void setFoodName(String foodname) {
        name = foodname;
    }

    protected void setFoodPrice(String foodprice) { price = new BigDecimal(foodprice); }

    protected void setFoodUnits(String foodunits) {
        units = foodunits;
    }

    protected void setFoodQuantity(int n) { quantity = n; }

    // Sort object by name
    @Override
    public int compareTo(Food other) {
        return this.getFoodName().compareTo(other.getFoodName());
    }
}
