import com.carpcap.utils.excel.CEasyExcelUtil;
import com.carpcap.utils.excel.CExcelTypeEnum;
import com.carpcap.utils.excel.CExcelUtil;
import org.junit.Test;

import java.io.*;

/**
 * @author CarpCap
 * @since 2025/12/19 16:22
 */
public class TestEx {

    @Test
    public void testExcel() throws IOException {

        InputStream is = new FileInputStream("C:\\Users\\carpc\\Desktop\\qdq.xlsx");
        CExcelUtil.parseExcelIterator(CExcelTypeEnum.XLSX, is, 0, 1, 0, i -> {
            System.out.print(i.next()+" ");
            System.out.print(i.next());
            System.out.println();
        });


        InputStream iss = new FileInputStream("C:\\Users\\carpc\\Desktop\\qqq.xls");
        CEasyExcelUtil.parseExcel(iss, 0, 1, map -> {
            System.out.print(map.get(0)+" ");
            System.out.print(map.get(1));
            System.out.println();
        });




    }
}
