import org.apache.commons.collections.MapUtils;

/**
 * @author: LiaoMingtao
 * @date: 2021/6/23
 */
public class Ctest6 {

    public static void main(String[] args) {
        int scriptType = 2;
        boolean beatType = false;
        String shell = "";
        if (scriptType == 1) {
            if (beatType) {
                shell = "service metricbeat start";
            } else {
                shell = "service execbeat start";
            }
        } else {
            shell = "@echo off\n" +
                    "echo Get Administrator privileges\n" +
                    "cacls.exe \"%SystemDrive%\\System Volume Information\" >nul 2>nul\n" +
                    "if %errorlevel%==0 goto Admin\n" +
                    "if exist \"%temp%\\getadmin.vbs\" del /f /q \"%temp%\\getadmin.vbs\"\n" +
                    "echo Set RequestUAC = CreateObject^(\"Shell.Application\"^)>\"%temp%\\getadmin.vbs\"\n" +
                    "echo RequestUAC.ShellExecute \"%~s0\",\"\",\"\",\"runas\",1 >>\"%temp%\\getadmin.vbs\"\n" +
                    "echo WScript.Quit >>\"%temp%\\getadmin.vbs\"\n" +
                    "\"%temp%\\getadmin.vbs\" /f\n" +
                    "if exist \"%temp%\\getadmin.vbs\" del /f /q \"%temp%\\getadmin.vbs\"\n" +
                    "exit\n" +
                    "\n" +
                    ":Admin\n";
            if (beatType) {
                shell = shell + "net start metricbeat ";
            } else {
                if ("system".equals("system")){
                    shell = shell + "net start execbeat ";
                }else {
                    shell = "@echo off\n"+
                            "%1 mshta vbscript:CreateObject(\"WScript.Shell\").Run(\"%~s0 ::\",0,FALSE)(window.close)&&exit\n"+
                            "set diskpath=\"D:\\\"\n"+
                            "rem 如果D盘存在 \n"+
                            "if exist %diskpath% ( \n"+
                            "d: \n" +
                            "cd  D:\\gse_ext\\execbeat \n"+
                            "execbeat.exe \n"+
                            ") else ( \n"+
                            "set cu_time=[%date:~0,10% %time:~0,8%] \n"+
                            "for /F \"skip=3 tokens=2\" %%i in ('tasklist /v /FI \"IMAGENAME eq cmd.exe\" /FI \"STATUS eq Unknown\"') do ( \n"+
                            "set pid=[PID:%%i] \n"+
                            "goto:break \n"+
                            ") \n"+
                            ":break \n" +
                            "rem 不存在，写入日志 \n" +
                            "echo %cu_time% %pid% Error,D-disk not Exists... > C:\\gse\\logs\\ExecbeatError.txt )";
                }
            }
        }
        System.out.println(shell);
    }
}
