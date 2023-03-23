package cn.joker.ncode.datastruct.leetCode;

/**
 * 10 : 正则匹配
 */
public class SimpleRexMatch {

    /**
     * @param s 目标字符串
     * @param p 正则表达式
     * @return
     */
    public boolean isMatch(String s, String p) {

        //退出条件
        if (p.equals(".*")) {
            return true;
        }
        if(s.equals(p)){
            return true;
        }
        if(p.indexOf("*") < 0 ){
            if(p.equals("")){
                return true;
            }
            return false;
        }else{
            char c = p.charAt(p.indexOf("*")-1);
            return c=='.' || (s.indexOf(c) > -1 && isMatch(s, p.substring(p.indexOf("*")+1)));
        }
    }

    public static void main(String[] args) {
        String s=  "thisagooday";
        String p= "ag*o*";
        System.out.println(new SimpleRexMatch().isMatch(s,p));
    }

}
