package chapter7;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MalformedObjectNameException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.relation.InvalidRelationTypeException;
import javax.management.relation.RoleInfo;
import javax.management.relation.RelationService;
import java.util.Iterator;
import java.util.List;

public class ExampleJMXAddRelationTypes {

       private static MBeanServer server = null;


       public ExampleJMXAddRelationTypes(){
              server = MBeanServerFactory.createMBeanServer();
       }

       public static void main(String args[]){

              ExampleJMXAddRelationTypes agent =
                     new ExampleJMXAddRelationTypes();

              RelationService relServ = new RelationService(false);

              PrintObject prntObj = new PrintObject();

              try {

                  ObjectName objName = 
                             new ObjectName("RelationService:type=relate");
 
                  server.registerMBean(relServ,objName);

                  ObjectName pObj = 
                             new ObjectName("PrinterObject:name=HP Deskjet");

                  server.registerMBean(prntObj,pObj);

                  // Add Relations //

                  String className = 
                             server.getObjectInstance(pObj).getClassName();

                  RoleInfo rInfo = new RoleInfo("Printers",className);

                  relServ.createRelationType("Organization Printers",
                                             new RoleInfo[]{rInfo});

                  // List all internal relations //
                  List relations = relServ.getAllRelationTypeNames();
            
                  Iterator iter = relations.iterator();
                  while (iter.hasNext())
                        System.out.println("Relation Type: " + 
                                          (String)iter.next());

              } catch (MalformedObjectNameException ex){
                  System.out.println(ex);
              } catch (InstanceAlreadyExistsException ex){
                  System.out.println(ex);
              } catch (MBeanRegistrationException ex){
                  System.out.println(ex);
              } catch (NotCompliantMBeanException ex){
                  System.out.println(ex);
              } catch (InvalidRelationTypeException ex){ 
                  System.out.println(ex);
              } catch (InstanceNotFoundException ex){
                  System.out.println(ex);
              } catch (ClassNotFoundException ex){
                  System.out.println(ex);
              }
       }

}
