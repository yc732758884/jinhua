package com.hzwc.intelligent.lock.model.bean;

import java.util.List;

/**
 * Created by mayn on 2018/7/23.
 */

public class PostListBean {

    /**
     * msg : success
     * code : 0
     * posts : [{"postId":1,"postName":"施工人员"},{"postId":2,"postName":"安全员"},{"postId":3,"postName":"负责人"},{"postId":5,"postName":"坑死你单位"},{"postId":6,"postName":"Java工程师"},{"postId":7,"postName":"单位1"},{"postId":41,"postName":"12"},{"postId":42,"postName":"测试超"},{"postId":43,"postName":"施工人员111"},{"postId":44,"postName":"岗位1"},{"postId":45,"postName":"dfhgfdhg"},{"postId":46,"postName":"qqq"},{"postId":47,"postName":"7658876"},{"postId":48,"postName":"98709808"},{"postId":49,"postName":"软件研发工程师"},{"postId":50,"postName":"12111"},{"postId":51,"postName":"1242342345"},{"postId":52,"postName":"214"},{"postId":53,"postName":"23432"},{"postId":54,"postName":"333"},{"postId":55,"postName":"xsxx"},{"postId":56,"postName":"dddd"},{"postId":57,"postName":"3333"},{"postId":58,"postName":"rtewtewt"},{"postId":59,"postName":"121112"},{"postId":60,"postName":"121110"},{"postId":61,"postName":"121111"},{"postId":62,"postName":"hdfhdfh"},{"postId":63,"postName":"12111dfg"},{"postId":64,"postName":"12111dfgdfgdfg"},{"postId":65,"postName":"33"},{"postId":66,"postName":"1"},{"postId":67,"postName":"safdsaffsa"},{"postId":68,"postName":"2134214213"},{"postId":69,"postName":"121342134"},{"postId":70,"postName":"12111dfgqqqq"},{"postId":71,"postName":"position"},{"postId":72,"postName":"1111"},{"postId":73,"postName":"3334"},{"postId":74,"postName":"sadasd"},{"postId":75,"postName":"323"},{"postId":76,"postName":"sad"},{"postId":77,"postName":"de"},{"postId":78,"postName":"3"},{"postId":79,"postName":"岗位2"},{"postId":80,"postName":"WEB"},{"postId":81,"postName":"3123"},{"postId":82,"postName":"180226005"},{"postId":83,"postName":""}]
     */

    private String msg;
    private int code;
    private List<PostsBean> posts;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<PostsBean> getPosts() {
        return posts;
    }

    public void setPosts(List<PostsBean> posts) {
        this.posts = posts;
    }

    public static class PostsBean {
        /**
         * postId : 1
         * postName : 施工人员
         */

        private int postId;
        private String postName;

        public int getPostId() {
            return postId;
        }

        public void setPostId(int postId) {
            this.postId = postId;
        }

        public String getPostName() {
            return postName;
        }

        public void setPostName(String postName) {
            this.postName = postName;
        }
    }
}
