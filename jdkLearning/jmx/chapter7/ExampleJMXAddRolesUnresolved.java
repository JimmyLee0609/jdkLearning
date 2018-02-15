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


public class ExampleJMXAddRolesUnresolved {

       private static MBeanServer server = null;


       public ExampleJMXAddRolesUnresolved(){
              server = MBeanServerFactory.createMBeanServer();
       }

       public static void main(String args[]){

              ExampleJMXAddRolesUnresolved agent = 
                     new ExampleJMXAddRolesUnresolved();

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
                                                false,
                                                true,
                                                0,
                                                3,
                                                "Printers Role");

                  relServ.createRelationType("IT Printers",
                                             new RoleInfo[]{rInfo});

                  // Add a Role //
                  List roles = new ArrayList();
                  roles.add(pObj);
                  roles.add(pObj1);
                  roles.add(pObj2);

                  // Add a relation //
                  relServ.createRelation("ID1","IT Printers",
                                         new RoleList());

                  Role aRole = new Role("Printers",roles);

                  RoleList aRoleList = new RoleList();
                  aRoleList.add(aRole);
                  relServ.setRoles("ID1",aRoleList);

                  // Access the Roles //
                  RoleResult roleResult = relServ.getAllRoles("ID1");

                  // Print Roles Successfully Accessed //
                  RoleList roleList = roleResult.getRoles();
                  System.out.println("Resolved roles: " + 
                                     roleList.size());
                  Iterator roleIter = roleList.iterator();
                  while (roleIter.hasNext()){
                         Role theRole = (Role)roleIter.next();
                         System.out.println("Role Name: " +
                                            theRole.getRoleName());
                         System.out.println("MBeans in Role: " +
                                  theRole.getRoleValue().size()); 
                  }

                  // Print Roles Unsuccessfully Accessed//
                  RoleUnresolvedList roleListBad = 
                         roleResult.getRolesUnresolved();
                  System.out.println("Unresolved roles: " + 
                         roleListBad.size());
                  Iterator roleIterBad = roleListBad.iterator();
                  while (roleIterBad.hasNext()){
                         RoleUnresolved theRole = 
                             (RoleUnresolved)roleIterBad.next();
                         System.out.println("Unresolved Role Name: " + 
                                            theRole.getRoleName());
                         System.out.println("Reason unresolved: " + 
                               mapReason(theRole.getProblemType())); 
                  }

              } catch (Exception ex) {
                  ex.printStackTrace();
              }
       }

       // Map problem type to a string //
       private static String mapReason(int problem){
               String reason = "Unknown";
               switch(problem){
                     case RoleStatus.LESS_THAN_MIN_ROLE_DEGREE:
                          reason = "Too few MBeans in role";
                          break;
                     case RoleStatus.MORE_THAN_MAX_ROLE_DEGREE:
                          reason = "Too many MBeans in role";
                          break;
                     case RoleStatus.NO_ROLE_WITH_NAME:
                          reason = "No role with that name found";
                          break;
                     case RoleStatus.REF_MBEAN_NOT_REGISTERED:
                          reason = "No MBean with that classname registered";
                          break;
                     case RoleStatus.REF_MBEAN_OF_INCORRECT_CLASS:
                          reason = "An MBean of incorrect type is in role";
                          break;
                     case RoleStatus.ROLE_NOT_READABLE:
                          reason = "The role is not readable";
                          break;
                     case RoleStatus.ROLE_NOT_WRITABLE:
                          reason = "The role is not writable";
                          break;
                     default:
                          reason = "Unknown";
                           
               }

               return reason;
       }

}
