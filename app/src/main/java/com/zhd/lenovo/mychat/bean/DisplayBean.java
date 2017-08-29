package com.zhd.lenovo.mychat.bean;

import java.util.List;

/**
 * Created by lenovo on 2017/8/7.
 */

public class DisplayBean {


    /**
     * result_message :
     * result_code : 200
     * list : [{"streamKey":"m15jQIaW","uid":"0","livepic":"http://pic.qiantucdn.com/58pic/17/96/08/83I58PICjwm_1024.jpg","id":134,"time":1502115126481,"type":1,"content":"randomString","publishUrl":"rtmp://pili-publish.2dyt.com/1503d/m15jQIaW?e=1502118726&token=tYBGEzG7NE_D23EScw43ZTxynVkyt1IpHig5WHRY:B1wRfRiDKPmf-hD8eGE_sn7kR_8=","playUrl":"rtmp://pili-live-rtmp.2dyt.com/1503d/m15jQIaW"},{"streamKey":"64CN75G0","uid":"1","livepic":"http://baijunjian.keliren.cn/tuku/a/20160504/5729eeafc1f5e.jpg_600.jpg","id":133,"time":1502115118323,"type":1,"content":"randomString","publishUrl":"rtmp://pili-publish.2dyt.com/1503d/64CN75G0?e=1502118718&token=tYBGEzG7NE_D23EScw43ZTxynVkyt1IpHig5WHRY:YVolnl9fxIhjYtuGYPvAd_uaXnU=","playUrl":"rtmp://pili-live-rtmp.2dyt.com/1503d/64CN75G0"},{"streamKey":"ma3A3eI7","uid":"3","livepic":"http://scimg.jb51.net/allimg/160512/14-16051210002TX.jpg","id":132,"time":1502115113802,"type":1,"content":"randomString","publishUrl":"rtmp://pili-publish.2dyt.com/1503d/ma3A3eI7?e=1502118713&token=tYBGEzG7NE_D23EScw43ZTxynVkyt1IpHig5WHRY:RfCJCvEPOqv6H_1AhkB_jaNwDKs=","playUrl":"rtmp://pili-live-rtmp.2dyt.com/1503d/ma3A3eI7"},{"streamKey":"q340tA35","uid":"5","livepic":"http://img06.tooopen.com/images/20160823/tooopen_sy_176456148363.jpg","id":131,"time":1502115100433,"type":1,"content":"randomString","publishUrl":"rtmp://pili-publish.2dyt.com/1503d/q340tA35?e=1502118700&token=tYBGEzG7NE_D23EScw43ZTxynVkyt1IpHig5WHRY:K9Z9DE19IiBQ_BnbJwfg1ws48_U=","playUrl":"rtmp://pili-live-rtmp.2dyt.com/1503d/q340tA35"},{"streamKey":"IU364vV2","uid":"4","livepic":"http://pic.58pic.com/58pic/12/86/21/20758PICfV6.jpg","id":130,"time":1502114726970,"type":1,"content":"randomString","publishUrl":"rtmp://pili-publish.2dyt.com/1503d/IU364vV2?e=1502118326&token=tYBGEzG7NE_D23EScw43ZTxynVkyt1IpHig5WHRY:oSRstq5iRrJNXLnvy2iloxJTQi0=","playUrl":"rtmp://pili-live-rtmp.2dyt.com/1503d/IU364vV2"},{"streamKey":"6931TTL9","uid":"5","livepic":"http://img06.tooopen.com/images/20160823/tooopen_sy_176456148363.jpg","id":129,"time":1502114679074,"type":1,"content":"randomString","publishUrl":"rtmp://pili-publish.2dyt.com/1503d/6931TTL9?e=1502118279&token=tYBGEzG7NE_D23EScw43ZTxynVkyt1IpHig5WHRY:kRl8OrdZLOpq8MZ19cH2DvF4ugY=","playUrl":"rtmp://pili-live-rtmp.2dyt.com/1503d/6931TTL9"},{"streamKey":"4X9W0Mj2","uid":"5","livepic":"http://img06.tooopen.com/images/20160823/tooopen_sy_176456148363.jpg","id":128,"time":1502114666775,"type":1,"content":"randomString","publishUrl":"rtmp://pili-publish.2dyt.com/1503d/4X9W0Mj2?e=1502118266&token=tYBGEzG7NE_D23EScw43ZTxynVkyt1IpHig5WHRY:beRgpCYg4m3P7JNTKYRtYlZagVg=","playUrl":"rtmp://pili-live-rtmp.2dyt.com/1503d/4X9W0Mj2"},{"streamKey":"U2xO9VOp","uid":"1","livepic":"http://baijunjian.keliren.cn/tuku/a/20160504/5729eeafc1f5e.jpg_600.jpg","id":127,"time":1502114639982,"type":1,"content":"randomString","publishUrl":"rtmp://pili-publish.2dyt.com/1503d/U2xO9VOp?e=1502118239&token=tYBGEzG7NE_D23EScw43ZTxynVkyt1IpHig5WHRY:8HNgGfrlzlQJl86TdpSBrL5cuKY=","playUrl":"rtmp://pili-live-rtmp.2dyt.com/1503d/U2xO9VOp"},{"streamKey":"hYXd4Dc1","uid":"3","livepic":"http://scimg.jb51.net/allimg/160512/14-16051210002TX.jpg","id":126,"time":1502114510703,"type":1,"content":"randomString","publishUrl":"rtmp://pili-publish.2dyt.com/1503d/hYXd4Dc1?e=1502118110&token=tYBGEzG7NE_D23EScw43ZTxynVkyt1IpHig5WHRY:FIph0lrx55B6ZAu9b6VqrcBPKkQ=","playUrl":"rtmp://pili-live-rtmp.2dyt.com/1503d/hYXd4Dc1"},{"streamKey":"bcy88f67","uid":"0","livepic":"http://pic.qiantucdn.com/58pic/17/96/08/83I58PICjwm_1024.jpg","id":125,"time":1502114114963,"type":1,"content":"randomString","publishUrl":"rtmp://pili-publish.2dyt.com/1503d/bcy88f67?e=1502117714&token=tYBGEzG7NE_D23EScw43ZTxynVkyt1IpHig5WHRY:5v_gL4Iy5CaRXiOTMbQfrN9jAbQ=","playUrl":"rtmp://pili-live-rtmp.2dyt.com/1503d/bcy88f67"}]
     */

