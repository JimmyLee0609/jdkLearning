package lang;

import java.beans.EventSetDescriptor;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.net.URL;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;

import javax.annotation.Resource;

public class ClassTest {

	@SuppressWarnings({ "unused", "rawtypes" })
	public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, SecurityException, NoSuchFieldException, InstantiationException, IllegalAccessException {
		ClassTest classTest = new ClassTest();
		Class<?> forName = Class.forName("lang.StringTest");
		
		classTest.getTheClass();//获取class对象
		
		classTest.className(forName);//类名
		classTest.type(forName);//类型
		classTest.annotation(forName);//注解
		classTest.constructor(forName);//构造器
		classTest.field(forName);//字段
		classTest.method(forName);//方法
		classTest.generic(forName);//泛型
		classTest.isType(forName);//是否是
		Class <? extends ClassDomain> cd=ClassDomain.class;
		boolean desiredAssertionStatus = cd.desiredAssertionStatus();//false
		String canonicalName = cd.getCanonicalName();//lang.ClassDomain
		ClassLoader classLoader = cd.getClassLoader();
//		sun.misc.Launcher$AppClassLoader@18b4aac2
		Object [] enumConstants=cd.getEnumConstants();//枚举常量   null
		Class<?>[] interfaces = cd.getInterfaces();// 接口  [interface java.util.Comparator]
		int modifiers = cd.getModifiers();//修饰符   1
		Package package1 = cd.getPackage();//包名   package lang
		ProtectionDomain protectionDomain = cd.getProtectionDomain();
		/*ProtectionDomain  (file:/C:/Users/cobbl/git/jdkLearning/jdkLearning/bin/ <no signer certificates>)
		 sun.misc.Launcher$AppClassLoader@18b4aac2
		 <no principals>
		 java.security.Permissions@6d86b085 (
		 ("java.lang.RuntimePermission" "exitVM")
		 ("java.io.FilePermission" "\C:\Users\cobbl\git\jdkLearning\jdkLearning\bin\-" "read")
		)*/
		Class<?> superclass = cd.getSuperclass();//超类  class java.lang.Thread
		URL resource = cd.getResource("");//获取资源路径 类路径下 
//		file:/C:/Users/cobbl/git/jdkLearning/jdkLearning/bin/lang
		InputStream resourceAsStream = cd.getResourceAsStream("");//返回一个Stream
		Object[] signers = cd.getSigners();//null
		
		
		
		Object newInstance = cd.newInstance();//创建一个新的实例  Thread[Thread-0,5,main]
		String genericString = cd.toGenericString();//获取修饰这个类的全部  public class lang.ClassDomain
		String string = cd.toString();//class lang.StringTest
	}

	@SuppressWarnings("unused")
	private  void isType(Class<?> forName) {
		Class<? extends ClassDomain>cd=ClassDomain.class;
		boolean annotation2 = cd.isAnnotation();//是否是注解  false
		boolean anonymousClass = cd.isAnonymousClass();//
		boolean enum1 = cd.isEnum();//是否是枚举
		boolean interface1 = cd.isInterface();//是否是接口
		boolean localClass = cd.isLocalClass();//是否是本地方法？
		boolean memberClass = cd.isMemberClass();//是否是成员类？
		boolean instance = cd.isInstance(new Object());//是否是指定对象的实例
		boolean primitive = cd.isPrimitive();//是否是基本数据类型
		boolean synthetic = cd.isSynthetic();		//是否是系统合成的
		boolean annotationPresent = cd.isAnnotationPresent(Resource.class);//传入注解是否出现在类中
		boolean array = cd.isArray();//对象是否是数组
		int b=0;
	}

	@SuppressWarnings("unused")
	private  void generic(Class<?> forName) {
		Class <? extends ClassDomain>cd=ClassDomain.class;
//		返回这个类的接口
		Type[] genericInterfaces = cd.getGenericInterfaces();
//		[java.util.Comparator<lang.ClassDomain>]
		
//		返回直接代表这个实体的超类
		Type genericSuperclass = cd.getGenericSuperclass();
//		class java.lang.Thread
		
		
		
		
		int c=0;
	}

