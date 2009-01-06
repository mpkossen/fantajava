package efg.jpa.bank.db;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table
( name = "Status" 
, schema = "test"
)
public class Status implements Serializable
{  
  private static final long serialVersionUID = 1L;
/*****************************************************************************
   * Static
   ****************************************************************************/
  public static final Object maxAccountLock = new Object();
  public static final Object maxTransactionLock = new Object();
  public static final int CLOSED = 1; // Account offices are closed
  public static final int IDLE   = 2; // TransactionManager is idle
  
  //private static int INC = 0;
  //private static synchronized int getINC() { return INC++; }
  
  /*****************************************************************************
   * Dynamic
   ****************************************************************************/
  //private int inc = getINC();
  
  private String id;
  private String bank;
  private String maxAccount;
  private String maxTransaction;
  
  /*****************************************************************************
   * Construction
   ****************************************************************************/
  public Status()
  {
    //System.out.println(inc+"-Status()");
  }
  
  public Status(String newId, String newMaxAccount, String newMaxTransaction)
  {
    //System.out.println(inc+"-Status("+newId+","+newMaxAccount+", "+newMaxTransaction+")");
    id = newId;             
    bank = "" + IDLE;          
    maxAccount = newMaxAccount;     
    maxTransaction = newMaxTransaction; 
  }
    
  /*****************************************************************************
   * Id
   ****************************************************************************/
  @Id
  @Column( name = "Status_Id" )
  public String getId() 
  { 
    //System.out.println(inc+"-Status.getId()");
    return id;
  }
  
  public void setId( String newId ) 
  { 
    //System.out.println(inc+"-Status.setId("+newId+")");
    id= newId;
  }
  
  /*****************************************************************************
   * Bank
   ****************************************************************************/
  @Column( name = "Status_Bank" )
  public String getBank() 
  { 
    //System.out.println(inc+"-Status.getBank()");
    return bank;
  }
  
  public void setBank( String newBank ) 
  { 
    //System.out.println(inc+"-Status.setBank("+newBank+")");
    bank = newBank;
  }
  
  /*****************************************************************************
   * MaxAccount
   ****************************************************************************/
  @Column( name = "Status_MaxAccount" )
  public String getMaxAccount() 
  { 
    //System.out.println(inc+"-Status.getMaxAccount()");
    return maxAccount; 
  }
  
  public void setMaxAccount( String newMaxAccount ) 
  { 
    //System.out.println(inc+"-Status.setMaxAccount("+newMaxAccount+")");
    maxAccount = newMaxAccount;     
  }
  
  /*****************************************************************************
   * MaxTransaction
   ****************************************************************************/
  @Column( name = "Status_MaxTransaction" )
  public String getMaxTransaction() 
  { 
    //System.out.println(inc+"-Status.getMaxTransaction()");
    return maxTransaction; 
  }

  public void setMaxTransaction( String newMaxTransaction) 
  { 
    //System.out.println(inc+"-Status.setMaxTransaction("+newMaxTransaction+")");
    maxTransaction = newMaxTransaction; 
  }
  
  /*****************************************************************************
   * Entity Callbacks
   ****************************************************************************/
  @PrePersist 
  void prePersist()
  {
    //System.out.println(inc+"-Status.prePersist()");
  }
  
  @PostPersist 
  void postPersist()
  {
    //System.out.println(inc+"-Status.postPersist()");
  }
  
  @PreUpdate 
  void preUpdate()
  {
    //System.out.println(inc+"-Status.preUpdate()");
  }
  
  @PostUpdate 
  void postUpdate()
  {
    //System.out.println(inc+"-Status.postUpdate()");
  }
  
  @PreRemove 
  void preRemove()
  {
    //System.out.println(inc+"-Status.preRemove()");
  }
  
  @PostRemove 
  void postRemove()
  {
    //System.out.println(inc+"-Status.postRemove()");
  }
  
  @PostLoad 
  void postLoad()
  {
    //System.out.println(inc+"-Status.postLoad()");
  }  
  
  /*****************************************************************************
   * toString
   ****************************************************************************/
  @Override
  public String toString()
  {
    return "Status["+id+", "+bank+", "+maxAccount+", "+maxTransaction+"]";
  }  
}