package abc.bank.beans;
// Xiabili Xiahilil

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseEvent;
import javax.faces.validator.ValidatorException;
import abc.bank.jaas.MyPrincipal;
import efg.jpa.bank.AccountManager;

public class BankManagerBean extends CommonBean
{
	/**********************************************************\
	 * Static
	\**********************************************************/
	private static final long serialVersionUID     = 1L;
	private static boolean[] render = 
	  { false // renderNewAcount
	  , false // renderCheckAccount
	  };
	
	/**********************************************************\
	 * Protected Attributes
	\**********************************************************/
	protected ExternalContext	ex		= FacesContext.getCurrentInstance().getExternalContext();
	protected String name = "";
	protected String pass = "";
	protected String pass2 = "";
	protected String accountnr = "";
	protected String limit = "";
	
	MyPrincipal mp = (MyPrincipal)ex.getUserPrincipal();
	/**********************************************************\
	 * Constructor
	\**********************************************************/
	
	public BankManagerBean()
	{
		System.out.println("BankManagerBean()");
		
	}
	
	/*****************************************************************************
	 * call backs
	 ****************************************************************************/
	 public void beforePhase(PhaseEvent phaseEvent)
	 {
	    System.out.println("BankManagerBean.beforePhase(" + phaseEvent.getPhaseId() + ")");
	 }

	 public void afterPhase(PhaseEvent phaseEvent)
	 {
	    System.out.println("BankManagerBean.afterPhase(" + phaseEvent.getPhaseId() + ")");
	 }
	 
	 public boolean getRenderNewAccount()
	 {
		 System.out.println("BankManagerBean.getRenderNewAccount()");
		 setError("");
		 return render[0];
	 }
	 
	 public boolean getRenderCheckAccount()
	 {
		 System.out.println("BankManagerBean.getRenderCheckAccount()");
		 setError("");
		 return render[1];
	 }
	 
	 public boolean getRenderTransactie()
	 {	 
		 System.out.println("BankManagerBean.getRenderTransactie()");
		 setError("");
		 MyPrincipal mp = (MyPrincipal) ex.getUserPrincipal();
		 System.out.println("mp="+mp);		 
		 AccountManager am = mp.getAccountManager();
		 System.out.println("am="+am);
		 String[][] tr;
		 try
		 {
			 tr = am.getTransactions(getAccountnr());
			 System.out.println("tr="+tr);	
			 return tr.length>0;
		 }
		 catch(Exception e)
		 {
			 System.out.println("Geen transactie");
		 }
			 		 
		 return false;
	 }
	 
	 public boolean getRenderError()
	 {
		 boolean b = getError().length()>0;
		 System.out.println("---------> BankManagerBean.getRenderError() " + b);
		 return b;
	 }
	 
	 public boolean getRenderClosed()
	 {
		 System.out.println("BankManagerBean.getRenderClosed()");
		 setError("");
		 getStatuss();
		 return isClosed;
	 }
	 
	 /*****************************************************************************
	  * actionListener methods
	  ****************************************************************************/
	 
	 public void newAccount(ActionEvent ae)
	 {
		 System.out.println("BankManagerBean.newAccount()");
		 reset(0);
	 }
	 
	 public void checkAccount(ActionEvent ae)
	 {
		 System.out.println("BankManagerBean.checkAccount()");
		 reset(1);
	 }
	 
	 @SuppressWarnings("null")
	public void exit(ActionEvent ae)
	 {
		 System.out.println("BankManager.exit()");
		 reset(-1);
	 }
	 
	 private void reset(int i)
	 {
		 setError("");
		 render = new boolean[]{false,false,false};
		 if(i<0)
		 {
			 return;
		 }
		 render[i] = true;
	 }
	 
	 
	 /*****************************************************************************
	   * Set Bank Open/Close
	  ****************************************************************************/
	 
	 public void doOpen(ActionEvent ae)
	 {
		 System.out.println("BankManager.doOpen()");
		 String s= mp.getAccountManager().getStatus();
		 if(!s.equals("abc-bank is open and busy")||!s.equals("abc-bank is open and idle"))
		 {
			 mp.getAccountManager().setOpen(true);
			 getStatus();
		 }
	 }
	 
	 public void doClose(ActionEvent ae)
	 {
		 System.out.println("BankManagerBean.doClose()");
		 String s= mp.getAccountManager().getStatus();
		 if(s.equals("abc-bank is open and busy")||s.equals("abc-bank is open and idle"))
		 {
			 mp.getAccountManager().setOpen(false);
			 getStatus();
		 }
	 }
	 
