package DataAcess;

import Model.Product;
import Utils.Subject;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.*;
import java.util.ArrayList;

/**
 * Class responsible for managing and accessing database tables/data related to products
 */
public class ProductsDataManager implements ProductsDataAcessor, Subject {
    private static final String SCHEMA = "eshop";
    private PropertyChangeSupport support;

    public ProductsDataManager() {
        this.support = new PropertyChangeSupport(this);
    }

    /**
     * Get all products in the product-relation
     * @return arraylist of products corresponding to the index that is provided
     */
    @Override
    public ArrayList<Product> getProducts(int index) {
        System.out.println(index);
        String SQL = "SELECT * FROM " +SCHEMA+ ".products where products.product_id > "+ (index-1)*6 + " and products.product_id < "+(index*6+1);
        System.out.println(SQL);
        ArrayList<Product> products=new ArrayList<>();
        try (Connection conn =  DBSConnection.getInstance().connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {
            System.out.println("All products:");
            while (rs.next())
            {
                products.add(new Product(rs.getString("product_name"), rs.getString("type"), Double.parseDouble(rs.getString("price")),
                        rs.getString("description"),Integer.parseInt(rs.getString("amount"))));

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(products);
        return products;
    }

    /**
     * Method that adds a product to the database
     * @param p the product to be added
     */
    @Override
    public void addProduct(Product p) {
        String SQL = "INSERT INTO " + "eshop.products(product_name,description,type,amount,price) " + "VALUES(?,?,?,?,?)";

        try (Connection conn = DBSConnection.getInstance().connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS))
        {
            System.out.println(p);
            pstmt.setString(1, p.getName());
            pstmt.setString(2, p.getDescription());
            pstmt.setString(3, p.getType());
            pstmt.setInt(4, p.getQuantityP());
            pstmt.setDouble(5, p.getPrice());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows <= 0)
                throw new RuntimeException("Product insertion failed");

        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public void addListener(String eventName, PropertyChangeListener listener) {
        support.addPropertyChangeListener(eventName,listener);
    }

    @Override
    public void removeListener(String eventName, PropertyChangeListener listener) {
        support.removePropertyChangeListener(eventName,listener);
    }
}
