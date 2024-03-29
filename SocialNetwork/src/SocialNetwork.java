import java.util.HashSet;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

public class SocialNetwork implements ISocialNetwork{
	
	private Collection<Account> accounts = new HashSet<Account>();
	private static Hashtable<String, String> blockPeople = new Hashtable<String, String>();// key is for the blocked person, value is for who block them
	Account loginMember;
	private boolean isLogin = false;

	// join SN with a new user name
	public Account join(String userName) {
		if(userName!=null && userName!=""){
			if(findAccountForUserName(userName)!=null){
				try {
					throw new UserExistsException();
				} catch (UserExistsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
			else{
				Account newAccount = new Account(userName);
				accounts.add(newAccount);
				return newAccount;
			}
			
		}
		else{
			return null;
		}
	}

	// find a member by user name 
	private Account findAccountForUserName(String userName) {
		// find account with user name userName
		// not accessible to outside because that would give a user full access to another member's account
		for (Account each : accounts) {
			if (userName!=null && userName!="" && each.getUserName().equals(userName)) 
					return each;
		}
		return null;
	}
	
	// list user names of all members
	public Collection<String> listMembers() {
		Collection<String> members = new HashSet<String>();
		for (Account each : accounts) {
			if(loginMember != null && !blockPeople.isEmpty() && blockPeople.containsKey(loginMember.getUserName()) && blockPeople.get(loginMember.getUserName()) == each.getUserName()){
				continue;
			}else{
				members.add(each.getUserName());
			}
			
		}
		return members;
		
	}
	
	//A2: accept all friend requests
	public void acceptAllFriendRequestsTo() throws UserNotFoundException{
			Collection<String> pendingReq = loginMember.whoWantsToBeFriends();
			Collection<String> pendingReqBack = new HashSet<String>(pendingReq);
			for (String userName : pendingReqBack){
				if(findAccountForUserName(userName)!=null){
					//Account user = findAccountForUserName(userName);
					acceptFriendRequestFrom(userName);
					
				}
				
			}
			
		}
		
	//A4: reject all friend requests sent to me
	public void rejectAllFriendRequestsTo() throws UserNotFoundException{
		Collection<String> pendingReq = loginMember.whoWantsToBeFriends();
		Collection<String> pendingReqBack = new HashSet<String>(pendingReq);
		for (String userName : pendingReqBack){
			rejectFriendRequestFrom(userName);
		}		
	}

	//B2: Implement the login method
	@Override
	public Account login(Account me) throws UserNotFoundException {
		// TODO Auto-generated method stub
		if(!accounts.contains(me)){
			throw new UserNotFoundException();
		}
		if(loginMember!=null){
			logout();
			loginMember = me;
			isLogin = true;
			return me;
		}else{
			loginMember = me;
			isLogin = true;
			return me;
		}
	}

	@Override
	public void logout() {
		// TODO Auto-generated method stub
		loginMember = null;
		isLogin = false;		
	}

	@Override
	public boolean hasMember(String userName) {
		// TODO Auto-generated method stub
		if(findAccountForUserName(userName)!=null){
			return true;
		}else{
			return false;
		}		
	}

	@Override
	// send request from loginMember to userName
	public void sendFriendRequestTo(String userName) throws UserNotFoundException {
		// TODO Auto-generated method stub
		if(findAccountForUserName(userName)!=null){
			findAccountForUserName(userName).befriend(loginMember);
		}
	}

	@Override
	public void block(String userName) throws UserNotFoundException {
		// TODO Auto-generated method stub
		Account personBlocked = findAccountForUserName(userName);
		blockPeople.put(userName, loginMember.getUserName());
		loginMember.unfriend(personBlocked);
		loginMember.whoDidIAskToBefriend().remove(userName);
		loginMember.whoWantsToBeFriends().remove(userName);
	}

	@Override
	public void unBlock(String userName) throws UserNotFoundException {
		// TODO Auto-generated method stub
		if(blockPeople.containsKey(userName) && blockPeople.get(userName) == loginMember.getUserName()){
			blockPeople.remove(userName);
		}
	}

	//send unfriend request to userName
	@Override
	public void unfriend(String userName) throws UserNotFoundException {
		// TODO Auto-generated method stub
		if(findAccountForUserName(userName)!=null){
			findAccountForUserName(userName).unfriend(loginMember);
		}
	}

	//loginMember accept userName
	@Override
	public void acceptFriendRequestFrom(String userName) throws UserNotFoundException {
		// TODO Auto-generated method stub
		if(findAccountForUserName(userName)!=null){
			findAccountForUserName(userName).accepted(loginMember);
		}
	}

	@Override
	public void rejectFriendRequestFrom(String userName) throws UserNotFoundException {
		// TODO Auto-generated method stub
		if(findAccountForUserName(userName)!=null){
			findAccountForUserName(userName).rejected(loginMember);
		}
	}

	@Override
	public void autoAcceptFriendRequests() {
		// TODO Auto-generated method stub
		loginMember.autoAccept();
	}

	@Override
	public void acceptFriendRequestsExplicitly() {
		// TODO Auto-generated method stub
		loginMember.setAutoAccept =  false;
	}

	@Override
	public Collection<String> recommendFriends() {
		// TODO Auto-generated method stub
		Collection<String> friendsRecommened = new HashSet<String>();//people who will be recommended to the loginMember
		Hashtable<String, Integer> allFriends = new Hashtable<String, Integer>();//save all friends of friends
		Set<String> friends = loginMember.getFriends();
		Iterator<String> it = friends.iterator();
		while(it.hasNext()){
			String str = it.next();
			Account oneFri = findAccountForUserName(str);
			Set<String> friOfFri = oneFri.getFriends();
			Iterator<String> itFri = friOfFri.iterator();
			while(itFri.hasNext()){
				String tmp = itFri.next();
				if(allFriends.containsKey(tmp)){ //if the person has been put in, then the count add 1
					int count = allFriends.get(tmp);
					allFriends.put(tmp, count+1);
				}else{
					allFriends.put(tmp, 1);
				}
					
			}
				
		}
		Set<String> keys = allFriends.keySet();
		for(String key:keys){
			// if the person isn't a friend of the loginMember and has more than one common friends with the loginMember
			if(!loginMember.hasFriend(key) && allFriends.get(key) > 1){
				friendsRecommened.add(key);
			}
		}
		return friendsRecommened;
	}

	@Override
	public void leave() {
		// TODO Auto-generated method stub
		for(Account account : accounts){
			if(account!=loginMember){
				if(account.hasFriend(loginMember.getUserName())){
					account.unfriend(loginMember);
				}
				if(account.whoDidIAskToBefriend().contains(loginMember.getUserName())){
					account.whoDidIAskToBefriend().remove(loginMember.getUserName());
				}
				if(account.whoWantsToBeFriends().contains(loginMember.getUserName())){
					account.whoWantsToBeFriends().remove(loginMember.getUserName());
				}
			}
				
		}
		accounts.remove(loginMember);
		logout();
	}

}
