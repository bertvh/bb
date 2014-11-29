package com.github.ginjaninja.bb.auth;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.github.ginjaninja.bb.account.user.UserLoginDTO;

@Service
public class AccountUserDetailsService implements UserDetailsService {
	private static final Logger LOG = LoggerFactory.getLogger("UserDetailsService");
	
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private CapabilityDAO capabilityDAO;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LOG.info("loadUserByUsername");
		Map<String, Object> params = new HashMap<>();
		params.put("userName", username);
		//fetch user with username
		User user = userDAO.getOne("getUserByUserName", params);
		if(user == null){
			throw new UsernameNotFoundException("Could not find user " + username);
		}
		
		//create UserLoginDTO from user
		UserLoginDTO userLoginDTO = new UserLoginDTO(user);
		
		params.clear();
		params.put("id", user.getId());
		Collection<Capability> capabilities = capabilityDAO.getMany("getUserCapabilities", params);
		
		//set capabilities in UserLoginDTO
		userLoginDTO.setCapabilities(capabilities);
		return new AccountUserDetails(userLoginDTO);
	}
	
	/**
	 * Implement UserDetails
	 *
	 */
	private static final class AccountUserDetails extends UserLoginDTO implements UserDetails{
		/**
		 * Generated Serial
		 */
		private static final long serialVersionUID = -3783991284033523909L;

		private AccountUserDetails(UserLoginDTO userLoginDTO){
			super(userLoginDTO);
		}
		
		/**
		 * Get capabilities of user's role and set in Collection<GrantedAuthority>
		 */
		@Override
		public Collection<? extends GrantedAuthority> getAuthorities(){
			//make list of capability names
			String[] auths = new String[getCapabilities().size()];
			int i = 0;
			for(Capability c : getCapabilities()){
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
			return getUserName();
		}
	}

}