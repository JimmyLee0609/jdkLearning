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
		
		classTest.getTheClass();//��ȡclass����
		
		classTest.className(forName);//����
		classTest.type(forName);//����
		classTest.annotation(forName);//ע��
		classTest.constructor(forName);//������
		classTest.field(forName);//�ֶ�
		classTest.method(forName);//����
		classTest.generic(forName);//����
		classTest.isType(forName);//�Ƿ���
		Class <? extends ClassDomain> cd=ClassDomain.class;
		boolean desiredAssertionStatus = cd.desiredAssertionStatus();//false
		String canonicalName = cd.getCanonicalName();//lang.ClassDomain
		ClassLoader classLoader = cd.getClassLoader();
//		sun.misc.Launcher$AppClassLoader@18b4aac2
		Object [] enumConstants=cd.getEnumConstants();//ö�ٳ���   null
		Class<?>[] interfaces = cd.getInterfaces();// �ӿ�  [interface java.util.Comparator]
		int modifiers = cd.getModifiers();//���η�   1
		Package package1 = cd.getPackage();//����   package lang
		ProtectionDomain protectionDomain = cd.getProtectionDomain();
		/*ProtectionDomain  (file:/C:/Users/cobbl/git/jdkLearning/jdkLearning/bin/ <no signer certificates>)
		 sun.misc.Launcher$AppClassLoader@18b4aac2
		 <no principals>
		 java.security.Permissions@6d86b085 (
		 ("java.lang.RuntimePermission" "exitVM")
		 ("java.io.FilePermission" "\C:\Users\cobbl\git\jdkLearning\jdkLearning\bin\-" "read")
		)*/
		Class<?> superclass = cd.getSuperclass();//����  class java.lang.Thread
		URL resource = cd.getResource("");//��ȡ��Դ·�� ��·���� 
