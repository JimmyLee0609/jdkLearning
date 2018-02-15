package chapter7;

 import javax.management.MBeanServer;
 import javax.management.MBeanServerFactory;
 import javax.management.ObjectName;
 import javax.management.relation.Role;
 import javax.management.relation.RoleInfo;
 import javax.management.relation.RoleList;
 import javax.management.relation.RoleResult;
 import javax.management.relation.RelationNotification;
 import javax.management.relation.RelationService;
 import java.util.ArrayList;
 import java.util.Map;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Vector;
 
 
 public class ExampleJMXFindMethods{
 
        private static MBeanServer server = null;
 
 
        public ExampleJMXFindMethods(){
               server = MBeanServerFactory.createMBeanServer();
        }
 
        public static void main(String args[]){
 
               ExampleJMXFindMethods agent = new ExampleJMXFindMethods();
 
               RelationService relServ = new RelationService(false);
 
               PrintObject prntObj = new PrintObject();
 
               try {
 
                   ObjectName objName = new ObjectName("RelationService:type=relate");
  
                   server.registerMBean(relServ,objName);
 
                   ObjectName pObj = new ObjectName("PrinterObject:name=HP Deskjet");
                   ObjectName pObj1 = new ObjectName("PrinterObject:name=HP Laserjet");
                   ObjectName pObj2 = new ObjectName("PrinterObject:name=HP Deskjet Color");
 
                   server.registerMBean(prntObj,pObj);
                   server.registerMBean(prntObj,pObj1);
                   server.registerMBean(prntObj,pObj2);
 
                   // Add Relation Type //
 
                   String className = server.getObjectInstance(pObj).getClassName();
 
                   RoleInfo rInfo = new RoleInfo("Printers",
                                                 className,
                                                 true,
                                                 true,
                                                 0,
                                                 3,
                                                 "Printers Role");
 
                   relServ.createRelationType("IT Printers",new RoleInfo[]{rInfo});
                   relServ.createRelationType("HR Printers",new RoleInfo[]{rInfo});
                   relServ.createRelationType("Marketing Printers",new RoleInfo[]{rInfo});
 
                   // Add a relation //
                   relServ.createRelation("IT1","IT Printers",new RoleList());
                   relServ.createRelation("HR1","HR Printers",new RoleList());
                   relServ.createRelation("MK1","Marketing Printers",new RoleList());
 
                   // Add a Role //
                   List roles = new ArrayList();
                   roles.add(pObj);
                   roles.add(pObj1);
                   roles.add(pObj2);
 
                   Role aRole = new Role("Printers",roles);
 
                   // Set the role //
                   relServ.setRole("IT1",aRole);
                   relServ.setRole("HR1",aRole);
                   relServ.setRole("MK1",aRole);
 
                   // Do queries //
                   System.out.println("Referencing relations>>>>>>>>>>>>");
                   Map map = relServ.findReferencingRelations(pObj2,null,null);
                   System.out.println("Object: " + pObj2.getCanonicalName() + " appears in these relations...");
                   Iterator iter = map.keySet().iterator();
                   while (iter.hasNext()) {
                          String relId = (String)iter.next();
                          System.out.println("Relation: " + relId);                        
                   }
 
 
               } catch (Exception ex) {
                   ex.printStackTrace();
               }
        }
 
 }
