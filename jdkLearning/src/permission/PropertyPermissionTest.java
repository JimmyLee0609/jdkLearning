package permission;

import java.security.PermissionCollection;
import java.util.PropertyPermission;

public class PropertyPermissionTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		PropertyPermission permission = new PropertyPermission("name", "action");
		String actions = permission.getActions();
		String name = permission.getName();
		permission.checkGuard("guard");

		boolean implies = permission.implies(permission);

		PermissionCollection newPermissionCollection = permission.newPermissionCollection();

	}

}
