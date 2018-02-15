package chapter7;

import javax.management.relation.InvalidRelationTypeException;
import javax.management.relation.RelationType;
import javax.management.relation.RoleInfo;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class HMIRelationTypeDirect implements RelationType{

       private String relationTypeName = "";
       private List roleInfos = null;

       public HMIRelationTypeDirect(String relationName){
              relationTypeName = relationName;
       }

       public HMIRelationTypeDirect(String relationName, RoleInfo rInfos[]){
              relationTypeName = relationName;
              roleInfos = Arrays.asList(rInfos);
       }

       public void addRoleInfo(RoleInfo roleInfo) 
                   throws InvalidRelationTypeException{
              if (roleInfos.contains(roleInfo))
                  throw new InvalidRelationTypeException("Role already exists");
              else
                  roleInfos.add(roleInfo);
       }

       public String getRelationTypeName(){
              return relationTypeName;
       }

       public RoleInfo getRoleInfo(String roleInfoName){
              RoleInfo foundRoleInfo = null;
              Iterator iter = roleInfos.iterator();
              while (iter.hasNext()) {
                  RoleInfo rInfo = (RoleInfo)iter.next();
                  if (rInfo.getName().equals(roleInfoName)){
                      foundRoleInfo = rInfo;
                      break;
                  }                    
              }
              return foundRoleInfo;
       }

       public List getRoleInfos(){
              return roleInfos;
       }

}
