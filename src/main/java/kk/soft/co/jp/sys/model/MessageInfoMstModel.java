package kk.soft.co.jp.sys.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class MessageInfoMstModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private int id;

    /**
     * msg
     */
    private String msg;

    /**
     * sender
     */
    private String sender;

    public MessageInfoMstModel() {
    }


}
