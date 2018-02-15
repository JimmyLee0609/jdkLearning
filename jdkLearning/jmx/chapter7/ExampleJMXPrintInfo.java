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
// 		创建服务器
               ExampleJMXPrintInfo agent = new ExampleJMXPrintInfo();
// 		创建一个关系服务
               RelationService relServ = new RelationService(false);
// 			新建一个标准的MBean
               PrintObject prntObj = new PrintObject();
 
               try {
 
                   ObjectName objName =
                       new ObjectName("RelationService:type=relate");
//  	将关系服务注册到服务器
                   server.registerMBean(relServ,objName);
 
                   ObjectName pObj =
                       new ObjectName("PrinterObject:name=HP Deskjet");
                   ObjectName pObj1 = 
                       new ObjectName("PrinterObject:name=HP Laserjet");
                   ObjectName pObj2 =
                    new ObjectName("PrinterObject:name=HP Deskjet Color");
// 				注册三个打印
                   server.registerMBean(prntObj,pObj);
                   server.registerMBean(prntObj,pObj1);
                   server.registerMBean(prntObj,pObj2);
 
                   // Add Relation Type //
 
                   String className = 
                       server.getObjectInstance(pObj).getClassName();
// 			新建一个RoleInfo
                   RoleInfo rInfo = new RoleInfo("Printers",
                                                 className,
                                                 true,
                                                 true,
                                                 0,
                                                 3,
                                                 "Printers Role");
// 			使用关系服务器创建关联
                   relServ.createRelationType("IT Printers",
                                              new RoleInfo[]{rInfo});
 
                   RoleInfo rInfoPrint =
                        relServ.getRoleInfo("IT Printers","Printers");
//                   										类型名字      关系名字
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
