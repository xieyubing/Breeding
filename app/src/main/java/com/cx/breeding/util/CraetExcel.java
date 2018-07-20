package com.cx.breeding.util;

import android.os.Environment;
import android.util.Log;

import com.cx.breeding.bean.Animal;
import com.cx.breeding.bean.Category;
import com.cx.breeding.bean.User;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * Created by asus on 2017/11/9.
 */
public class CraetExcel {

    private List<Category> Clists;
    private List<User> Ulists;
    private List<Animal> Alists;
    private Label label;
    private WritableSheet sheet;
    private WritableWorkbook book;
    private String[] Utitle = {"用户名", "密码", "性别", "年龄", "头像地址", "日期"};
    private String[] Ctitle = {"类别名称", "图片地址"};
    private String[] Atitle = {"动物名称", "图片地址", "日期", "类别", "质量"};
    private File file;
    public CraetExcel() {
        Clists = DataSupport.findAll(Category.class);
        Ulists = DataSupport.findAll(User.class);
        Alists = DataSupport.findAll(Animal.class);
    }

    public boolean craetExcel() {
        try {
            File sdcardDir = Environment.getExternalStorageDirectory();
            String path = sdcardDir.getPath() + "/Breeding/Excel/";
            String name = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
            // 打开文件
            file = new File(path + name + ".xls");
            if (!file.exists()) {
                file.createNewFile();
            }
            book = Workbook.createWorkbook(file);
            AdddTitl();
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("导入数据异常：",e.toString());
            return false;
        }
        return true;
    }

    public void AdddTitl(){
        try {
            sheet = book.createSheet("用户工作表", 0);
            for (int i = 0; i < Utitle.length; i++) {
                label = new Label(i, 0, Utitle[i]);
                sheet.addCell(label);
            }
            for (int i=0;i<Ulists.size();i++){
                sheet.addCell(new Label(0,i+1,Ulists.get(i).getUsername()));
                sheet.addCell(new Label(1,i+1,Ulists.get(i).getPassword()));
                sheet.addCell(new Label(2,i+1,String.valueOf(Ulists.get(i).getSex())));
                sheet.addCell(new Label(3,i+1,String.valueOf(Ulists.get(i).getAge())));
                sheet.addCell(new Label(4,i+1,Ulists.get(i).getImgurl()));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String newdate = sdf.format(Ulists.get(i).getDate());
                sheet.addCell(new Label(5,i+1,newdate));

            }
            sheet = book.createSheet("类别工作表", 1);
            for (int i = 0; i < Ctitle.length; i++) {
                Label label = new Label(i, 0, Ctitle[i]);
                sheet.addCell(label);
            }
            for (int i=0;i<Clists.size();i++){
                sheet.addCell(new Label(0,i+1,Clists.get(i).getName()));
                sheet.addCell(new Label(1,i+1,Clists.get(i).getPic()));
            }
            sheet = book.createSheet("分类工作表", 2);
            for (int i = 0; i < Atitle.length; i++) {
                Label label = new Label(i, 0, Atitle[i]);
                sheet.addCell(label);
            }
            for (int i=0;i<Alists.size();i++){
                sheet.addCell(new Label(0,i+1,Alists.get(i).getName()));
                sheet.addCell(new Label(1,i+1,Alists.get(i).getImgurl()));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String newdate = sdf.format(Alists.get(i).getDate());
                sheet.addCell(new Label(2,i+1,newdate));
                sheet.addCell(new Label(3,i+1,String.valueOf(Alists.get(i).getCategory())));
                sheet.addCell(new Label(3,i+1,Alists.get(i).getQuality()));
            }
            book.write();
            book.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("导入数据异常：",e.toString());
        }
    }

}
