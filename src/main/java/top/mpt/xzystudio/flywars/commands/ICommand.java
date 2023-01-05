package top.mpt.xzystudio.flywars.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import top.mpt.xzystudio.flywars.utils.ChatUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class ICommand {

    /**
     * 子指令名
     */
    private String cmdName;

    /**
     * 子指令参数
     */
    private String params;
    private List<String> listParams = new ArrayList<>();

    /**
     * 子指令描述
     */
    private String info;

    public ICommand(String cmdName) {
        this.cmdName = cmdName;
    }

    public ICommand(String cmdName, String params, String usage) {
        this.cmdName = cmdName;
        this.info = usage;
        this.params = params;
    }

    public String getCmdName() {
        return cmdName;
    }
    public String getParams() {
        return params;
    }
    public List<String> getListParams() {
        return this.listParams;
    }
    public void setListParams(List<String> params) {
        this.listParams = params;
    }


    public String showUsage() {
        return ChatUtils.translateColor("/fw &b%s %s &f -- &a%s", cmdName, params, info);
    }
    /**
     * 指令内容
     * @param sender    发送者
     * @param args      参数
     * @return          是否执行成功
     */
    public abstract boolean onCommand(CommandSender sender, String[] args);

    /**
     * 指令权限
     * @return        权限
     */
    public abstract String permission();
}