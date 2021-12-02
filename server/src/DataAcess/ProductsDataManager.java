package DataAcess;

import Model.Order;
import Model.Product;
import Utils.Subject;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.*;
import java.util.ArrayList;

/**
 * Class responsible for managing and accessing database tables/data related to products
 */
public class ProductsDataManager implements ProductsDataAcessor {

    private static final String SCHEMA = "eshop";
    private static final String TABLE = "products";
    private PropertyChangeSupport support;

    public ProductsDataManager() {
        this.support = new PropertyChangeSupport(this);
    }

    /**
     * Get all products in the product-relation
     * @return arraylist of products corresponding to the index that is provided
     */
    @Override
    public ArrayList<Product> getProducts() {
        String SQL = "SELECT * FROM " +SCHEMA+ "." +TABLE;
        ArrayList<Product> products = new ArrayList<>();

        try (Connection conn =  DBSConnection.getInstance().connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL))
        {
            while (rs.next())
            {
                products.add(new Product(rs.getString("product_name"), rs.getString("type"), rs.getDouble("price"),
                        rs.getString("description"), rs.getInt("quantity"), rs.getInt("product_id")));

            }
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        }

        return products;
    }


    /**
     * Method that adds a product to the database
     * @param p the product to be added
     */
    @Override
    public void addProduct(Product p) {
        String SQL = "INSERT INTO " +SCHEMA+ "." +TABLE+ "(product_name,description,type,amount,price) " + "VALUES(?,?,?,?,?)";

        try (Connection conn = DBSConnection.getInstance().connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS))
        {
            System.out.println(p);
            pstmt.setString(1, p.getName());
            pstmt.setString(2, p.getDescription());
            pstmt.setString(3, p.getType());
            pstmt.setInt(4, p.getQuantity());
            pstmt.setDouble(5, p.getPrice());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows <= 0)
                throw new RuntimeException("Product insertion failed");
            support.firePropertyChange("ProductReply",null,getProducts());
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override public void deleteProduct(Product p)
    {
        String SQL = "DELETE FROM " +SCHEMA+ "." +TABLE+ " WHERE " +SCHEMA+ "." +TABLE+ ".product_name = '" +p.getName()+ "'";

        try (Connection conn = DBSConnection.getInstance().connect();
            Statement stmt = conn.createStatement())
        {
            int affectedRows = stmt.executeUpdate(SQL);
            if (affectedRows <= 0)
                throw new RuntimeException("Product deletion failed");
            support.firePropertyChange("ProductReply",null,getProducts());
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
