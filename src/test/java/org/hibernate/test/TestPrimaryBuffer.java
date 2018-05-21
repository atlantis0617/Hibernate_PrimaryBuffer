package org.hibernate.test;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.model.Student;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author atlantis0617
 *
 *1. 一级缓存无法取消，用两个方法管理

　　　　- evict()：用于将对象从Session的一级缓存中清除

　　　　- clear()：用于将一级缓存中的所有对象清除

　　2. 相关方法

　　　　- query.list()

　　　　- query.iterate()
 *
 */
public class TestPrimaryBuffer {
	private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;
    
    @Before
    public void before() {
        sessionFactory = new Configuration().configure().buildSessionFactory();// 创建会话工厂对象
        session = sessionFactory.openSession();// 创建会话
        transaction = session.beginTransaction();// 开始事务
    }
    
    @After
    public void after() {
        transaction.commit();// 提交事务
        session.close();// 关闭会话
        sessionFactory.close();// 关闭会话工厂
    }
    
    /**
     * 初始化数据库
     */
    @Test
    public void init() {
        Student student = new Student(1, "张三", new Date(), "男");
        session.save(student);
        student = new Student(2, "李四", new Date(), "男");
        session.save(student);
        student = new Student(3, "王五", new Date(), "男");
        session.save(student);
    }
    
    /**
     * 同一个session
     */
    @Test
    public void testSameSession() {
        Student student = session.get(Student.class, 1L);
        System.out.println(student.getUsername());
         
        student = session.get(Student.class, 1L);
        System.out.println(student.getUsername());
    }
    
    /**
     * 不同session
     */
    @Test
    public void testDiffSession() {
        Student student = session.get(Student.class, 1L);
        System.out.println(student.getUsername());
         
        session = sessionFactory.openSession();
        student = session.get(Student.class, 1L);
        System.out.println(student.getUsername());
    }
    
    
    /**
     * Evict，同一个session
     */
    @Test
    public void testEvict() {
        Student student = session.get(Student.class, 1L);
        System.out.println(student.getUsername());
        session.evict(student);
        student = session.get(Student.class, 1L);
        System.out.println(student.getUsername());
    }
    
    
    /**
     * Clear，同一个session
     */
    @Test
    public void testClear() {
        Student student = session.get(Student.class, 1L);
        System.out.println(student.getUsername());
        session.clear();
        student = session.get(Student.class, 1L);
        System.out.println(student.getUsername());
    }
    
    /**
     * Query
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testQuery() {
         
        Query query = session.createQuery("from Student");
        List<Student> list = query.list();
        for (Student s: list) {
            System.out.println(s.getUsername());
        }
         
        System.out.println();
         
        list = query.list();
        for (Student s: list) {
            System.out.println(s.getUsername());
        }
         
    }
     
    /**
     * Interate
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testIterate() {
         
        Query query = session.createQuery("from Student");
        List<Student> list = query.list();
        for (Student s: list) {
            System.out.println(s.getUsername());
        }
         
        System.out.println();
         
        Iterator<?> iterator = query.iterate();
        while (iterator.hasNext()) {
            Student s = (Student) iterator.next();
            System.out.println(s.getUsername());
        }
         
    }
     
    /**
     * Interate2
     */
    @Test
    public void testIterate2() {
        Query query = session.createQuery("from Student");
        Iterator<?> iterator = query.iterate();
        while (iterator.hasNext()) {
            Student s = (Student) iterator.next();
            System.out.println(s.getUsername());
        }
    }
    
}
