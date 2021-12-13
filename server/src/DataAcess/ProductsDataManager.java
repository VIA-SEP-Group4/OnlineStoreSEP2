package DataAcess;

import Model.Models.Product;

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
    public synchronized ArrayList<Product> getProducts() {

        String SQL = "SELECT * FROM " +SCHEMA+ "." +TABLE;
        return getProducts(SQL);
    }

    private ArrayList<Product> getProducts(String SQL) {
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
        System.out.println(products);
        return products;
    }

    @Override public  ArrayList<Product> getProducts(int page, int pagQuant)
    {
        int offset = (page) * pagQuant;
        String SQL = "SELECT * FROM " +SCHEMA+ "." +TABLE+ " OFFSET "+offset+ "LIMIT "+pagQuant;

        return getProducts(SQL);
    }

    @Override public  ArrayList<Product> getFilterProd(int page, int pagQuant,String type)
    {
        int offset = (page) * pagQuant;
        String SQL = "SELECT * FROM " +SCHEMA+ "." +TABLE+ " WHERE type ="+ " '"+type+"' "+ "LIMIT " +pagQuant+ " OFFSET "+offset ;

        return getProducts(SQL);
    }
    /**
     * Method that adds a product to the database
     * @param p the product to be added
     */
    @Override
    public  void addProduct(Product p) {
        String SQL = "INSERT INTO " +SCHEMA+ "." +TABLE+ "(product_name,description,type,quantity,price) " + "VALUES(?,?,?,?,?)";

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

        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
        String SQL2 = "Select product_id FROM " +SCHEMA+"."+TABLE+ " WHERE product_name="+"'"+p.getName()+"'";
        try (Connection conn = DBSConnection.getInstance().connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL2))
        {
            rs.next();
            int newProductID = rs.getInt("product_id");
            p.setProductId(newProductID);
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
        support.firePropertyChange("ProductsReply",null,p);
    }

    @Override public  void deleteProduct(Product p)
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
        support.firePropertyChange("productDeleted",null,p);
    }

    @Override public  void updateStock(Product p, int prodQuantity)
    {
        String SQL = "UPDATE " +SCHEMA+ "." +TABLE+ " SET quantity=quantity +"+(prodQuantity)+ " WHERE product_id = '" +p.getProductId()+ "'";

        try (Connection conn = DBSConnection.getInstance().connect();
            Statement stmt = conn.createStatement())
        {
            int affectedRows = stmt.executeUpdate(SQL);
            if (affectedRows <= 0)
                throw new RuntimeException("Product update failed");
            support.firePropertyChange("ProductsReply",null,p);
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }
    @Override public  void editProduct(Product p)
    {
        System.out.println(p);
        String SQL = "UPDATE " +SCHEMA+ "." +TABLE+ " SET product_name="+"'"+p.getName()+"'"+",description="+"'"+p.getDescription()+"'"
                +",type="+"'"+p.getType()+"'"+",quantity="+p.getQuantity()+",price="+p.getPrice()
                + " WHERE product_id = '" +p.getProductId()+ "'";

        try (Connection conn = DBSConnection.getInstance().connect();
             Statement stmt = conn.createStatement())
        {
            int affectedRows = stmt.executeUpdate(SQL);
            if (affectedRows <= 0)
                throw new RuntimeException("Product update failed");
            support.firePropertyChange("ProductsReply",null,p);
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
