package chapter7;

 import javax.management.MBeanServer;
 import javax.management.MBeanServerFactory;
 import javax.management.ObjectName;
 import javax.management.relation.Role;
 import javax.management.relation.RoleInfo;
 import javax.management.relation.RoleList;
 import javax.management.relation.RoleResult;
 import javax.management.relation.RoleStatus;
 import javax.management.relation.RoleUnresolved;
 import javax.management.relation.RoleUnresolvedList;
 import javax.management.relation.RelationService;
 import java.util.ArrayList;
 import java.util.Iterator;
 import java.util.List;
 
 
 public class ExampleJMXPrintInfo {
 
        private static MBeanServer server = null;
 
 
        public ExampleJMXPrintInfo(){
               server = MBeanServerFactory.createMBeanServer();
        }
 
        public static void main(String args[]){
// 		����������
               ExampleJMXPrintInfo agent = new ExampleJMXPrintInfo();
// 		����һ����ϵ����
               RelationService relServ = new RelationService(false);
// 			�½�һ����׼��MBean
               PrintObject prntObj = new PrintObject();
 
               try {
 
                   ObjectName objName =
                       new ObjectName("RelationService:type=relate");
//  	����ϵ����ע�ᵽ������
                   server.registerMBean(relServ,objName);
 
                   ObjectName pObj =
                       new ObjectName("PrinterObject:name=HP Deskjet");
                   ObjectName pObj1 = 
                       new ObjectName("PrinterObject:name=HP Laserjet");
                   ObjectName pObj2 =
                    new ObjectName("PrinterObject:name=HP Deskjet Color");
// 				ע��������ӡ
                   server.registerMBean(prntObj,pObj);
                   server.registerMBean(prntObj,pObj1);
                   server.registerMBean(prntObj,pObj2);
 
                   // Add Relation Type //
 
                   String className = 
                       server.getObjectInstance(pObj).getClassName();
// 			�½�һ��RoleInfo
                   RoleInfo rInfo = new RoleInfo("Printers",
                                                 className,
                                                 true,
                                                 true,
                                                 0,
                                                 3,
                                                 "Printers Role");
// 			ʹ�ù�ϵ��������������
                   relServ.createRelationType("IT Printers",
                                              new RoleInfo[]{rInfo});
 
                   RoleInfo rInfoPrint =
                        relServ.getRoleInfo("IT Printers","Printers");
//                   										��������      ��ϵ����
                   System.out.println("Role Name: " +
                                      rInfoPrint.getName());
                   System.out.println("Role Description: " + 
                                      rInfoPrint.getDescription());
                   System.out.println("Role Class Name: " + 
                                    rInfoPrint.getRefMBeanClassName());
                   System.out.println("Is Readable: " + 
                                    rInfoPrint.isReadable());
                   System.out.println("Is Writable: " + 
                                    rInfoPrint.isWritable());
                   System.out.println("Min Beans: " + 
                                    rInfoPrint.getMinDegree());
                   System.out.println("Max MBeans: " + 
                                    rInfoPrint.getMaxDegree());
 
               } catch (Exception ex) {
                   ex.printStackTrace();
               }
        }
 
 
 }
