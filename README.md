# FlyWars 飞行战争

![](https://img.shields.io/badge/Spigot%2FPaper%201.13.x+-E34F26?logo=minecraft&logoColor=white)
![](https://img.shields.io/github/actions/workflow/status/MinecraftProgrammingTeam/FlyWars/maven.yml?branch=main)
![](https://img.shields.io/github/license/MinecraftProgrammingTeam/FlyWars)
![](https://img.shields.io/badge/made%20in-MPT-important)

## 来源
- 帖子：[[插件][1.16.x]FlyWars](https://minept.top/p.php?id=2)
- 原描述：一个类似于空岛战争的小游戏插件，有admincommand，具体是这样的:玩家进入服务器后传送到等待大厅，如果进入的玩家足够多了就开始游戏，这个时候系统分队，两人一队，其中一个人穿翅翘和烟花，但是没有武器。另一个人没有翅翘和烟花，但是有弓和箭，并且坐在有翅翘的人的背上。胜利规则是存活只有一个队伍时游戏自动结束。失败规则是当开始游戏后一个队伍中只要有一个人死亡或者退出，那么另一个人也失去资格游戏，直接kill，死亡或者重进或者前来观战的玩家都是旁观者模式。我说的admincommand是指设置游戏区域和大厅的指令。还有游戏开始前所有玩家都没有任何物品且是冒险模式，也不能有任何伤害和被伤害，游戏结束后清除所有玩家的所有物品且传送回大厅，游戏重启。

## 环境
- 构建环境：`Amazon Corretto 1.8`
- 测试环境：`Spigot 1.16.5`
- 支持版本：`1.13.x+`

## 部署教程
- 下载[最新的Release](https://github.com/MinecraftProgrammingTeam/FlyWars/releases)，将插件放到您服务器上
- 配置文件位于`plugin/FlyWars/config.yml`中，注释非常全面，通俗易懂
- 指令：
  请注意，前面带`*`的是测试时使用的指令
  - `clearteam` 清除当前的所有Team团队
  - `eject` 将当前玩家身上的所有乘客弹出（可能与`EntityDismountEvent`事件冲突）
  - `start` 开始游戏
  - \* `ride` 骑某个人
  - \* `test` 测试

## 贡献者
- 主要逻辑：[WindLeaf_qwq](https://github.com/WindLeaf233)、[xzyStudio](https://github.com/Gingmzmzx)、[X_huihui](https://github.com/xiaohuihui1022)
- 注释：[X_huihui](https://github.com/xiaohuihui1022)

## 其他
本仓库使用 `Apache License v2.0` 作为开源协议，附加条款：
- 不得商用
- 二次开发或者使用时，必须保留原作者的版权声明  
  
请您保护原作者版权，感谢配合！
