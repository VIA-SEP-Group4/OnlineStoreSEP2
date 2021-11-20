package Model;

import java.io.Serializable;

public class Product implements Serializable
{
  private String name;
  private String type;
  private double price;
  private String description;
  private int quantityP;

  public Product(String name, String type, double price, String description, int quantityP)
  {
    this.name = name;
    this.type = type;
    this.price = price;
    this.description = description;
    this.quantityP = quantityP;
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

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public int getQuantityP()
  {
    return quantityP;
  }

  public void setQuantityP(int quantityP)
  {
    this.quantityP = quantityP;
  }

  @Override public String toString()
  {
    return name + "(" + type + "),"
        + "\ndescription= " + description
        + "\n, price= " + price +", quantity= " + quantityP + '}';
  }
}
