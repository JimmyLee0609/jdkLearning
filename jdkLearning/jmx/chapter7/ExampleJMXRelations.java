package chapter7;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MalformedObjectNameException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.relation.RelationService;


public class ExampleJMXRelations {

       private static MBeanServer server = null;


       public ExampleJMXRelations(){
              server = MBeanServerFactory.createMBeanServer();
       }

       public static void main(String args[]){

              ExampleJMXRelations agent = new ExampleJMXRelations();

              RelationService relServ = new RelationService(false);

              try {
//				将relationService注册到服务器
                  ObjectName objName = 
                       new ObjectName("RelationService:type=relate");
 
                  server.registerMBean(relServ,objName);

              } catch (MalformedObjectNameException ex){
                  System.out.println(ex);
              } catch (InstanceAlreadyExistsException ex){
                  System.out.println(ex);
              } catch (MBeanRegistrationException ex){
                  System.out.println(ex);
              } catch (NotCompliantMBeanException ex){
                  System.out.println(ex);
              }
       }

}