	@SuppressWarnings("unused")
	private  void method(Class<?> forName) throws NoSuchMethodException, SecurityException {
	Class<? extends ClassDomain>cd=ClassDomain.class;
//			获取声明的方法
		Method[] declaredMethods = cd.getDeclaredMethods();
//		[public int lang.ClassDomain.compare(lang.ClassDomain,lang.ClassDomain),
//		public int lang.ClassDomain.compare(java.lang.Object,java.lang.Object)]
		
//		根据名字和参数获取指定的方法
		Method declaredMethod = cd.getDeclaredMethod("compare", ClassDomain.class,ClassDomain.class);	
//		public int lang.ClassDomain.compare(lang.ClassDomain,lang.ClassDomain)
		
		Method add = cd.getDeclaredMethod("add", null);	//根据名字获取类中声明的方法
//		int lang.ClassDomain.add()
		Method enclosingMethod = cd.getEnclosingMethod();//null
//		获取这个类的public方法
		Method[] methods = cd.getMethods();
//		[public int lang.ClassDomain.compare(lang.ClassDomain,lang.ClassDomain),
//		public int lang.ClassDomain.compare(java.lang.Object,java.lang.Object), 
//		public void java.lang.Thread.run(), 等父类方法。。。。。。
		
//		根据方法名和参数获取  public方法
		Method method = cd.getMethod("compare",ClassDomain.class,ClassDomain.class);
//		public int lang.ClassDomain.compare(lang.ClassDomain,lang.ClassDomain)
//		Method method1 = cd.getMethod("add",null);这个就不行了
		
		int b=0;
	}

	@SuppressWarnings("unused")
	private void field(Class<?> forName) throws NoSuchFieldException, SecurityException {
	Class<? extends ClassDomain>cd=ClassDomain.class;
//	[java.util.List lang.ClassDomain.list, 
//	static int lang.ClassDomain.b, 
//	java.lang.String lang.ClassDomain.c, 
//	static int[] lang.ClassDomain.gg]
		Field[] declaredFields = cd.getDeclaredFields();//获取本类声明的字段 
		
//		[public static final int java.lang.Thread.MIN_PRIORITY, 
//		public static final int java.lang.Thread.NORM_PRIORITY, 
//		public static final int java.lang.Thread.MAX_PRIORITY]
//		public static int lang.ClassDomain.b,
		Field[] fields = cd.getFields();//获取声明为  public 的字段包括父类的
		
		Field declaredField = cd.getDeclaredField("gg");//更具声明的字段名字获取字段
		//static int[] lang.ClassDomain.gg
		
		Field field = cd.getField("b");//根据字段名字获取public的字段。
		//public static int lang.ClassDomain.b
		int c=0;
	}

	@SuppressWarnings("unused")
	private  void constructor(Class<?> forName) throws NoSuchMethodException, SecurityException {
		Class<? extends ClassDomain>cd=ClassDomain.class;
//		public lang.ClassDomain(int,java.lang.String)
//		更具构造函数的参数获取   构造函数
		Constructor<?> constructor = cd.getConstructor(int.class,String.class);
//		[public lang.ClassDomain(), public lang.ClassDomain(int,java.lang.String)]
//		获取所有的构造函数
		Constructor<?>[] constructors = cd.getConstructors();	
//		public lang.ClassDomain()
//		根据指定参数获取声明的构造函数
		Constructor<?> declaredConstructor = cd.getDeclaredConstructor(null);
		
//		[public lang.ClassDomain(), public lang.ClassDomain(int,java.lang.String)]
//		获取声明的构造函数
		Constructor<?>[] declaredConstructors = cd.getDeclaredConstructors();
		
		Constructor<?> enclosingConstructor = cd.getEnclosingConstructor();//null
		
		int c=0;
		
	}

	@SuppressWarnings("unused")
	private  void annotation(Class<?> forName) {
		Class<ClassDomain> name=ClassDomain.class;
		
//		[sun.reflect.annotation.AnnotatedTypeFactory$AnnotatedParameterizedTypeImpl@e6ea0c6]
//		获取标注在类上的注解
		AnnotatedType[] annotatedInterfaces = name.getAnnotatedInterfaces();
		
//		sun.reflect.annotation.AnnotatedTypeFactory$AnnotatedTypeBaseImpl@6a38e57f
//		获取标注在类上的注解的超类
		AnnotatedType annotatedSuperclass = name.getAnnotatedSuperclass();
		
//		[@javax.annotation.Resource(shareable=true, lookup=, name=, description=, authenticationType=CONTAINER, type=class java.lang.Object, mappedName=)]
//		获取标注在类上的注解
		Annotation[] annotations = name.getAnnotations();
//		[@javax.annotation.Resource(shareable=true, lookup=, name=, description=, authenticationType=CONTAINER, type=class java.lang.Object, mappedName=)]
//		获取直接标注在类上的注解元素
		Annotation[] declaredAnnotations = name.getDeclaredAnnotations();
		
//		@javax.annotation.Resource(shareable=true, lookup=, name=, description=, authenticationType=CONTAINER, type=class java.lang.Object, mappedName=)
//		根据class获取声明在类上的注解
		Annotation annotation = name.getAnnotation(javax.annotation.Resource.class);
//		根据类型获取 声明在类上的注解
		Annotation[] annotationsByType = name.getAnnotationsByType(javax.annotation.Resource.class);		
//		根据class获取直接标注在类上的注解
		Annotation declaredAnnotation = name.getDeclaredAnnotation(javax.annotation.Resource.class);
//		根据类型获取直接标注在类上的注解
		Annotation[] declaredAnnotationsByType = name.getDeclaredAnnotationsByType(javax.annotation.Resource.class);
		
		int c=0;
	}

