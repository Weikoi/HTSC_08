package com.sample.test;

import com.sample.dao.IAccountDao;
import com.sample.dao.IUserDao;
import com.sample.domain.Account;
import com.sample.domain.AccountUser;
import com.sample.domain.QueryVo;
import com.sample.domain.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import static org.apache.ibatis.io.Resources.getResourceAsStream;

public class UserDaoCrudTests {
    private InputStream in;
    private SqlSessionFactory factory;
    private SqlSession session;
    private IUserDao userDao;
    private IAccountDao accountDao;

    @Before
    public void setUp () throws Exception {
        // 读取配置文件
        in = getResourceAsStream(("SqlMapConfig.xml"));
        // 创建构建者对象
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        // 创建 SqlSession 工厂对象
        factory = builder.build(in);
        // 创建 SqlSession 对象
        session = factory.openSession();
        // 创建 Dao 的代理对象
        userDao = session.getMapper(IUserDao.class);
    }

    @Test
    public void testFindOne (){
        // 执行操作
        User user = userDao.findById(41);
        System.out.println(user);
        assert user.getUsername().equals("张三");
    }

    @Test
    public void testFindAll(){
        List<AccountUser> accountUsers = accountDao.findAll();
        for (AccountUser au :
                accountUsers) {
            System.out.println(au);
        }
    }

    @Test
    public void testSave(){
        User user = new User();
        user.setUsername("Weikoi");
        user.setAddress("上海市浦东新区");
        user.setBirthday(new Date());
        user.setSex("男");

        int affectedRows = userDao.saveUser(user);
        System.out.println(affectedRows);
        System.out.println(user);
//        User savedUser = userDao.findById(id);
//        System.out.println(savedUser);
        Assert.assertEquals(1,affectedRows);
    }

    @Test
    public void updateTest(){

        int id = 56;
        User user = userDao.findById(id);
        System.out.println(user);
        user.setAddress("四川省成都市");

        userDao.updateUser(user);


        User savedUser = userDao.findById(id);
        System.out.println(savedUser);
        Assert.assertEquals(savedUser.getAddress(), "四川省成都市");
    }

    @Test
    public void deleteTest(){
        int res = userDao.deleteUser(57);
        Assert.assertEquals(1,res);
    }

    @Test
    public void testFindByName(){
        List<User> users = userDao.findByName("王");

        assert users.size()==2;
        for (User user : users) {
            System.out.println(user);
        }
    }
    @Test
    public void testCount(){
        System.out.println(userDao.count());
        assert userDao.count() == 13;
    }

    @Test
    public void testQueryByVo(){
        QueryVo vo = new QueryVo();
        vo.setName("%王%");
        vo.setAddress("%南%");

        List<User> users = userDao.findByVo(vo);
        System.out.println(users);
    }

    @After
    public void tearDown () throws Exception {
        session.commit();
        session.close();
        in.close();
    }

}
