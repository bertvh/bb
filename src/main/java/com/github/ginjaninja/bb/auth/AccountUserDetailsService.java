package com.github.ginjaninja.bb.auth;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.github.ginjaninja.bb.account.capability.Capability;
import com.github.ginjaninja.bb.account.capability.CapabilityDAO;
import com.github.ginjaninja.bb.account.user.User;
import com.github.ginjaninja.bb.account.user.UserDAO;

@Service
public class AccountUserDetailsService implements UserDetailsService {
	@Autowired
	private UserDAO userDAO;
	
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Map<String, Object> params = new HashMap<>();
		params.put("userName", username);
		//fetch user with username
		User user = userDAO.getOne("getUserByUserName", params);
		if(user == null){
			throw new UsernameNotFoundException("Could not find user " + username);
		}
		return new AccountUserDetails(user);
	}
	
	/**
	 * Implement UserDetails
	 *
	 */
	private static final class AccountUserDetails extends User implements UserDetails{
		@Autowired
		private CapabilityDAO capabilityDAO;
		
		/**
		 * Generated Serial
		 */
		private static final long serialVersionUID = -3783991284033523909L;

		private AccountUserDetails(User user){
			super(user);
		}
		
		/**
		 * Get capabilities of user's role and set in Collection<GrantedAuthority>
		 */
		@Override
		public Collection<? extends GrantedAuthority> getAuthorities(){
			Map<String, Object> params = new HashMap<>();
			params.put("id", getId());
			Collection<Capability> capabilities = capabilityDAO.getMany("getUserCapabilities", params);
			//make list of capability names
			String[] auths = new String[capabilities.size()];
			int i = 0;
			for(Capability c : capabilities){
				auths[i] = c.getName();
				i++;
			}
			return AuthorityUtils.createAuthorityList(auths);
		}
		
		@Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }

		@Override
		public String getUsername() {
			return getUsername();
		}
	}

}
