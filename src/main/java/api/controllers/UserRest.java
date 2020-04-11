package api.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.beans.UserBean;
import api.dao.GenericDao;
import api.dao.UserDao;
import api.dto.SimpleDto;
import api.entities.User;
import api.security.annotations.DeleteUserAuth;
import api.security.annotations.EditUserAuth;
import api.security.annotations.ViewUserAuth;
import api.services.JwtService;
import api.services.UserService;

@RestController
@RequestMapping(value = "/api")
public class UserRest {

	@Autowired
	private UserService userService;
	@Autowired
	private JwtService loginService;
	@Autowired
	private UserDao userDao;
	@Autowired
	private GenericDao<User, Integer> gUserDao;
	
//	@PostMapping(value = "/login")
//	public Object login(@RequestBody LoginBean bean, HttpServletRequest request, HttpSession session) {
//		
//		return loginService.login(bean, request, session);
//	}
	
//	@RequestMapping(value="/logout") 
//	public void logout(HttpServletRequest request) {
//		request.getSession().invalidate();
//	}
	
	@PostMapping(value = "/users")
	@EditUserAuth
	public Integer saveUser(@RequestBody UserBean bean) {
		
		return userService.saveUser(bean);
	}
	
	@GetMapping(value = "/users/loading")
	@EditUserAuth
	public Map<String, Object> userLoading(HttpServletRequest request) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(request.getParameter("user") != null) {			
			map.put("userInfos", userService.getUserForEdit(Integer.valueOf(request.getParameter("user"))));
		}
		map.put("roles", userDao.getListRoles());
		map.put("divisions", userDao.getDivisions());
	
		
		return map;
	}
	
	@GetMapping(value = "/users")
	@ViewUserAuth
	public List<UserBean> getListUsers() {
		return userDao.getListUsers();
	}
	
	@DeleteMapping(value = "/users/{idUser}")
	@DeleteUserAuth
	public void deleteUser(@PathVariable Integer idUser) {
		
		gUserDao.delete(User.class, idUser);
	}
	
	
//	@GetMapping(value = "/chargesSuivi")
//	public Collection<SelectGrpDto<SimpleDto>> getChargesSuivi() {
//		return userService.getChargesSuivi();
//	}

	@GetMapping(value = "/roles")
	@ViewUserAuth
	public List<SimpleDto> getListRoles() { return userDao.getListRoles(); }
	
	@GetMapping(value = "/permissions")
	@ViewUserAuth
	public List<SimpleDto> getUserTypes() { return userDao.getPermissions(); }
	

	
	@GetMapping(value = "/divisions")
	@ViewUserAuth
	public List<SimpleDto> getDivisions() { return userDao.getDivisions(); }
	
	
}
