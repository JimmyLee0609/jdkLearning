package chapter7;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.management.relation.Role;
import javax.management.relation.RoleInfo;
import javax.management.relation.RoleList;
import javax.management.relation.RoleResult;
import javax.management.relation.RelationService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;


public class ExampleJMXAddRoles {

       private static MBeanServer server = null;


       public ExampleJMXAddRoles(){
              server = MBeanServerFactory.createMBeanServer();
       }

       public static void main(String args[]){

              ExampleJMXAddRoles agent = new ExampleJMXAddRoles();

              RelationService relServ = new RelationService(false);

              PrintObject prntObj = new PrintObject();

              try {

                  ObjectName objName = 
                      new ObjectName("RelationService:type=relate");
 
                  server.registerMBean(relServ,objName);

                  ObjectName pObj = 
                      new ObjectName("PrinterObject:name=HP Deskjet");
                  ObjectName pObj1 = 
                      new ObjectName("PrinterObject:name=HP Laserjet");
                  ObjectName pObj2 = 
                      new ObjectName("PrinterObject:name=HP Deskjet Color");

                  server.registerMBean(prntObj,pObj);
                  server.registerMBean(prntObj,pObj1);
                  server.registerMBean(prntObj,pObj2);

                  // Add Relation Type //

                  String className =
                      server.getObjectInstance(pObj).getClassName();

                  RoleInfo rInfo = new RoleInfo("Printers",
                                                className,
                                                true,
                                                true,
                                                0,
                                                3,
                                                "Printers Role");

                  relServ.createRelationType("IT Printers",
                                             new RoleInfo[]{rInfo});

                  // Add a relation //
                  relServ.createRelation("ID1","IT Printers",
                                         new RoleList());

                  // Add a Role //
                  List roles = new ArrayList();
                  roles.add(pObj);
                  roles.add(pObj1);
                  roles.add(pObj2);

                  Role aRole = new Role("Printers",roles);

                  // Set the role //
                  relServ.setRole("ID1",aRole);

                  // Access the Roles //
                  RoleResult roleResult = relServ.getAllRoles("ID1");

                  // Print Roles Successfully Accessed //
                  RoleList roleList = roleResult.getRoles();
                  Iterator roleIter = roleList.iterator();
                  while (roleIter.hasNext()){
                         Role theRole = (Role)roleIter.next();
                         System.out.println("Role Name: " + 
                                            theRole.getRoleName());
                         System.out.println("MBeans in Role: " + 
                                       theRole.getRoleValue().size()); 
                  }

              } catch (Exception ex) {
                  ex.printStackTrace();
              }
       }

}
