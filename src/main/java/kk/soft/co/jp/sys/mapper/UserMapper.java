package kk.soft.co.jp.sys.mapper;

import kk.soft.co.jp.sys.model.UserModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {
    @Select("SELECT * FROM huynh_username WHERE username = #{username}")
    public UserModel getUserByUsername(String username);
    @Select("SELECT COUNT(*) FROM huynh_username WHERE username = #{username}")
    public int checkusername(String username);


    
    public UserModel selectpass(UserModel pass);
    int insert(UserModel user);
    int setNewPassword(UserModel pass);
    int updatepass(UserModel pass);
}
