package util;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeMap;

public class ComparetorTest {

	public static void main(String[] args) {
		Comparator<Domain> c=(Domain bb, Domain cc)->{
		
			return -1;
		};
		
		TreeMap<Domain, Integer> treeMap = new TreeMap<Domain,Integer>(c);
		
		for(int i=0;i<50;i++){
			treeMap.put(new Domain(i,"name: "+i), 50-1);
		}
		Set<Domain> keySet = treeMap.keySet();
		
		Comparator<Domain> reversed = c.reversed();
		Set<Domain> keySet2 = treeMap.keySet();
		System.out.println();
	}

}
