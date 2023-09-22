package kk.soft.co.jp.sys.controller;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import kk.soft.co.jp.sys.mapper.UserMapper;
import kk.soft.co.jp.sys.model.UserModel;
import kk.soft.co.jp.sys.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class UseController {
	private static final Logger logger = LoggerFactory.getLogger(UseController.class);
	
    @Resource
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
//	controller login
    @GetMapping("/login")
    public String ShowLogin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String UserLogin = (String) session.getAttribute("UserLogin");
        if (UserLogin != null) {
        return "redirect:/information";
        }else{
        return "login";
        }
    }
    @GetMapping("/information")
    public String ShowInformation(UserModel userModel, Model model,HttpServletRequest request) {
        HttpSession session = request.getSession();
        String UserLogin = (String) session.getAttribute("UserLogin");
        if (UserLogin != null) {
        UserModel user = userMapper.getUserByUsername(UserLogin);
                model.addAttribute("username", user.getUsername());
                model.addAttribute("password", "安全性のため、表示しません");
                model.addAttribute("role", user.getRole());
                model.addAttribute("youname", user.getYouname());
                model.addAttribute("readname", user.getReadname());
                model.addAttribute("birthday", user.getBirthday());
                model.addAttribute("email", user.getEmail());
                model.addAttribute("sex", user.getSex());
                model.addAttribute("contry", user.getContry());
                model.addAttribute("hobbies", user.getHobbies());
            return "showuser";
        } else {
            model.addAttribute("login", "ログインして下さい");
            return "showuser";
        }
    }
    @PostMapping("/information")
    public String LoginUser(UserModel userModel, Model model,HttpServletRequest  request) {
        UserModel user = userMapper.getUserByUsername(userModel.getUsername());

        if(user!=null){
            if(hashMD5(userModel.getPassword()).equals(user.getPassword())){
                model.addAttribute("username", user.getUsername());
                model.addAttribute("password", userModel.getPassword());
                model.addAttribute("role", user.getRole());
                model.addAttribute("youname", user.getYouname());
                model.addAttribute("readname", user.getReadname());
                model.addAttribute("birthday", user.getBirthday());
                model.addAttribute("email", user.getEmail());
                model.addAttribute("sex", user.getSex());
                model.addAttribute("contry", user.getContry());
                model.addAttribute("hobbies", user.getHobbies());
                logger.info(user.getUsername() + "ログインしました");
                // set session
                if(userModel.getLoginCheck()!=null &&userModel.getLoginCheck().equals("check")){
                    HttpSession session = request.getSession();
                    session.setAttribute("UserLogin", userModel.getUsername());
                }
                return "showuser";
            }else{
                model.addAttribute("login_error", "パスワードが一致しません");
                logger.error(user.getUsername() +" ログインがパスワードが一致しません");
                return "login";
            }
        }else{
        model.addAttribute("login_error", "会員ユーザーが一致しません");
        logger.error("ログイン失敗："+userModel.getUsername()+"が存じません" );
            return "login";
        }
    }
    
    public static String hashMD5(String plaintext) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plaintext.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // Handle exception appropriately
            return null;
        }
    }
    //    controller logout
    @GetMapping("/logout")
    public String Logout(Model model,HttpServletRequest request) {
        HttpSession session = request.getSession();
        model.addAttribute("success","ログアウトできました。");
        logger.warn(session.getAttribute("UserLogin")+"をログアウトできました");
        session.invalidate();
    	return "login";
    }
    
//    controller register
    @GetMapping("/register")
    public String ShowRegister(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String UserLogin = (String) session.getAttribute("UserLogin");
        if (UserLogin != null) {
        return "redirect:/information";
        }else{
        return "register";
        }
    }
    @GetMapping("/registration")
    public String ShowRegistration() {
        return "register";
    }

    @PostMapping("/registration")
    public String save(UserModel userModel, Model model){
        int count = userMapper.checkusername(userModel.getUsername());
        if(count != 0){
        	model.addAttribute("error","登録できませんでした。");
        	model.addAttribute("errorusername","会員ユーザー: "+userModel.getUsername()+"が存在していました。");
        	return "register";
        }else if (!userModel.getPassword().equals(userModel.getRepeatpassword())) {
        	model.addAttribute("error","登録できませんでした。");
        	model.addAttribute("errorpass","パスワードを再確認してください。");
        	return "register";
		} else if (userModel.getUsername()==""||userModel.getPassword()==""||userModel.getYouname()=="") {
			model.addAttribute("error","(*) とことが必須です");
        	return "register";
		}else{
//        	userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        userService.insert(userModel);
            model.addAttribute("success","登録OK");
            logger.info(userModel.getUsername()+"で登録できました");
            return "login";
        }

    }

    //  pass henko
    @GetMapping("/newpass")
    public String newpass(HttpServletRequest request){
        HttpSession session = request.getSession();
        String UserLogin = (String) session.getAttribute("UserLogin");
        if (UserLogin != null) {
            return "newpass";
        }else{
            return "redirect:/login";
        }
    }
    @PostMapping("/newpass")
    public String newpassword(UserModel userModel, Model model,HttpServletRequest request){
        HttpSession session = request.getSession();
        String UserLogin = (String) session.getAttribute("UserLogin");
        if (UserLogin != null) {
            if(userModel.getPassword().equals(userModel.getRepeatpassword())){
            int count = userMapper.checkusername(UserLogin);
                if(count==1){
                        userModel.setUsername(UserLogin);
                        userService.setNewPassword(userModel);
                        model.addAttribute("success_pass","新しいパスワードは:"+userModel.getPassword()+"変更できました。");
                        logger.info(UserLogin +": 新しいパスワードは:"+userModel.getPassword()+"変更できました");
                }else{
                    
                    model.addAttribute("error_pass","会員ユーザーがありません");
                }
            }else{
                model.addAttribute("error_pass","再パスワードが同じ入力してください。");
            }
            return "newpass";
        }else{
            return "redirect:/login";
        }
    }
    @GetMapping("/forgotpassword")
    public String showforgotpassword(HttpServletRequest request){
        HttpSession session = request.getSession();
        String UserLogin = (String) session.getAttribute("UserLogin");
        if (UserLogin != null) {
        return "redirect:/information";
        }else{
        return "forgotpassword";
        }
    }
    @PostMapping("/forgotpassword")
    public String forgotpassword(UserModel userModel, Model model){
        UserModel user = userMapper.selectpass(userModel);

        System.out.println(user);
        if(user==null){
            model.addAttribute("error_pass","入力した情報は正しくない！");
            return "forgotpassword";
        }else{
            if(userModel.getPassword().equals(userModel.getRepeatpassword()) && !userModel.getPassword().equals("")){

                userService.setNewPassword(userModel);
                model.addAttribute("success","新しいパスワードは:"+userModel.getPassword()+"変更できました。");
                logger.info(userModel.getUsername()+"新しいパスワードが変更できました。");
                return "login";
            }else{
                model.addAttribute("warning_pass","パスワードが確認してください。");
                return "forgotpassword";
            }
        }
    }
}
