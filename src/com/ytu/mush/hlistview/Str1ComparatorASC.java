package com.ytu.mush.hlistview;

import java.util.Comparator;

class Str1ComparatorASC implements Comparator {
	@Override
	public int compare(Object o1, Object o2) {
		String name1=((Data)o1).getStr1();
		String name2=((Data)o2).getStr1();
		//int age1=((Data)o1).getAge();
		//int age2=((Data)o2).getAge();
		int compareName = name1.compareTo(name2);
		/*if (compareName == 0) {
			return (age1 == age2 ? 0 : (age1 > age2 ? 1 : -1));
		}*/
		return compareName;
	}
	
	
}
