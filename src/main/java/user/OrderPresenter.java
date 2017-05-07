package user;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import userAccountManagment.Order;

public class OrderPresenter
{
	private String referenceId;
	private String collegeName;
	private String branchName;
	private int amountPayable;
	private List<Order> orders;

	public String getCollegeName()
	{
		return collegeName;
	}
	public void setCollegeName(String collegeName)
	{
		this.collegeName = collegeName;
	}
	public String getReferenceId()
	{
		return referenceId;
	}
	public void setReferenceId(String referenceId)
	{
		this.referenceId = referenceId;
	}
	public String getBranchName()
	{
		return branchName;
	}
	public void setBranchName(String branchName)
	{
		this.branchName = branchName;
	}
	public int getAmountPayable()
	{
		return amountPayable;
	}
	public void setAmountPayable(int amountPayable)
	{
		this.amountPayable = amountPayable;
	}
	public List<Order> getOrders()
	{
		return orders;
	}
	public void setOrders(List<Order> orders)
	{
		this.orders = orders;
	}
	
	public static void main(String args[])
	{
		String paragraph = new String();
        paragraph+="I am at office right now.I love to work at oFFicE.My OFFICE located at center of kathmandu valley";
        String searchWord = "office";
        Pattern pattern = Pattern.compile(searchWord, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(paragraph);
        int count = 0;
        while (matcher.find())
            count++;
        System.out.println(count);

	}
	
}
