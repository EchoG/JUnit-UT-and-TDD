import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


public class Account  {
	
	// the unique user name of account owner
	private String userName;
	
	// list of members who awaiting an acceptance response from this account's owner 
	private Set<String> pendingResponses = new HashSet<String>();
	
	// list of members who are friends of this account's owner
	private Set<String> friends = new HashSet<String>();
	
	private Collection<String> sentRequests = new HashSet<String>();
	boolean setAutoAccept = false;
	
	public Account(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	// return list of member who had sent  a friend request to this account's owners 
	// and still waiting for a response
	public Set<String> whoWantsToBeFriends() {
		return pendingResponses; 
	}

	private void addSentRequest(String toAccount){
		if(!sentRequests.contains(toAccount)){
			sentRequests.add(toAccount);
		}
		
	}
	
	// an incoming friend request to this account's owner from member with user name userName
	public void befriend(Account fromAccount) {
		if(fromAccount!=null){
			if (!friends.contains(fromAccount.getUserName()) && !pendingResponses.contains(fromAccount.getUserName())) {
				pendingResponses.add(fromAccount.getUserName());
				fromAccount.addSentRequest(this.getUserName());
				
				if (setAutoAccept==true){
					friends.add(fromAccount.getUserName());
					fromAccount.friends.add(this.getUserName());
					this.pendingResponses.remove(fromAccount.getUserName());
					fromAccount.sentRequests.remove(this.getUserName());
				}
			}
			
		}
		
	}

	// check if account owner has member with user name userName as a friend
	public boolean hasFriend(String userName) {
		if(!friends.isEmpty() && friends.contains(userName)){
			return true;
		}else{
			return false;
		}
	}

	// send an acceptance to a member still awaiting a friend-request response from this account owner 
	public void accepted(Account toAccount) {
		if(toAccount.pendingResponses.contains(this.getUserName()) && !friends.contains(toAccount.getUserName())){
			friends.add(toAccount.getUserName());
			toAccount.friends.add(this.getUserName());
			toAccount.pendingResponses.remove(this.getUserName());
			sentRequests.remove(toAccount.getUserName());
		}
	}
	
	/* automatically accept all new friend requests sent to me
	 */
	public void autoAccept() {
		setAutoAccept = true;
	}
	
	/* an rejection notification from an account that 
	 * a friend request sent from this account 
	 * has been rejected
	 * Another one reject my friend request. I was rejected my toAccount
	 */
	public void rejected(Account toAccount) {
		toAccount.pendingResponses.remove(this.getUserName());
		sentRequests.remove(toAccount.getUserName());
	}
	
	//A1: who did I ask to be friend
	public Collection<String> whoDidIAskToBefriend(){
		return sentRequests;
	}
		
	//I was rejected by toAccount
	public void unfriend(Account toAccount){
		friends.remove(toAccount.getUserName());
		toAccount.friends.remove(this.getUserName());
	}
		
	public Set<String> getFriends(){
		return friends;
	}

}
