package kk.soft.co.jp.sys.service;



import kk.soft.co.jp.sys.model.UserModel;

public interface UserService {

    /**
     * 新規作成
     */
    public Object insert(UserModel user);
    public Object setNewPassword(UserModel pass);
    public Object updatepass(UserModel pass);
    // public Object selectpass(UserModel pass);

}
