package efg.jpa.bank.db;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "Accounts", schema = "test")
public class Account implements Serializable
{
  private static final long serialVersionUID = 1L;
  /*****************************************************************************
   * Static
   ****************************************************************************/
  //private static int        INC              = 0;
  //private static synchronized int getINC() { return INC++; }
  
  /*****************************************************************************
   * Dynamic
   ****************************************************************************/
  //private int                     inc              = getINC();
  private String                  number;
  private double                  balance;
  private double                  limit;
  private String                  name;
  private String                  pincode;
  private String                  salt;
  private int                     lock;
  private Collection<Transaction> fromTransactions = null;
  private Collection<Transaction> toTransactions   = null;

  /*****************************************************************************
   * Construction
   ****************************************************************************/
  public Account()
  {
    //System.out.println(inc+"-Account()");
  }

  public Account(String newNumber, double newBalance, double newLimit, String newName, String newPincode, String newSalt)
  {
    //System.out.println(inc + "-Account(" + newNumber + ", " + newBalance + ", " + newLimit + ", " + newName + ", " + newPincode + ", " + newSalt + ")");
    number = newNumber;
    balance = newBalance;
    limit = newLimit;
    name = newName;
    pincode = newPincode;
    salt = newSalt;
    lock = 0;
  }

  /*****************************************************************************
   * getDetails
   ****************************************************************************/
  public String[] details()
  {
    // System.out.println(inc+"-Account.details()");
    return new String[]
    {
        name
      , number
      , ""+Math.round(100D * balance) / 100D
      , ""+limit
      , pincode
      , salt
    };
  }

  /*****************************************************************************
   * Number
   ****************************************************************************/
  @Id
  @Column(name = "Account_Number")
  public String getNumber()
  {
    // System.out.println(inc+"-Account.getNumber()");
    return number;
  }

  public void setNumber(String newNumber)
  {
    // System.out.println(inc+"-Account.setNumber("+newNumber+")");
    number = newNumber;
  }

  /*****************************************************************************
   * Balance
   ****************************************************************************/
  @Column(name = "Account_Balance")
  public double getBalance()
  {
    // System.out.println(inc+"-Account.getBalance()");
    return balance;
  }

  public void setBalance(double newBalance)
  {
    // System.out.println(inc+"-Account.setBalance("+newBalance+")");
    balance = newBalance;
  }

  /*****************************************************************************
   * Limit
   ****************************************************************************/
  @Column(name = "Account_Limit")
  public double getLimit()
  {
    // System.out.println(inc+"-Account.getLimit()");
    return limit;
  }

  public void setLimit(double newLimit)
  {
    // System.out.println(inc+"-Account.setLimit("+newLimit+")");
    limit = newLimit;
  }

  /*****************************************************************************
   * Name
   ****************************************************************************/
  @Column(name = "Account_Name")
  public String getName()
  {
    // System.out.println(inc+"-Account.getName()");
    return name;
  }

  public void setName(String newName)
  {
    // System.out.println(inc+"-Account.setName("+newName+")");
    name = newName;
  }

  /*****************************************************************************
   * Pincode
   ****************************************************************************/
  @Column(name = "Account_Pincode")
  public String getPincode()
  {
    // System.out.println(inc+"-Account.getPincode()");
    return pincode;
  }

  public void setPincode(String newPincode)
  {
    // System.out.println(inc+"-Account.setPincode("+newPincode+")");
    pincode = newPincode;
  }

  /*****************************************************************************
   * Salt
   ****************************************************************************/
  @Column(name = "Account_Salt")
  public String getSalt()
  {
    // System.out.println(inc+"-Account.getSalt()");
    return salt;
  }

  public void setSalt(String newSalt)
  {
    // System.out.println(inc+"-Account.setSalt("+newSalt+")");
    salt = newSalt;
  }

  /*****************************************************************************
   * Status
   ****************************************************************************/
  @Column(name = "Account_Lock")
  public int getLock()
  {
    // System.out.println(inc+"-Account.getLock()");
    return lock;
  }

  public void setLock(int newLock)
  {
    // System.out.println(inc+"-Account.setLock("+newLock+")");
    lock = newLock;
  }

  /*****************************************************************************
   * FromTransactions
   ****************************************************************************/
  @OneToMany // One Account has Many Transactions
  (fetch = FetchType.EAGER, mappedBy = "fromAccount")
  public Collection<Transaction> getFromTransactions()
  {
    //System.out.println(inc+"-Account.getFromTransactions()");
    return fromTransactions;
  }

  public void setFromTransactions(Collection<Transaction> newFromTransactions)
  {
    //System.out.println(inc+"-Account.setFromTransactions("+newFromTransactions.size()+")");
    fromTransactions = newFromTransactions;
  }

  /*****************************************************************************
   * ToTransactions
   ****************************************************************************/
  @OneToMany // One Account has Many Transactions
  (fetch = FetchType.EAGER, mappedBy = "toAccount")
  public Collection<Transaction> getToTransactions()
  {
    //System.out.println(inc+"-Account.getToTransactions()");
    return toTransactions;
  }

  public void setToTransactions(Collection<Transaction> newToTransactions)
  {
    //System.out.println(inc+"-Account.setToTransactions("+newToTransactions.size()+")");
    toTransactions = newToTransactions;
  }

  /*****************************************************************************
   * Entity Callbacks
   ****************************************************************************/
  @PrePersist
  void prePersist()
  {
    // System.out.println(inc+"-Account.prePersist()");
  }

  @PostPersist
  void postPersist()
  {
    // System.out.println(inc+"-Account.postPersist()");
  }

  @PreUpdate
  void preUpdate()
  {
    // System.out.println(inc+"-Account.preUpdate()");
  }

  @PostUpdate
  void postUpdate()
  {
    // System.out.println(inc+"-Account.postUpdate()");
  }

  @PreRemove
  void preRemove()
  {
    // System.out.println(inc+"-Account.preRemove()");
  }

  @PostRemove
  void postRemove()
  {
    // System.out.println(inc+"-Account.postRemove()");
  }

  @PostLoad
  void postLoad()
  {
    // System.out.println(inc+"-Account.postLoad()");
  }

  /*****************************************************************************
   * toString
   ****************************************************************************/
  @Override
  public String toString()
  {
    return "Account[" + number + "]";
  }
}