package chapter7;

import javax.management.relation.InvalidRelationTypeException;
import javax.management.relation.RelationTypeSupport;
import javax.management.relation.RoleInfo;


public class HMIRelationType extends RelationTypeSupport{

       public HMIRelationType(String relationName, RoleInfo roles[])
              throws InvalidRelationTypeException{
              super(relationName,roles);
       }
}
