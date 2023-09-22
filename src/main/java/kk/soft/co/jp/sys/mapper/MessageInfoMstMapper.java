package kk.soft.co.jp.sys.mapper;

import kk.soft.co.jp.sys.model.MessageInfoMstModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface MessageInfoMstMapper {

    int insert(MessageInfoMstModel messageInfoMst);
}