	 /*****************************************************************************
	  * Validation
	  ****************************************************************************/
	 
	 public void nameInputValidator(FacesContext ctx, UIComponent cmp, Object val) throws ValidatorException
	 {
		 
		 System.out.println("BankManagerBean.nameInputValidator(" + val + ")");
		 String newName = (String) val;
		 if (newName.equals(" "))
		 {
			 System.out.println("BankManagerBean.nameInputValidator(): wrong input from: " + cmp.getId());
			 throw new ValidatorException(new FacesMessage("  Name is a required field!"));
		 }
	 }
	 
	 public void passInputValidator(FacesContext ctx, UIComponent cmp, Object val) throws ValidatorException
	 {
		 
		 System.out.println("BankManagerBean.passInputValidator(" + val + ")");
		 String newPass = (String) val;
		 if (newPass.equals(" "))
		 {
			 System.out.println("BankManagerBean.passInputValidator(): wrong input from: " + cmp.getId());
			 throw new ValidatorException(new FacesMessage("  Pass is a required field!"));
		 }
	 }
	 
	 public void accountnrInputValidator(FacesContext ctx, UIComponent cmp, Object val) throws ValidatorException
	 {
		 
		 System.out.println("BankManagerBean.accountnrInputValidator(" + val + ")");
		 String newAccountNr = (String) val;
		 if (newAccountNr.equals(" "))
		 {
			 System.out.println("BankManagerBean.accountnrInputValidator(): wrong input from: " + cmp.getId());
			 throw new ValidatorException(new FacesMessage("  Accountnr is a required field!"));
		 }else{
			 try
			 {
				 int i = Integer.parseInt(newAccountNr);
			 }
			 catch (Exception e) {
				// TODO: handle exception
				 System.out.println("BankManagerBean.accountnrInputValidator():not integer from: " + cmp.getId());
				 throw new ValidatorException(new FacesMessage("  Accountnr must be numeric !"));
			}
		 }
	 }
	 
	 public void limitInputValidator(FacesContext ctx, UIComponent cmp, Object val) throws ValidatorException
	 {
		 
		 System.out.println("BankManagerBean.limitInputValidator(" + val + ")");
		 String newAccountNr = (String) val;
		 if (newAccountNr.equals(" "))
		 {
			 System.out.println("BankManagerBean.limitInputValidator(): wrong input from: " + cmp.getId());
			 throw new ValidatorException(new FacesMessage("  Limit is a required field!"));
		 }
	 }
	 
	 /*****************************************************************************
	   * Get and Set Method
	  ****************************************************************************/
	 
	 public String getName()
	 {
		 System.out.println("BankManagerBean.getName()");
		 return name;
	 }
	 
	 public String getPass()
	 {
		 System.out.println("BankManagerBean.getPass()");
		 return pass;
	 }
	 
	 public String getPass2()
	 {
		 System.out.println("BankManagerBean.getPass()");
		 return pass2;
	 }
	 
	 public String getLimit()
	 {
		 System.out.println("BankManagerBean.getLimit()");
		 return limit;
	 }
	 
	 public String getAccountnr()
	 {
		 System.out.println("BankManagerBean.getAccountnr()");
		 return accountnr;
	 }
	 
	 public void setName(String n)
	 {
		 System.out.println("BankManagerBean.setName()");
		 name = n;
	 }
	 
	 public void setPass(String n)
	 {
		 System.out.println("BankManagerBean.setPass()");
		 pass = n;
	 }
	 
	 public void setPass2(String n)
	 {
		 System.out.println("BankManagerBean.setPass()");
		 pass2 = n;
	 }
	 
	 public void setLimit(String n)
	 {
		 System.out.println("BankManagerBean.setLimit()");
		 limit = n;
	 }
	 
	 public void setAccountnr(String n)
	 {
		 System.out.println("BankManagerBean.setAccountnr()");
		 accountnr = n;
	 }
	 
	 public void clear()
	 {
		 System.out.println("Clear()");
		 name = ""; pass= "";
		 limit=""; 	accountnr = "";
	 }
	 
	 public String getStatus()
	 {
		 MyPrincipal mp = (MyPrincipal) ex.getUserPrincipal();
		 String s = mp.getAccountManager().getStatus();
		 if(s.equals("abc-bank is open and idle") || s.equals("abc-bank is open and busy"))
		 {
			 s = "Bank is open";
			 isClosed = false;
		 }
		 else
		 {
			 s = "Bank is closed";
			 isClosed = true;
		 }
		 System.out.println("BankManagerBean.getStatus(): "+s);
		 return s;
	 }
}
