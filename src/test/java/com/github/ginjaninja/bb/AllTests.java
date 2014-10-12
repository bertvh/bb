package com.github.ginjaninja.bb;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.github.ginjaninja.bb.account.account.AccountControllerTest;
import com.github.ginjaninja.bb.account.role.RoleControllerTest;
import com.github.ginjaninja.bb.account.user.UserControllerTest;
import com.github.ginjaninja.bb.capability.CapabilityControllerAccountsTest;
import com.github.ginjaninja.bb.capability.CapabilityControllerRolesTest;
import com.github.ginjaninja.bb.capability.CapabilityControllerTest;
import com.github.ginjaninja.bb.domain.DomainDTOTest;
import com.github.ginjaninja.bb.domain.DomainObjectTest;
import com.github.ginjaninja.bb.home.HomeControllerTest;

@RunWith(Suite.class)
@SuiteClasses({DomainDTOTest.class,
				DomainObjectTest.class,
				HomeControllerTest.class,
				AccountControllerTest.class,
				RoleControllerTest.class,
				UserControllerTest.class,
				CapabilityControllerTest.class,
				CapabilityControllerAccountsTest.class,
				CapabilityControllerRolesTest.class
				})
public class AllTests {

}
