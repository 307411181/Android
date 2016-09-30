package com.unihiltop.xiangliao.net;

public interface CommonConstant {
    public interface KEY {
        /**
         * 附加消息的key
         */
        public final String ATTACH_TEXT_KEY = "attach_text";
        /**
         * 返回数据的Key
         */
        public final String RESPONSE_DATA_KEY = "response_data_key";
        /**
         * MapMessage中状态的key
         */
        public final String KEY_STATE = "state";
    }

    public interface VALUE {
        /**
         * 操作成功
         */
        public final int RESULT_SUCCESS = 0;
        /**
         * 操作失败
         */
        public final int RESULT_FAILURE = 1;
    }
}