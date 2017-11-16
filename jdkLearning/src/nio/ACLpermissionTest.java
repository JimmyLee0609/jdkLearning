package nio;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntry.Builder;
import java.nio.file.attribute.AclEntryFlag;
import java.nio.file.attribute.AclEntryPermission;
import java.nio.file.attribute.AclEntryType;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.List;
import java.util.Set;

public class ACLpermissionTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		FileSystem fs = FileSystems.getDefault();
		
//		获取用户权限查找服务，就是注册表的服务
		UserPrincipalLookupService upls = fs.getUserPrincipalLookupService();
//		查找用户
		UserPrincipal user = upls.lookupPrincipalByName("cobbl");
		String string = upls.toString();
		Path path = Paths.get("d:/temp/abc.txt");
		
//		获取文件拥有者视图
		FileOwnerAttributeView foav = Files.getFileAttributeView(path, FileOwnerAttributeView.class);
//		获取文件的拥有者
		UserPrincipal owner = foav.getOwner();
//		设置文件的拥有者
		// foav.setOwner(user);//需要权限
		
		
//		获取ACL文件控制视图
		AclFileAttributeView view = Files.getFileAttributeView(path, AclFileAttributeView.class);
//		获取ACL的键
		List<AclEntry> acl = view.getAcl();
		for (AclEntry aclEntry : acl) {
			Set<AclEntryFlag> flags = aclEntry.flags();
			for (AclEntryFlag aclEntryFlag : flags) {
				String name = aclEntryFlag.name();
			}
//			用户权限
			UserPrincipal principal = aclEntry.principal();
//			键的类型，是枚举
			AclEntryType type = aclEntry.type();
			int ordinal = type.ordinal();
//			许可集
			Set<AclEntryPermission> permissions = aclEntry.permissions();
		}
//		获取视图的所有者
		UserPrincipal owner2 = view.getOwner();
//		ACL的许可
		AclEntryPermission deleteChild = AclEntryPermission.DELETE_CHILD;
		AclEntryPermission appendData = AclEntryPermission.APPEND_DATA;
//		建造一个ACL
		Builder newBuilder = AclEntry.newBuilder();
//		设置用户
		Builder setPrincipal = newBuilder.setPrincipal(owner2);
//		设置类型
		Builder setType = newBuilder.setType(AclEntryType.ALLOW);
//		设置许可
		Builder setPermissions = newBuilder.setPermissions(deleteChild);
//		创力建
		AclEntry build = newBuilder.build();
		
		
//		添加到键集
		acl.add(build);
//		将键集设置到视图
		view.setAcl(acl);

	}

}
