package soyouarehere.imwork.speed;

public class Test {



    public static void main(String [] args){

        StringBuffer buffer = new StringBuffer("kxd");

        buffer.append("蛮王");
        buffer.append("1234567890");
        buffer = new StringBuffer(buffer.substring(0,buffer.length()-7));
        System.out.print(buffer.toString());

    }

}
