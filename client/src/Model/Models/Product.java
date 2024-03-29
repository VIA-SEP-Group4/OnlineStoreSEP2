package Model.Models;

import java.io.Serial;
import java.io.Serializable;

public class Product implements Serializable
{
  private String name;
  private String type;
  private double price;
  private String description;
  private int quantity;
  private int productId;
  @Serial
  private static final long serialVersionUID=3L;
  //when being  created and posted to the DB (-> id created in the DB [-SERIAL])
  public Product(String name, String type, double price, String description, int quantity)
  {
    this.name = name;
    this.type = type;
    this.price = price;
    this.description = description;
    this.quantity = quantity;
    productId = -1;
  }
  //when being fetched (-> id must be fetched)
  public Product(String name, String type, double price, String description, int quantity, int productId)
  {
    this.name = name;
    this.type = type;
    this.price = price;
    this.description = description;
    this.quantity = quantity;
    this.productId = productId;
  }


  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getType()
  {
    return type;
  }

  public void setType(String type)
  {
    this.type = type;
  }

  public double getPrice()
  {
    return price;
  }

  public void setPrice(double price)
  {
    this.price = price;
  }

  public double getTotalPrice()
  {
    return quantity*price;
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public int getQuantity()
  {
    return quantity;
  }

  public void setQuantityP(int amount)
  {
    this.quantity = amount;
  }

  @Override
  public String toString() {
    return "Product{" +
            "name='" + name + '\'' +
            ", type='" + type + '\'' +
            ", price=" + price +
            ", description='" + description + '\'' +
            ", quantity=" + quantity +
            ", productId=" + productId +
            '}';
  }

  public int getProductId()
  {
    return productId;
  }

  public Product copy()
  {
    return new Product(name, type, price, description, quantity, productId);
  }
}
