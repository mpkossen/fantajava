package efg.jpa.bank.db;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import oracle.toplink.essentials.ejb.cmp3.EntityManagerFactoryProvider;

public class Database
{
  /*****************************************************************************
   * Static - Attributes
   ****************************************************************************/
  private static final String         name                 = "ABC-BANK";
  private static EntityManagerFactory entityManagerFactory = null;
  private static EntityManager        entityManager        = null;

  // http://www.oracle.com/technology/products/ias/toplink/JPA/essentials/toplink-jpa-extensions.html
  public static void createDatabase()
  {
    EntityTransaction tx = null;
    try
    {
      System.out.println("Database.createDatabase()");
      System.setProperty("INTERACT_WITH_DB", "true"); // false: only DDL files are created!
      Map<String, String> properties = new HashMap<String, String>();
      // properties.put(EntityManagerFactoryProvider.DDL_GENERATION, EntityManagerFactoryProvider.CREATE_ONLY);
      properties.put(EntityManagerFactoryProvider.DDL_GENERATION, EntityManagerFactoryProvider.DROP_AND_CREATE);
      properties.put(EntityManagerFactoryProvider.DDL_GENERATION_MODE, EntityManagerFactoryProvider.DDL_DATABASE_GENERATION);
      entityManagerFactory = Persistence.createEntityManagerFactory(name, properties);
      if (entityManagerFactory == null)
      {
        System.out.println("Database.createDatabase(): no EntityManagerFactory!");
        return;
      }
      entityManager = entityManagerFactory.createEntityManager();
      if (entityManager == null)
      {
        System.out.println("Database.createDatabase(): no EntityManager!");
        return;       
      }      
      tx = entityManager.getTransaction();     
      if (tx == null)
      {
        System.out.println("Database.createDatabase(): no Transaction!");
        return;       
      }
      tx.begin();
      System.out.println("creating status");
      entityManager.persist(new Status("0", "100009", "1000000"));
      System.out.println("creating 10 managers");
      entityManager.persist(new Account("100000", 0.0, 0.0, "beheerder", "geheim", "0"));
      entityManager.persist(new Account("100001", 0.0, 0.0, "beheerder", "geheim", "0"));
      entityManager.persist(new Account("100002", 0.0, 0.0, "beheerder", "geheim", "0"));
      entityManager.persist(new Account("100003", 0.0, 0.0, "beheerder", "geheim", "0"));
      entityManager.persist(new Account("100004", 0.0, 0.0, "beheerder", "geheim", "0"));
      entityManager.persist(new Account("100005", 0.0, 0.0, "beheerder", "geheim", "0"));
      entityManager.persist(new Account("100006", 0.0, 0.0, "beheerder", "geheim", "0"));
      entityManager.persist(new Account("100007", 0.0, 0.0, "beheerder", "geheim", "0"));
      entityManager.persist(new Account("100008", 0.0, 0.0, "beheerder", "geheim", "0"));
      entityManager.persist(new Account("100009", 0.0, 0.0, "beheerder", "geheim", "0"));
      System.out.println("creating 90 accounts");
      for (int i = 10; i < 100; i++)
      {
        String number = "1000" + i;
        String account = "test" + number;
        entityManager.persist(new Account(number, 0D, 1000000D, account, account, "0"));
      }
      System.out.println("creating 1 transaction");
      Account account1 = entityManager.find(Account.class, "100010");
      Account account2 = entityManager.find(Account.class, "100011");
      String t = "" + System.currentTimeMillis();
      entityManager.persist(new Transaction("1000", account1, account2, 1000000D, t, t));
    }
    finally
    {
      if (tx != null)
      {
        if (!tx.getRollbackOnly())
        {
          try
          {
            tx.commit();
          }
          catch (RollbackException re)
          {
            try
            {
              tx.rollback();
            }
            catch (PersistenceException pe)
            {
              System.err.println("commit: " + pe.getMessage());
            }
          }
        }
        else
        {
          try
          {
            tx.rollback();
          }
          catch (PersistenceException pe)
          {
            System.err.println("rollback: " + pe.getMessage());
          }
        }
      }
      
      if (entityManager != null)
      {
        entityManager.close();
        entityManager = null;
      }
      if (entityManagerFactory != null)
      {
        entityManagerFactory.close();
        entityManagerFactory = null;
      }
    }
  }

  public static synchronized EntityManager getEntityManager(String message)
  {
    // System.out.println("Database.getEntityManager(): " + message);
    if (entityManager == null)
    {
      if (entityManagerFactory == null)
      {
        entityManagerFactory = Persistence.createEntityManagerFactory(name);
      }
      entityManager = entityManagerFactory.createEntityManager();
    }
    return entityManager;
  }

  /*****************************************************************************
   * removeEntitymanager
   ****************************************************************************/
  public static synchronized void removeEntityManager()
  {
    if (entityManager != null)
    {
      entityManager.close();
      entityManager = null;
    }
    if (entityManagerFactory != null)
    {
      entityManagerFactory.close();
      entityManagerFactory = null;
    }
  }
}
