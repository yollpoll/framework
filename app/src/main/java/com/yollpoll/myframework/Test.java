package com.yollpoll.myframework;

/**
 * Created by spq on 2021/3/12
 */
public class Test {
    static String content="%20";
    public static void main(String[] args) {
        char[] array=new char[100];
        array[0]='w';
        array[1]='e';
        array[2]=' ';
        array[3]='a';
        array[4]='r';
        array[4]='e';
        array[5]=' ';
        array[6]='w';
        array[7]='o';
        array[8]='r';
        array[9]='l';
        array[10]='d';

        try {
            System.out.println(new String(changeUrl(array)));
        }catch (Exception e){
            System.out.println("error");
        }
    }
    public static char[] changeUrl(char[] url) throws Exception{
       int i=0;

       int spaceCount=0;//空格数量
       while (url[i]!='\0'){
           if(url[i]==' '){
               spaceCount++;
           }
           i++;
       }

       int len=i+(spaceCount)*2;
       if(len>url.length){
           throw new Exception();
       }

       int right=len-1;
       for (int j=i-1;j>=0;j--){
           if(url[j]==' '){
               url[right]='0';
               right--;
               url[right]='2';
               right--;
               url[right]='%';
               right--;
           }else {
               url[right]=url[j];
               right--;
           }
       }
       return url;
    }

}