    private String result_message;
    private int result_code;
    private List<ListBean> list;

    public String getResult_message() {
        return result_message;
    }

    public void setResult_message(String result_message) {
        this.result_message = result_message;
    }

    public int getResult_code() {
        return result_code;
    }

    public void setResult_code(int result_code) {
        this.result_code = result_code;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * streamKey : m15jQIaW
         * uid : 0
         * livepic : http://pic.qiantucdn.com/58pic/17/96/08/83I58PICjwm_1024.jpg
         * id : 134
         * time : 1502115126481
         * type : 1
         * content : randomString
         * publishUrl : rtmp://pili-publish.2dyt.com/1503d/m15jQIaW?e=1502118726&token=tYBGEzG7NE_D23EScw43ZTxynVkyt1IpHig5WHRY:B1wRfRiDKPmf-hD8eGE_sn7kR_8=
         * playUrl : rtmp://pili-live-rtmp.2dyt.com/1503d/m15jQIaW
         */

        private String streamKey;
        private String uid;
        private String livepic;
        private int id;
        private long time;
        private int type;
        private String content;
        private String publishUrl;
        private String playUrl;

        public String getStreamKey() {
            return streamKey;
        }

        public void setStreamKey(String streamKey) {
            this.streamKey = streamKey;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getLivepic() {
            return livepic;
        }

        public void setLivepic(String livepic) {
            this.livepic = livepic;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPublishUrl() {
            return publishUrl;
        }

        public void setPublishUrl(String publishUrl) {
            this.publishUrl = publishUrl;
        }

        public String getPlayUrl() {
            return playUrl;
        }

        public void setPlayUrl(String playUrl) {
            this.playUrl = playUrl;
        }
    }
}
