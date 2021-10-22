import org.apache.commons.collections.MapUtils;

/**
 * @author: LiaoMingtao
 * @date: 2021/6/23
 */
public class Ctest5 {

    public static void main(String[] args) {

        int scriptType = 2;
        boolean beatType = false;
        String shell = "";
        if (scriptType == 1) {
            if (beatType) {
                shell = shell + "service metricbeat stop";
            } else {
                shell = shell + "service execbeat stop";
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
                shell = shell + "net stop metricbeat ";
            } else {
                if ("system".equals("system")){
                    shell = shell + "net stop execbeat ";
                }else {
                    shell = "@echo off\n" +
                            "sc stop exexbeat\n" +
                            "taskkill /f /im execbeat.exe";
                }

            }
        }

        System.out.println(shell);

    }
}
