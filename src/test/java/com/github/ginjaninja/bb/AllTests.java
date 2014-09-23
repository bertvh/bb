package com.github.ginjaninja.bb;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.github.ginjaninja.bb.account.account.AccountControllerTest;
import com.github.ginjaninja.bb.account.role.RoleController;
import com.github.ginjaninja.bb.account.user.UserControllerTest;
import com.github.ginjaninja.bb.account.userRole.UserRoleControllerTest;
import com.github.ginjaninja.bb.domain.DomainDTOTest;
import com.github.ginjaninja.bb.domain.DomainObjectTest;
import com.github.ginjaninja.bb.home.HomeControllerTest;

@RunWith(Suite.class)
@SuiteClasses({HomeControllerTest.class,
				AccountControllerTest.class,
				RoleController.class,
				UserControllerTest.class,
				UserRoleControllerTest.class,
				DomainDTOTest.class,
				DomainObjectTest.class})
public class AllTests {

}