//		file:/C:/Users/cobbl/git/jdkLearning/jdkLearning/bin/lang
		InputStream resourceAsStream = cd.getResourceAsStream("");//����һ��Stream
		Object[] signers = cd.getSigners();//null
		
		
		
		Object newInstance = cd.newInstance();//����һ���µ�ʵ��  Thread[Thread-0,5,main]
		String genericString = cd.toGenericString();//��ȡ����������ȫ��  public class lang.ClassDomain
		String string = cd.toString();//class lang.StringTest
	}

	@SuppressWarnings("unused")
	private  void isType(Class<?> forName) {
		Class<? extends ClassDomain>cd=ClassDomain.class;
		boolean annotation2 = cd.isAnnotation();//�Ƿ���ע��  false
		boolean anonymousClass = cd.isAnonymousClass();//
		boolean enum1 = cd.isEnum();//�Ƿ���ö��
		boolean interface1 = cd.isInterface();//�Ƿ��ǽӿ�
		boolean localClass = cd.isLocalClass();//�Ƿ��Ǳ��ط�����
		boolean memberClass = cd.isMemberClass();//�Ƿ��ǳ�Ա�ࣿ
		boolean instance = cd.isInstance(new Object());//�Ƿ���ָ�������ʵ��
		boolean primitive = cd.isPrimitive();//�Ƿ��ǻ�����������
		boolean synthetic = cd.isSynthetic();		//�Ƿ���ϵͳ�ϳɵ�
		boolean annotationPresent = cd.isAnnotationPresent(Resource.class);//����ע���Ƿ����������
		boolean array = cd.isArray();//�����Ƿ�������
		int b=0;
	}

	@SuppressWarnings("unused")
	private  void generic(Class<?> forName) {
		Class <? extends ClassDomain>cd=ClassDomain.class;
//		���������Ľӿ�
		Type[] genericInterfaces = cd.getGenericInterfaces();
//		[java.util.Comparator<lang.ClassDomain>]
		
//		����ֱ�Ӵ������ʵ��ĳ���
		Type genericSuperclass = cd.getGenericSuperclass();
//		class java.lang.Thread
		
		
		
		
		int c=0;
	}

	@SuppressWarnings("unused")
	private  void method(Class<?> forName) throws NoSuchMethodException, SecurityException {
	Class<? extends ClassDomain>cd=ClassDomain.class;
//			��ȡ�����ķ���
		Method[] declaredMethods = cd.getDeclaredMethods();
//		[public int lang.ClassDomain.compare(lang.ClassDomain,lang.ClassDomain),
//		public int lang.ClassDomain.compare(java.lang.Object,java.lang.Object)]
		
//		�������ֺͲ�����ȡָ���ķ���
		Method declaredMethod = cd.getDeclaredMethod("compare", ClassDomain.class,ClassDomain.class);	
//		public int lang.ClassDomain.compare(lang.ClassDomain,lang.ClassDomain)
		
		Method add = cd.getDeclaredMethod("add", null);	//�������ֻ�ȡ���������ķ���
//		int lang.ClassDomain.add()
		Method enclosingMethod = cd.getEnclosingMethod();//null
//		��ȡ������public����
		Method[] methods = cd.getMethods();
//		[public int lang.ClassDomain.compare(lang.ClassDomain,lang.ClassDomain),
//		public int lang.ClassDomain.compare(java.lang.Object,java.lang.Object), 
//		public void java.lang.Thread.run(), �ȸ��෽��������������
		
//		���ݷ������Ͳ�����ȡ  public����
		Method method = cd.getMethod("compare",ClassDomain.class,ClassDomain.class);
//		public int lang.ClassDomain.compare(lang.ClassDomain,lang.ClassDomain)
//		Method method1 = cd.getMethod("add",null);����Ͳ�����
		
		int b=0;
	}

	@SuppressWarnings("unused")
	private void field(Class<?> forName) throws NoSuchFieldException, SecurityException {
	Class<? extends ClassDomain>cd=ClassDomain.class;
//	[java.util.List lang.ClassDomain.list, 
//	static int lang.ClassDomain.b, 
//	java.lang.String lang.ClassDomain.c, 
//	static int[] lang.ClassDomain.gg]
		Field[] declaredFields = cd.getDeclaredFields();//��ȡ�����������ֶ� 
		
//		[public static final int java.lang.Thread.MIN_PRIORITY, 
//		public static final int java.lang.Thread.NORM_PRIORITY, 
//		public static final int java.lang.Thread.MAX_PRIORITY]
//		public static int lang.ClassDomain.b,
		Field[] fields = cd.getFields();//��ȡ����Ϊ  public ���ֶΰ��������
		
		Field declaredField = cd.getDeclaredField("gg");//�����������ֶ����ֻ�ȡ�ֶ�
		//static int[] lang.ClassDomain.gg
		
		Field field = cd.getField("b");//�����ֶ����ֻ�ȡpublic���ֶΡ�
		//public static int lang.ClassDomain.b
		int c=0;
	}

	@SuppressWarnings("unused")
	private  void constructor(Class<?> forName) throws NoSuchMethodException, SecurityException {
		Class<? extends ClassDomain>cd=ClassDomain.class;
//		public lang.ClassDomain(int,java.lang.String)
//		���߹��캯���Ĳ�����ȡ   ���캯��
		Constructor<?> constructor = cd.getConstructor(int.class,String.class);
//		[public lang.ClassDomain(), public lang.ClassDomain(int,java.lang.String)]
//		��ȡ���еĹ��캯��
		Constructor<?>[] constructors = cd.getConstructors();	
//		public lang.ClassDomain()
//		����ָ��������ȡ�����Ĺ��캯��
		Constructor<?> declaredConstructor = cd.getDeclaredConstructor(null);
		
//		[public lang.ClassDomain(), public lang.ClassDomain(int,java.lang.String)]
//		��ȡ�����Ĺ��캯��
		Constructor<?>[] declaredConstructors = cd.getDeclaredConstructors();
		
		Constructor<?> enclosingConstructor = cd.getEnclosingConstructor();//null
		
		int c=0;
		
	}

	@SuppressWarnings("unused")
	private  void annotation(Class<?> forName) {
		Class<ClassDomain> name=ClassDomain.class;
		
//		[sun.reflect.annotation.AnnotatedTypeFactory$AnnotatedParameterizedTypeImpl@e6ea0c6]
//		��ȡ��ע�����ϵ�ע��
		AnnotatedType[] annotatedInterfaces = name.getAnnotatedInterfaces();
		
//		sun.reflect.annotation.AnnotatedTypeFactory$AnnotatedTypeBaseImpl@6a38e57f
//		��ȡ��ע�����ϵ�ע��ĳ���
		AnnotatedType annotatedSuperclass = name.getAnnotatedSuperclass();
		
//		[@javax.annotation.Resource(shareable=true, lookup=, name=, description=, authenticationType=CONTAINER, type=class java.lang.Object, mappedName=)]
//		��ȡ��ע�����ϵ�ע��
		Annotation[] annotations = name.getAnnotations();
//		[@javax.annotation.Resource(shareable=true, lookup=, name=, description=, authenticationType=CONTAINER, type=class java.lang.Object, mappedName=)]
//		��ȡֱ�ӱ�ע�����ϵ�ע��Ԫ��
		Annotation[] declaredAnnotations = name.getDeclaredAnnotations();
		
//		@javax.annotation.Resource(shareable=true, lookup=, name=, description=, authenticationType=CONTAINER, type=class java.lang.Object, mappedName=)
//		����class��ȡ���������ϵ�ע��
		Annotation annotation = name.getAnnotation(javax.annotation.Resource.class);
//		�������ͻ�ȡ ���������ϵ�ע��
		Annotation[] annotationsByType = name.getAnnotationsByType(javax.annotation.Resource.class);		
//		����class��ȡֱ�ӱ�ע�����ϵ�ע��
		Annotation declaredAnnotation = name.getDeclaredAnnotation(javax.annotation.Resource.class);
//		�������ͻ�ȡֱ�ӱ�ע�����ϵ�ע��
		Annotation[] declaredAnnotationsByType = name.getDeclaredAnnotationsByType(javax.annotation.Resource.class);
		
		int c=0;
	}

	@SuppressWarnings("unused")
	private void type(Class<?> forName) {
		Collection<String> list=new ArrayList<String>();
		Class<? extends Collection> class1 = list.getClass();
		
		
		boolean assignableFrom = class1.isAssignableFrom(ArrayList.class);
		//�Ƿ������ת�Ͷ���   true
		Class<? extends Object> asSubclass = class1.asSubclass(Object.class);
		//������Ϊ��������������  class java.util.ArrayList
		Class<? extends Object> asSubclass2 = class1.asSubclass(Collection.class);
		//������Ϊ��������������  class java.util.ArrayList
		
		Object cast = class1.cast(new ArrayList<String>());//������ת��Ϊָ������
		
		String typeName = class1.getTypeName();//��ȡ���͵�����  java.util.ArrayList
		TypeVariable<?>[] typeParameters = class1.getTypeParameters();//��ȡ���͵Ĳ���   [E]
		
		Class<?> componentType = class1.getComponentType();//���������װ�࣬û�оͷ���null
		int [] tt=new int [5];
		Class<?> componentType2 = tt.getClass().getComponentType();//int
		
		int c=0;
	}

	@SuppressWarnings("unused")
	private void className(Class<?> forName) {
		String name = forName.getName();		//��ȡȫ����    lang.StringTest
		String simpleName = forName.getSimpleName();//��ȡ����     StringTest

		int c=0;
	}

	@SuppressWarnings({ "unused", "rawtypes" })
	private void getTheClass() throws ClassNotFoundException {
		Class<? extends Class> zz=Class.class;//������̬�ֶ�   class java.lang.Class
//		class lang.StringTest
		Class<?> forName = Class.forName("lang.StringTest");//Class�ľ�̬������ʹ��ȫ����������·���²���
		
//		ʹ��ȫ������ָ����classLoader�²��ң����������   class lang.StringTest
		Class<?> forName2 = Class.forName("lang.StringTest", true, getClass().getClassLoader());		
//		�����ȡClass����
		Class<? extends Class> class1 = forName.getClass();//�������������ʱ��class   class java.lang.Class
		Class<? extends String> class2 = "afasdf".getClass();//class java.lang.String
		
		Class<? extends ScheduledThreadPoolExecutor> ee=ScheduledThreadPoolExecutor.class;
		Class<?>[] classes = ee.getClasses();//��ȡ�̳е��ڲ���
//		[class java.util.concurrent.ThreadPoolExecutor$AbortPolicy, class java.util.concurrent.ThreadPoolExecutor$CallerRunsPolicy, class java.util.concurrent.ThreadPoolExecutor$DiscardOldestPolicy, class java.util.concurrent.ThreadPoolExecutor$DiscardPolicy]
		
		Class<? extends Executors>e=Executors.class;
		Class<?>[] declaredClasses = e.getDeclaredClasses();//��ȡ�ڲ���
		//[class java.util.concurrent.Executors$DefaultThreadFactory, class java.util.concurrent.Executors$DelegatedExecutorService, class java.util.concurrent.Executors$DelegatedScheduledExecutorService, class java.util.concurrent.Executors$FinalizableDelegatedExecutorService, class java.util.concurrent.Executors$PrivilegedCallable, class java.util.concurrent.Executors$PrivilegedCallableUsingCurrentClassLoader, class java.util.concurrent.Executors$PrivilegedThreadFactory, class java.util.concurrent.Executors$RunnableAdapter]
		Class<? extends AbortPolicy> abortPolicy = ThreadPoolExecutor.AbortPolicy.class;
		Class<?> declaringClass = abortPolicy.getDeclaringClass();
		//�����������ĳ�Ա���ͷ����Ǹ���     class java.util.concurrent.ThreadPoolExecutor
		Class<?> enclosingClass = abortPolicy.getEnclosingClass();
		//���ض�����ⲿ��          class java.util.concurrent.ThreadPoolExecutor
		
	int c=0;	
	}

}
