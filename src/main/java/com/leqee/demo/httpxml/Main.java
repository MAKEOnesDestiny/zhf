package com.leqee.demo.httpxml;

import org.jibx.binding.BindingGenerator;
import org.jibx.binding.Compile;

public class Main {

    public static void main(String[] args) {
//        genBindFiles();
        compile();
    }

    public static void compile(){
        String[] args = new String[2];
        args[0] = "-v";
        args[1] = "./src/main/java/com/leqee/demo/httpxml/binding.xml";
        Compile.main(args);
    }

    public static void genBindFiles() {
        String[] args = new String[4];
//        args[0] = "-s";
        args[0] = "com.leqee.demo.httpxml.Order";
        args[1] = "com.leqee.demo.httpxml.Customer";
        args[2] = "com.leqee.demo.httpxml.Address";
        args[3] = "com.leqee.demo.httpxml.Shipping";
//        args[2] = "-b";
//        args[3] = "binding.xml";
//        args[4] = "-v";

        // 如果目录已经存在，就删除目录。可选
//        args[5] = "-w";

        // 指定输出路径。默认路径 .（当前目录,即根目录）。可选
//        args[6] = "-t";
//        args[7] = "./src/main/java/com/leqee/demo/httpxml/order";

        // 告诉 BindGen 使用下面的类作为 root 生成 binding 和 schema。必须
//        args[5] = "com.leqee.demo.httpxml.Order";
        BindingGenerator.main(args);
    }


}
