package net.wendal.nutzbook.module;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import net.wendal.nutzbook.bean.User;

@IocBean
@At("/user")
@Ok("json")
@Fail("http:500")
public class UserModule {
	@Inject
	protected Dao dao;
	@At
    public int count() {
        return dao.count(User.class);
    }
	@At
	public Object login(@Param("name")String name, @Param("password")String password, HttpSession session) {
		System.out.println(name+password);
		User user = dao.fetch(User.class,Cnd.where("name","=",name).and("password","=",password));
		if(user == null) {
			return false;
		}else {
			session.setAttribute("me", user.getId());
			return true;
		}
	}
	@At
	@Ok(">>:/")
	public void logout(HttpSession session) {
		session.invalidate();
	}
}