	@SuppressWarnings("unused")
	private void type(Class<?> forName) {
		Collection<String> list=new ArrayList<String>();
		Class<? extends Collection> class1 = list.getClass();
		
		
		boolean assignableFrom = class1.isAssignableFrom(ArrayList.class);
		//是否从类中转型而来   true
		Class<? extends Object> asSubclass = class1.asSubclass(Object.class);
		//对象作为传入类的子类的类  class java.util.ArrayList
		Class<? extends Object> asSubclass2 = class1.asSubclass(Collection.class);
		//对象作为传入类的子类的类  class java.util.ArrayList
		
		Object cast = class1.cast(new ArrayList<String>());//将对象转型为指定类型
		
		String typeName = class1.getTypeName();//获取类型的名字  java.util.ArrayList
		TypeVariable<?>[] typeParameters = class1.getTypeParameters();//获取类型的参数   [E]
		
		Class<?> componentType = class1.getComponentType();//返回数组包装类，没有就返回null
		int [] tt=new int [5];
		Class<?> componentType2 = tt.getClass().getComponentType();//int
		
		int c=0;
	}

	@SuppressWarnings("unused")
	private void className(Class<?> forName) {
		String name = forName.getName();		//获取全类名    lang.StringTest
		String simpleName = forName.getSimpleName();//获取类名     StringTest

		int c=0;
	}

	@SuppressWarnings({ "unused", "rawtypes" })
	private void getTheClass() throws ClassNotFoundException {
		Class<? extends Class> zz=Class.class;//类名静态字段   class java.lang.Class
//		class lang.StringTest
		Class<?> forName = Class.forName("lang.StringTest");//Class的静态方法，使用全类名，在类路径下查找
		
//		使用全类名在指定的classLoader下查找，缓存这个类   class lang.StringTest
		Class<?> forName2 = Class.forName("lang.StringTest", true, getClass().getClassLoader());		
//		对象获取Class对象
		Class<? extends Class> class1 = forName.getClass();//返回这个类运行时的class   class java.lang.Class
		Class<? extends String> class2 = "afasdf".getClass();//class java.lang.String
		
		Class<? extends ScheduledThreadPoolExecutor> ee=ScheduledThreadPoolExecutor.class;
		Class<?>[] classes = ee.getClasses();//获取继承的内部类
//		[class java.util.concurrent.ThreadPoolExecutor$AbortPolicy, class java.util.concurrent.ThreadPoolExecutor$CallerRunsPolicy, class java.util.concurrent.ThreadPoolExecutor$DiscardOldestPolicy, class java.util.concurrent.ThreadPoolExecutor$DiscardPolicy]
		
		Class<? extends Executors>e=Executors.class;
		Class<?>[] declaredClasses = e.getDeclaredClasses();//获取内部类
		//[class java.util.concurrent.Executors$DefaultThreadFactory, class java.util.concurrent.Executors$DelegatedExecutorService, class java.util.concurrent.Executors$DelegatedScheduledExecutorService, class java.util.concurrent.Executors$FinalizableDelegatedExecutorService, class java.util.concurrent.Executors$PrivilegedCallable, class java.util.concurrent.Executors$PrivilegedCallableUsingCurrentClassLoader, class java.util.concurrent.Executors$PrivilegedThreadFactory, class java.util.concurrent.Executors$RunnableAdapter]
		Class<? extends AbortPolicy> abortPolicy = ThreadPoolExecutor.AbortPolicy.class;
		Class<?> declaringClass = abortPolicy.getDeclaringClass();
		//如果对象是类的成员，就返回那个类     class java.util.concurrent.ThreadPoolExecutor
		Class<?> enclosingClass = abortPolicy.getEnclosingClass();
		//返回对象的外部类          class java.util.concurrent.ThreadPoolExecutor
		
	int c=0;	
	}

}
