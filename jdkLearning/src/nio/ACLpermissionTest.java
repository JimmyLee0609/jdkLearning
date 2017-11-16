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
		
//		��ȡ�û�Ȩ�޲��ҷ��񣬾���ע���ķ���
		UserPrincipalLookupService upls = fs.getUserPrincipalLookupService();
//		�����û�
		UserPrincipal user = upls.lookupPrincipalByName("cobbl");
		String string = upls.toString();
		Path path = Paths.get("d:/temp/abc.txt");
		
//		��ȡ�ļ�ӵ������ͼ
		FileOwnerAttributeView foav = Files.getFileAttributeView(path, FileOwnerAttributeView.class);
//		��ȡ�ļ���ӵ����
		UserPrincipal owner = foav.getOwner();
//		�����ļ���ӵ����
		// foav.setOwner(user);//��ҪȨ��
		
		
//		��ȡACL�ļ�������ͼ
		AclFileAttributeView view = Files.getFileAttributeView(path, AclFileAttributeView.class);
//		��ȡACL�ļ�
		List<AclEntry> acl = view.getAcl();
		for (AclEntry aclEntry : acl) {
			Set<AclEntryFlag> flags = aclEntry.flags();
			for (AclEntryFlag aclEntryFlag : flags) {
				String name = aclEntryFlag.name();
			}
//			�û�Ȩ��
			UserPrincipal principal = aclEntry.principal();
//			�������ͣ���ö��
			AclEntryType type = aclEntry.type();
			int ordinal = type.ordinal();
//			��ɼ�
			Set<AclEntryPermission> permissions = aclEntry.permissions();
		}
//		��ȡ��ͼ��������
		UserPrincipal owner2 = view.getOwner();
//		ACL�����
		AclEntryPermission deleteChild = AclEntryPermission.DELETE_CHILD;
		AclEntryPermission appendData = AclEntryPermission.APPEND_DATA;
//		����һ��ACL
		Builder newBuilder = AclEntry.newBuilder();
//		�����û�
		Builder setPrincipal = newBuilder.setPrincipal(owner2);
//		��������
		Builder setType = newBuilder.setType(AclEntryType.ALLOW);
//		�������
		Builder setPermissions = newBuilder.setPermissions(deleteChild);
//		������
		AclEntry build = newBuilder.build();
		
		
//		��ӵ�����
		acl.add(build);
//		���������õ���ͼ
		view.setAcl(acl);

	}

}
