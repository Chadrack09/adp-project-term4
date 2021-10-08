package za.ac.cput.model;
/**
 *
 * @author    Chadrack B. Boudzoumou
 * @email     219383847@mycput.ac.za
 * @student   219383847
 * @uni       Cape Peninsula University Of Technology
 * @since     Oct 7, 2021 | 2:06:50 AM
 *
 */
public class Customer {

  private int customerId;
  private String customerName;
  private String phone;

  public Customer(String customerName, String phone) {
    this.customerName = customerName;
    this.phone = phone;
  }

  public Customer(int customerId, String customerName, String phone) {
    this.customerId = customerId;
    this.customerName = customerName;
    this.phone = phone;
  }

  public int getCustomerId() {
    return customerId;
  }

  public void setCustomerId(int customerId) {
    this.customerId = customerId;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  @Override
  public String toString() {
    return "Customer{" + "customerId=" + customerId + ", customerName=" 
            + customerName + ", phone=" + phone + '}';
  }
}
