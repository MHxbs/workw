

public class user {
    private String username;//用户名
    private String password;//密码

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


   /* public static void main(String[] args){
        //测试数据库链接是否成功
        DBConnction dbConnction=new DBConnction();
        Connection con=dbConnction.getConnction();
        if(con!=null){
            System.out.println("ok");
        }
        //测试string转换为timeStamp
        Timestamp ts = Timestamp.valueOf("2012-1-14 08:11:00");
        System.out.println(ts);
    }*/


}
