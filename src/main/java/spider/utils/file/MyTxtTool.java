package spider.utils.file;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * txt文本处理程序
 * <p>
 * Created by lishilin on 2017/2/16.
 */
public class MyTxtTool {

    private static File file = null;
    private static InputStreamReader inputStreamReader = null;
    private static BufferedReader bufferedReader = null;
    private static FileInputStream inputStream = null;

    public static void init(String path) throws IOException {

        try {
            file = new File(path); // 要读取以上路径的input。txt文件
            inputStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            inputStreamReader.close();
            inputStreamReader.close();
        }
    }

    /**
     * 获取文本中所有行的内容
     *
     * @return List<String>
     */
    public static List<String> readAllLine() {
        List<String> result = new ArrayList<>();
        try {
            String line = "";
            line = bufferedReader.readLine();
            while (line != null) {
                line = bufferedReader.readLine(); // 一次读入一行数据
                result.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
//        MyTxtTool.init("");
//        List<String> list=MyTxtTool.readAllLine();
//        System.out.println(list);
        String sdate = "2017.01.10 14:26:36";
        Date date = new Date(sdate);

    }

}
