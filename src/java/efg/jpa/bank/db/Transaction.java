package efg.jpa.bank.db;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table
( name = "Transactions" 
, schema = "test"
)
public class Transaction implements Comparable<Transaction>, Serializable
{  
  private static final long serialVersionUID = 1L;

/*****************************************************************************
   * Static
   ****************************************************************************/
  //private static int INC = 0;
  //private static synchronized int getINC() { return INC++; }
  
  /*****************************************************************************
   * Dynamic
   ****************************************************************************/
  //private int inc = getINC();
  
  private String  id;
  private String  from;
  private Account fromAccount;
  private String  to;
  private Account toAccount;
  private double  amount;
  private String  transactionTime;
  private String  transferTime;
        
  /*****************************************************************************
   * Construction
   ****************************************************************************/
  public Transaction() 
  {
    //System.out.println(inc+"-Transaction()");
  }
  
  public Transaction(String newId, Account newFromAccount, Account newToAccount, double newAmount, String newTransactionTime, String newTransferTime)
  {
    //System.out.println(inc+"-Transaction("+newId+", "+newFromAccount+", "+newToAccount+", "+newToAccount+", "+newAmount+", "+newTransactionTime+", "+newTransferTime+")");
    id              = newId;
    fromAccount     = newFromAccount;
    from            = newFromAccount.getNumber();
    toAccount       = newToAccount;
    to              = newToAccount.getNumber();
    amount          = newAmount;
    transactionTime = newTransactionTime;
    transferTime    = newTransferTime;
  }
  
  public Transaction(String newId, String newFrom, String newTo, double newAmount, String newTransactionTime, String newTransferTime)
  {
    //System.out.println(inc+"-Transaction("+newId+", "+newFrom+", "+newTo+", "+newAmount+", "+newTransactionTime+", "+newTransferTime+")");
    id              = newId;
    from            = newFrom;
    to              = newTo;
    amount          = newAmount;
    transactionTime = newTransactionTime;
    transferTime    = newTransferTime;
  }
    
  /*****************************************************************************
   * getDetails
   ****************************************************************************/
  public String[] details()
  {
    //System.out.println(inc+"-Transaction.details()");
    return new String[]
    { id
    , "" + fromAccount
    , "" + toAccount
    , "" + amount
    , transactionTime
    , transferTime
    };
  }
    
  /*****************************************************************************
   * Id
   ****************************************************************************/
  @Id
  @Column( name = "Transaction_Id")
  public String getId() 
  { 
    //System.out.println(inc+"-Transaction.getToAccount()");
    return id;
  }
  
   public void setId( String newId) 
  { 
    //System.out.println(inc+"-Transaction.setId("+newId+")");
    id = newId;
  }
  
  /*****************************************************************************
   * From
   ****************************************************************************/
  @Transient
  public String getFrom() 
  { 
    //System.out.println(inc+"-Transaction.getFrom()");
    return from;
  }
  
  public void setFrom( String newFrom ) 
  { 
    //System.out.println(inc+"-Transaction.setFrom("+newFrom+")");
    from = newFrom; 
  }
  
  /*****************************************************************************
   * FromAccount
   ****************************************************************************/
  @ManyToOne // Many Transactions refer to One Account
  ( fetch = FetchType.EAGER
  )
  @JoinColumn( name = "Transaction_FromAccount" )
  public Account getFromAccount() 
  { 
    //System.out.println(inc+"-Transaction.getFromAccount()");
    return fromAccount;
  }
  
  public void setFromAccount( Account newFromAccount ) 
  { 
    //System.out.println(inc+"-Transaction.setFromAccount("+newFromAccount+")");
    fromAccount = newFromAccount; 
    from = newFromAccount.getNumber();
  }
  
  /*****************************************************************************
   * To
   ****************************************************************************/
  @Transient
  public String getTo() 
  { 
    //System.out.println(inc+"-Transaction.getTo()");
    return to;
  }
  
  public void setTo( String newTo ) 
  { 
    //System.out.println(inc+"-Transaction.setTo("+newTo+")");
    to = newTo; 
  }
  
  /*****************************************************************************
   * ToAccount
   ****************************************************************************/
  @ManyToOne // Many Transactions refer to One Account
  ( fetch = FetchType.EAGER
  )
  @JoinColumn( name = "Transaction_ToAccount" )
  public Account getToAccount() 
  { 
    //System.out.println(inc+"-Transaction.getToAccount()");
    return toAccount; 
  }
  
  public void setToAccount( Account newToAccount ) 
  { 
    //System.out.println(inc+"-Transaction.setToAccount("+newToAccount+")");
    toAccount = newToAccount; 
    to = newToAccount.getNumber();
  }
  
  /*****************************************************************************
   * Amount
   ****************************************************************************/
  @Column( name = "Transaction_Amount" )
  public double getAmount() 
  { 
    //System.out.println(inc+"-Transaction.getAmount()");
    return amount;
  }
  
  public void setAmount( double newAmount ) 
  { 
    //System.out.println(inc+"-Transaction.setAmount("+newAmount+")");
    amount = newAmount;
  }
  
  /*****************************************************************************
   * TransactionTime
   ****************************************************************************/
  @Column( name = "Transaction_TransactionTime" )
  public String getTransactionTime() 
  { 
    //System.out.println(inc+"-Transaction.getTransactionTime()");
    return transactionTime; 
  }
  
  public void setTransactionTime(String newTransactionTime) 
  { 
    //System.out.println(inc+"-Transaction.setTransactionTime("+newTransactionTime+")");
    transactionTime = newTransactionTime; 
  }
  
  /*****************************************************************************
   * TransferTime
   ****************************************************************************/
  @Column( name = "Transaction_TransferTime" )
  public String getTransferTime() 
  { 
    //System.out.println(inc+"-Transaction.getTransferTime()");
    return transferTime;    
  }
  
  public void setTransferTime( String newTransferTime ) 
  { 
    //System.out.println(inc+"-Transaction.setTransferTime("+newTransferTime+")");
    transferTime = newTransferTime;    
  }  
  
  /*****************************************************************************
   * Entity Callbacks
   ****************************************************************************/
  @PrePersist 
  void prePersist()
  {
    //System.out.println(inc+"-Transaction.prePersist()");
  }
  
  @PostPersist 
  void postPersist()
  {
    //System.out.println(inc+"-Transaction.postPersist()");
  }
  
  @PreUpdate 
  void preUpdate()
  {
    //System.out.println(inc+"-Transaction.preUpdate()");
  }
  
  @PostUpdate 
  void postUpdate()
  {
    //System.out.println(inc+"-Transaction.postUpdate()");
  }
  
  @PreRemove 
  void preRemove()
  {
    //System.out.println(inc+"-Transaction.preRemove()");
  }
  
  @PostRemove 
  void postRemove()
  {
    //System.out.println(inc+"-Transaction.postRemove()");
  }
  
  @PostLoad 
  void postLoad()
  {
    //System.out.println(inc+"-Transaction.postLoad()");
  }  
  
  /*****************************************************************************
   * toString
   ****************************************************************************/
  @Override
  public String toString()
  {
    return "Transaction["+id+", "+from+", "+to+", "+amount+"]";
  }

  public int compareTo(Transaction o)
  {
    return id.compareTo(o.getId());
  }  
}