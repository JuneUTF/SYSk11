package kk.soft.co.jp.sys.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserModel implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private String username;
    private String password;
    private String repeatpassword;
    private String youname;
    private String readname;
    private String birthday;
    private String email;
    private String sex;
    private String contry;
    private String hobbies;
	private String role;
	private String loginCheck;
	private String registerCheck;

    public UserModel() {
    }


}
